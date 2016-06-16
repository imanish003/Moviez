package data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Manish Menaria on 09-Jun-16.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "hnmn3.mechanic.optimist.popularmovies";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITE = "favorite";
    public static final String PATH_REVIEW = "review";
    public static final String PATH_TRAILER = "trailer";

    /* Inner class that defines the table contents of the popular table */
    public static final class FavoriteTableContents implements BaseColumns {

        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_release_date = "release_date";
        public static final String COLUMN_poster_path = "poster_path";
        public static final String COLUMN_overview = "overview";
        public static final String COLUMN_original_title = "original_title";
        public static final String COLUMN_vote_average = "vote_average";
        public static final String COLUMN_ = "";


    }

    public static final class ReviewTableContent implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "review";

        public static final String COLUMN_author = "author";
        public static final String COLUMN_review = "content";
        public static final String COLUMN_movie_id = "movie_id";

    }

    public static final class TrailerTableContent implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "trailer";

        public static final String COLUMN_source = "source";
        public static final String COLUMN_Trailer_name = "name";
        public static final String COLUMN_Internet_Source= "url_source";
        public static final String COLUMN_movie_id = "movie_id";

    }


}
