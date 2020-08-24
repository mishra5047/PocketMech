package com.mishra.pocketmech.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mishra.pocketmech.R;

import es.dmoral.toasty.Toasty;

public class NumberEnter extends AppCompatActivity {

    FirebaseAuth auth;
    EditText num;
    TextView getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_enter);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null)
            startActivity(new Intent(getApplicationContext(), MainActivity.class));


        num = findViewById(R.id.number);
        getStarted = findViewById(R.id.started);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = num.getText().toString();

                if (number.length() != 10){

                    Toasty.error(getApplicationContext(), "Invalid Number Entered", Toasty.LENGTH_SHORT).show();
                }

                else{
                    Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                    intent.putExtra("number", number);
                    startActivity(intent);
                }

            }
        });

    }
}