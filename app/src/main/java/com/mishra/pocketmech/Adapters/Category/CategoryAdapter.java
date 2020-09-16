package com.mishra.pocketmech.Adapters.Category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mishra.pocketmech.Activity.OptionActivity;
import com.mishra.pocketmech.Adapters.Listing.InsuranceListing;
import com.mishra.pocketmech.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemCategory> list;
    Context context;

    public CategoryAdapter(List<ItemCategory> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemCategory itemCategory = list.get(position);
        ((ViewHolder) holder).imageItem.setImageResource(itemCategory.getImage());
        ((ViewHolder) holder).nameItem.setText(itemCategory.getName());
        ((ViewHolder) holder).relativeLayout.setBackgroundResource(itemCategory.getGradient());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{

        TextView nameItem;
        ImageView imageItem;
        RelativeLayout relativeLayout;

        public ViewHolder(final View itemView){
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relLayout);
            nameItem = itemView.findViewById(R.id.textItem);
            imageItem = itemView.findViewById(R.id.image);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (nameItem.getText().equals("My Profile")){
                        Toasty.info(v.getContext().getApplicationContext(), "Coming Soon", Toasty.LENGTH_SHORT).show();
                    }else if(nameItem.getText().toString().equalsIgnoreCase("Insurance")){
                        Intent intent = new Intent(v.getContext().getApplicationContext(), InsuranceListing.class);
                        v.getContext().startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(v.getContext(), OptionActivity.class);
                        intent.putExtra("need", nameItem.getText().toString());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
