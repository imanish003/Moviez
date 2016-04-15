package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hnmn3.mechanic.optimist.popularmovies.R;

/**
 * Created by Menaria on 4/9/2016.
 */
public class GridViewAdapter extends ArrayAdapter<GridItem> {

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
        ViewHolder holder;

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

        Picasso
                .with(mContext)
                .load(item.getURL())
                .into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
