package hr.fipu.bookifymobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private EditText etSearchQuery;
    private Button btnSearch;
    private TextView tvApiResult;
    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearchQuery = view.findViewById(R.id.etSearchQuery);
        btnSearch = view.findViewById(R.id.btnSearch);
        tvApiResult = view.findViewById(R.id.tvApiResult);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String query = etSearchQuery.getText().toString().trim();
            //Dodao sam knjigu za testiranje
            BookDto testBook = new BookDto();
            testBook.id = 1;
            testBook.title = "Clean Code";
            testBook.authors = "Robert C. Martin";
            testBook.originalTitle = "Clean Code description";
            testBook.imageUrl = "";
            if (query.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a search term.", Toast.LENGTH_SHORT).show();
                return;
            }
            //executeApiCall(query);
            //Ovdje testiram kako se prikazuje knjiga
            List<BookDto> testBooks = new ArrayList<>();
            testBooks.add(testBook);
            tvApiResult.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.setBooks(testBooks);
        });

        return view;
    }

    private void executeApiCall(String query) {
        tvApiResult.setVisibility(View.VISIBLE);
        tvApiResult.setText("Loading...");
        recyclerView.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<BookDto>> call = apiService.getBookByName(query);

        call.enqueue(new Callback<List<BookDto>>() {
            @Override
            public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookDto> foundBooks = response.body();

                    if (foundBooks.isEmpty()) {
                        tvApiResult.setText("No results.");
                        tvApiResult.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tvApiResult.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.setBooks(foundBooks);
                        Toast.makeText(getContext(), "Found " + foundBooks.size() + " books", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tvApiResult.setText("Error: " + response.code());
                    tvApiResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BookDto>> call, Throwable t) {
                Log.e("API_ERROR", "Error fetching data", t);
                tvApiResult.setText("Error fetching data: " + t.getMessage());
                tvApiResult.setVisibility(View.VISIBLE);
            }
        });
    }
}
