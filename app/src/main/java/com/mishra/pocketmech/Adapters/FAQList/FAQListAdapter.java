package com.mishra.pocketmech.Adapters.FAQList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mishra.pocketmech.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class FAQListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemFAQDisplay> list;
    Context context;

    public FAQListAdapter(List<ItemFAQDisplay> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemFAQDisplay itemCategory = list.get(position);
        ((ViewHolder) holder).problem.setText(itemCategory.getProblem().toUpperCase());
        ((ViewHolder) holder).solution.setText(itemCategory.getSolution());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{

        TextView problem, solution;
        ImageView trans;
        LinearLayout sol, rel;
        int i = 0;

        public ViewHolder(final View itemView){
            super(itemView);

            problem = itemView.findViewById(R.id.textProb);
            solution = itemView.findViewById(R.id.textSol);
            trans = itemView.findViewById(R.id.transition);
            sol = itemView.findViewById(R.id.lay_sol);
            trans.setImageResource(R.drawable.ic_down);
            rel = itemView.findViewById(R.id.relLayout);

            trans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (i == 0) {
                            rel.setBackgroundColor(itemView.getResources().getColor(R.color.red_light_2));
                            trans.setImageResource(R.drawable.ic_up);
                            sol.setVisibility(View.VISIBLE);
                            i = 1;
                        }
                        else if(i == 1){
                            rel.setBackgroundColor(itemView.getResources().getColor(R.color.white));
                            trans.setImageResource(R.drawable.ic_down);
                            sol.setVisibility(View.GONE);
                            i = 0;
                        }
                }
            });

        }
    }
}
