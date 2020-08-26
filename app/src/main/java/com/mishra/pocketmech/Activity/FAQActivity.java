package com.mishra.pocketmech.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mishra.pocketmech.Adapters.FAQ.FAQAdapter;
import com.mishra.pocketmech.Adapters.FAQ.ItemFAQ;
import com.mishra.pocketmech.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    ImageView top;
    LinearLayout search;
    RecyclerView display;
    EditText searchIn;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fep_layout);

        top = findViewById(R.id.topImage);
        search = findViewById(R.id.searchLay);
        display = findViewById(R.id.problemRec);
        searchIn = findViewById(R.id.searchIn);

        type = getIntent().getStringExtra("type");
        if(type.equalsIgnoreCase("car")){
            top.setImageResource(R.drawable.car);
            loadFAQCar(display);
        }else {
            top.setImageResource(R.drawable.rsz_bike);
            loadFAQBike(display);
        }
    }


    private void loadFAQCar(RecyclerView display) {
        RecyclerView.LayoutManager layoutManager;

        final List<ItemFAQ> list = new ArrayList();
        list.clear();
        layoutManager = new GridLayoutManager(this, 3);
        display.setLayoutManager(layoutManager);

        ItemFAQ faq = new ItemFAQ();
        faq.setImage(R.drawable.alarm);
        faq.setName("Alarm");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.bumper);
        faq.setName("Bumper");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.chassis);
        faq.setName("Chassis");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.dashboard);
        faq.setName("Dashboard");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.car_door);
        faq.setName("Door");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.lights);
        faq.setName("Electronics");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.engine);
        faq.setName("Engine");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.fuel);
        faq.setName("Fuel");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.suspension);
        faq.setName("Suspension");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.wheel);
        faq.setName("Tyres");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.lubricant);
        faq.setName("Lubricants");
        list.add(faq);


        faq = new ItemFAQ();
        faq.setImage(R.drawable.spray_paint);
        faq.setName("Paint");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.steering_wheel);
        faq.setName("Steering");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.towing_vehicle);
        faq.setName("Tow");
        list.add(faq);

        FAQAdapter adapter = new FAQAdapter(list, this);
        display.setLayoutManager(layoutManager);
        display.setAdapter(adapter);
    }

    private void loadFAQBike(RecyclerView display) {

        RecyclerView.LayoutManager layoutManager;

        final List<ItemFAQ> list = new ArrayList();
        layoutManager = new GridLayoutManager(this, 3);
        display.setLayoutManager(layoutManager);

        ItemFAQ faq = new ItemFAQ();
        faq.setImage(R.drawable.chain_bike);
        faq.setName("Chain");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.engine_bike);
        faq.setName("Engine");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.gear_bike);
        faq.setName("Gear");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.headlamp_bike);
        faq.setName("Lights");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.helmet_bike);
        faq.setName("Accessories");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.speedometer_bike_1);
        faq.setName("Speedometer");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.suspension_bike);
        faq.setName("Suspension");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.wheels_bike);
        faq.setName("Wheels");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.spray_paint);
        faq.setName("Paint");
        list.add(faq);

        faq = new ItemFAQ();
        faq.setImage(R.drawable.towing_vehicle);
        faq.setName("Tow");
        list.add(faq);

        FAQAdapter adapter = new FAQAdapter(list, this);
        display.setLayoutManager(layoutManager);
        display.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}