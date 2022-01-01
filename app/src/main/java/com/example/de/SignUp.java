package com.example.de;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 3000;

    private static ArrayList<String[]> Contact = new ArrayList<String[]>();

    private static ArrayList<String[]> PureContactList = new ArrayList<String[]>();

    private static ArrayList<String> OnlyNameContact = new ArrayList<String>();

    private static ArrayList<String> OnlyNamePureContactList = new ArrayList<String>();

    private static ArrayAdapter<String> adapter;

    private static ContentResolver cr;

    private static Context context;

    private TextView Name;

    private TextView Phone;

    private TextView Email;

    private TextView Password;

    private TextView ConfirmPassword;

    private Button SignUpButton;

    private Button LogInButton;

    LogInDatabaseHandler DB;

    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        SignUp.cr = getContentResolver();

        SignUp.context = getApplicationContext();

        Name = (TextView) findViewById(R.id.signName);

        Phone = (TextView) findViewById(R.id.signPhone);

        Email = (TextView) findViewById(R.id.signEmail);

        Password = (TextView) findViewById(R.id.signPassword);

        ConfirmPassword = (TextView) findViewById(R.id.signConfirmPassword);

        SignUpButton = (Button) findViewById(R.id.signupButton);

        LogInButton = (Button) findViewById(R.id.signupLogIn);

        DB = new LogInDatabaseHandler(this);

        db = new DatabaseHandler(this);

//        SQLiteDatabase sb = db.getWritableDatabase();
//
//        db.onCreate(sb);


//        SQLiteDatabase sb = DB.getWritableDatabase();
//
//        DB.onUpgrade(sb, 0, 0);

        Password.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(), "HINT: Sample_123", Toast.LENGTH_LONG).show();

                return false;

            }
        });

        Email.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(), "HINT: sample@gmail.com", Toast.LENGTH_LONG).show();

                return false;

            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String userName = Name.getText().toString();

                String userPhone = Phone.getText().toString();

                String userEmail = Email.getText().toString();

                String userPassword = Password.getText().toString();

                String userConfirmPassword = ConfirmPassword.getText().toString();

                if(userName.equals("") || userEmail.equals("") || userPassword.equals("") || userConfirmPassword.equals("")){

                    Toast.makeText(getApplicationContext(), "Please fill all Details", Toast.LENGTH_SHORT).show();

                }
                else{

                    if(!userPassword.equals(userConfirmPassword)){

                        Toast.makeText(getApplicationContext(), "Check The Confirmation", Toast.LENGTH_SHORT).show();

                    }
                    else{

                        int flag = 0;

                        List<User> Users = DB.getAllUsers();

                        if(Users.size() > 0) {

                            for (User us : Users) {

                                if (us.get_email().equals(userEmail) || us.get_password().equals(userPassword)) {

                                    flag = 1;

                                    break;

                                }

                            }

                        }

                        if(flag == 0) {

                            if(ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                                if (userPhone.length() == 10 && isValidEmail(userEmail) && isValidPassword(userPassword) && userPassword.length() >= 8) {

                                    LogInButton.setClickable(false);

                                    DB.addUser(new User(userName, userPhone, userEmail, userPassword, "false"));

                                    User CurrentUser = DB.getUser(userEmail);

                                    SignUp.ContactSaver(CurrentUser.getID());

                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                                    startActivity(intent);

                                    finish();
                                }
                                else {

                                    if (!(userPhone.length() == 10) ) {

                                        Toast.makeText(getApplicationContext(), "Phone number is not valid", Toast.LENGTH_SHORT).show();

                                    }
                                    if (!isValidEmail(userEmail)) {

                                        Toast.makeText(getApplicationContext(), "Email is not valid", Toast.LENGTH_SHORT).show();

                                    }
                                    if (!(userPassword.length() >= 8 )){

                                        Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();

                                    }
                                    if (!isValidPassword(userPassword)){

                                        Toast.makeText(getApplicationContext(), "Password is not valid", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            }
                            else{

                               Toast.makeText(getApplicationContext(), "Please Provide contact permission", Toast.LENGTH_SHORT).show();

                            }

                        }
                        else{

//                            Toast.makeText(getApplicationContext(), "Already Used", Toast.LENGTH_SHORT).show();

                        }

                    }

                }


            }
        });

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUpButton.setClickable(false);

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(intent);

                finish();

            }
        });

    }

    public static void ContactSaver(int ID) {

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {

            while (cur != null && cur.moveToNext()) {

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));

                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {

                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                    while (pCur.moveToNext()) {

                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        if (Contact.isEmpty()) {

                            Contact.add(new String[]{name, phoneNo});

                        } else {

                            int flag = 0;

                            for (int itemNum = 0; itemNum < Contact.size(); itemNum++) {

                                if (Contact.get(itemNum)[0].equals(name)) {

                                    flag = 1;

                                    break;

                                }

                            }

                            if (flag == 0) {

                                Contact.add(new String[]{name, phoneNo});

                            }
                        }

                        OnlyNameContact.add(name);

                    }

                    pCur.close();

                }
            }

            Set<String[]> PureContact = new LinkedHashSet<String[]>(Contact);

            PureContactList = new ArrayList<String[]>(PureContact);

            Set<String> OnlyNamePureContact = new LinkedHashSet<String>(OnlyNameContact);

            OnlyNamePureContactList = new ArrayList<String>(OnlyNamePureContact);

//            SQLiteDatabase sb = db.getWritableDatabase();
//
//            db.onUpgrade(sb, 0, 0);

              ArrayList<String> PhoneTrue =  new ArrayList<String>();

              ArrayList<String> nameTrue =  new ArrayList<String>();

            for (int i = 0; i < PureContactList.size(); i++){

                nameTrue.add((PureContactList.get(i))[0]);
            }

            Collections.sort(nameTrue);

            for (int i = 0; i < PureContactList.size(); i++) {

                for (int j = 0; j < PureContactList.size(); j++){

                    if ((PureContactList.get(j))[0].equals(nameTrue.get(i))){

                        PhoneTrue.add((PureContactList.get(j))[1]);



                    }
                }

            }

            for (int i = 0; i < PhoneTrue.size(); i++){

                Log.d("ss", PhoneTrue.get(i));

            }

            for (int ConNum = 0; ConNum < PureContactList.size(); ConNum++) {

                db.addContact(new Contact(ID, nameTrue.get(ConNum), PhoneTrue.get(ConNum), "false"));

//                Toast.makeText(context, String.valueOf((PureContactList.get(ConNum))[0]), Toast.LENGTH_SHORT).show();

            }

            List<Contact> contacts = db.getAllContacts();

//            Log.d("UserContact", String.valueOf(contacts.size()));

            for (Contact cn : contacts) {

                String s = cn.getID() + " " + cn.getName() + " " + cn.getPhoneNumber() + " " + cn.get_status();

                Log.d("UserContact", s);

            }

//            Toast.makeText(context, "Inside the contact saver method", Toast.LENGTH_SHORT).show();

        }
        else{

//            Toast.makeText(context, "No Contacts are available", Toast.LENGTH_SHORT).show();

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