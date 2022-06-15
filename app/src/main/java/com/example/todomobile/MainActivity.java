package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        Map<String, Object> insertedData = new HashMap<>();
        insertedData.put("name", "Kevin");
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

        //db.collection("students").add(insertedData);

    }
}