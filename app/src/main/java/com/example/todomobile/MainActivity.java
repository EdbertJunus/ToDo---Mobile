package com.example.todomobile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.todomobile.database.UserHelper;
import com.example.todomobile.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ImageButton home_ib_btn;

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserHelper userHelper = new UserHelper("AdminId");
        Vector<User> users = userHelper.showUserList();
//        userHelper.showUserList( new UserHelper.FirestoreCallback(){
//            @Override
//            public void onCallback(Vector<User> userVector) {
//                for (User user : userVector) {
//                    users.add(user);
//                    Log.d("Data", user.getUserName());
//                }
//            }
//        });

        Integer totalUser = users.size();
        System.out.println("Total User " + totalUser);

        for (User user : users) {
            Log.d("Data di Loop", user.getUserName());
        }

        init();
    }

    public void init(){
        home_ib_btn = findViewById(R.id.homepage_ib_button);
        home_ib_btn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
