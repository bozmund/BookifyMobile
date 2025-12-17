package hr.fipu.k1_z2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Knjiznica knjiznica = Knjiznica.getInstance();
    private TextView listaKnjiga = null;
    private EditText unesiNaslov = null;

    private ActivityResultLauncher<Intent> addKnjigaLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            String naslov = data.getStringExtra("naslov");
                            String autor = data.getStringExtra("autor");
                            int godinaIzdavanja = data.getIntExtra("godinaIzdavanja", 0);
                            String opis = data.getStringExtra("opis");

                            knjiznica.dodajKnjigu(new Knjiga(naslov, autor, godinaIzdavanja, opis));
                            UpdateUI();
                            Toast.makeText(this, "Knjiga uspješno dodana", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    private void UpdateUI() {
        String tekst = "";
        for (Knjiga knjiga : knjiznica.dohvatiSveKnjige()) {
            tekst += knjiga.getNaslov() + " ▪ " + knjiga.getAutor() + "\n";
        }
        listaKnjiga.setText(tekst);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        knjiznica.dodajKnjigu(new Knjiga("Hobit", "J.R.R. Tolkien", 1937,
                "Fantastični roman koji prati avanture hobita Bilba Bagginsa dok putuje Međuzemljem " +
                        "kako bi povratio blago koje je zmaj Smaug oteo patuljcima. Knjiga je uvod u " +
                        "bogati svijet 'Gospodara prstenova' i predstavlja čitateljima mnoge rase i mjesta " +
                        "koja će kasnije postati ključna."));
        knjiznica.dodajKnjigu(new Knjiga("1984", "George Orwell", 1949,
                "Distopijski roman koji opisuje život u totalitarnoj državi Oceaniji, gdje Veliki Brat " +
                        "sve nadzire, a misao je zločin. Glavni lik, Winston Smith, radi za Ministarstvo istine " +
                        "i potajno se buni protiv režima, što ga dovodi u veliku opasnost."));
        knjiznica.dodajKnjigu(new Knjiga("Ponos i predrasude", "Jane Austen", 1813,
                "Ljubavni roman koji istražuje složene društvene odnose i pitanja braka, morala i odgoja " +
                        "u Engleskoj početkom 19. stoljeća kroz priču o Elizabeth Bennet i gospodinu Darcyju. " +
                        "Kroz duhovite dijaloge i složene likove, Austen kritizira društvene konvencije svog vremena."));
        knjiznica.dodajKnjigu(new Knjiga("Ubiti pticu rugalicu", "Harper Lee", 1960,
                "Roman smješten na američkom Jugu tijekom Velike depresije, koji se bavi ozbiljnim temama " +
                        "rasne nepravde i gubitka nevinosti, promatranim očima djevojčice Scout Finch. Njezin otac, " +
                        "Atticus Finch, odvjetnik je koji brani crnca lažno optuženog za silovanje bjelkinje, " +
                        "što izaziva bijes u njihovoj zajednici."));
        knjiznica.dodajKnjigu(new Knjiga("Alkemičar", "Paulo Coelho", 1988,
                "Filozofski roman koji prati mladog andaluzijskog pastira Santiaga u njegovoj potrazi " +
                        "za blagom skrivenim u egipatskim piramidama. Putovanje ga uči o važnosti slušanja " +
                        "vlastitog srca, prepoznavanju znakova i ostvarivanju svoje 'Osobne legende'."));

        listaKnjiga = findViewById(R.id.txtLista);
        unesiNaslov = findViewById(R.id.editTextText);
        UpdateUI();

        Button btnDodaj = findViewById(R.id.btnDodaj1);
        Button btnUkloni = findViewById(R.id.btnUkloni);
        Button btnDetalji = findViewById(R.id.btnDetalji);

        btnDodaj.setOnClickListener(view -> {
            Intent intent = new Intent(this, DodajKnjiguActivity.class);
            addKnjigaLauncher.launch(intent);
        });

        btnUkloni.setOnClickListener(view -> {
            String naslov = unesiNaslov.getText().toString();
            boolean uklonjena = knjiznica.ukloniKnjigu(naslov);
            if (uklonjena) {
                UpdateUI();
                Toast.makeText(this, "Knjiga uspješno uklonjena", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Knjiga nije pronađena", Toast.LENGTH_SHORT).show();
            }
        });

        btnDetalji.setOnClickListener(view ->{
            String naslov = unesiNaslov.getText().toString();
            Knjiga knjiga = knjiznica.nadiKnjigu(naslov);
            if(knjiga == null) {
                Toast.makeText(this, "Knjiga nije pronađena", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, DetaljiKnjigeActivity.class);
            intent.putExtra("naslov", knjiga.getNaslov());
            intent.putExtra("autor", knjiga.getAutor());
            intent.putExtra("godinaIzdavanja", knjiga.getGodinaIzdavanja());
            intent.putExtra("opis", knjiga.getOpis());
            startActivity(intent);
        });
    }
}