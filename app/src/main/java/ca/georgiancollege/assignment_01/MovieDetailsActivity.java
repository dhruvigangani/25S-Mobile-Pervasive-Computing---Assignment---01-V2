package ca.georgiancollege.assignment_01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}
