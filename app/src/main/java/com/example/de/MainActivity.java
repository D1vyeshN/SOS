package com.example.de;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;


@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 4000;

    private ImageView mImageView;

    private static final int REQUEST_CODE_LOCATION = 1;

    private static final int REQUEST_CODE_SMS = 2;

    private static final int REQUEST_CODE_CONTACT = 3;

    String[] permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS};


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.gifImage);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(ContextCompat.checkSelfPermission(MainActivity.this, permission[0]) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[0]}, REQUEST_CODE_LOCATION);

        } else if (ContextCompat.checkSelfPermission(MainActivity.this, permission[1]) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[1]}, REQUEST_CODE_SMS);

        }
        else if(ContextCompat.checkSelfPermission(MainActivity.this, permission[2]) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[2]}, REQUEST_CODE_CONTACT);

        }
        else{

            ActivityControl();

        }

//        startService(new Intent(this, MyService.class));

    }

    private void ActivityControl(){

        Glide.with(this).load(R.drawable.world_800x600).into(mImageView);

        Handler myHandler = new Handler();

        myHandler.postDelayed((Runnable) () -> {

            Intent intent = new Intent(MainActivity.this, Permission.class);
            startActivity(intent);
            finish();

        }, SPLASH_SCREEN);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_LOCATION){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (ContextCompat.checkSelfPermission(MainActivity.this, permission[1]) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[1]}, REQUEST_CODE_SMS);

                }
                else if(ContextCompat.checkSelfPermission(MainActivity.this, permission[2]) == PackageManager.PERMISSION_DENIED){

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[2]}, REQUEST_CODE_CONTACT);

                }
                else{

                    ActivityControl();

                }

            }
            else{

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[0]}, REQUEST_CODE_LOCATION);

            }

        }
        else if(requestCode == REQUEST_CODE_SMS){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (ContextCompat.checkSelfPermission(MainActivity.this, permission[0]) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[0]}, REQUEST_CODE_LOCATION);

                }
                else if(ContextCompat.checkSelfPermission(MainActivity.this, permission[2]) == PackageManager.PERMISSION_DENIED){

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[2]}, REQUEST_CODE_CONTACT);

                }
                else{

                    ActivityControl();

                }

            }
            else{

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[1]}, REQUEST_CODE_SMS);

            }

        }
        else if(requestCode == REQUEST_CODE_CONTACT){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (ContextCompat.checkSelfPermission(MainActivity.this, permission[1]) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[1]}, REQUEST_CODE_SMS);

                }
                else if(ContextCompat.checkSelfPermission(MainActivity.this, permission[0]) == PackageManager.PERMISSION_DENIED){

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[0]}, REQUEST_CODE_LOCATION);

                }
                else{

                    ActivityControl();

                }

            }
            else{

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission[2]}, REQUEST_CODE_CONTACT);

            }

        }
        else{
            ;
            ActivityControl();

        }

    }

}