package hr.fipu.bookifymobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

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
    private Button shuffleBookButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bookOfTheDayTitle = view.findViewById(R.id.book_of_the_day_title);
        bookOfTheDayAuthor = view.findViewById(R.id.book_of_the_day_author);
        shuffleBookButton = view.findViewById(R.id.shuffle_book_button);

        shuffleBookButton.setOnClickListener(v -> fetchBookOfTheDay());

        fetchBookOfTheDay();

        return view;
    }

    private void fetchBookOfTheDay() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // Fetch a random book ID
        List<Integer> bookIds = Collections.singletonList(new Random().nextInt(9999) + 1); // Assuming book IDs are between 1 and 9999

        apiService.getBooksByIds(bookIds).enqueue(new Callback<List<BookDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<BookDto>> call, @NonNull Response<List<BookDto>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    BookDto bookOfTheDay = response.body().get(0);
                    bookOfTheDayTitle.setText(bookOfTheDay.originalTitle);
                    bookOfTheDayAuthor.setText(bookOfTheDay.authors.split(",")[0]);
                } else {
                    Log.e("HomeFragment", "Failed to fetch book of the day: " + response.message());
                    bookOfTheDayTitle.setText("Failed to load book of the day");
                    bookOfTheDayAuthor.setText("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BookDto>> call, @NonNull Throwable t) {
                Log.e("HomeFragment", "Error fetching book of the day", t);
                bookOfTheDayTitle.setText("Error loading book of the day");
                bookOfTheDayAuthor.setText("");
            }
        });
    }
}