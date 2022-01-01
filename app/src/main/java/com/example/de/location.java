package com.example.de;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

@SuppressLint("UseSwitchCompatOrMaterialCode")

public class location extends AppCompatActivity {

    ImageButton ContactView;

    ImageButton ProfileView;

    private DrawerLayout dLayout;

    private SupportMapFragment mapFragment;

    private FusedLocationProviderClient Client;

    private Switch locSwitch;

    private BottomNavigationView bottomNavigationView;

    private int ID;

    private NavigationView navView;

    private String UserName;

    LogInDatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DB = new LogInDatabaseHandler(this);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        
        setContentView(R.layout.activity_location);

        Intent intent = getIntent();

        ID = intent.getIntExtra("userId", -1);

        UserName = intent.getStringExtra("userName");

        locSwitch = (Switch) findViewById(R.id.locationSwitch);

//        ContactView = (ImageButton) findViewById(R.id.LocContactView);
//
//        ProfileView = (ImageButton) findViewById(R.id.LocProfileView);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_location);

        if(stopService(new Intent(getApplicationContext(), MyService.class))){

            startService(new Intent(getApplicationContext(), MyService.class));

            locSwitch.setChecked(true);

        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(location.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();

        } else {

            ActivityCompat.requestPermissions(location.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View view) {

                dLayout.openDrawer(Gravity.LEFT);
            }

        });

        locSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((Switch) v).isChecked()){

                    startService(new Intent(getApplicationContext(), MyService.class));

                }
                else{

                    stopService(new Intent(getApplicationContext(), MyService.class));

                }

            }
        });

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

                if (itemId == R.id.navigation_contact){

                    Intent intent = new Intent(location.this, Dashboard.class);

                    intent.putExtra("userId", ID);

                    intent.putExtra("userName", UserName);

                    startActivity(intent);

                    finish();

                }
                else if (itemId == R.id.navigation_profile){

                    Intent intent = new Intent(location.this, Profile.class);

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
//                Intent intent = new Intent(location.this, Dashboard.class);
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
//        ProfileView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(location.this, Profile.class);
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

        setNavigationDrawer();

        ConstraintLayout Conname = (ConstraintLayout) navView.getMenu().getItem(0).getActionView();

        TextView Profilename = (TextView) Conname.findViewById(R.id.profilename);

        User CUser = DB.getUser(UserName);

        Profilename.setText((CharSequence)(CUser.getName()));

    }

    public void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;

        }
        Task<Location> task = Client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location != null){

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

                            MarkerOptions options = new MarkerOptions().position(latlng).title("I am here");

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));

//                            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                            googleMap.addMarker(options);

                        }
                    });

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

                    Intent intent = new Intent(location.this, appinfo.class);
                    startActivity(intent);
//            finish();

                }
                else if (itemId == R.id.third){

                    Intent intent = new Intent(location.this, aboutus.class);
                    startActivity(intent);
//            finish();

                }

//                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 44){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getCurrentLocation();

            }

        }

    }
}