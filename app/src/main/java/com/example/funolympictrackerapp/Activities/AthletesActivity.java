package com.example.funolympictrackerapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.funolympictrackerapp.Adapter.AthletesAdapter;
import com.example.funolympictrackerapp.Helper.Users;
import com.example.funolympictrackerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AthletesActivity extends AppCompatActivity {

    ListView mListView;
    List<Users> athletesList;

    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athletes);

        /*mListView = findViewById(R.id.myListView);
        athletesList = new ArrayList<>();

        userReference = FirebaseDatabase.getInstance().getReference("Users");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                athletesList.clear();

                for(DataSnapshot athleteDataSnapshot : snapshot.getChildren()){

                    Users users = athleteDataSnapshot.getValue(Users.class);
                    athletesList.add(users);

                }

                AthletesAdapter athletesAdapter = new AthletesAdapter(AthletesActivity.this, athletesList);
                mListView.setAdapter(athletesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }
}