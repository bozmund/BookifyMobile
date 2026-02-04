package hr.fipu.bookifymobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReadBooksFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_books, container, false);

        recyclerView = view.findViewById(R.id.recyclerRead);
        tvEmpty = view.findViewById(R.id.tvReadEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        if (getContext() == null) return;
        List<BookDto> books = BookShelfStore.getReadBooks(getContext());
        adapter.setBooks(books);

        boolean isEmpty = books == null || books.isEmpty();
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }
}