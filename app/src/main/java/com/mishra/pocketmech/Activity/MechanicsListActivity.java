package com.mishra.pocketmech.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mishra.pocketmech.Adapters.FilterAdapter;
import com.mishra.pocketmech.Adapters.MechNearby.OverView.ItemMechanic;
import com.mishra.pocketmech.Adapters.MechNearby.OverView.MechOverviewAdapter;
import com.mishra.pocketmech.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class MechanicsListActivity extends AppCompatActivity implements FilterAdapter.BottomSheetListener {

    String type;

    TextView areaTxt;

    List<ItemMechanic> mech;
    MechOverviewAdapter adapter;
    LinearLayoutManager manager;
    RecyclerView recyclerView;
    FloatingActionButton filter;

    TextView typeDisp;

    String lat, lon;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics_list);

        type = "Bike";

        if (getIntent().hasExtra("type")){
            type = getIntent().getStringExtra("type");
        }

        typeDisp = findViewById(R.id.type);
        typeDisp.setText(type);

        areaTxt = findViewById(R.id.txtArea);
        recyclerView = findViewById(R.id.recMech);

        //get from shared preference
        SharedPreferences pref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String area = pref.getString("area", "");
        areaTxt.setText("Mechanic Nearby " + area);

        Double l_1, l_2;

        l_1 = Double.valueOf(pref.getString("latitude", ""));
        l_2 = Double.valueOf(pref.getString("longitude", ""));

         lat = String.format("%.4f", l_1);
         lon = String.format("%.4f", l_2);

        if (!(lat.isEmpty() && lon.isEmpty())) {
           latitude  = Double.parseDouble(lat);
           longitude = Double.parseDouble(lon);
        }

        mech = new ArrayList();
        String databasePath = "Mechanics/" + type;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(databasePath);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ItemMechanic mechanic = dataSnapshot.getValue(ItemMechanic.class);
                    mech.add(mechanic);
                }
                adapter = new MechOverviewAdapter(mech, getApplicationContext(),
                        type, latitude, longitude);

                manager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //filter
        filter = findViewById(R.id.filterIcon);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterAdapter filterNav = new FilterAdapter(type);
                filterNav.show(getSupportFragmentManager(), "example bottom tag");

            }
        });

    }

    @Override
    public void onItemClicked(String text, int limit) {
        Intent intent = new Intent(getApplicationContext(), MechanicsListActivity.class);

        if (text.equals("Car")){
        intent.putExtra("type", "Car");
        }else if (text.equals("Bike")){
            intent.putExtra("type", "Bike");
        }

        startActivity(intent);
    }
}