package com.example.funolympictrackerapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funolympictrackerapp.Helper.AthleteLocations;
import com.example.funolympictrackerapp.Helper.CurrentLocation;
import com.example.funolympictrackerapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    private TextView one, two, three, four, five;

    private Button locate, save;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        one = findViewById(R.id.text);
        two = findViewById(R.id.text1);
        three = findViewById(R.id.text2);
        four = findViewById(R.id.text3);
        five = findViewById(R.id.text4);

        locate = findViewById(R.id.locate_btn);
        save = findViewById(R.id.save_locate_btn);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewCurrentLocation();

            }
        });
        
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                saveCurrentLocation();
                
            }
        });

    }

    private void viewCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(CurrentLocationActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult();
                    if (location != null){

                        try {

                            Geocoder geocoder = new Geocoder(CurrentLocationActivity.this,
                                    Locale.getDefault());

                            List<Address> address = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );

                            one.setText(Html.fromHtml(
                                    "<font color='#000000'><b>Latitude :</b>  </font>"
                                            + address.get(0).getLatitude()
                            ));
                            two.setText(Html.fromHtml(
                                    "<font color='#000000'><b>Longitude :</b>  </font>"
                                            + address.get(0).getLongitude()
                            ));
                            three.setText(Html.fromHtml(
                                    "<font color='#000000'><b>Country Name :</b>  </font>"
                                            + address.get(0).getCountryName()
                            ));
                            four.setText(Html.fromHtml(
                                    "<font color='#000000'><b>Locality :</b>  </font>"
                                            + address.get(0).getLocality()
                            ));
                            five.setText(Html.fromHtml(
                                    "<font color='#000000'><b>Address :</b>  </font>"
                                            + address.get(0).getAddressLine(0)
                            ));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

        }else{

            ActivityCompat.requestPermissions(CurrentLocationActivity.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        }

    }

    private void saveCurrentLocation() {

        String loc_one = one.getText().toString().trim();
        String loc_two = two.getText().toString().trim();
        String loc_three = three.getText().toString().trim();
        String loc_four = four.getText().toString().trim();
        String loc_five = five.getText().toString().trim();

        if(loc_one.isEmpty() || loc_two.isEmpty() || loc_three.isEmpty() || loc_four.isEmpty() || loc_five.isEmpty()){

            showToastMessage("Please Get Current Location And Then Save");
            return;

        }else{

            saveCurrentUserLocation(loc_one, loc_two, loc_three, loc_four, loc_five);

        }

    }

    private void saveCurrentUserLocation(String loc_one, String loc_two, String loc_three, String loc_four, String loc_five) {

        String user_Id = mAuth.getCurrentUser().getUid();
        CurrentLocation currentLocation = new CurrentLocation(user_Id, loc_one, loc_two, loc_three, loc_four, loc_five);
        reference.child("User Current Location").push().setValue(currentLocation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                showToastMessage("Saved To Database");

            }
        });

    }

    private void showToastMessage(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

    }

}