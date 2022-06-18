package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.todomobile.database.UserHelper;
import com.example.todomobile.model.GlobalVariable;
import com.example.todomobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Vector;

public class LoginActivity extends AppCompatActivity {

    Button login_btn_log, login_btn_regis;
    EditText login_et_email, login_et_pwd;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    UserHelper uHelper = new UserHelper();

    public static ArrayList<User> userList = new ArrayList<>();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        readUserData();

        init();
    }

    public void init(){
        login_et_email = findViewById(R.id.login_et_email);
        login_et_pwd = findViewById(R.id.login_et_password);
        login_btn_log = findViewById(R.id.login_btn_log);
        login_btn_regis = findViewById(R.id.login_btn_regis);

        // login button
        login_btn_log.setOnClickListener(view -> {
            String user_email = login_et_email.getText().toString();
            String user_pwd = login_et_pwd.getText().toString();
            String user_name = "";

            // Check if email exists
            boolean isUserExist = false;
            boolean isPassCorrect = false;
            for (User users : userList) {
                if(user_email.equals(users.getUserEmail())){
                    isUserExist = true;
                    if(user_pwd.equals(users.getUserPassword())){
                        isPassCorrect = true;
                        userId = users.getUserId();
                        user_name = users.getUserName();
                    }
                }
            }

            // Email
            if(user_email.isEmpty()){
                login_et_email.setError("Email Cannot be Empty!");
                return;
            }
            else if(!isUserExist){
                login_et_email.setError("Email is not registered!");
                return;
            }

            // Password
            else if(user_pwd.isEmpty()){
                login_et_pwd.setError("Password Cannot be Empty!");
                return;
            }
            else if(!isPassCorrect){
                login_et_pwd.setError("Password is not Correct!");
                return;
            }
            else{
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, TaskActivity.class);
                intent.putExtra("UserId", userId);
                GlobalVariable.loggedUser = new User(userId, user_email, user_name, user_pwd);
                
                intent.putParcelableArrayListExtra("UserList", userList);
                startActivity(intent);
                finish();
            }

        });

        // regis button
        login_btn_regis.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Read Current User
    private void readUserData() {
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        for (DocumentSnapshot snapshot : task.getResult()){
                            User user = new User(snapshot.getId(),
                                    snapshot.getString("UserEmail"),
                                    snapshot.getString("UserName"),
                                    snapshot.getString("UserPassword"));
                            userList.add(user);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(LoginActivity.this, "Read data failed!", Toast.LENGTH_SHORT).show();
            }
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