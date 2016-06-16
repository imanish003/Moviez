package hnmn3.mechanic.optimist.popularmovies;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Boolean mTabletMode = false;
    GridViewFragment gridViewFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        gridViewFragment = new GridViewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,gridViewFragment).commit();
        if(findViewById(R.id.container)!=null){
            mTabletMode = true;
            MovieDetails_Fragment detailsFragment = new MovieDetails_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,detailsFragment).commit();
        }
    }

    public boolean isTablet() {
        return mTabletMode;
    }

    public void replaceFragment(String getoriginal_title, String getoverview, String getrelease_date, String url, String getvote_average,String id) {
        Bundle args = new Bundle();
        args.putString("getoriginal_title", getoriginal_title);
        args.putString("getrelease_date", getrelease_date);
        args.putString("getvote_average", getvote_average);
        args.putString("getoverview", getoverview);
        args.putString("getURL", url);
        args.putString("id",id);
        //getSupportActionBar().setTitle(getoriginal_title);

        MovieDetails_Fragment detailfragment = new MovieDetails_Fragment();
        detailfragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,detailfragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedpreferences ;
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedpreferences.edit();

        int id = item.getItemId();
        switch (id){
            case R.id.menuPopular:
                editor.putString("filter","/movie/popular");
                editor.commit();
                gridViewFragment = new GridViewFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,gridViewFragment).commit();
                break;
            case R.id.menuTopRated:
                editor.putString("filter","/movie/top_rated");
                editor.commit();
                gridViewFragment = new GridViewFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,gridViewFragment).commit();
                break;
            case R.id.menuFavorite:
                editor.putString("filter","favorite");
                editor.commit();
                gridViewFragment = new GridViewFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,gridViewFragment).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
