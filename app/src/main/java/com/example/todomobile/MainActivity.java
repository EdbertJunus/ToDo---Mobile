package com.example.todomobile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ImageButton home_ib_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        Map<String, Object> insertedData = new HashMap<>();
        insertedData.put("name", "WAKANDA");
        insertedData.put("age", 21);
        insertedData.put("gender", "Male");

        //Jika nama collectionPath asal, dia auto kebuat di db
        //Jika db tidak diterima, dia auto id
//        db.collection("students")
//                .document("2201767623")
//                .set(insertedData);
        db.collection("students")
                .document()
                .set(insertedData);

        db.collection("students").add(insertedData);

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