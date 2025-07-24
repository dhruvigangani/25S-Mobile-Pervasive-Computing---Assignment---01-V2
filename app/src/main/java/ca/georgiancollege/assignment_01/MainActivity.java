package ca.georgiancollege.assignment_01;

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
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemClick(Movie movie) {
    }


}