package com.mishra.pocketmech.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mishra.pocketmech.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class LoginScreen extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    String number;
    EditText numb;
    TextView send;

    FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    Double latitude, longitude;
    String address_user, city_user;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();
        numb = findViewById(R.id.code);
        send = findViewById(R.id.login);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getArea();

        if (getIntent().hasExtra("number")) {
            number = getIntent().getStringExtra("number");
        }

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //Getting the code sent by SMS
                String code = phoneAuthCredential.getSmsCode();

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {
                    numb.setText(code);
                    //verifying the code
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getApplicationContext(), "Checking Your OTP", Toast.LENGTH_SHORT).show();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, numb.getText().toString());
                signUp(credential);
            }

        });

    }

    private void checkInDB() {
        String uId = mAuth.getCurrentUser().getUid();

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("USERS/" + uId + "/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    UploadInfo info = snapshot.getValue(UploadInfo.class);

                    SharedPreferences pref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = pref.edit();
                    mEditor.putString("name", info.getName());
                    mEditor.putString("phone", info.getPhone());
                    mEditor.putString("city", city_user);
                    mEditor.putString("area", address_user);
                    mEditor.commit();

                    Intent intent = new Intent(getApplicationContext(), NumberEnter.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), DetailsUploadActivity.class);
                    intent.putExtra("number", number);
                    intent.putExtra("city", city_user);
                    intent.putExtra("area", address_user);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.error(getApplicationContext(), error.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    void signUp(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toasty.success(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                    checkInDB();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toasty.error(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    void getArea() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginScreen.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.app.AlertDialog.Builder(LoginScreen.this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(LoginScreen.this,
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
                ActivityCompat.requestPermissions(LoginScreen.this,
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
                .addOnSuccessListener(LoginScreen.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            Geocoder geocoder;
                            geocoder = new Geocoder(LoginScreen.this, Locale.getDefault());
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
                                mEditor.commit();
                            } else {

                                Toast.makeText(getApplicationContext(), "Not Detected", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(LoginScreen.this, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(LoginScreen.this,
                            "Permission accepted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(LoginScreen.this,
                            "Permission denied", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}