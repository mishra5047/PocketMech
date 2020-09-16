package com.mishra.pocketmech.Adapters.Listing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mishra.pocketmech.Adapters.FAQList.FAQListAdapter;
import com.mishra.pocketmech.Adapters.FAQList.ItemFAQDisplay;
import com.mishra.pocketmech.R;

import java.util.ArrayList;
import java.util.List;

public class FAQListing extends AppCompatActivity {

    RecyclerView recyclerView;
    FAQListAdapter adapter;
    List<ItemFAQDisplay> list;
    String item, type;

    TextView cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q_listing);

        recyclerView = findViewById(R.id.recFAQList);

        cat = findViewById(R.id.textCat);

        list = new ArrayList();
        item = getIntent().getStringExtra("item");
        cat.setText(item);
        type = getIntent().getStringExtra("type");

        String databasePath = "FAQ/" + type + "/" + item;

        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(databasePath);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ItemFAQDisplay item = dataSnapshot.getValue(ItemFAQDisplay.class);
                    list.add(item);
                }
                adapter = new FAQListAdapter(list, getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}