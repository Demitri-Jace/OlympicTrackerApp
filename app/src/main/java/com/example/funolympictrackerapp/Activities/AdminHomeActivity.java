package com.example.funolympictrackerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.example.funolympictrackerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminHomeActivity extends AppCompatActivity {

    private CardView athletes, athlete_location, athlete_search, admin_log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        athletes = findViewById(R.id.athletes);
        athlete_location = findViewById(R.id.athlete_location);
        athlete_search = findViewById(R.id.athlete_search);
        admin_log_out = findViewById(R.id.admin_log_out);

        athletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayRegisteredAthletes();

            }
        });

        athlete_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayAthletesLocations();

            }
        });

        athlete_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchForAthletes();

            }
        });

        admin_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();

            }
        });

    }

    private void displayRegisteredAthletes() {

        Intent athletes = new Intent(getApplicationContext(), AthletesActivity.class);
        startActivity(athletes);

    }

    private void displayAthletesLocations() {

        Intent locations = new Intent(getApplicationContext(), LocationsActivity.class);
        startActivity(locations);

    }

    private void searchForAthletes() {

        Intent search = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(search);

    }

    private void logout() {

        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(login);
        finish();

    }

}