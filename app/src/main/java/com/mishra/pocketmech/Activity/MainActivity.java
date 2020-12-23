package com.mishra.pocketmech.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mishra.pocketmech.Adapters.Banner.itemBanner;
import com.mishra.pocketmech.Adapters.Category.CategoryAdapter;
import com.mishra.pocketmech.Adapters.Category.ItemCategory;
import com.mishra.pocketmech.Adapters.Listing.FAQListing;
import com.mishra.pocketmech.Fragments.HomeFragment;
import com.mishra.pocketmech.Fragments.ProfileFragment;
import com.mishra.pocketmech.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class MainActivity extends Activity {

    //views
    RecyclerView recCat;
    ArrayList<itemBanner> list;
    TextView name;

    Double latitude, longitude;
    String address_user, city_user;
    BottomNavigationView navigationView;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    MaterialSearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        views();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getArea();
        searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void views() {
        recCat = findViewById(R.id.recCategory);
        list = new ArrayList();
        name = findViewById(R.id.txtWelcome);

        SharedPreferences preferences = getSharedPreferences("UserDetails", 0);
        String n = preferences.getString("name", "");
        name.setText("Welcome, " + n);

        RecyclerView.LayoutManager layoutManager;
        LinearLayoutManager linearLayoutManager;

        final List<ItemCategory> list = new ArrayList();
        layoutManager = new LinearLayoutManager(getApplicationContext());
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

        CategoryAdapter adapter = new CategoryAdapter(list, getApplicationContext());
        linearLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recCat.setLayoutManager(linearLayoutManager);
        recCat.setAdapter(adapter);

    }

    private void emergency_function() {
        SharedPreferences preferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        final String num_1 = preferences.getString("emergency_num_1", "");
        final String num_2 = preferences.getString("emergency_num_2", "");

        String location =  "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
        final String message = "This message is sent by PocketMech as Has Used the SOS Feature. " + "Location = " + location ;

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false)
                .setPositiveButton("Send SOS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        
                        SmsManager manager = SmsManager.getDefault();
                        manager.sendTextMessage("+91-9599656583", null, message, null, null);
                        manager.sendTextMessage(num_2, null, message, null, null);

                        Toasty.warning(MainActivity.this, "SOS Message Sent", Toasty.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Toasty.error(MainActivity.this, "SOS Cancelled", Toasty.LENGTH_SHORT).show();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setTitle("SOS");
        dialog.create();
        dialog.show();

    }


    void getArea() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Toasty.error(getApplicationContext(), "In order to use Boozziie you must enable Location", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addCategory(Intent.CATEGORY_HOME);
                                startActivity(intent1);
                            }
                        }).create().show();

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_COARSE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        getLocation();

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
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            Geocoder geocoder;
                            geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(latitude, longitude, 1);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addresses != null) {
                                city_user = addresses.get(0).getAdminArea();
                                address_user = addresses.get(0).getSubLocality();

                                SharedPreferences pref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                                SharedPreferences.Editor mEditor = pref.edit();
                                mEditor.putString("city", city_user);
                                mEditor.putString("area", address_user);
                                mEditor.putString("latitude", latitude.toString());
                                mEditor.putString("longitude", longitude.toString());
                                mEditor.commit();
                            } else {

                                Toast.makeText(getApplicationContext(), "Not Detected", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
    }

}