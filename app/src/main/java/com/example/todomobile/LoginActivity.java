package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    Button login_btn_log, login_btn_regis;
    EditText login_et_email, login_et_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void init(){
        login_et_email = findViewById(R.id.login_et_email);
        login_et_pwd = findViewById(R.id.login_et_password);
        login_btn_log = findViewById(R.id.login_btn_log);
        login_btn_regis = findViewById(R.id.login_btn_regis);

        // login button

        // regis button
        login_btn_regis.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}