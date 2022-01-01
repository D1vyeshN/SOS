package com.example.de;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profile extends AppCompatActivity {

    private TextView ProName;

    private  TextView ProEmail;

    private TextView ProPhone;

    private TextView ProPassword;

    private ImageButton allEditButton;

    private TextInputLayout togglePassword;

    private int ID;

    private BottomNavigationView bottomNavigationView;

    private String UserName;

    private NavigationView navView;

    LogInDatabaseHandler DB;

    ImageButton ContactView;

    ImageButton LocationView;

    private DrawerLayout dLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DB = new LogInDatabaseHandler(this);

        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        ID = intent.getIntExtra("userId", -1);

        UserName = intent.getStringExtra("userName");

//        ContactView = (ImageButton) findViewById(R.id.ProContactView);
//
//        LocationView = (ImageButton) findViewById(R.id.ProLocationView);

        ProName = (TextView) findViewById(R.id.ProName);

        ProEmail = (TextView) findViewById(R.id.ProEmail);

        ProPhone = (TextView) findViewById(R.id.ProPhone);

        ProPassword = (TextView) findViewById(R.id.ProPassword);

        togglePassword = (TextInputLayout) findViewById((R.id.ParentProPassword));

        allEditButton = (ImageButton) findViewById(R.id.AllEditButton);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);

        setContent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @SuppressLint("RtlHardcoded")

            @Override

            public void onClick(View view) {

                dLayout.openDrawer(Gravity.LEFT);
            }

        });

        setNavigationDrawer();

        ConstraintLayout Conname = (ConstraintLayout) navView.getMenu().getItem(0).getActionView();

        TextView Profilename = (TextView) Conname.findViewById(R.id.profilename);

        User CUser = DB.getUser(UserName);

        Profilename.setText((CharSequence)(CUser.getName()));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (item.isChecked()){

                    item.setChecked(false);

                }
                else{

                    item.setChecked(true);

                }

                if (itemId == R.id.navigation_location){

                    Intent intent = new Intent(Profile.this, location.class);

                    intent.putExtra("userId", ID);

                    intent.putExtra("userName", UserName);

                    startActivity(intent);

                    finish();

                }
                else if (itemId == R.id.navigation_contact){

                    Intent intent = new Intent(Profile.this, Dashboard.class);

                    intent.putExtra("userId", ID);

                    intent.putExtra("userName", UserName);

                    startActivity(intent);

                    finish();

                }

                return false;
            }
        });

//        ContactView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Profile.this, Dashboard.class);
//
//                intent.putExtra("userId", ID);
//
//                intent.putExtra("userName", UserName);
//
//                startActivity(intent);
//
//                finish();
//
//            }
//        });
//
//        LocationView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Profile.this, location.class);
//
//                intent.putExtra("userId", ID);
//
//                intent.putExtra("userName", UserName);
//
//                startActivity(intent);
//
//                finish();
//
//            }
//        });

        ProPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(), "HINT: Sample_123", Toast.LENGTH_LONG).show();

                return false;

            }
        });

        ProEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(), "HINT: sample@gmail.com", Toast.LENGTH_LONG).show();

                return false;

            }
        });

        allEditButton.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("UseCompatLoadingForDrawables")

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

            @Override

            public void onClick(View v) {

//              Objects.equal(((v.getBackground()).getConstantState()), ((getResources().getDrawable(R.drawable.ic_baseline_edit_24)).getConstantState()))

//                ImageButton myAllEditButton = (ImageButton) (v);

                if(!ProName.isEnabled() && !ProEmail.isEnabled() && !ProPassword.isEnabled() && !ProPhone.isEnabled()){

                    v.setBackground(getDrawable(R.drawable.ic_baseline_check_circle_24));

                    ProName.setEnabled(true);

                    ProEmail.setEnabled(true);

                    ProPhone.setEnabled(true);

                    ProPassword.setEnabled(true);

                    togglePassword.setPasswordVisibilityToggleEnabled(true);
                }
                else{

                    if(ProName.getText().toString().equals("") || ProEmail.getText().toString().equals("") || ProPhone.getText().toString().equals("") || ProPassword.getText().toString().equals("")){


                        Toast.makeText(getApplicationContext(), "Inputs can't be empty", Toast.LENGTH_SHORT).show();


                    }
                    else{

                        if (ProPhone.getText().toString().length() == 10 && isValidEmail(ProEmail.getText().toString()) && isValidPassword(ProPassword.getText().toString()) && ProPassword.getText().toString().length() >= 8) {

                            v.setBackground(getDrawable(R.drawable.ic_baseline_edit_24));

                            User CurrentUser = DB.getUser(UserName);

                            CurrentUser.setName(ProName.getText().toString());

                            CurrentUser.set_email(ProEmail.getText().toString());

                            CurrentUser.setPhoneNumber(ProPhone.getText().toString());

                            CurrentUser.set_password(ProPassword.getText().toString());

                            DB.updateUser(CurrentUser);

                            UserName = ProEmail.getText().toString();

                            setContent();

                            ProName.setEnabled(false);

                            ProEmail.setEnabled(false);

                            ProPhone.setEnabled(false);

                            ProPassword.setEnabled(false);

                            togglePassword.setPasswordVisibilityToggleEnabled(false);

                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                        }
                        else {

                            if (!(ProPhone.getText().toString().length() == 10) ) {

                                Toast.makeText(getApplicationContext(), "Phone number is not valid", Toast.LENGTH_SHORT).show();

                            }
                            if (!isValidEmail(ProEmail.getText().toString())) {

                                Toast.makeText(getApplicationContext(), "Email is not valid", Toast.LENGTH_SHORT).show();

                            }
                            if (!(ProPassword.getText().toString().length() >= 8 )){

                                Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();

                            }
                            if (!isValidPassword(ProPassword.getText().toString())){

                                Toast.makeText(getApplicationContext(), "Password is not valid", Toast.LENGTH_SHORT).show();

                            }
                        }


                    }


                }

            }
        });

    }

    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navView = (NavigationView) findViewById(R.id.navigation);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override

            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                if (itemId == R.id.second) {

                    stopService(new Intent(getApplicationContext(), MyService.class));

//                    Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();

                    User CurrentUser = DB.getUser(UserName);

                    CurrentUser.set_logStatus("false");

                    DB.updateUser(CurrentUser);

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                    startActivity(intent);

                    finish();

                }
                else if (itemId == R.id.first){

                    Intent intent = new Intent(Profile.this, appinfo.class);
                    startActivity(intent);
//            finish();

                }
                else if (itemId == R.id.third){

                    Intent intent = new Intent(Profile.this, aboutus.class);
                    startActivity(intent);
//            finish();

                }

//                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void setContent(){

        User CurrentUser = DB.getUser(UserName);

        ProName.setText(CurrentUser.getName());

        ProEmail.setText(CurrentUser.get_email());

        ProPhone.setText(CurrentUser.getPhoneNumber());

        ProPassword.setText(CurrentUser.get_password());

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