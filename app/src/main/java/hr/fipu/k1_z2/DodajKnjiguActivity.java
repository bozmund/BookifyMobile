package hr.fipu.k1_z2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DodajKnjiguActivity extends AppCompatActivity {

    private Knjiznica knjiznica = Knjiznica.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dodaj_knjigu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnDodaj = findViewById(R.id.btnDodaj1);
        Button btnOdustani = findViewById(R.id.btnOdustani);

        EditText inputNaslov = findViewById(R.id.editTextText2);
        EditText inputAutor = findViewById(R.id.editTextText3);
        EditText inputGodinaIzdavanja = findViewById(R.id.editTextNumber);
        EditText inputOpis = findViewById(R.id.editTextText4);

        btnDodaj.setOnClickListener(view -> {
            String naslov = inputNaslov.getText().toString();
            String autor = inputAutor.getText().toString();
            int godinaIzdavanja = Integer.parseInt(inputGodinaIzdavanja.getText().toString());
            String opis = inputOpis.getText().toString();

            if(knjiznica.nadiKnjigu(naslov) != null) {
                Toast.makeText(this, "Knjiga već postoji", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("naslov", naslov);
                intent.putExtra("autor", autor);
                intent.putExtra("godinaIzdavanja", godinaIzdavanja);
                intent.putExtra("opis", opis);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

        btnOdustani.setOnClickListener(view ->{
            finish();
        });
    }
}