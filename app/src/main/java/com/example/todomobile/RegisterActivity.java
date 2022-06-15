package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    Button register_btn_log, register_btn_regis;
    EditText register_et_name, register_et_email, register_et_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
            String user_name = register_et_name.getText().toString();
            String user_email = register_et_email.getText().toString();
            String user_pwd = register_et_pwd.getText().toString();

            Integer count_confirmed = 0;
            // Name
            if(user_name.isEmpty()){
                register_et_name.setError("Name Cannot be Empty!");
                return;
            }
            else if(user_name.length() < 5){
                register_et_name.setError("Name Must be At Least 5 Characters!");
                return;
            }
            else{
                count_confirmed++;
            }

            Integer count = 0;
            // Count @
            for (char ch : user_email.toCharArray()) {
                if(ch == '@') count++;
            }

            // Check if email exists
//            boolean isUserExist = false;
//            for (User users : userList) {
//                if(get_user_email.equals(user.getUser_email())){
//                    isUserExist = true;
//                }
//            }

            // Email
            if(user_email.isEmpty()){
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
//            else if(isUserExist){
//                user_email.setError("Email Already Registered");
//                return;
//            }
            else{
                count_confirmed++;
            }

            // Password
            if(user_pwd.length() < 8){
                register_et_pwd.setText("");
                register_et_pwd.setError("Password Must Be At Least 8 Characters!");
                return;
            }
            else if(!isAlphanum(user_pwd)){
                register_et_pwd.setText("");
                register_et_pwd.setError("Password Must Contain Both Alphabet and Numeric! (Minimum 1 Big Letter, 1 Small Letter, 1 Number)");
                return;
            }
            else{
                count_confirmed++;
            }

            // Insert to db
            if(count_confirmed == 3){

            }

        });

        register_btn_log.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
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