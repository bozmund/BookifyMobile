package hr.fipu.k1_z2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PretragaActivity extends AppCompatActivity {

    private EditText etSearchQuery;
    private Button btnSearch;
    private TextView tvApiResult;

    // ExecutorService za izvršavanje mrežnih operacija na pozadinskoj dretvi
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    // Handler za ažuriranje UI-a s glavne dretve
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pretraga);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Povezivanje UI elemenata s varijablama
        etSearchQuery = findViewById(R.id.etSearchQuery);
        btnSearch = findViewById(R.id.btnSearch);
        tvApiResult = findViewById(R.id.tvApiResult);

        // Postavljanje slušača događaja na gumb
        btnSearch.setOnClickListener(view -> {
            String query = etSearchQuery.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, "Molimo unesite pojam za pretragu.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Pokretanje mrežnog poziva
            izvrsiApiPoziv(query);
        });
    }

    private void izvrsiApiPoziv(String query) {
        // Prikaz poruke o učitavanju
        tvApiResult.setText("Učitavanje...");

        executor.execute(() -> {
            // Pozadinska operacija
            OkHttpClient client = new OkHttpClient();

            // !! OVDJE UNESITE SVOJ API URL !!
            // Primjer: korištenje javnog API-ja za testiranje
            String apiUrl = "https://api.agify.io/?name=" + query;
            // String apiUrl = "https://VAŠ_API_ENDPOINT?query=" + query;

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Neočekivani kod " + response);
                }

                ResponseBody responseBody = response.body();
                final String rezultat = (responseBody != null) ? responseBody.string() : "Prazan odgovor.";

                // Povratak na glavnu dretvu za ažuriranje UI-a
                handler.post(() -> {
                    Log.d("API_RESPONSE", rezultat);
                    tvApiResult.setText(rezultat);
                    Toast.makeText(PretragaActivity.this, "Podaci dohvaćeni", Toast.LENGTH_SHORT).show();
                });

            } catch (IOException e) {
                Log.e("API_ERROR", "Greška pri dohvaćanju podataka", e);
                // Ažuriranje UI-a u slučaju greške
                handler.post(() -> {
                    tvApiResult.setText("Greška pri dohvaćanju podataka.");
                    Toast.makeText(PretragaActivity.this, "Mrežna greška", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Obavezno ugasiti ExecutorService kako bi se oslobodili resursi
        executor.shutdown();
    }
}