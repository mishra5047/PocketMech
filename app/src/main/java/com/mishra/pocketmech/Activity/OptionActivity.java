package com.mishra.pocketmech.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mishra.pocketmech.R;

public class OptionActivity extends AppCompatActivity {

    ImageView bike, car;

    String need;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        need = getIntent().getStringExtra("need");

        car = findViewById(R.id.img_1);
        bike = findViewById(R.id.img_2);


        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openActivityOnNeed("car");
            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openActivityOnNeed("bike");
            }
        });

    }

    private void openActivityOnNeed(String type) {
            if (need.equals("FAQ's")){
                Intent intent = new Intent(getApplicationContext(), FAQActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }else if(need.equals("Insurance")){

            }
    }
}