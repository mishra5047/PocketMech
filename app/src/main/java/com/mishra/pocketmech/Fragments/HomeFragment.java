package com.mishra.pocketmech.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
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

public class HomeFragment extends Fragment {

    TextView name;
    RecyclerView recCat;
    RecyclerView recBanner;
    RelativeLayout emergency;
    ArrayList<itemBanner> list;


    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        name = view.findViewById(R.id.name);
        recCat = view.findViewById(R.id.recCategory);
        recBanner = view.findViewById(R.id.banner);
        emergency = view.findViewById(R.id.emergencyLay);

        setViews();
        setCategory();
        setBanner();

        return view;
    }

    private void setViews() {
        list = new ArrayList();

        SharedPreferences preferences = getContext().getSharedPreferences("UserDetails", 0);
        String n = preferences.getString("name", "");
        name.setText("Welcome, " + n);

    }
    private void setBanner() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Banner/");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        itemBanner item = dataSnapshot.getValue(itemBanner.class);
                        list.add(item);
                    }
                    BannerAdapter adapter = new BannerAdapter(getContext(), list);
                    recBanner.setAdapter(adapter);
                    recBanner.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCategory() {
        RecyclerView.LayoutManager layoutManager;
        LinearLayoutManager linearLayoutManager;

        final List<ItemCategory> list = new ArrayList();
        layoutManager = new LinearLayoutManager(getContext());
        recCat.setLayoutManager(layoutManager);

        ItemCategory cat = new ItemCategory();
        cat.setGradient(R.drawable.rounded_rectangle_blue_no_border);
        cat.setImage(R.drawable.car_insurance);
        cat.setName("Insurance");
        list.add(cat);

        cat = new ItemCategory();
        cat.setGradient(R.drawable.rounded_rectangle_blue_no_border);
        cat.setImage(R.drawable.faq_icon);
        cat.setName("FAQ's");
        list.add(cat);

        cat = new ItemCategory();
        cat.setGradient(R.drawable.rounded_rectangle_blue_no_border);
        cat.setImage(R.drawable.profile);
        cat.setName("My Profile");
        list.add(cat);

        cat = new ItemCategory();
        cat.setGradient(R.drawable.rounded_rectangle_blue_no_border);
        cat.setImage(R.drawable.mechanic);
        cat.setName("Mechanics Nearby");
        list.add(cat);

        CategoryAdapter adapter = new CategoryAdapter(list, getContext());
        linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recCat.setLayoutManager(linearLayoutManager);
        recCat.setAdapter(adapter);
    }

}
