package com.mishra.pocketmech.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mishra.pocketmech.R;

import es.dmoral.toasty.Toasty;

public class UserProfile extends AppCompatActivity {

    TextView name_1, name_2, num_1, num_2, user_name, user_no;
    LinearLayout whyLayout;
    ImageView edit_1, edit_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        loadViews();
        loadData();
        functionality();
    }

    private void functionality() {
        edit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getApplicationContext(), "Coming Soon", Toasty.LENGTH_SHORT).show();
            }
        });

        edit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getApplicationContext(), "Coming Soon", Toasty.LENGTH_SHORT).show();
            }
        });

        whyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.error(getApplicationContext(), "Required To Use SOS Feature").show();
            }
        });
    }


    private void loadData() {
        SharedPreferences preferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        final String n_1 = preferences.getString("emergency_num_1", "");
        final String n_2 = preferences.getString("emergency_num_2", "");
        final String na_1 = preferences.getString("emergency_name_1","");
        final String na_2 = preferences.getString("emergency_name_2","");
        final String name = preferences.getString("name","");
        final String num = preferences.getString("phone","");

        name_1.setText(na_1);
        name_2.setText(na_2);
        num_1.setText(n_1);
        num_2.setText(n_2);
        user_name.setText(name);
        user_no.setText(num);
    }

    private void loadViews() {
        user_name = findViewById(R.id.user_name);
        user_no = findViewById(R.id.user_no);
        name_1 = findViewById(R.id.name_1);
        name_2 = findViewById(R.id.name_2);
        num_1 = findViewById(R.id.num_1);
        num_2 = findViewById(R.id.num_2);
        whyLayout = findViewById(R.id.why_layout);
        edit_1 = findViewById(R.id.edit_1);
        edit_2 = findViewById(R.id.edit_2);
    }
}