package com.mishra.pocketmech.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
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

    private FusedLocationProviderClient fusedLocationClient;
    String area_user;
    Double latitude, longitude;

    String type;

    TextView areaTxt;

    List<ItemMechanic> mech;
    MechOverviewAdapter adapter;
    LinearLayoutManager manager;
    RecyclerView recyclerView;
    FloatingActionButton filter;

    TextView typeDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics_list);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        type = "Bike";

        if (getIntent().hasExtra("type")){
            type = getIntent().getStringExtra("type");
        }

        typeDisp = findViewById(R.id.type);
        typeDisp.setText(type);

        areaTxt = findViewById(R.id.txtArea);
        recyclerView = findViewById(R.id.recMech);

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
                String lat = String.format("%.4d", latitude);
                String lon = String.format("%.4d", longitude);
                adapter = new MechOverviewAdapter(mech, getApplicationContext(), type, Double.valueOf(lat), Double.valueOf(lon));
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

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(MechanicsListActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();


                     //       Toast.makeText(getApplicationContext(), "lat = " + latitude + "long = " + longitude, Toast.LENGTH_LONG).show();
                            Geocoder geocoder;
                            geocoder = new Geocoder(MechanicsListActivity.this, Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(latitude, longitude, 1);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addresses != null) {
                                area_user = addresses.get(0).getSubLocality();
                                areaTxt.setText("Mechanics Nearby " + area_user);
                            } else {

                                Toast.makeText(getApplicationContext(), "Not Detected", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
    }

    @Override
    public void onItemClicked(String text, int limit) {
    if (text.equals("Car")){
        Intent intent = new Intent(getApplicationContext(), MechanicsListActivity.class);
        intent.putExtra("type", "Car");
        startActivity(intent);
    }
    }
}