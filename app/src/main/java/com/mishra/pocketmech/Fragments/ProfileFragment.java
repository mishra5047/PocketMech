package com.mishra.pocketmech.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mishra.pocketmech.Adapters.Banner.BannerAdapter;
import com.mishra.pocketmech.Adapters.Banner.itemBanner;
import com.mishra.pocketmech.Adapters.Category.CategoryAdapter;
import com.mishra.pocketmech.Adapters.Category.ItemCategory;
import com.mishra.pocketmech.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ProfileFragment extends Fragment {

    TextView name_1, name_2, num_1, num_2, user_name, user_no;
    LinearLayout whyLayout;
    ImageView edit_1, edit_2;
    public static final int MODE_PRIVATE = 0;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_profile, container, false);

        loadViews(view);
        loadData();
        functionality();

        return view;
    }

    private void functionality() {
        edit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getContext(), "Coming Soon", Toasty.LENGTH_SHORT).show();
            }
        });

        edit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getContext(), "Coming Soon", Toasty.LENGTH_SHORT).show();
            }
        });

        whyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.error(getContext(), "Required To Use SOS Feature").show();
            }
        });
    }


    private void loadData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);
        final String n_1 = preferences.getString("emergency_num_1", "");
        final String n_2 = preferences.getString("emergency_num_2", "");
        final String na_1 = preferences.getString("emergency_name_1","");
        final String na_2 = preferences.getString("emergency_name_2","");
        final String name = preferences.getString("name","");
        final String num = preferences.getString("phone","");

        name_1.setText(na_1);
        name_2.setText(na_2);
        num_1.setText(n_1);
        num_2.setText(n_2);
        user_name.setText(name);
        user_no.setText(num);
    }

    private void loadViews(View view) {
        user_name = view.findViewById(R.id.user_name);
        user_no = view.findViewById(R.id.user_no);
        name_1 = view.findViewById(R.id.name_1);
        name_2 = view.findViewById(R.id.name_2);
        num_1 = view.findViewById(R.id.num_1);
        num_2 = view.findViewById(R.id.num_2);
        whyLayout = view.findViewById(R.id.why_layout);
        edit_1 = view.findViewById(R.id.edit_1);
        edit_2 = view.findViewById(R.id.edit_2);
    }
}
