package hnmn3.mechanic.optimist.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import adapter.GridItem;
import adapter.GridViewAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GridView GridView;
    String baseUrl;
    private GridViewAdapter GridAdapter;
    private ArrayList<GridItem> GridData;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Picasso.with(this).load("http://i.imgur.com/rFLNqWI.jpg").into(iv);



        GridData = new ArrayList<>();
        GridView = (GridView) findViewById(R.id.gridView);
        GridView.setOnItemClickListener(this);
        GridAdapter = new GridViewAdapter(this, R.layout.gridview_item, GridData);
        GridView.setAdapter(GridAdapter);

        int densityDpi = getResources().getDisplayMetrics().densityDpi;
        if (densityDpi <= DisplayMetrics.DENSITY_MEDIUM) {
            baseUrl= "http://image.tmdb.org/t/p/w185/";
        } else if (densityDpi <= DisplayMetrics.DENSITY_HIGH) {
            baseUrl= "http://image.tmdb.org/t/p/w185/";
        } else if (densityDpi <= DisplayMetrics.DENSITY_XHIGH) {
            baseUrl= "http://image.tmdb.org/t/p/w342/";
        } else if (densityDpi <= DisplayMetrics.DENSITY_XXHIGH) {
            baseUrl= "http://image.tmdb.org/t/p/w500/";
        } else {
            baseUrl= "http://image.tmdb.org/t/p/w780/";
        }

        updateMovies();

    }

    private void GetMoviesDataFromJson(String jsonString){
        GridData = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonArray = jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                String url = baseUrl +jsonObject.getString("poster_path");
                String overview = jsonObject.getString("overview");
                String release_date = jsonObject.getString("release_date");
                String original_title = jsonObject.getString("original_title");
                String vote_average = jsonObject.getString("vote_average");
                GridItem item = new GridItem(url,overview,release_date,original_title,vote_average);
                GridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, GridData.get(position).getoriginal_title(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MainActivity.this,MovieDetails_Activity.class);
        i.putExtra("getoriginal_title", GridData.get(position).getoriginal_title());
        i.putExtra("getoverview", GridData.get(position).getoverview());
        i.putExtra("getrelease_date", GridData.get(position).getrelease_date());
        i.putExtra("getURL", GridData.get(position).getURL());
        i.putExtra("getvote_average", GridData.get(position).getvote_average());
        startActivity(i);
    }

    private class GetMoviesInfo extends AsyncTask<String, Void, String> {


        HttpURLConnection conn;

        @Override
        protected String doInBackground(String... params) {
            String url_ = "http://api.themoviedb.org/3"+params[0];
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
                //SharedPreferences userInfo = getSharedPreferences("mypref", 0);
                /*String urlParameters = "username="
                        + URLEncoder
                        .encode(userInfo.getString("username", "Null"),
                                "UTF-8");*/
                String urlParameters = "api_key="
                        + URLEncoder
                        .encode(BuildConfig.MOVIE_API_KEY,
                                "UTF-8");
                writer.write(urlParameters);
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
                    GetMoviesDataFromJson(jsonString);

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
            // TODO Auto-generated method stub
            GridAdapter.setGridData(GridData);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.action_filter){
            startActivity(new Intent(MainActivity.this,Filter_Activity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMovies();
    }

    void updateMovies(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url_end = preferences.getString("filter","/movie/popular");
        new GetMoviesInfo().execute(url_end);
    }
}
