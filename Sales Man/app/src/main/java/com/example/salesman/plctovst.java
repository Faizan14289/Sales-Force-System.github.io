package com.example.salesman;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesman.Modelclasses.cart;
import com.example.salesman.Modelclasses.customer;
import com.example.salesman.Modelclasses.orders;
import com.example.salesman.ViewHolder.orderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class plctovst extends AppCompatActivity {

    RecyclerView recyclermanu;
    RecyclerView.LayoutManager manager;



    FirebaseRecyclerAdapter<orders, orderViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference ref;
    String cnic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plctovst);

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
        cnic = sharedpreferences.getString("cnic",null);

        db=FirebaseDatabase.getInstance();
        ref=db.getReference("AssignedOrdersDetails");

        recyclermanu=findViewById(R.id.CartView);
        recyclermanu.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        recyclermanu.setLayoutManager(manager);
        recyclermanu.setAdapter(adapter);

        loadCart();

    }

    private void loadCart() {


        adapter=new FirebaseRecyclerAdapter<orders, orderViewHolder>(orders.class,R.layout.order_item_list,orderViewHolder.class,ref) {


            @Override
            protected void populateViewHolder(final orderViewHolder orderViewHolder, final orders order, int i) {


                if(cnic.equalsIgnoreCase(order.getSaleManId()))
                {

                // orderViewHolder.order_number.setText(order.ge);
                FirebaseDatabase db1 = FirebaseDatabase.getInstance();
                DatabaseReference ref1 = db1.getReference("customer-list").child(order.getCustomerId());
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        customer c = dataSnapshot.getValue(customer.class);
                        orderViewHolder.customer_name.setText("Customer Name= " + c.getName());
                        orderViewHolder.customer_phone.setText("Phone= " + c.getPhone());
                        orderViewHolder.customer_address.setText("Customer Address= " + c.getAddress());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                FirebaseDatabase db2 = FirebaseDatabase.getInstance();
                DatabaseReference ref2 = db2.getReference("AssignedOrders").child(order.getOrderId());
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            TextView place_name1 = new TextView(getApplicationContext());
                            place_name1.setText("Name");
                            place_name1.setTextSize(20);
                            place_name1.setGravity(Gravity.LEFT);
                            place_name1.setPadding(5, 15, 0, 15);
                            place_name1.setBackgroundColor(Color.parseColor("#f0f0f0"));
                            place_name1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 5f));
                            place_name1.setTextColor(Color.BLACK);

                            //  place_name1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                            TextView place_type1 = new TextView(getApplicationContext());
                            place_type1.setText("Price");
                            place_type1.setTextSize(20);
                            place_type1.setGravity(Gravity.CENTER);
                            place_type1.setPadding(5, 15, 0, 15);
                            place_type1.setBackgroundColor(Color.parseColor("#f7f7f7"));
                            place_type1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 5f));
                            place_type1.setTextColor(Color.BLACK);

                            TextView price_range1 = new TextView(getApplicationContext());
                            price_range1.setText("Quantity");
                            price_range1.setGravity(Gravity.RIGHT);
                            price_range1.setTextSize(20);
                            price_range1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 5f));
                            price_range1.setTextColor(Color.BLACK);


                            TableRow row1 = new TableRow(getApplicationContext());
                            //         row1.setGravity(Gravity.CENTER_HORIZONTAL);
                            row1.addView(place_name1);
                            row1.addView(place_type1);
                            row1.addView(price_range1);


                            orderViewHolder.tableLayout.addView(row1);


                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                cart c = postSnapshot.getValue(cart.class);
                                TextView place_name = new TextView(getApplicationContext());
                                place_name.setText(c.getMedName());
                                place_name.setTextSize(15);
                                place_name.setGravity(Gravity.LEFT);
                                place_name.setPadding(5, 15, 0, 15);
                                place_name.setBackgroundColor(Color.parseColor("#f0f0f0"));
                                place_name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 5f));
                                place_name.setTextColor(Color.BLACK);

                                TextView place_type = new TextView(getApplicationContext());
                                place_type.setText(c.getMedPrice());
                                place_type.setTextSize(15);
                                place_type.setGravity(Gravity.CENTER);
                                place_type.setPadding(5, 15, 0, 15);
                                place_type.setBackgroundColor(Color.parseColor("#f7f7f7"));
                                place_type.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 5f));
                                place_type.setTextColor(Color.BLACK);

                                TextView price_range = new TextView(getApplicationContext());
                                price_range.setText(c.getMedQuantity());
                                price_range.setTextSize(15);
                                price_range.setGravity(Gravity.RIGHT);
                                price_range.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 5f));
                                price_range.setTextColor(Color.BLACK);

                                TableRow row = new TableRow(getApplicationContext());
                                row.setGravity(Gravity.CENTER_HORIZONTAL);
                                row.addView(place_name);
                                row.addView(place_type);
                                row.addView(price_range);


                                orderViewHolder.tableLayout.addView(row);



                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                orderViewHolder.order_date.setText("Date = " + order.getDate().toString());
                orderViewHolder.order_time.setText("Time = " + order.getTime().toString());
                orderViewHolder.order_price.setText("Price = " + order.getPrice().toString());
                orderViewHolder.order_status.setText("Order Status = " + order.getStatus().toString());
                orderViewHolder.order_priceStatus.setText("Payment = " + order.getPriceStatus().toString());

                orderViewHolder.btn_paid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase db22 = FirebaseDatabase.getInstance();
                        DatabaseReference ref22 = db22.getReference("AssignedOrdersDetails").child(order.getOrderId());
                        ref22.child("priceStatus").setValue("paid");
                    }
                });

                final orders o = order;


                orderViewHolder.btn_assigned.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase db22 = FirebaseDatabase.getInstance();
                        DatabaseReference ref22 = db22.getReference("AssignedOrdersDetails").child(order.getOrderId());
                        ref22.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                orders priceStatus = dataSnapshot.getValue(orders.class);
                                if(priceStatus.getPriceStatus().equalsIgnoreCase("paid")){
                                    o.setStatus("Delivered");
                                    FirebaseDatabase.getInstance()
                                            .getReference("DeliverdOrderDetails")
                                            .child(order.getOrderId())
                                            .setValue(o);

                                    FirebaseDatabase db2 = FirebaseDatabase.getInstance();
                                    DatabaseReference ref2 = db2.getReference("AssignedOrders").child(order.getOrderId());
                                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                cart c = postSnapshot.getValue(cart.class);
                                                FirebaseDatabase.getInstance()
                                                        .getReference("DeliverdOrder")
                                                        .child(order.getOrderId()).child(c.getMedId())
                                                        .setValue(c);

                                            }
                                            FirebaseDatabase.getInstance()
                                                    .getReference("AssignedOrders")
                                                    .child(order.getOrderId()).removeValue();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    FirebaseDatabase.getInstance()
                                            .getReference("AssignedOrdersDetails")
                                            .child(order.getOrderId()).removeValue();
                                }else{
                                    Toast.makeText(plctovst.this, "Order Is Not Yet Paid", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





                    }
                });


            }else{
                    orderViewHolder.itemView.setVisibility(View.GONE);
                    orderViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            }

        };
        recyclermanu.setAdapter(adapter);


    }
}
