package com.mishra.pocketmech.Adapters.FAQ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mishra.pocketmech.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class FAQAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemFAQ> list;
    Context context;

    public FAQAdapter(List<ItemFAQ> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemFAQ itemCategory = list.get(position);
        ((ViewHolder) holder).imageItem.setImageResource(itemCategory.getImage());
        ((ViewHolder) holder).nameItem.setText(itemCategory.getName());
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
                    Toasty.info(v.getContext(), nameItem.getText().toString(), Toasty.LENGTH_SHORT).show();
                }
            });
        }
    }
}
