package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton home_ib_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
//        Intent intent = new Intent(MainActivity.this, TaskActivity.class);
//        startActivity(intent);

        home_ib_btn = findViewById(R.id.homepage_ib_button);
        home_ib_btn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TaskActivity.class);
            startActivity(intent);
        });
    }
}
