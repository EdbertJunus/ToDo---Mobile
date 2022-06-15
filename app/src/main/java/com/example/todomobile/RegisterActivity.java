package com.example.todomobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todomobile.database.UserHelper;
import com.example.todomobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    Button register_btn_log, register_btn_regis;
    EditText register_et_name, register_et_email, register_et_pwd;
    String user_name, user_email, user_pwd;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    UserHelper uHelper = new UserHelper();

    private Vector<User> userList = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        readUserData();

        init();
    }

    public void init(){
        register_et_name = findViewById(R.id.register_et_name);
        register_et_email = findViewById(R.id.register_et_email);
        register_et_pwd = findViewById(R.id.register_et_password);
        register_btn_regis = findViewById(R.id.register_btn_regis);
        register_btn_log = findViewById(R.id.register_btn_log);

        // Register Validation
        register_btn_regis.setOnClickListener(view -> {
            user_name = register_et_name.getText().toString();
            user_email = register_et_email.getText().toString();
            user_pwd = register_et_pwd.getText().toString();

            Integer count = 0;
            // Count @
            for (char ch : user_email.toCharArray()) {
                if(ch == '@') count++;
            }

            // Check if email exists
            boolean isUserExist = false;
            for (User users : userList) {
                if(user_email.equals(users.getUserEmail())){
                    isUserExist = true;
                }
            }

            // Name
            if(user_name.isEmpty()){
                register_et_name.setError("Name Cannot be Empty!");
                return;
            }
            else if(user_name.length() < 5){
                register_et_name.setError("Name Must be At Least 5 Characters!");
                return;
            }

            // Email
            else if(user_email.isEmpty()){
                register_et_email.setError("Email Cannot be Empty!");
                return;
            }
            else if(!user_email.endsWith(".com")){
                register_et_email.setError("Email Must Ends With . com!");
                return;
            }
            else if(user_email.startsWith("@")){
                register_et_email.setError("Email Cannot Start With @!");
                return;
            }
            else if(count != 1){
                register_et_email.setError("Email Can Only Contain One @!");
                return;
            }
//            Check arraylist
            else if(isUserExist){
                register_et_email.setError("Email Already Registered");
                return;
            }

            // Password
            else if(user_pwd.length() < 8){
                register_et_pwd.setText("");
                register_et_pwd.setError("Password Must Be At Least 8 Characters!");
                return;
            }
            else if(!isAlphanum(user_pwd)){
                register_et_pwd.setText("");
                register_et_pwd.setError("Password Must Contain Both Alphabet and Numeric! (Minimum 1 Big Letter, 1 Small Letter, 1 Number)");
                return;
            }

            // Insert to db
            else{
                uHelper.addNewUser(user_name, user_email, user_pwd, getApplicationContext());
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        });

        register_btn_log.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
                Toast.makeText(RegisterActivity.this, "Read data failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check Pass Alphanumeric
    public Boolean isAlphanum(String password){
        final String regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).+$";
        Pattern pass_pattern = Pattern.compile(regexp);

        Matcher isPassMatch = pass_pattern.matcher(password);
        return isPassMatch.matches();
    }
}