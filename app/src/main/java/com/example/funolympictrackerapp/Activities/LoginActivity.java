package com.example.funolympictrackerapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funolympictrackerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText log_email, log_password;
    private Button log_btn, reg_btn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        log_email = findViewById(R.id.user_email);
        log_password = findViewById(R.id.user_password);

        log_btn = findViewById(R.id.login_btn);
        reg_btn = findViewById(R.id.register_btn);

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateInputs();

            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);

            }
        });

    }

    private void validateInputs() {

        String user_email = log_email.getText().toString().trim();
        String user_password = log_password.getText().toString().trim();

        if (user_email.isEmpty()){

            log_email.setError("Email Address Required");
            log_email.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {

            log_email.setError("Valid Email Address Required");
            log_email.requestFocus();
            return;

        }

        if (user_password.isEmpty()){

            log_password.setError("Password Required");
            log_password.requestFocus();
            return;

        }

        if(user_password.length() < 6){

            log_password.setError("Minimum Password Characters Should Be 6");
            log_password.requestFocus();
            return;

        }
        if (user_email.equals("admin5@mail.com") && user_password.equals("admin101#5")){

            showToastMessage("Hi and Welcome Administrator");
            openAdminHome();

        }else {

            userLogInInformation(user_email, user_password);

        }

    }

    private void openAdminHome() {

        Intent admin_home = new Intent(getApplicationContext(), AdminHomeActivity.class);
        startActivity(admin_home);

    }

    private void userLogInInformation(String user_email, String user_password) {

        mAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    openHomeActivity();

                    showToastMessage("Successfully Logged In");

                }else{

                    showToastMessage("Failed to Login! Please Make Sure Your Credentials Are Correct");

                }

            }
        });

    }

    private void openHomeActivity() {

        Intent home = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(home);

    }

    private void showToastMessage(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

    }
    
}