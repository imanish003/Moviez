package hnmn3.mechanic.optimist.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetails_Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if(getIntent()!=null){
            Intent i = getIntent();
            Bundle args = new Bundle();
            args.putString("getoriginal_title", i.getStringExtra("getoriginal_title"));
            args.putString("getrelease_date", i.getStringExtra("getrelease_date"));
            args.putString("getvote_average", i.getStringExtra("getvote_average"));
            args.putString("getoverview", i.getStringExtra("getoverview"));
            args.putString("getURL", i.getStringExtra("getURL"));
            //getSupportActionBar().setTitle(getoriginal_title);

            MovieDetails_Fragment detailfragment = new MovieDetails_Fragment();
            detailfragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,detailfragment).commit();

        }
    }
}
