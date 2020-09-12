package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customer.ModelClasses.cart;
import com.example.customer.ModelClasses.customer;
import com.example.customer.ModelClasses.orders;
import com.example.customer.ModelClasses.sales_man;
import com.example.customer.ViewHolder.assignedOrderViewHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Assigned_order extends AppCompatActivity {

    RecyclerView recyclermanu;
    RecyclerView.LayoutManager manager;
    TableLayout tableLayout;


    FirebaseRecyclerAdapter<orders, assignedOrderViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference ref;
    String cnic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_order);

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
        cnic = sharedpreferences.getString("cnic",null);

        db=FirebaseDatabase.getInstance();
        ref=db.getReference("AssignedOrdersDetails");

//        tableLayout = findViewById(R.id.medicine_list_view);
//        tableLayout.setStretchAllColumns(true);
        recyclermanu=findViewById(R.id.Assigned_list);
        recyclermanu.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        recyclermanu.setLayoutManager(manager);
        recyclermanu.setAdapter(adapter);

        loadCart();
    }
    private void loadCart() {


        adapter=new FirebaseRecyclerAdapter<orders, assignedOrderViewHolder>(orders.class,R.layout.assigned_item_list,assignedOrderViewHolder.class,ref) {


            @Override
            protected void populateViewHolder(final assignedOrderViewHolder assignedOrderViewHolder, final orders order, int i) {


                if(cnic.equalsIgnoreCase(order.getCustomerId()))
                {

                    // orderViewHolder.order_number.setText(order.ge);
                    FirebaseDatabase db1 = FirebaseDatabase.getInstance();
                    DatabaseReference ref1 = db1.getReference("salesMan-list").child(order.getSaleManId());
                    ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            sales_man c = dataSnapshot.getValue(sales_man.class);
                            assignedOrderViewHolder.saleMan_name.setText("SalesMan= " + c.getName());
                            assignedOrderViewHolder.saleMan_phone.setText("Phone= " + c.getPhone());


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


                                assignedOrderViewHolder.tableLayout.addView(row1);


//                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference() ;
//                            String key = ref1.push().getKey();
//                            cart c1   =  dataSnapshot.getValue(cart.class);
//                            Log.e(TAG, c1.getMedName());
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    // DataSnapshot d =  postSnapshot.getChildren();

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


                                    assignedOrderViewHolder.tableLayout.addView(row);


                                    //   Log.e(TAG, c.getMedName());

                                    // FirebaseDatabase db1;


//                                ref1.child("Orders").child(cnic).child(key).child(c.getMedId()).setValue(c);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //      Toast.makeText(Order.this, "In populate", Toast.LENGTH_SHORT).show();
                    assignedOrderViewHolder.order_date.setText("Date = " + order.getDate().toString());
                    assignedOrderViewHolder.order_time.setText("Time = " + order.getTime().toString());
                    assignedOrderViewHolder.order_price.setText("Price = " + order.getPrice().toString());
                    assignedOrderViewHolder.order_status.setText("Order Status = " + order.getStatus().toString());



                }else{
                    assignedOrderViewHolder.itemView.setVisibility(View.GONE);
                    assignedOrderViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            }

        };
        recyclermanu.setAdapter(adapter);


    }
}
