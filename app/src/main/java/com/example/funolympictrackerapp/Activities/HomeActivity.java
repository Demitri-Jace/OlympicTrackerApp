package com.example.funolympictrackerapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.LocalServerSocket;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funolympictrackerapp.Helper.AthleteLocationDetails;
import com.example.funolympictrackerapp.Helper.AthleteLocations;
import com.example.funolympictrackerapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    private CardView loc_details, loc_add, loc_current, log_out_btn;

     private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        loc_details = findViewById(R.id.location_details);
        loc_add = findViewById(R.id.add_location);
        loc_current = findViewById(R.id.current_location);
        log_out_btn = findViewById(R.id.log_out);

        loc_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogToAddLocationDetails();

            }
        });

        loc_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogToAddLocation();

            }
        });

        loc_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCurrentLocation();
                /*viewCurrentLocationDialog();*/

            }
        });

        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                Intent log_in_activity = new Intent(HomeActivity.this, LoginActivity.class);
                log_in_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(log_in_activity);

            }
        });

    }

    private void getCurrentLocation() {

        Intent current_location = new Intent(getApplicationContext(), CurrentLocationActivity.class);
        startActivity(current_location);

    }

    private void viewCurrentLocationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Current Location");
        builder.setMessage("This Is Where You Are Right Now");

        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.view_current_location_dialog, null);

        final TextView one = view.findViewById(R.id.text);
        final TextView two = view.findViewById(R.id.text1);
        final TextView three = view.findViewById(R.id.text2);
        final TextView four = view.findViewById(R.id.text3);
        final TextView five = view.findViewById(R.id.text4);

        builder.setView(view);

        builder.setPositiveButton("location", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {

                            Location location = task.getResult();
                            if (location != null){

                                try {

                                    Geocoder geocoder = new Geocoder(HomeActivity.this,
                                            Locale.getDefault());

                                    List<Address> address = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                    );

                                    one.setText(Html.fromHtml(
                                            "<font color='#000000'><b>Latitude :</b><br></font>"
                                            + address.get(0).getLatitude()
                                    ));
                                    two.setText(Html.fromHtml(
                                            "<font color='#000000'><b>Longitude :</b><br></font>"
                                                    + address.get(0).getLongitude()
                                    ));
                                    three.setText(Html.fromHtml(
                                            "<font color='#000000'><b>Country Name :</b><br></font>"
                                                    + address.get(0).getCountryName()
                                    ));
                                    four.setText(Html.fromHtml(
                                            "<font color='#000000'><b>Locality :</b><br></font>"
                                                    + address.get(0).getLocality()
                                    ));
                                    five.setText(Html.fromHtml(
                                            "<font color='#000000'><b>Address :</b><br></font>"
                                                    + address.get(0).getAddressLine(0)
                                    ));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    });

                }
                else{

                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }

            }

        });

        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        builder.show();


    }

    private void showDialogToAddLocation() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Athlete Locations");
        builder.setMessage("Fill In Your Departure And Arrival Location");

        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.add_locations_dialog, null);

        final EditText departure = view.findViewById(R.id.dep_location);
        final EditText arrival = view.findViewById(R.id.arr_location);

        builder.setView(view);

        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (TextUtils.isEmpty(departure.getText().toString())){

                    departure.setError("Please Fill In");

                }
                if (TextUtils.isEmpty(arrival.getText().toString())){

                    arrival.setError("Please Fill In");

                }
                else{

                    saveLocations(departure.getText().toString(),
                            arrival.getText().toString());

                }

            }
        });

        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        builder.show();

    }

    private void saveLocations(String departure, String arrival) {

        String user_Id = mAuth.getCurrentUser().getUid();
        AthleteLocations athleteLocations = new AthleteLocations(user_Id, departure, arrival);
        reference.child("User Locations").push().setValue(athleteLocations).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                showToastMessage("Saved To Database");

            }
        });

    }

    private void showDialogToAddLocationDetails() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location Details");
        builder.setMessage("Fill In All Your Location Details");

        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.add_location_details_dialog, null);

        final EditText venue = view.findViewById(R.id.venue_name);
        final EditText date = view.findViewById(R.id.date_attend);
        final EditText time = view.findViewById(R.id.time_attend);

        builder.setView(view);

        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(TextUtils.isEmpty(venue.getText().toString())){

                    venue.setError("Please Fill In");

                }
                if (TextUtils.isEmpty(date.getText().toString())){

                    date.setError("Please Fill In");

                }
                if (TextUtils.isEmpty(time.getText().toString())){

                    time.setError("Please Fill In");

                }
                else{

                    saveLocationDetails(venue.getText().toString(),
                            date.getText().toString(),
                            time.getText().toString());

                }

            }
        });

        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        builder.show();

    }

    private void saveLocationDetails(String venue, String date, String time) {

        String user_Id = mAuth.getCurrentUser().getUid();
        AthleteLocationDetails athleteLocationDetails = new AthleteLocationDetails(user_Id, venue, date, time);
        reference.child("User Location Details").push().setValue(athleteLocationDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                showToastMessage("Saved To Database");

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser!=null){

        }else {

            startActivity(new Intent(this,LoginActivity.class));
            finish();

        }

    }

    private void showToastMessage(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

    }

}