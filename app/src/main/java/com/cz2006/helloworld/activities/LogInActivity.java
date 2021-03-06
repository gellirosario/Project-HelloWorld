package com.cz2006.helloworld.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.managers.SessionManager;
import com.cz2006.helloworld.models.User;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Represents Log In Activity
 * where User can log in to the application
 *
 * @author Rosario Gelli Ann
 *
 */
public class LogInActivity extends AppCompatActivity {

    private TextInputEditText inputEmail;
    private TextInputEditText inputPassword;
    private Button btnLogIn;
    private Button btnSignUp;

    private AccountManager accountManager;
    private SessionManager sessionManager;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getSupportActionBar().hide();

        sessionManager = new SessionManager(getApplicationContext());
        accountManager = new AccountManager(getApplicationContext());

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        accountManager.open(); // Open Database Connection

        // Log In Button Click Event
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();

                logIn(email,password);

            }
        });

        // Sign Up Button On Click Event
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(new Intent(LogInActivity.this, SignUpActivity.class),
                            ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this).toBundle());
                } else {
                    // Swap without transition
                    startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
                }
            }
        });

    }

    public void logIn(String email, String password){
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(getApplicationContext(), "Please fill in all details", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "Please enter a correct email address", Toast.LENGTH_SHORT).show();
        } else if (!accountManager.checkExistingEmail(email)) {
            Toast.makeText(getApplicationContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
        } else {
            if (accountManager.authenticate(email, password)) {
                // Success
                Toast.makeText(getApplicationContext(), "Logged in successfully!", Toast.LENGTH_SHORT).show();

                // Get user info from Database
                User currentUser = accountManager.getAccount(email, password);

                // Set log in session
                sessionManager.createLoginSession(currentUser.getUserID());

                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    // Apply activity transition
                    startActivity(new Intent(LogInActivity.this, BottomNavigationActivity.class));

                    ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this).toBundle();
                } else {
                    // Swap without transition
                    startActivity(new Intent(LogInActivity.this, BottomNavigationActivity.class));
                }
            } else {
                // Failed
                Toast.makeText(getApplicationContext(), "Incorrect email/password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Close database connection
        if (accountManager != null) {
            accountManager.close();
            accountManager = null;
        }
    }
}
