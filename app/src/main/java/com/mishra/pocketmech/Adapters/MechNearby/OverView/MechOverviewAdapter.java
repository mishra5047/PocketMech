package com.mishra.pocketmech.Adapters.MechNearby.OverView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mishra.pocketmech.Activity.ImageDisplayActivity;
import com.mishra.pocketmech.Activity.MechanicsDetailsActivity;
import com.mishra.pocketmech.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MechOverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemMechanic> list;
    Context context;
    String typeVeh;
    double latitude;
    double longitude;

    public MechOverviewAdapter(List<ItemMechanic> list, Context context, String typeVeh, double latitude, double longitude) {
        this.list = list;
        this.context = context;
        this.typeVeh = typeVeh;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mechanic, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        ItemMechanic item = list.get(position);

        Picasso.get().load(item.getPhoto()).into(new Target() {
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
        ((ViewHolder) holder).address.setText(item.getAddress());
        ((ViewHolder) holder).timings.setText(item.getTimings());
        ((ViewHolder) holder).type.setText(item.getType());
        ((ViewHolder) holder).id.setText(item.getId());
        ((ViewHolder) holder).url.setText(item.getPhoto());

        Location mech = new Location("mechanic");
        mech.setLatitude(item.getLatitude());
        mech.setLongitude(item.getLongitude());

        Location user = new Location("User");
        user.setLatitude(latitude);
        user.setLongitude(longitude);

        double dist = user.distanceTo(mech);

        ((ViewHolder) holder).distance.setText(dist + "KMS");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, address, timings, type, id, url;
        ImageView image;
        LinearLayout details;
        TextView distance;

        public ViewHolder(final View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            timings = itemView.findViewById(R.id.timings);
            type = itemView.findViewById(R.id.type);
            id = itemView.findViewById(R.id.txtId);
            url = itemView.findViewById(R.id.url);
            distance = itemView.findViewById(R.id.distance);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ImageDisplayActivity.class);
                intent.putExtra("name", name.getText());
                intent.putExtra("url", url.getText());
                intent.putExtra("type", typeVeh);
                v.getContext().startActivity(intent);
                }
            });

            details = itemView.findViewById(R.id.detailsLay);
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), MechanicsDetailsActivity.class);
                    intent.putExtra("type", typeVeh);
                    intent.putExtra("id", id.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
