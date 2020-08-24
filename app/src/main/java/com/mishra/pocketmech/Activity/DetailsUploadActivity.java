package com.mishra.pocketmech.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mishra.pocketmech.R;

import es.dmoral.toasty.Toasty;

public class DetailsUploadActivity extends AppCompatActivity {

    String number, name, id, city_user, address_user;
    EditText nameEnter, num;
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_upload);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        num = findViewById(R.id.number);
        nameEnter = findViewById(R.id.name);
        submit = findViewById(R.id.submit);

        if (getIntent().hasExtra("number")){
            number = getIntent().getStringExtra("number");
            num.setText(number);
            city_user = getIntent().getStringExtra("city");
            address_user = getIntent().getStringExtra("area");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEnter.getText().toString();

                if (name.isEmpty()){
                    Toasty.error(getApplicationContext(), "Name Can't Be Empty", Toasty.LENGTH_SHORT).show();
                }
                else{
                    String databasePath = "USERS/" + id +"/";
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(databasePath);

                    UploadInfo info = new UploadInfo(name, number);
                    reference.child("DETAILS").setValue(info);

                    SharedPreferences pref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = pref.edit();
                    mEditor.putString("name", info.getName());
                    mEditor.putString("phone", info.getPhone());
                    mEditor.putString("city", city_user);
                    mEditor.putString("area", address_user);
                    mEditor.commit();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}