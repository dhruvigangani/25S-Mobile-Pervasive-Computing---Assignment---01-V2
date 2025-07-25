package ca.georgiancollege.assignment_01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView titleText, directorText, yearText, ratingText, plotText;
    private ImageView posterImage;
    private Button backButton;

    private final String API_KEY = "5bfa8ddf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        titleText = findViewById(R.id.titleText);
        directorText = findViewById(R.id.directorText);
        yearText = findViewById(R.id.yearText);
        ratingText = findViewById(R.id.ratingText);
        plotText = findViewById(R.id.plotText);
        posterImage = findViewById(R.id.posterImage);
        backButton = findViewById(R.id.backButton);

        String movieId = getIntent().getStringExtra("movie_id");

        if (TextUtils.isEmpty(movieId)) {
            Toast.makeText(this, "Movie ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        new GetMovieDetailsTask().execute(movieId);

        backButton.setOnClickListener(v -> finish());
    }

    private class GetMovieDetailsTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... id) {
            try {
                String apiUrl = "https://www.omdbapi.com/?apikey=" + API_KEY + "&i=" + id[0] + "&plot=full";

                URL url = new URL(apiUrl);
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

        @Override
        protected void onPostExecute(JSONObject data) {
            if (data == null) {
                Toast.makeText(MovieDetailsActivity.this, "Couldn't load movie info", Toast.LENGTH_SHORT).show();
                return;
            }

            titleText.setText(data.optString("Title", "N/A"));
            directorText.setText("Director: " + data.optString("Director", "N/A"));
            yearText.setText("Year: " + data.optString("Year", "N/A"));
            ratingText.setText("IMDB Rating: " + data.optString("imdbRating", "N/A"));
            plotText.setText(data.optString("Plot", "N/A"));

            String posterLink = data.optString("Poster", "");
            if (!TextUtils.isEmpty(posterLink) && !posterLink.equals("N/A")) {
                new LoadImageTask(posterImage).execute(posterLink);
            }
        }
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... links) {
            try {
                InputStream input = new URL(links[0]).openStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                imageView.setImageBitmap(image);
            }
        }
    }
}
