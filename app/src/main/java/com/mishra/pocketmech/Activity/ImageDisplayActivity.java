package com.mishra.pocketmech.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mishra.pocketmech.R;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    String name, url, type;
    TextView textName;
    ImageView image, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        textName = findViewById(R.id.nameMech);
        image = findViewById(R.id.imgMech);
        back = findViewById(R.id.back);

        if (getIntent().hasExtra("name")){
            name = getIntent().getStringExtra("name");
            url = getIntent().getStringExtra("url");
            type = getIntent().getStringExtra("type");
            textName.setText(name);
            Picasso.get().load(url).into(image);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MechanicsListActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
    }


}