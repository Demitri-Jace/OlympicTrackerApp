package com.example.funolympictrackerapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funolympictrackerapp.Helper.Users;
import com.example.funolympictrackerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextView reg_name, reg_surname, reg_email, reg_password, reg_confirm_password;
    private Button register_btn, login_back_btn;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_name = findViewById(R.id.register_name);
        reg_surname = findViewById(R.id.register_surname);
        reg_email = findViewById(R.id.register_email);
        reg_password = findViewById(R.id.register_password);
        reg_confirm_password = findViewById(R.id.register_confirm_password);

        register_btn = findViewById(R.id.register_button);
        login_back_btn = findViewById(R.id.log_in_button);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerAthleteToFirebase();

            }
        });

        login_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);

            }
        });

    }

    private void registerAthleteToFirebase() {

        String name = reg_name.getText().toString().trim();
        String surname = reg_surname.getText().toString().trim();
        String email = reg_email.getText().toString().trim();
        String password = reg_password.getText().toString().trim();
        String confirm_password = reg_confirm_password.getText().toString().trim();

        if(name.isEmpty()){

            reg_name.setError("Name Required");
            reg_name.requestFocus();
            return;
        }

        if(surname.isEmpty()){

            reg_surname.setError("Surname Required");
            reg_surname.requestFocus();
            return;

        }

        if(email.isEmpty()){

            reg_email.setError("Email Address Required");
            reg_email.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            reg_email.setError("Provide Valid Email Address");
            reg_email.requestFocus();
            return;

        }

        if(password.isEmpty()){

            reg_password.setError("Password Required");
            reg_password.requestFocus();
            return;

        }

        if(password.length() < 6){

            reg_password.setError("Minimum Password Characters Should Be 6");
            reg_password.requestFocus();
            return;

        }

        if(confirm_password.isEmpty()){

            reg_confirm_password.setError("Confirm Password Required");
            reg_confirm_password.requestFocus();
            return;

        }

        if(!confirm_password.equals(password)){

            showToastMessage("Password Must Be The Same");
            return;

        }else{

            userRegisterInformation(name,surname, email, password, confirm_password);

        }

    }

    private void userRegisterInformation(String name, String surname, String email, String password, String confirm_password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Users user = new Users(name, surname, email, password, confirm_password);

                    database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        showToastMessage("User Successfully Registered");

                                        Intent home = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(home);

                                    }

                                    else{

                                        showToastMessage("Registration Failed, Please Try Again!");

                                    }

                                }
                            });

                }else{

                    showToastMessage("Registration Failed!");

                }

            }
        });

    }

    private void showToastMessage(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

    }

}