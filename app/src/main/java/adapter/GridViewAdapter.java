package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import hnmn3.mechanic.optimist.popularmovies.R;

/**
 * Created by Menaria on 4/9/2016.
 */
public class GridViewAdapter extends ArrayAdapter<GridItem> {

    ViewHolder holder;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> data_to_be_added = new ArrayList<GridItem>();

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<GridItem> GridData) {
        super(mContext, layoutResourceId, GridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data_to_be_added = GridData;
    }

    public void setGridData(ArrayList<GridItem> GridData) {
        this.data_to_be_added.clear();
        this.data_to_be_added.addAll(GridData);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = data_to_be_added.get(position);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String url_end = preferences.getString("filter","/movie/popular");
        if(url_end.equals("favorite")){
            load(item.getURL(),item.getId());
        }else{
            Picasso
                    .with(mContext)
                    .load(item.getURL())
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imageView);
        }

        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }

    public void load(String path,String id) {
        try {
            File f = new File(path, id+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            holder.imageView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
