package com.beyga.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        ImageView poster = findViewById(R.id.poster_image_view);
        TextView title = findViewById(R.id.title_text_view);
        TextView releaseDate = findViewById(R.id.release_date_text_view);
        TextView voteAverage = findViewById(R.id.vote_average_text_view);
        TextView overview = findViewById(R.id.overview_text_view);

        if(movie != null) {
            setTitle(movie.getTitle());
            Glide.with(this).load(movie.getPoster_path()).into(poster);
            title.setText(movie.getTitle());
            releaseDate.setText(movie.getRelease_date());
            voteAverage.setText(String.valueOf(movie.getVote_average()));
            overview.setText(movie.getOverview());
        }
    }
}
