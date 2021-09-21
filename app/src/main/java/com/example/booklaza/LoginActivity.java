package com.example.booklaza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.booklaza.Database.DBHelper;

public class LoginActivity extends AppCompatActivity {

    DBHelper helper;
    EditText et_email, et_pass;
    Button btn_login, btn_signup;
    ProgressBar progressBar;

    @Override
    protected void onResume() {
        et_email.setText("");
        et_pass.setText("");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // DB Helper
        helper = new DBHelper(LoginActivity.this);
        // Initialize view
        et_email = findViewById(R.id.login_et_email);
        et_pass = findViewById(R.id.login_et_pass);
        btn_login = findViewById(R.id.login_button);
        btn_signup = findViewById(R.id.signup_button);
        progressBar = findViewById(R.id.progressbar_login);

        // on clicking login button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // check network
                Boolean status = Config.checkNetworkStatus(LoginActivity.this);
                if (status){
                    progressBar.setVisibility(View.VISIBLE);
                    String email = et_email.getText().toString();
                    String password = et_pass.getText().toString();

                    // check login data from database
                    Boolean success = helper.getUser(email,password);
                    if (success){
                        Config.USER_ID = email;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        progressBar.setVisibility(View.GONE);
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "No user found. Please enter valid details.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "No internet connection. please try again!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // on clicking signup button
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}