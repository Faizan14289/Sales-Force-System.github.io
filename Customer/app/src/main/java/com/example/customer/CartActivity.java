package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customer.ModelClasses.cart;
import com.example.customer.ModelClasses.medicine;
import com.example.customer.ViewHolder.cartViewHolder;
import com.example.customer.ViewHolder.medicineViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CartActivity extends AppCompatActivity {
    TextView price;

    Button confirmOrder,btn_addMore,btn_Cancle;
    RecyclerView recyclermanu;
    RecyclerView.LayoutManager manager;
    FirebaseRecyclerAdapter<cart, cartViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference ref;
    String cnic;
    int overAllPrice;

    boolean doubleBackToExitPressedOnce = false;

//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            finish();
//       //     super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

//        db=FirebaseDatabase.getInstance();
//        ref=db.getReference("CartList");

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
        cnic = sharedpreferences.getString("cnic",null);
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("CartList").child("User View").child(cnic).child("product");

        recyclermanu=findViewById(R.id.CartView);
        recyclermanu.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        recyclermanu.setLayoutManager(manager);
        recyclermanu.setAdapter(adapter);

        btn_Cancle= findViewById(R.id.btn_Cancle);
        confirmOrder = findViewById(R.id.btn_next);
        btn_addMore = findViewById(R.id.btn_addMore);
        price = findViewById(R.id.total_price);

        loadCart();

        btn_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db2;
                db2=FirebaseDatabase.getInstance();
                DatabaseReference ref2;
                db2.getReference("CartList").child("User View").child(cnic).child("product").removeValue();
                Toast.makeText(CartActivity.this, "Order Cancled", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CartActivity.this , MainPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        btn_addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this , MainPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "Your Order is Confirmed", Toast.LENGTH_SHORT).show();

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference() ;
                            String key = ref1.push().getKey();
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                cart c = postSnapshot.getValue(cart.class);




                                ref1.child("Orders").child(cnic).child(key).child(c.getMedId()).setValue(c);
                            }
                            String saveCurrentTime, saveCurrentDate;

                            Calendar calForDate = Calendar.getInstance();
                            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                            saveCurrentDate = currentDate.format(calForDate.getTime());

                            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                            saveCurrentTime = currentTime.format(calForDate.getTime());

                            ref1.child("Order Detail").child(cnic).child(key).child("priceStatus").setValue("unpaid");
                            ref1.child("Order Detail").child(cnic).child(key).child("status").setValue("not Confirmed");

                            ref1.child("Order Detail").child(cnic).child(key).child("price").setValue(String.valueOf(overAllPrice));
                            ref1.child("Order Detail").child(cnic).child(key).child("orderId").setValue(key);
                            ref1.child("Order Detail").child(cnic).child(key).child("date").setValue(saveCurrentDate);
                            ref1.child("Order Detail").child(cnic).child(key).child("time").setValue(saveCurrentTime);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

             ref.removeValue();

             Intent intent = new Intent(CartActivity.this , MainPage.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(intent);
             finish();


            }


        });


    }

    private void loadCart() {


        adapter=new FirebaseRecyclerAdapter<cart, cartViewHolder>(cart.class,R.layout.cart_item_list,cartViewHolder.class,ref) {


            @Override
            protected void populateViewHolder(cartViewHolder cartViewHolder, final cart cart, int i) {
                cartViewHolder.names.setText(cart.getMedName());
                cartViewHolder.price.setText("Price = "+cart.getMedPrice());
                cartViewHolder.quantity.setText("Quantity = "+cart.getMedQuantity());

                int priceOfSingle = ((Integer.valueOf(cart.getMedPrice()))*(Integer.valueOf(cart.getMedQuantity()))) ;
                overAllPrice = overAllPrice+priceOfSingle;
                price.setText("Total Price = " +String.valueOf(overAllPrice));

                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence option[] = new CharSequence[]{
                                "Edit",
                                "Remove"
                        };

                        final AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Option: ");

                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, final int i) {
                                if(i==0){
                                    Intent intent = new Intent(CartActivity.this , medicineDetail.class);
                                    intent.putExtra("pid", cart.getMedId());
                                    startActivity(intent);
                                }
                                if(i==1){

                                    ref.child(cart.getMedId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CartActivity.this, "Item Is succesfully removed", Toast.LENGTH_SHORT).show();
                                                recyclermanu.setAdapter(adapter);
                                            }
                                        }
                                    });



                                }
                            }
                        });
                        builder.show();

                    }
                });
            }

//            @Override
//            protected void populateViewHolder(medicineViewHolder menuViewHolder, final medicine category, final int i) {
//                menuViewHolder.name.setText(category.getName());
//                menuViewHolder.price.setText(category.getPrice());
//                menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(MainPage.this,medicineDetail.class);
//                        intent.putExtra("pid", category.getKey());
//                        startActivity(intent);
//
//                    }
//                });
//
//            }
        };
        recyclermanu.setAdapter(adapter);
    }


}
