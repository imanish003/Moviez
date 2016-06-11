package hnmn3.mechanic.optimist.popularmovies;

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
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import review.Review;
import review.ReviewAdapter;

/**
 * Created by Manish Menaria on 10-Jun-16.
 */
public class MovieDetails_Fragment extends Fragment {

    TextView tvReleaseDate, tvRating, tvOverview;
    ImageView imageView;
    private List<Review> reviewList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReviewAdapter mAdapter;

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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new ReviewAdapter(reviewList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        new GetReviewAndTrailers().execute("/movie/popular");

        /*


        Intent i = getIntent();
        String getoriginal_title = i.getStringExtra("getoriginal_title");
        getSupportActionBar().setTitle(getoriginal_title);
        */
        prepareMovieData();
        return rootView;
    }

    private class GetReviewAndTrailers extends AsyncTask<String, Void, String> {


        HttpURLConnection conn;

        @Override
        protected String doInBackground(String... params) {
            String url_ = "http://api.themoviedb.org/3/movie/269149/reviews?api_key="+BuildConfig.MOVIE_API_KEY;
            try {
                URL url = new URL(url_);




                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                /*String urlParameters = "api_key="
                        + URLEncoder
                        .encode(BuildConfig.MOVIE_API_KEY,
                                "UTF-8");
                writer.write(urlParameters);*/
                writer.flush();
                writer.close();
                os.close();

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
            //GridAdapter.setGridData(GridData);
        }

    }

    private void prepareMovieData() {

        Review review = new Review("Andres Gomez", "One of the best movies Disney has created in the last years. Smart plot with a great background topic talking about the differences, stereotypes, prejudices and joining the tendency of giving women more important roles.\\r\\n\\r\\nIt has still several gaps to fill and enhance on the latest point but it is, IMHO, a milestone in the right direction.\\r\\n\\r\\nThe characters work pretty well and it is funny when needed and not too full of cheesy songs.");
        reviewList.add(review);

        review = new Review("Reno", "Try everything (but differently). So Disney has done it again.\\r\\n\\r\\nThis beautiful animation came to exist because of coming together of the directors of 'Tangled' and 'Wreck-it-Ralp'. It is Disney who had once again done it, since their rival Pixer is going down in a rapid speed. As a Disney fan since my childhood, I'm very happy for their success in live-shot films and animations, especially for this one.\\r\\n\\r\\nOkay, since the revolution of 3D animation over 20 years ago after overthrowing the 2D animation, most of the big productions like Disney, Pixer and Dreamworks with few others never failed to deliver. Believe me, I was not interested in this film when I first saw the teaser and trailer. But they have done great promotions and so the film did awesomely at screens worldwide. I was totally blown away after seeing it, Disney's another unique universal charactered story. From the little children to the grown ups, everybody definitely going to enjoy it.\\r\\n\\r\\nAll kinds of animals coming together happens only in cinemas, and that too mostly in animations. But todays kids are very sharp who ask lots of questions, so they had a fine explanation for the doubts regarding putting animals in a same society. It was like the United States, where everyone came from different continents and represents different race. And so in this film every animal came from different land to live together peacefully in a city called Zootopia.\\r\\n\\r\\nSo the story begins when Judy the rabbit follows her dream to become a police officer in Zootopia. There she meets Nick the fox, who are actually arch-rival species in the wild, but it was thousands of years ago before adapting the civilisation. So trust is what not promised between them, but they're forced to work together after a small missing person case becomes their prime agenda. Solving the mystery is what brings the end to this wonderful tale.\\r\\n\\r\\nThese days animations are not just concentrated on comedies, trying to get us emotionally as well. Maybe that's how they're grabbing the adult audience, especially the families. Shakira's cameo was the highlight, and her song 'Try Everything' helped the get attention from all the corners.\\r\\n\\r\\nThe Oscars was concluded just a couple of months ago, but it already feels like the fever is gripping again for the next edition and looks like this film is leading the way for the animation category. I know it's too early, but I hope it wins it. And finally a request for the Disney, bring it on a sequel as soon as possible.");
        reviewList.add(review);

        mAdapter.notifyDataSetChanged();
    }
}
