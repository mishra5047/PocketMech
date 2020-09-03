package com.mishra.pocketmech.Adapters.MechNearby.Ratings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mishra.pocketmech.Activity.ImageDisplayActivity;
import com.mishra.pocketmech.Activity.MechanicsDetailsActivity;
import com.mishra.pocketmech.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class MechRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<mechRating> list;
    Context context;

    public MechRatingAdapter(List<mechRating> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mech_rating, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        mechRating item = list.get(position);

        Picasso.get().load(item.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ((ViewHolder) holder).image.setBackground(new BitmapDrawable(context.getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        ((ViewHolder) holder).name.setText(item.getName());
        ((ViewHolder) holder).rating.setText(String.valueOf(item.getRating()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, rating;
        ImageView image;

        public ViewHolder(final View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.ratings);
        }
    }
}
