package com.example.customer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.customer.ModelClasses.Threshold;
import com.example.customer.ModelClasses.cart;
import com.example.customer.ModelClasses.customer;
import com.example.customer.ModelClasses.medicine;
import com.example.customer.ViewHolder.cartViewHolder;
import com.example.customer.ViewHolder.medicineViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private  String cnic;
    FusedLocationProviderClient client;
    LocationRequest request;
    RecyclerView recyclermanu;
    RecyclerView.LayoutManager manager;
   int threshold;

    //FirebaseRecyclerAdapter<cart, cartViewHolder> checkListAdapter;

    FirebaseRecyclerAdapter<medicine, medicineViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference ref;
    SearchView editsearch;
    String text=null;
    Query query;
    FloatingActionButton addToCart;
    TextView CustomerName;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        FirebaseDatabase db2;
        db2=FirebaseDatabase.getInstance();
        DatabaseReference ref2;
        ref2 = db2.getReference("Threshold");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Threshold c=dataSnapshot.getValue(Threshold.class);
                    threshold = Integer.valueOf(c.getThreshold());
            //        Toast.makeText(MainPage.this, "Welcome", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = findViewById(R.id.bgHeader);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        db=FirebaseDatabase.getInstance();
        ref=db.getReference("medicine-list");


        CustomerName = findViewById(R.id.CustomerName);
        addToCart = findViewById(R.id.btn_cart);
        editsearch = (SearchView) findViewById(R.id.srch_medicine);
        editsearch.setOnQueryTextListener(MainPage.this);
        recyclermanu=findViewById(R.id.recyclerview);
        recyclermanu.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        recyclermanu.setLayoutManager(manager);
        recyclermanu.setAdapter(adapter);


        loadMedicine();
        onQueryTextChange(text);

   //     getSupportActionBar().hide();
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
        cnic = sharedpreferences.getString("cnic",null);
        FirebaseDatabase db1;
        db1=FirebaseDatabase.getInstance();
        DatabaseReference ref1;
        ref1 = db1.getReference("customer-list").child(cnic);
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    customer c=dataSnapshot.getValue(customer.class);
                    CustomerName.setText(c.getName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainPage.this , CartActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onQueryTextChange(String s) {
        text = s;
        query = ref.orderByChild("name").startAt(text).endAt(text + "\uf8ff");
        adapter=new FirebaseRecyclerAdapter<medicine, medicineViewHolder>(medicine.class,R.layout.medicine_list,medicineViewHolder.class,query) {


            @Override
            protected void populateViewHolder(medicineViewHolder menuViewHolder, final medicine category, final int i) {

                if(Integer. valueOf(category.getQuantity()) > threshold) {
                    menuViewHolder.name.setText(category.getName());
                    menuViewHolder.price.setText(category.getPrice());
//                for (int ii = 1; ii <= 2; ii++) {
//                    TextView textView = new TextView(MainPage.this);
//                    textView.setText("TextView " +category.getName());
//
//                    menuViewHolder.layout.addView(textView);
//                }
                    menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainPage.this, medicineDetail.class);
                            intent.putExtra("pid", category.getKey());
                            startActivity(intent);


                        }
                    });
                }else{
                    menuViewHolder.itemView.setVisibility(View.GONE);
                    menuViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            }
        };
        recyclermanu.setAdapter(adapter);
        return false;
    }
    public void loadMedicine(){

        recyclermanu.setAdapter(adapter);

    }
    LocationCallback mLocationCallback =new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            final DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("customer-list").child(cnic);
            Location location = locationResult.getLastLocation();
            if (location != null) {
                //Toast.makeText(MainPage.this, cnic, Toast.LENGTH_SHORT).show();

                mDatabaseRef.child("longitude").setValue(String.valueOf(location.getLongitude()));
                mDatabaseRef.child("latitude").setValue(String.valueOf(location.getLatitude()));
                if (client != null) {
                    client.removeLocationUpdates(mLocationCallback);
                }

            }
        }
    };

    private void requestLocationUpdates() {
        request = new LocationRequest();



        request.setInterval(10000);

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        client = LocationServices.getFusedLocationProviderClient(this);
        //  final String path = getString(R.string.firebase_path);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (permission == PackageManager.PERMISSION_GRANTED) {



            client.requestLocationUpdates(request,mLocationCallback , null);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent log = new Intent(MainPage.this,Login.class);
            startActivity(log);
            finish();

            return true;
        }else if(id == R.id.menu_location){
            Toast.makeText(MainPage.this, "Location Shared", Toast.LENGTH_SHORT).show();
            requestLocationUpdates();
        }else if(id == R.id.menu_chat){
            Intent intent=new Intent(MainPage.this,chat.class);
            startActivity(intent);
        }
        else if(id == R.id.order_item){
            Intent intent=new Intent(MainPage.this,Order.class);
            startActivity(intent);
        }else if(id == R.id.assigned_order){
            Intent intent=new Intent(MainPage.this,Assigned_order.class);
            startActivity(intent);
        }else if(id == R.id.delivered_order){
            Intent intent=new Intent(MainPage.this,Delivered_order.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
