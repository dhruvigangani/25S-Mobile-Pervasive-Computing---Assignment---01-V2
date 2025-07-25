package ca.georgiancollege.assignment_01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {

    private EditText searchInput;
    private Button searchBtn;
    private RecyclerView movieRecycler;
    private MovieAdapter adapter;
    private List<Movie> movies = new ArrayList<>();
    private final String API_KEY = "5bfa8ddf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.searchEditText);
        searchBtn = findViewById(R.id.searchButton);
        movieRecycler = findViewById(R.id.recyclerView);
        movieRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter(movies, this);
        movieRecycler.setAdapter(adapter);

        searchBtn.setOnClickListener(view -> {
            String query = searchInput.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                new FetchMoviesTask().execute(query);
            } else {
                Toast.makeText(this, "Enter movie name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie_id", movie.getImdbID());
        startActivity(intent);
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... params) {
            List<Movie> result = new ArrayList<>();
            try {
                String query = params[0].replace(" ", "+");
                String urlString = "https://www.omdbapi.com/?apikey=" + API_KEY + "&s=" + query;
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject json = new JSONObject(response.toString());

                if (json.has("Search")) {
                    JSONArray searchArray = json.getJSONArray("Search");
                    for (int i = 0; i < searchArray.length(); i++) {
                        JSONObject item = searchArray.getJSONObject(i);

                        String title = item.getString("Title");
                        String year = item.getString("Year");
                        String imdbID = item.getString("imdbID");

                        String detailsUrl = "https://www.omdbapi.com/?apikey=" + API_KEY + "&i=" + imdbID + "&plot=short";
                        URL detailsURL = new URL(detailsUrl);
                        HttpURLConnection detailsConn = (HttpURLConnection) detailsURL.openConnection();
                        BufferedReader detailsReader = new BufferedReader(new InputStreamReader(detailsConn.getInputStream()));

                        StringBuilder detailsResponse = new StringBuilder();
                        String detailsLine;
                        while ((detailsLine = detailsReader.readLine()) != null) {
                            detailsResponse.append(detailsLine);
                        }
                        detailsReader.close();

                        JSONObject detailsJson = new JSONObject(detailsResponse.toString());

                        String director = detailsJson.optString("Director", "N/A");
                        String rating = detailsJson.optString("imdbRating", "N/A");

                        Movie movie = new Movie(title, year, imdbID, director, rating);
                        result.add(movie);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            movies.clear();
            if (result != null && !result.isEmpty()) {
                movies.addAll(result);
            } else {
                Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }
    }
}
