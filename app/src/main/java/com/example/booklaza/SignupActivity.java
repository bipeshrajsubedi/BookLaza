package com.example.booklaza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.booklaza.Database.DBHelper;
import com.example.booklaza.models.User;

public class SignupActivity extends AppCompatActivity {

    EditText fname,lname,email,phone,pass, cpass;
    Button btn_reg, btn_login;
    ProgressBar progressBar;
    DBHelper helper;
    String fname_str,lname_str,email_str,phone_str,pass_str,cpass_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // db helper
        helper = new DBHelper(SignupActivity.this);
        // initializing views
        fname = findViewById(R.id.register_et_fname);
        lname = findViewById(R.id.register_et_lname);
        email = findViewById(R.id.register_et_email);
        phone = findViewById(R.id.register_et_phone);
        pass = findViewById(R.id.register_et_pass);
        cpass = findViewById(R.id.register_et_cpass);
        btn_reg = findViewById(R.id.register_button);
        btn_login = findViewById(R.id.goto_login_button);
        progressBar = findViewById(R.id.progressbar_signup);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting values from input
                fname_str = fname.getText().toString();
                lname_str = lname.getText().toString();
                email_str = email.getText().toString();
                phone_str = phone.getText().toString();
                pass_str = pass.getText().toString();
                cpass_str = cpass.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                System.out.println(pass_str.length()+pass_str);

                if (TextUtils.isEmpty(fname_str) || TextUtils.isEmpty(lname_str)
                    || TextUtils.isEmpty(email_str) || TextUtils.isEmpty(phone_str)
                        || TextUtils.isEmpty(pass_str) || TextUtils.isEmpty(cpass_str)){
                    Toast.makeText(SignupActivity.this, "Please provide required data in the input fields.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else if (pass_str.length() < 6){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignupActivity.this, "Password must be atleast 6 characters.", Toast.LENGTH_SHORT).show();
                }
                else if (!TextUtils.equals(pass_str,cpass_str)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignupActivity.this, "Password and confirm password fields donot match", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User(fname_str,lname_str,phone_str,email_str,pass_str);
                    if (Config.checkNetworkStatus(SignupActivity.this)) {
                        registerUser(user);
                    }else {
                        Toast.makeText(SignupActivity.this, "No internet connection. Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // goto login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void registerUser(User user) {
        long success = helper.addUSer(user);
        if (success == -1){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(SignupActivity.this, "Failed to register. Please try again!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(SignupActivity.this, "Successfully registered!!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}