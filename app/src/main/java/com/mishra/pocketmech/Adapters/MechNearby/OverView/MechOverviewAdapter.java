package com.mishra.pocketmech.Adapters.MechNearby.OverView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mishra.pocketmech.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MechOverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemMechanic> list;
    Context context;

    public MechOverviewAdapter(List<ItemMechanic> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_insurance, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemMechanic item = list.get(position);

        Picasso.get().load(item.getPhoto()).into(((ViewHolder) holder).image);
        ((ViewHolder) holder).name.setText(item.getName());
        ((ViewHolder) holder).address.setText(item.getAddress());
        ((ViewHolder) holder).timings.setText(item.getTimings());
        ((ViewHolder) holder).type.setText(item.getType());
        ((ViewHolder) holder).id.setText(item.getId());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, address, timings, type, id;
        ImageView image;

        public ViewHolder(final View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            timings = itemView.findViewById(R.id.timings);
            type = itemView.findViewById(R.id.type);
            id = itemView.findViewById(R.id.txtId);

        }
    }
}
