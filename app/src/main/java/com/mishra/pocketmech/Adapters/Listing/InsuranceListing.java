package com.mishra.pocketmech.Adapters.Listing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mishra.pocketmech.Adapters.Insurance.InsuranceAdapter;
import com.mishra.pocketmech.Adapters.Insurance.ItemInsurance;
import com.mishra.pocketmech.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class InsuranceListing extends AppCompatActivity {

    String type;
    RecyclerView recyclerView;
    InsuranceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_listing);

        if (getIntent().hasExtra("type")){
            type = getIntent().getStringExtra("type");
        }

        recyclerView = findViewById(R.id.recInsurance);

        DatabaseReference reference;
        String databasePath = "Insurance";

        final List<ItemInsurance> list= new ArrayList();
        final GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);

        reference = FirebaseDatabase.getInstance().getReference(databasePath);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ItemInsurance item = dataSnapshot.getValue(ItemInsurance.class);
                        list.add(item);
                }
                adapter = new InsuranceAdapter(list, getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.error(getApplicationContext(), error.toString()).show();
            }
        });

    }
}