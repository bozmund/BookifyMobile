package hr.fipu.bookifymobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView bookOfTheDayTitle;
    private TextView bookOfTheDayAuthor;
    private TextView bookOfTheDaySummary;
    private BookDto currentBookOfTheDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bookOfTheDayTitle = view.findViewById(R.id.book_of_the_day_title);
        bookOfTheDayAuthor = view.findViewById(R.id.book_of_the_day_author);
        bookOfTheDaySummary = view.findViewById(R.id.book_of_the_day_summary);
        Button shuffleBookButton = view.findViewById(R.id.shuffle_book_button);
        Button addToReadBooksButton = view.findViewById(R.id.add_to_read_books_button);
        Button addToBooksToReadButton = view.findViewById(R.id.add_to_books_to_read_button);

        shuffleBookButton.setOnClickListener(v -> fetchBookOfTheDay());

        addToReadBooksButton.setOnClickListener(v -> {
            if (currentBookOfTheDay != null) {
                BookShelfStore.addToRead(getContext(), currentBookOfTheDay);
                Toast.makeText(getContext(), "Added to Read Books: " + currentBookOfTheDay.originalTitle, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No book to add", Toast.LENGTH_SHORT).show();
            }
        });

        addToBooksToReadButton.setOnClickListener(v -> {
            if (currentBookOfTheDay != null) {
                BookShelfStore.addToToRead(getContext(), currentBookOfTheDay);
                Toast.makeText(getContext(), "Added to Books to Read: " + currentBookOfTheDay.originalTitle, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No book to add", Toast.LENGTH_SHORT).show();
            }
        });

        fetchBookOfTheDay();

        return view;
    }

    private void fetchBookOfTheDay() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // Fetch a random book ID
        List<Integer> bookIds = Collections.singletonList(new Random().nextInt(9999) + 1); // Assuming book IDs are between 1 and 9999

        apiService.getBooksByIds(bookIds).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<BookDto>> call, @NonNull Response<List<BookDto>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    currentBookOfTheDay = response.body().get(0);
                    bookOfTheDayTitle.setText(currentBookOfTheDay.originalTitle);
                    bookOfTheDayAuthor.setText(currentBookOfTheDay.authors.split(",")[0]);

                    // Display loading text for summary
                    bookOfTheDaySummary.setText("Loading summary...");

                    // Fetch and display the book summary
                    apiService.interpretBookWithAI(currentBookOfTheDay.id).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> summaryResponse) {
                            if (summaryResponse.isSuccessful() && summaryResponse.body() != null) {
                                bookOfTheDaySummary.setText(summaryResponse.body());
                            } else {
                                Log.e("HomeFragment", "Failed to fetch book summary: " + summaryResponse.message());
                                bookOfTheDaySummary.setText("Summary not available");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Log.e("HomeFragment", "Error fetching book summary", t);
                            bookOfTheDaySummary.setText("Summary not available");
                        }
                    });

                } else {
                    Log.e("HomeFragment", "Failed to fetch book of the day: " + response.message());
                    bookOfTheDayTitle.setText("Failed to load book of the day");
                    bookOfTheDayAuthor.setText("");
                    bookOfTheDaySummary.setText(""); // Clear summary on failure
                    currentBookOfTheDay = null; // Clear current book on failure
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BookDto>> call, @NonNull Throwable t) {
                Log.e("HomeFragment", "Error fetching book of the day", t);
                bookOfTheDayTitle.setText("Error loading book of the day");
                bookOfTheDayAuthor.setText("");
                bookOfTheDaySummary.setText(""); // Clear summary on failure
                currentBookOfTheDay = null; // Clear current book on failure
            }
        });
    }
}