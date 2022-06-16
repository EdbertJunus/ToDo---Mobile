package com.example.todomobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todomobile.database.UserHelper;
import com.example.todomobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {
    Button profile_btn_changepwd;
    EditText profile_et_oldpwd, profile_et_newpwd;
    TextView profile_tv_name, profile_tv_email;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<User> userList = new ArrayList<>();
    private String userId, userName, userEmail, userPwd, userOldPwd, userNewPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        readUserData();
        init();
    }

    public void init(){
        profile_tv_name = findViewById(R.id.profile_tv_name);
        profile_tv_email = findViewById(R.id.profile_tv_email);
        profile_et_oldpwd = findViewById(R.id.profile_et_oldpwd);
        profile_et_newpwd = findViewById(R.id.profile_et_newpwd);
        profile_btn_changepwd = findViewById(R.id.profile_btn_changepwd);

        // Get User List and Id
        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        userList = intent.getParcelableArrayListExtra("UserList");
        for (User user:userList) {
            if(user.getUserId().equals(userId)){
                userName = user.getUserName();
                userEmail = user.getUserEmail();
                userPwd = user.getUserPassword();
                break;
            }
        }

        // Set textview
        profile_tv_name.setText(userName);
        profile_tv_email.setText(userEmail);

        profile_btn_changepwd.setOnClickListener(view -> {
            userOldPwd = profile_et_oldpwd.getText().toString();
            userNewPwd = profile_et_newpwd.getText().toString();

            // Validation
            if(userOldPwd.isEmpty() || userNewPwd.isEmpty()){
                Toast.makeText(this, "Please Insert Both Password!", Toast.LENGTH_SHORT).show();
            }
            else if(!userOldPwd.equals(userPwd)){
                Toast.makeText(this, "Old Password Incorrect!", Toast.LENGTH_SHORT).show();
                profile_et_oldpwd.setText("");
                profile_et_newpwd.setText("");
            }
            else if(userOldPwd.equals(userPwd)){
                if(userNewPwd.length() < 8){
                    profile_et_oldpwd.setText("");
                    profile_et_newpwd.setError("Password Must Be At Least 8 Characters!");
                    return;
                }
                else if(!isAlphanum(userNewPwd)){
                    profile_et_oldpwd.setText("");
                    profile_et_newpwd.setError("Password Must Contain Both Alphabet and Numeric! (Minimum 1 Big Letter, 1 Small Letter, 1 Number)");
                    return;
                }
                else{
                    // Update password
                    DocumentReference documentReference =
                            db.collection("user")
                            .document(userId);
                    documentReference.update(
                            "UserPassword", userNewPwd
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProfileActivity.this, "Password Updated!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(ProfileActivity.this, "Password Update Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Ganti ke task activity
                    Intent newIntent = new Intent(ProfileActivity.this, LoginActivity.class);
                    newIntent.putExtra("UserId", userId);
//                Toast.makeText(this, "User Id: " + userId, Toast.LENGTH_SHORT).show();
                    startActivity(newIntent);
                    finish();
                }
            }

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
                        Toast.makeText(ProfileActivity.this, "Data Load Successfully!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ProfileActivity.this, "Read data failed!", Toast.LENGTH_SHORT).show();
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