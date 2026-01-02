package hr.fipu.bookifymobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        TextView title = findViewById(R.id.tvTitle);
        TextView author = findViewById(R.id.tvAuthor);
        TextView description = findViewById(R.id.tvDescription);

        String bookTitle = getIntent().getStringExtra("title");
        String bookAuthor = getIntent().getStringExtra("author");
        String bookDescription = getIntent().getStringExtra("description");

        title.setText(bookTitle);
        author.setText(bookAuthor);
        description.setText(bookDescription);
    }
}