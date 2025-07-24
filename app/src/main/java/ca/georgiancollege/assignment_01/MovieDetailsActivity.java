package ca.georgiancollege.assignment_01;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView titleText;
    private TextView directorText;
    private TextView ratingText;
    private TextView yearText;

    private TextView plotText;
    private ImageView posterImage;
    private Button backButton;
    private final String API_KEY = "5bfa8ddf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        titleText = findViewById(R.id.titleText);
        directorText = findViewById(R.id.directorText);
        ratingText = findViewById(R.id.ratingText);
        yearText = findViewById(R.id.yearText);
        posterImage = findViewById(R.id.posterImage);
        backButton = findViewById(R.id.backButton);
        plotText = findViewById(R.id.plotText);

        String movieId = getIntent().getStringExtra("movie_id");

        if (TextUtils.isEmpty(movieId)) {
            Toast.makeText(this, "Invalid Movie ID", Toast.LENGTH_SHORT).show();
            finish();
            return;

        }

        new GetMovieDetailsTask().execute(movieId);

        backButton.setOnClickListener(view -> finish());

    }

    private class GetMovieDetailsTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... id) {
            try {
                String apiURL = "https://www.omdbapi.com/?apikey=" + API_KEY + "&i=" + id[0] + "&plot=full";
                URL url = new URL(apiURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                return new JSONObject(result.toString());
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;

        }

    }
}
