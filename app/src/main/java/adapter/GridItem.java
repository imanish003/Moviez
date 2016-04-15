package adapter;

/**
 * Created by Menaria on 4/9/2016.
 */
public class GridItem {
    private String URL,overview,release_date,original_title,vote_average;


    public GridItem() {

    }

    public GridItem(String url) {
        this.URL = url;
    }

    public GridItem(String url,String overview,String release_date,String original_title,String vote_average) {
        this.URL = url;
        this.overview = overview;
        this.release_date = release_date;
        this.original_title = original_title;
        this.vote_average = vote_average;
    }

    public String getURL() {
        return URL;
    }
    public String getoverview() {
        return overview;
    }
    public String getrelease_date() {
        return release_date;
    }
    public String getoriginal_title() {
        return original_title;
    }
    public String getvote_average() {
        return vote_average;
    }

}
