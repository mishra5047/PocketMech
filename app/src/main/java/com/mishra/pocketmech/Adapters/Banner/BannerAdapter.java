package com.mishra.pocketmech.Adapters.Banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mishra.pocketmech.Adapters.Category.CategoryAdapter;
import com.mishra.pocketmech.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder>{

    Context context;
    ArrayList<itemBanner> banner;

    public BannerAdapter(Context context, ArrayList<itemBanner> banner) {
        this.context = context;
        this.banner = banner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);

        BannerAdapter.ViewHolder viewHolder = new BannerAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        itemBanner bannerItem = banner.get(position);
        Picasso.get().load(bannerItem.getUrl()).into(((ViewHolder) holder).image);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
        }
    }
}
