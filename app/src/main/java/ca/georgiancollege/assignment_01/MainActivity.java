package ca.georgiancollege.assignment_01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

    private RecyclerView movieRecycler;
    private MovieAdapter adapter;
    private List<Movie> movies = new ArrayList<>();
    private final String API_KEY = "5bfa8ddf";

    private EditText searchInput;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup recycler view
        movieRecycler = findViewById(R.id.recyclerView);
        adapter = new MovieAdapter(movies, this);
        movieRecycler.setAdapter(adapter);

        searchInput = findViewById(R.id.searchEditText);
        searchBtn = findViewById(R.id.searchButton);


        //search button click listener
        searchBtn.setOnClickListener(view -> {
            String query = searchInput.getText().toString().trim();
            if(!TextUtils.isEmpty(query)) {
                new FetchMovieTask().execute(query);
            }else {
                Toast.makeText(this, "Enter Movie Title", Toast.LENGTH_SHORT).show();
            }
        });

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie_id", movie.getImdbID());
        startActivity(intent);
    }

    private class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {
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
                if(json.has("Search")) {
                    JSONArray searchArray = json.getJSONArray("Search");
                    for(int i = 0; i < searchArray.length(); i++) {
                        JSONObject item = searchArray.getJSONObject(i);

                        String title = item.getString("Title");
                        String year = item.getString("Year");
                        String imdbID = item.getString("imdbID");

                        String detailsURL = "https://www.omdbapi.com/?apikey=" + API_KEY + "&i=" + imdbID + "&plot=short";
                        URL detailsUrl = new URL(detailsURL);

                        HttpURLConnection detailsConn = (HttpURLConnection) detailsUrl.openConnection();
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

                        Movie movie = new Movie(title, director, year, rating, imdbID);
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
            if(result!=null && !result.isEmpty()) {
                movies.addAll(result);
            }else {
                Toast.makeText(MainActivity.this, "No Results Found", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }

    }

}