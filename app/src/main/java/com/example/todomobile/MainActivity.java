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

<<<<<<<<< Temporary merge branch 1
import com.google.firebase.firestore.FirebaseFirestore;

=========
>>>>>>>>> Temporary merge branch 2
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
<<<<<<<<< Temporary merge branch 1
        Map<String, Object> insertedData = new HashMap<>();
        insertedData.put("name", "Kevin");
=========

        Map<String, Object> insertedData = new HashMap<>();
        insertedData.put("name", "WAKANDA");
>>>>>>>>> Temporary merge branch 2
        insertedData.put("age", 21);
        insertedData.put("gender", "Male");

        //Jika nama collectionPath asal, dia auto kebuat di db
        //Jika db tidak diterima, dia auto id
<<<<<<<<< Temporary merge branch 1
        //        db.collection("students")
        //                .document("2201767623")
        //                .set(insertedData);
=========
//        db.collection("students")
//                .document("2201767623")
//                .set(insertedData);
>>>>>>>>> Temporary merge branch 2
        db.collection("students")
                .document()
                .set(insertedData);

<<<<<<<<< Temporary merge branch 1
        //db.collection("students").add(insertedData);

=========
        db.collection("students").add(insertedData);
>>>>>>>>> Temporary merge branch 2
    }
}