package hr.fipu.k1_z2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetaljiKnjigeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalji_knjige);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String naslov = getIntent().getStringExtra("naslov");
        String autor = getIntent().getStringExtra("autor");
        int godinaIzdavanja = getIntent().getIntExtra("godinaIzdavanja", 0);
        String opis = getIntent().getStringExtra("opis");

        TextView naslovTextView = findViewById(R.id.naslov);
        naslovTextView.setText(naslov);

        TextView autorTextView = findViewById(R.id.textView6);
        autorTextView.setText(autor);

        TextView godinaTextView = findViewById(R.id.textView7);
        godinaTextView.setText(String.valueOf(godinaIzdavanja));

        TextView opisTextView = findViewById(R.id.textView9);
        opisTextView.setText(opis);

        Button povratakButton = findViewById(R.id.button);
        povratakButton.setOnClickListener(view -> {
            finish();
        });
    }
}