package com.mishra.pocketmech.Adapters.Insurance;

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

import es.dmoral.toasty.Toasty;

public class InsuranceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemInsurance> list;
    Context context;

    public InsuranceAdapter(List<ItemInsurance> list, Context context) {
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

        ItemInsurance itemCategory = list.get(position);

        Picasso.get().load(itemCategory.getImage()).into(((ViewHolder) holder).imageItem);
        ((ViewHolder) holder).nameItem.setText(itemCategory.getName());
        ((ViewHolder) holder).urlCom.setText(itemCategory.getUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{

        TextView nameItem;
        ImageView imageItem;
        RelativeLayout relativeLayout;
        TextView urlCom;

        public ViewHolder(final View itemView){
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relLayout);
            nameItem = itemView.findViewById(R.id.textItem);
            imageItem = itemView.findViewById(R.id.image);
            urlCom = itemView.findViewById(R.id.url);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = urlCom.getText().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
