package com.mishra.pocketmech.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mishra.pocketmech.R;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class DetailsUploadActivity extends AppCompatActivity {

    String number, name, id, city_user, address_user, number_emg_1, number_emg_2, name_1,name_2;
    EditText nameEnter, num, no_emg_1, no_emg_2, name_emg_1, name_emg_2;
    TextView submit, txtEmg;
    ImageView ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_upload);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        num = findViewById(R.id.number);
        nameEnter = findViewById(R.id.name);
        submit = findViewById(R.id.submit);
        ques = findViewById(R.id.imgQues);
        no_emg_1 = findViewById(R.id.emergency_1);
        no_emg_2 = findViewById(R.id.emergency_2);
        name_emg_1 = findViewById(R.id.name_emergency_1);
        name_emg_2 = findViewById(R.id.name_emergency_2);
        txtEmg = findViewById(R.id.txtEmg);

        ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmg.setVisibility(View.VISIBLE);
            }
        });

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
                name_1 = name_emg_1.getText().toString();
                name_2 = name_emg_2.getText().toString();
                number_emg_1 = no_emg_1.getText().toString();
                number_emg_2 = no_emg_2.getText().toString();

                if (name.isEmpty()){
                    Toasty.error(getApplicationContext(), "Name Can't Be Empty", Toasty.LENGTH_SHORT).show();
                }
                else if(name_1.isEmpty() || name_2.isEmpty() || number_emg_1.isEmpty() || number_emg_2.isEmpty()){
                    Toasty.error(getApplicationContext(), "Emergency Contact Details cannot be empty", Toasty.LENGTH_SHORT).show();
                }
                else{
                    String databasePath = "USERS/" + id +"/";
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(databasePath);

                    UploadInfo info = new UploadInfo(name, number);
                    reference.child("DETAILS").setValue(info);

                    HashMap<String, String> hash = new HashMap<>();
                    hash.put("number_1", number_emg_1);
                    hash.put("name_1", name_1);
                    hash.put("name_2", name_2);
                    hash.put("number_2", number_emg_2);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(databasePath + "emergency_contacts/");
                    databaseReference.setValue(hash);

                    SharedPreferences pref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = pref.edit();
                    mEditor.putString("name", info.getName());
                    mEditor.putString("phone", info.getPhone());
                    mEditor.putString("city", city_user);
                    mEditor.putString("area", address_user);
                    mEditor.putString("emergency_num_1", number_emg_1);
                    mEditor.putString("emergency_name_1", name_1);
                    mEditor.putString("emergency_num_2", number_emg_2);
                    mEditor.putString("emergency_name_2", name_2);
                    mEditor.commit();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}