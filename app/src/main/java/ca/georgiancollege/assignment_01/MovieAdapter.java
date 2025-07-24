package ca.georgiancollege.assignment_01;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList, OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.titleText.setText(movie.getTitle());
        holder.directorText.setText("Director: " + movie.getDirector());
        holder.ratingText.setText("Rating: " + movie.getRating());
        holder.yearText.setText("Year: " + movie.getYear());

        holder.itemView.setOnClickListener(
                v -> {
                    if (listener != null) {
                        listener.onItemClick(movie);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView directorText;
        TextView ratingText;
        TextView yearText;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            directorText = itemView.findViewById(R.id.directorText);
            ratingText = itemView.findViewById(R.id.ratingText);
            yearText = itemView.findViewById(R.id.yearText);
        }
    }

}
