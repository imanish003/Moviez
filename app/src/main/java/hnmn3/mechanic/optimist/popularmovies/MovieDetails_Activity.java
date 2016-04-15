package hnmn3.mechanic.optimist.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vstechlab.easyfonts.EasyFonts;

public class MovieDetails_Activity extends AppCompatActivity {

    TextView tvReleaseDate, tvRating, tvOverview;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvRating = (TextView) findViewById(R.id.tvRating);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        imageView = (ImageView) findViewById(R.id.ivPoster);

        tvReleaseDate.setTypeface(EasyFonts.droidSerifBold(this));
        tvRating.setTypeface(EasyFonts.droidSerifBold(this));
        tvOverview.setTypeface(EasyFonts.droidSerifBold(this));

        Intent i = getIntent();
        String getoriginal_title = i.getStringExtra("getoriginal_title");
        getSupportActionBar().setTitle(getoriginal_title);
        tvReleaseDate.setText(i.getStringExtra("getrelease_date"));
        tvRating.setText(i.getStringExtra("getvote_average") + "/10");
        tvOverview.setText(i.getStringExtra("getoverview"));

        Picasso
                .with(this)
                .load(i.getStringExtra("getURL"))
                .into(imageView);


    }
}
