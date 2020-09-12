package com.example.salesman.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesman.R;


public class orderViewHolder extends RecyclerView.ViewHolder {
    public TextView order_number , order_date,order_time,order_price,order_priceStatus , order_status,customer_name,customer_phone,customer_address;
    public Button btn_assigned,btn_paid;
    public TableLayout tableLayout;
    public orderViewHolder(@NonNull View itemView) {
        super(itemView);
        tableLayout  = itemView.findViewById(R.id.medicine_list_view);
        tableLayout.setStretchAllColumns(true);
        btn_paid = itemView.findViewById(R.id.btn_paid);
        btn_assigned = itemView.findViewById(R.id.btn_assigned);
        customer_name = itemView.findViewById(R.id.customer_name);
        customer_phone = itemView.findViewById(R.id.customer_phone);
        customer_address = itemView.findViewById(R.id.customer_address);
        order_priceStatus = itemView.findViewById(R.id.order_priceStatus);
        order_date = itemView.findViewById(R.id.order_date);
        order_time = itemView.findViewById(R.id.order_time);
        order_price = itemView.findViewById(R.id.order_price);
        order_status = itemView.findViewById(R.id.order_status);

    }
}
