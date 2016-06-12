package hnmn3.mechanic.optimist.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Trailer.Trailer;
import review.Review;
import review.ReviewAdapter;

/**
 * Created by Manish Menaria on 10-Jun-16.
 */
public class MovieDetails_Fragment extends Fragment {

    TextView tvReleaseDate, tvRating, tvOverview,noReviewAvailable;
    ImageView imageView;
    LinearLayout trailerLayout;
    ProgressBar pBar;
    private List<Review> reviewList = new ArrayList<>();
    private List<Trailer> trailerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReviewAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);

        tvReleaseDate = (TextView) rootView.findViewById(R.id.tvReleaseDate);
        tvRating = (TextView) rootView.findViewById(R.id.tvRating);
        noReviewAvailable = (TextView) rootView.findViewById(R.id.tvNoReviewAvailbale);
        tvOverview = (TextView) rootView.findViewById(R.id.tvOverview);
        imageView = (ImageView) rootView.findViewById(R.id.ivPoster);
        pBar = (ProgressBar) rootView.findViewById(R.id.progressBarReview);
        trailerLayout = (LinearLayout) rootView.findViewById(R.id.linearLayoutYoutube);
        tvReleaseDate.setTypeface(EasyFonts.droidSerifBold(getContext()));
        tvRating.setTypeface(EasyFonts.droidSerifBold(getContext()));
        tvOverview.setTypeface(EasyFonts.droidSerifBold(getContext()));


        String id = "null";
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(bundle.getString("getoriginal_title"));
            tvReleaseDate.setText(bundle.getString("getrelease_date"));
            tvRating.setText(bundle.getString("getvote_average") + "/10");
            tvOverview.setText(bundle.getString("getoverview"));
            id=bundle.getString("id");
            Toast.makeText(getContext(), "id="+id, Toast.LENGTH_SHORT).show();

            Picasso
                    .with(getContext())
                    .load(bundle.getString("getURL"))
                    .into(imageView);
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new ReviewAdapter(reviewList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        new GetReviewAndTrailers().execute(id);

        /*


        Intent i = getIntent();
        String getoriginal_title = i.getStringExtra("getoriginal_title");
        getSupportActionBar().setTitle(getoriginal_title);
        */
        //prepareMovieData();
        return rootView;
    }

    private class GetReviewAndTrailers extends AsyncTask<String, Void, String> {


        HttpURLConnection conn;

        @Override
        protected String doInBackground(String... params) {
            String url_ = "http://api.themoviedb.org/3/movie/"+params[0]+"?api_key="+BuildConfig.MOVIE_API_KEY+"&append_to_response=trailers,reviews";
            try {
                URL url = new URL(url_);




                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                /*OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                *//*String urlParameters = "api_key="
                        + URLEncoder
                        .encode(BuildConfig.MOVIE_API_KEY,
                                "UTF-8");
                writer.write(urlParameters);*//*
                writer.flush();
                writer.close();
                os.close();*/

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + '\n');
                    }

                    String jsonString = stringBuilder.toString();
                    System.out.println(jsonString);
                    fatchReviewNTrailerDataFromJSON(jsonString);

                    return "Movies Data Loaded Sucessfully";

                } else {

                    return "Error occured while fatching data";

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("Exception1");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception2");
            } finally {
                conn.disconnect();
            }
            return "Null";
        }

        @Override
        protected void onPostExecute(String result) {
            pBar.setVisibility(View.GONE);
            if(reviewList.size()==0){
                noReviewAvailable.setVisibility(View.VISIBLE);
            }
            mAdapter.notifyDataSetChanged();
            addTailersTolayout();
            //GridAdapter.setGridData(GridData);
        }

    }

    private void addTailersTolayout() {
        trailerLayout.setPadding(5, 10, 5, 0);
        if(trailerList.size()>0){

            for(int i=0;i<trailerList.size();i++){
                final String source = trailerList.get(i).getSource() ;
                String url ="http://img.youtube.com/vi/" + source + "/mqdefault.jpg";
                ImageView myImage = new ImageView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.leftMargin = 3;
                params.rightMargin = 3;
                params.topMargin = 6;
                params.bottomMargin = 3;
                myImage.setLayoutParams(params);
                Picasso.with(getContext())
                        .load(url)
                        .into(myImage);
                trailerLayout.addView(myImage);
                myImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        youtubeIntent(source);
                    }
                });
            }
        }else{
            TextView errorMsg = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            errorMsg.setLayoutParams(params);
            errorMsg.setText("Sorry , No trailers are available for this movie");
        }
    }

    private void youtubeIntent(String source) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + source));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + source));
            startActivity(intent);
        }
    }


    private void fatchReviewNTrailerDataFromJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObjectreviews =  jsonObject.getJSONObject("reviews");
            JSONArray jsonArray = jsonObjectreviews.getJSONArray("results");
            Review review ;
            Trailer trailer ;
            reviewList.clear();

            for(int i=0;i<jsonArray.length();i++){
                review = new Review();
                jsonObjectreviews = jsonArray.getJSONObject(i);
                review.setAuthor(jsonObjectreviews.getString("author"));
                review.setReview(jsonObjectreviews.getString("content"));
                reviewList.add(review);
            }


            JSONObject jsonObjectTrailer = jsonObject.getJSONObject("trailers");
            jsonArray=jsonObjectTrailer.getJSONArray("youtube");

            for(int i=0;i<jsonArray.length();i++){
                jsonObjectTrailer = jsonArray.getJSONObject(i);
                String name = jsonObjectTrailer.getString("name");
                String size = jsonObjectTrailer.getString("size");
                String source = jsonObjectTrailer.getString("source");
                String type = jsonObjectTrailer.getString("type");
                trailer = new Trailer(name,size,source,type);
                trailerList.add(trailer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
