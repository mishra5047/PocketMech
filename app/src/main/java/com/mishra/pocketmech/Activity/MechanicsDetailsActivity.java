package com.mishra.pocketmech.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mishra.pocketmech.Adapters.MechNearby.OverView.ItemMechanic;
import com.mishra.pocketmech.Adapters.MechNearby.Ratings.MechRatingAdapter;
import com.mishra.pocketmech.Adapters.MechNearby.Ratings.mechRating;
import com.mishra.pocketmech.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MechanicsDetailsActivity extends AppCompatActivity {

    String id;
    DatabaseReference details, reviews;

    List<mechRating> list;

    //views
    ImageView photo, back, phoneIcon,mapsIcon;
    TextView address, timeMon, timeSat, name, txtPhn;
    RecyclerView recReviews;
    LinearLayout addReview;
    String databasePath_1, databasePath_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics_details);

        list = new ArrayList();

        views();

        databasePath_1 = "Mechanics/" + getIntent().getStringExtra("type") + "/" + getIntent().getStringExtra("id") + "/";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(databasePath_1);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ItemMechanic mechanic = snapshot.getValue(ItemMechanic.class);
                    loadDetails(mechanic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databasePath_2 = databasePath_1 + "/" + "Reviews/";
        DatabaseReference reference_2 = FirebaseDatabase.getInstance().getReference(databasePath_2);
        reference_2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (snapshot.exists()){
                        recReviews.setVisibility(View.VISIBLE);
                        mechRating rating = dataSnapshot.getValue(mechRating.class);
                        list.add(rating);
                    }
            }
                MechRatingAdapter adapter = new MechRatingAdapter(list,getApplicationContext());
                recReviews.setAdapter(adapter);
                recReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.error(getApplicationContext(), error.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }


    void views(){
        photo = findViewById(R.id.image);
        back = findViewById(R.id.backBtn);
        phoneIcon = findViewById(R.id.phoneIcon);
        mapsIcon = findViewById(R.id.mapsIcon);
        address = findViewById(R.id.location);
        timeMon = findViewById(R.id.timeMon);
        timeSat = findViewById(R.id.timeSat);
        recReviews = findViewById(R.id.recReview);
        addReview = findViewById(R.id.reviewLay);
        name = findViewById(R.id.name);
        txtPhn = findViewById(R.id.phnNumber);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MechanicsListActivity.class);
                intent.putExtra("type", getIntent().getStringExtra("type"));
                startActivity(intent);
            }
        });

    }

    void loadDetails(final ItemMechanic mechanic){

        Picasso.get().load(mechanic.getPhoto()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                photo.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        name.setText(mechanic.getName());
        address.setText(mechanic.getAddress());
        timeMon.setText(mechanic.getMonTime());
        timeSat.setText(mechanic.getSatTime());
        txtPhn.setText(mechanic.getContact());

        phoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + mechanic.getContact());
                Intent intent = new Intent(Intent.ACTION_DIAL, u);
                startActivity(intent);
            }
        });

        mapsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string =  "http://maps.google.com/maps?q=loc:" + mechanic.getLatitude() + "," + mechanic.getLongitude() ;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(string));
                startActivity(intent);
            }
        });
    }
}