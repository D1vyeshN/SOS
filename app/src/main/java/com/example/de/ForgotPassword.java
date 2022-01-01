package com.example.de;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    private TextView forEmail;

    private TextView forPhone;

    private TextView forPassword;

    private TextView forConfirmPassword;

    private TextView forOTP;

    private TextInputLayout forParentPhone;

    private TextInputLayout forParentPassword;

    private TextInputLayout forParentConfirmPassword;

    private TextInputLayout forParentOTP;

    private Button forback;

    private int RandomOTP;

    LogInDatabaseHandler DB;

    private int SMS_REQUEST_CODE = 1;


    private Button forSendOTP;

    private Button forReSendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DB = new LogInDatabaseHandler(this);

        setContentView(R.layout.activity_forgot_password);

        Random randNumber = new Random();

        RandomOTP = randNumber.nextInt((99999 - 100) + 1) + 10;

        if(ActivityCompat.checkSelfPermission(ForgotPassword.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(ForgotPassword.this, new String[]{Manifest.permission.SEND_SMS}, SMS_REQUEST_CODE);

        }

        forback = (Button) findViewById(R.id.forBack);

        forEmail = (TextView) findViewById(R.id.ForEmail);

        forPhone = (TextView) findViewById(R.id.ForPhone);

        forPassword = (TextView) findViewById(R.id.ForPassword);

        forOTP = (TextView) findViewById(R.id.ForOTP);

        forConfirmPassword = (TextView) findViewById(R.id.ForConfirmPassword);

        forParentPhone = (TextInputLayout) findViewById(R.id.ForParentPhone);

        forParentPassword = (TextInputLayout) findViewById(R.id.ForParentPassword);

        forParentConfirmPassword = (TextInputLayout) findViewById(R.id.ForParentConfirmPassword);

        forParentOTP = (TextInputLayout) findViewById(R.id.ForParentOTP);

        forSendOTP = (Button) findViewById(R.id.ForSendOTP);

        forReSendOTP = (Button) findViewById(R.id.ForReSendOTP);

        forReSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RandomOTP = randNumber.nextInt((99999 - 100) + 1) + 10;

                try{

                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(forPhone.getText().toString(), null, String.valueOf(RandomOTP), null, null);

                    Toast.makeText(getApplicationContext(), "OTP Send Succesfully", Toast.LENGTH_SHORT).show();


                }
                catch (Exception e){

                    Toast.makeText(getApplicationContext(), "Failed To send SMS", Toast.LENGTH_SHORT).show();

                }

            }
        });

        forPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(), "HINT: Sample_123", Toast.LENGTH_LONG).show();

                return false;

            }
        });

        forback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // SignUpButton.setClickable(false);

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(intent);

                finish();
            }
        });

        forSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(forEmail.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Email can't be empty", Toast.LENGTH_SHORT).show();

                    return;

                }

                if(forSendOTP.getText().toString().equals("SEND OTP")){

                    SendOTP();

                    return;

                }

                if(forSendOTP.getText().toString().equals("CHECK OTP")){

                    CHECKOTP();

                    return;

                }

                if(forSendOTP.getText().toString().equals("Update Changes")){

                    UpdateCredentials();

                    return;

                }

                int flag = 0;

                List<User> users = DB.getAllUsers();

                for(User us : users){

                    if(us.get_email().equals(forEmail.getText().toString())){

                        flag = 1;

                        forEmail.setEnabled(false);

                        break;

                    }

                }

                if(flag == 1){

                    forParentPhone.setVisibility(View.VISIBLE);

                    forSendOTP.setText("SEND OTP");

                }
                else{

                    Toast.makeText(getApplicationContext(), "User not exist", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == SMS_REQUEST_CODE){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(getApplicationContext(), "SMS Permission Granted", Toast.LENGTH_SHORT).show();

            }
            else{

                ActivityCompat.requestPermissions(ForgotPassword.this, new String[]{Manifest.permission.SEND_SMS}, SMS_REQUEST_CODE);

            }

        }

    }

    public void SendOTP(){

        if(forPhone.getText().toString().equals("")){

            Toast.makeText(getApplicationContext(), "Phone number can't be empty", Toast.LENGTH_SHORT).show();

            return;

        }

        try{

            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(forPhone.getText().toString(), null, String.valueOf(RandomOTP), null, null);

            Toast.makeText(getApplicationContext(), "OTP Send Succesfully", Toast.LENGTH_SHORT).show();

            forPhone.setEnabled(false);

            forParentOTP.setVisibility(View.VISIBLE);

            forReSendOTP.setVisibility(View.VISIBLE);

            forSendOTP.setText("CHECK OTP");

        }
        catch (Exception e){

            Toast.makeText(getApplicationContext(), "Failed To send SMS", Toast.LENGTH_SHORT).show();

        }


    }

    public void CHECKOTP(){

        if(forOTP.getText().toString().equals("")){

            Toast.makeText(getApplicationContext(), "Enter the OTP", Toast.LENGTH_SHORT).show();

            return;

        }

        if(forOTP.getText().toString().equals(String.valueOf(RandomOTP))){

            forReSendOTP.setVisibility(View.GONE);

            forParentPassword.setVisibility(View.VISIBLE);

            forParentConfirmPassword.setVisibility(View.VISIBLE);

            forOTP.setEnabled(false);

            forSendOTP.setText("Update Changes");

        }

    }

    public void UpdateCredentials(){

        if(forPassword.getText().toString().equals("") || forConfirmPassword.getText().toString().equals("")){

            Toast.makeText(getApplicationContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show();

            return;

        }

        if(forPassword.getText().toString().equals(forConfirmPassword.getText().toString())){

            if (forPhone.getText().toString().length() == 10 && isValidEmail(forEmail.getText().toString()) && isValidPassword(forPassword.getText().toString()) && forPassword.getText().toString().length() >= 8) {

                User CurrentUser = DB.getUser(forEmail.getText().toString());

                CurrentUser.set_password(forPassword.getText().toString());

                DB.updateUser(CurrentUser);

                Toast.makeText(getApplicationContext(), "Updated Successfully Credentials", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(intent);

                finish();

            }
            else {

                if (!(forPhone.getText().toString().length() == 10) ) {

                    Toast.makeText(getApplicationContext(), "Phone number is not valid", Toast.LENGTH_SHORT).show();

                }
                if (!isValidEmail(forEmail.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "Email is not valid", Toast.LENGTH_SHORT).show();

                }
                if (!(forPassword.getText().toString().length() >= 8 )){

                    Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();

                }
                if (!isValidPassword(forPassword.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Password is not valid", Toast.LENGTH_SHORT).show();

                }
            }

        }
        else{

            Toast.makeText(getApplicationContext(), "Confirmation is wrong", Toast.LENGTH_SHORT).show();

        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());


    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[@#$%^&+=!_])(?=.*[A-Za-z])(?=.*[@#$%^&+=!_])(?=.*[0-9])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}