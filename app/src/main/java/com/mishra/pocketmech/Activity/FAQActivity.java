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
        }else
            top.setImageResource(R.drawable.rsz_bike);

        loadFAQCar(display);
    }


    private void loadFAQCar(RecyclerView display) {

        RecyclerView.LayoutManager layoutManager;

        final List<ItemFAQ> list = new ArrayList();
        layoutManager = new GridLayoutManager(this, 3);
        display.setLayoutManager(layoutManager);

        ItemFAQ faq = new ItemFAQ();
        faq.setImage(R.drawable.engine);
        faq.setName("Engine");
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
        faq.setImage(R.drawable.lights);
        faq.setName("Electronics");
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