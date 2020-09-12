package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customer.ModelClasses.cart;
import com.example.customer.ModelClasses.medicine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class medicineDetail extends AppCompatActivity {

    TextView medicine_name,medicine_price;
    EditText quantity;
    Button add_to_cart;
    String medicineId = null , cnic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicineId = getIntent().getStringExtra("pid");
        setContentView(R.layout.activity_medicine_detail  );
//        Toolbar toolbar = findViewById(R.id.bgHeader);
//        setSupportActionBar(toolbar);
//
        medicine_name = findViewById(R.id.lbl_medicines_name);
        medicine_price = findViewById(R.id.lbl_medicines_price);
        quantity = findViewById(R.id.number_btn);
        add_to_cart = findViewById(R.id.add_to_cart);

        getMedicineDetail(medicineId);
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
        cnic = sharedpreferences.getString("cnic",null);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("CartList");
        ref.child("User View").child(cnic).child("product").child(medicineId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    cart medicine = dataSnapshot.getValue(cart.class);
                    quantity.setText(medicine.getMedQuantity());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();

            }
        });
    }

    private void addToCart() {

        if(TextUtils.isEmpty(quantity.getText().toString())){
            Toast.makeText(this, "Please write quantity", Toast.LENGTH_SHORT).show();
        }else{
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String,Object> cartMap =new HashMap<>();

        cartMap.put("medId" , medicineId);
        cartMap.put("medName" , medicine_name.getText().toString());
        cartMap.put("medPrice" , medicine_price.getText().toString());
        cartMap.put("date" , saveCurrentDate);
        cartMap.put("time" , saveCurrentTime);
        cartMap.put("medQuantity" , quantity.getText().toString());


        ref.child("User View").child(cnic).child("product").child(medicineId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    ref.child("Admin View").child(cnic).child("product").child(medicineId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(medicineDetail.this, "Added to Cart List", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(medicineDetail.this, CartActivity.class);
                                startActivity(intent);
                                finish();
                            }    
                        }
                    });
                }
            }
        });
        }
    }


    public void getMedicineDetail(String medicineId){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("medicine-list");
        ref.child(medicineId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    medicine medicine = dataSnapshot.getValue(medicine.class);
                    medicine_name.setText(medicine.getName());
                    medicine_price.setText(medicine.getPrice());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
