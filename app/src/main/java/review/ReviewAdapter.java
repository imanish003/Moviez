package review;

/**
 * Created by Manish Menaria on 11-Jun-16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hnmn3.mechanic.optimist.popularmovies.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private List<Review> reviewList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView author, review;

        public MyViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.author);
            review = (TextView) view.findViewById(R.id.review);
        }
    }


    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.author.setText(review.getAuthor());
        holder.review.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}