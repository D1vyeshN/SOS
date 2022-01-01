package com.example.de;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Permission extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_permission);

        Handler myHandler = new Handler();

        myHandler.postDelayed((Runnable) () -> {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }, SPLASH_SCREEN);
    }

}