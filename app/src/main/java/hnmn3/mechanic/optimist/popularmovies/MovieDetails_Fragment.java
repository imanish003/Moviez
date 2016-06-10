package hnmn3.mechanic.optimist.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vstechlab.easyfonts.EasyFonts;

/**
 * Created by Manish Menaria on 10-Jun-16.
 */
public class MovieDetails_Fragment extends Fragment {

    TextView tvReleaseDate, tvRating, tvOverview;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);

        tvReleaseDate = (TextView) rootView.findViewById(R.id.tvReleaseDate);
        tvRating = (TextView) rootView.findViewById(R.id.tvRating);
        tvOverview = (TextView) rootView.findViewById(R.id.tvOverview);
        imageView = (ImageView) rootView.findViewById(R.id.ivPoster);
        tvReleaseDate.setTypeface(EasyFonts.droidSerifBold(getContext()));
        tvRating.setTypeface(EasyFonts.droidSerifBold(getContext()));
        tvOverview.setTypeface(EasyFonts.droidSerifBold(getContext()));

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(bundle.getString("getoriginal_title"));
            tvReleaseDate.setText(bundle.getString("getrelease_date"));
            tvRating.setText(bundle.getString("getvote_average") + "/10");
            tvOverview.setText(bundle.getString("getoverview"));

            Picasso
                    .with(getContext())
                    .load(bundle.getString("getURL"))
                    .into(imageView);
        }

        /*


        Intent i = getIntent();
        String getoriginal_title = i.getStringExtra("getoriginal_title");
        getSupportActionBar().setTitle(getoriginal_title);
        */

        return rootView;
    }
}
