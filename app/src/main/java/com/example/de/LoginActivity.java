package com.example.de;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 3000;

    private int ID = -1;

    private String StateOfLogIn = "false";

    private Button LogInButton;

    private Button SignInButton;

    private Button ForgotButton;

    private TextView logEmail;

    private TextView logPassword;

    LogInDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = new LogInDatabaseHandler(this);

        List<User> users = db.getAllUsers();

        if(users.size() != 0){

            for(User us : users){


                if(us.get_logStatus().equals("true")){

                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);

                    intent.putExtra("userId", us.getID());

                    intent.putExtra("userName", us.get_email());

                    startActivity(intent);

                    finish();

                }


            }

        }


        setContentView(R.layout.activity_login);

        LogInButton = (Button) findViewById(R.id.signupButton);

        SignInButton = (Button) findViewById(R.id.signupLogIn);

        ForgotButton = (Button) findViewById(R.id.logForgot);

        logEmail = (TextView) findViewById(R.id.signEmail);

        logPassword = (TextView) findViewById(R.id.signPassword);

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = logEmail.getText().toString();

                String userPassword = logPassword.getText().toString();

                if(userEmail.equals("") || userPassword.equals("")){

                    Toast.makeText(getApplicationContext(), "Please fill details", Toast.LENGTH_SHORT).show();

                }
                else{

                    int flag = 0;

                    List<User> Users = db.getAllUsers();

                    for (User us : Users) {

                        if(us.get_email().equals(userEmail) && us.get_password().equals(userPassword)){

                            us.set_logStatus("true");

                            db.updateUser(us);

                            ID = us.getID();

                            Log.d("CurrentuserId", String.valueOf(ID));

                            SignInButton.setClickable(false);

                            ForgotButton.setClickable(false);

                            flag = 1;

                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);

                            intent.putExtra("userId", ID);

                            intent.putExtra("userName", us.get_email());

                            startActivity(intent);

                            finish();

                            break;

                        }

                    }
                    if(flag == 0){

                        Toast.makeText(getApplicationContext(), "Doesn't Exists", Toast.LENGTH_SHORT).show();

                    }


                }

            }
        });

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogInButton.setClickable(false);

                ForgotButton.setClickable(false);

                Intent intent = new Intent(getApplicationContext(), SignUp.class);

                startActivity(intent);

                finish();

            }
        });

        ForgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogInButton.setClickable(false);

                SignInButton.setClickable(false);

                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);

                startActivity(intent);

                finish();

            }
        });

    }

}