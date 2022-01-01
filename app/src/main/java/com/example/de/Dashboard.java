
package com.example.de;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

  private int ID;

  SearchView searchView;

  ImageButton locationView;

  ImageButton ProfileView;

  Button mylocationbutton;

  private static Context context;

  private ListView listView;

  private ArrayList<String> OnlyPhonePureContactList = new ArrayList<String>();

  private ArrayList<String> OnlyNamePureContactlist = new ArrayList<String>();


  private static FusedLocationProviderClient fusedLocationProviderClient;

  private static int countNumber = 0;

  private ArrayList<String[]> Contact = new ArrayList<String[]>();

  private  ArrayList<String[]> PureContactList =  new ArrayList<String[]>();

  private ArrayList<String> name = new ArrayList<String>();

  private  ArrayList<String> pos =  new ArrayList<String>();

  private ArrayAdapter<String> adapter;

  private BottomNavigationView bottomNavigationView;

  private String UserName;

  private NavigationView navView;

  MyAdapter myAdapter;

  SingleRow singlerow;

  ArrayList<SingleRow> mylist;

  ArrayList<SingleRow> mytemplist;

  LogInDatabaseHandler DB;

  DatabaseHandler db;

  DrawerLayout dLayout;

  static NotificationCompat.Builder mBuilder;

  static NotificationManagerCompat mNotificationManager;

  @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n"})

  @RequiresApi(api = Build.VERSION_CODES.M)

  @Override

  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    DB = new LogInDatabaseHandler(this);

    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_dashboard);

    Dashboard.context = getApplicationContext();

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    listView = (ListView) findViewById(R.id.ListView);

    bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

    bottomNavigationView.setSelectedItemId(R.id.navigation_contact);



//    locationView = (ImageButton) findViewById(R.id.locationButton);
//
//    ProfileView = (ImageButton) findViewById(R.id.profileButton);




//        mySearchView = (SearchView) findViewById(R.id.DashSearchView);

    db = new DatabaseHandler(this);

    Intent intent = getIntent();

    ID = intent.getIntExtra("userId", -1);

    UserName = intent.getStringExtra("userName");

    if(ID != -1) {

      List<Contact> Contacts = db.getAllContactsKey(ID);

//      String [] name = new String[Contacts.size()];
//
//      String [] pos = new String[Contacts.size()];

//      for (Contact cn : Contacts) {
//
//        OnlyNamePureContactList.add(cn.getName());
//
//        OnlyPhonePureContactList.add(cn.getPhoneNumber());
//
//      }

//      adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, OnlyNamePureContactList);
//
//      listView.setAdapter(adapter);

      for (Contact cn : Contacts) {

//        if (cn.get_status().equals("true")) {

            name.add(cn.getName());

            pos.add(cn.get_status());

        OnlyPhonePureContactList.add(cn.getPhoneNumber());

        OnlyNamePureContactlist.add(cn.getName());

//        }

      }

      mylist = new ArrayList<>();

      for(int i = 0; i < name.size(); i++){

        singlerow = new SingleRow(name.get(i), pos.get(i));

        mylist.add(singlerow);


      }

      myAdapter = new MyAdapter(this, mylist);

      listView.setAdapter(myAdapter);

      int itemposition = 0;

      for (Contact cn : Contacts) {

        if (cn.get_status().equals("true")) {

          Log.d("inside", "if condition");

          listView.setItemChecked(itemposition, true);

        }

        itemposition += 1;

      }

    }
    else{

      Toast.makeText(this, "ID is -1", Toast.LENGTH_SHORT).show();

    }

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

          Intent intent = new Intent(Dashboard.this, location.class);

          intent.putExtra("userId", ID);

          intent.putExtra("userName", UserName);

          startActivity(intent);

          finish();

        }
        else if (itemId == R.id.navigation_profile){

          Intent intent = new Intent(Dashboard.this, Profile.class);

          intent.putExtra("userId", ID);

          intent.putExtra("userName", UserName);

          startActivity(intent);

          finish();

        }

        return false;
      }
    });


//    locationView.setOnClickListener(new View.OnClickListener() {
//
//      @Override
//
//      public void onClick(View v) {
//
//        Intent intent = new Intent(Dashboard.this, location.class);
//
//        intent.putExtra("userId", ID);
//
//        intent.putExtra("userName", UserName);
//
//        startActivity(intent);
//
//        finish();
//
//      }
//    });
//
//    ProfileView.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//
//        Intent intent = new Intent(Dashboard.this, Profile.class);
//
//        intent.putExtra("userId", ID);
//
//        intent.putExtra("userName", UserName);
//
//        startActivity(intent);
//
//        finish();
//
//      }
//    });

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

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        List<Contact> Contacts = db.getAllContactsKey(ID);

//        myAdapter = new MyAdapter(getApplicationContext(), mylist);

        TextView myText = (TextView) view.findViewById(R.id.myTextView);

        int flag = 0;

        String realViewPosition = "";

        for(int i = 0; i < name.size(); i++){

          if(name.get(i).equals(myText.getText().toString())){

            flag = 1;

            realViewPosition = name.get(i);

            break;

          }

        }

//        Toast.makeText(getApplicationContext(), myText.getText().toString(), Toast.LENGTH_LONG).show();


//        Log.d("val", String.valueOf(myAdapter.findView(myText.getText().toString())));

        @SuppressLint("UseSwitchCompatOrMaterialCode")

        Switch mySwitch =  (Switch) (myAdapter.getV(myAdapter.findView(myText.getText().toString()))).findViewById(R.id.mySwitch);

        if(mySwitch.isChecked()){

          mySwitch.setChecked(false);

          pos.set(myAdapter.findView(myText.getText().toString()), "false");

          db.updateContact(new Contact(ID, (OnlyNamePureContactlist.get(myAdapter.findView(myText.getText().toString()))), (OnlyPhonePureContactList.get(myAdapter.findView(myText.getText().toString()))), "false"));


        }
        else{

          mySwitch.setChecked(true);

          pos.set(myAdapter.findView(myText.getText().toString()), "true");

          db.updateContact(new Contact(ID, (OnlyNamePureContactlist.get(myAdapter.findView(myText.getText().toString()))), (OnlyPhonePureContactList.get(myAdapter.findView(myText.getText().toString()))), "true"));

        }

//        mylist = new ArrayList<>();
//
//        for (int i = 0; i < name.size(); i++) {
//
//          singlerow = new SingleRow(name.get(i), pos.get(i));
//
//          mylist.add(singlerow);
//
//        }

//        +Log.d("query", String.valueOf(searchView.getQuery()));

        singlerow = new SingleRow(name.get(myAdapter.findView(myText.getText().toString())), pos.get(myAdapter.findView(myText.getText().toString())));

        mylist.set(myAdapter.findView(myText.getText().toString()), singlerow);

        myAdapter.notifyDataSetChanged();

        if(!((searchView.getQuery()).toString().equals(""))) {

          myAdapter.getFilter().filter(searchView.getQuery());
//          Log.d("va", "inif");
//
//                    myAdapter.getFilter().filter(mySearchView.getQuery());
//
//          mytemplist = myAdapter.getExactAdapter(searchView.getQuery());
//
//          for(int i = 0; i < name.size(); i++){
//
//            for(int j = 0; j < mytemplist.size(); j++){
//
//              if(name.get(i) == mytemplist.get(j).getName()){
//
//                                mytemplist.get(j).setName(name[i]);
//
//                mytemplist.get(j).setPos(pos.get(i));
//
//              }
//
//            }
//
//          }
//
//          myAdapter = new MyAdapter(getApplicationContext(), mytemplist);
//
//          listView.setAdapter(myAdapter);
//
//          myAdapter = new MyAdapter(getApplicationContext(), mylist);


        }
//        else {
//
//          Log.d("va", "inelse");
//
//          myAdapter = new MyAdapter(getApplicationContext(), mylist);
//
//          listView.setAdapter(myAdapter);
//
//        }

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

//          Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();

          User CurrentUser = DB.getUser(UserName);

          CurrentUser.set_logStatus("false");

          DB.updateUser(CurrentUser);

          Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

          startActivity(intent);

          finish();

        }
        else if (itemId == R.id.first){

            Intent intent = new Intent(Dashboard.this, appinfo.class);
            startActivity(intent);
//            finish();

        }
        else if (itemId == R.id.third){

          Intent intent = new Intent(Dashboard.this, aboutus.class);
          startActivity(intent);
//            finish();

        }

//        Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

        return false;
      }

    });
  }

  @SuppressLint("ResourceType")
  @Override
  public boolean onCreateOptionsMenu(Menu manu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.layout.manu, manu);
    MenuItem searchViewItem = manu.findItem(R.id.searchBar);
    searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
     // private Object Switch;

      @Override
      public boolean onQueryTextSubmit(String query) {
//        searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
        return false;

      }

      @Override
      public boolean onQueryTextChange(String newText) {

//        Handler myHandler = new Handler();
//
//        myHandler.postDelayed((Runnable) () -> {
//
//          listView.setAdapter(myAdapter);
//
//        }, 20);

        myAdapter.getFilter().filter(newText);

        return false;
      }
    });
    return super.onCreateOptionsMenu(manu);
  }

//    @SuppressLint("ResourceType")
//
//    @Override
//
//    public boolean onCreateOptionsMenu(Menu menu){
//
//        MenuInflater inflater = getMenuInflater();
//
//        inflater.inflate(R.layout.menu, menu);
//
//        MenuItem searchViewItem = menu.findItem(R.id.action_search);
//
//        searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
//
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//
//
//
//                return false;
//
//            }
//        });
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//
//            }
//
//            @Override
//
//            public boolean onQueryTextChange(String newText) {
//
//                adapter.getFilter().filter(newText);
//
//                return false;
//
//            }
//
//        });
//
//        return super.onCreateOptionsMenu(menu);
//
//    }

}

