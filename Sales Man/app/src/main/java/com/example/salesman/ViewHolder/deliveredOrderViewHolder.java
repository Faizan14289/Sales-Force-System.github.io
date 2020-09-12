package com.example.salesman.ViewHolder;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesman.R;


public class deliveredOrderViewHolder extends RecyclerView.ViewHolder {

    public TextView customer_address,saleMan_name,saleMan_phone , order_date,order_time,order_price , order_status;
    public TableLayout tableLayout;
    public deliveredOrderViewHolder(@NonNull View itemView) {


        super(itemView);

        tableLayout  = itemView.findViewById(R.id.delivered_medicine_list_view);
        tableLayout.setStretchAllColumns(true);
        customer_address = itemView.findViewById(R.id.deliverd_customer_address);
        saleMan_name = itemView.findViewById(R.id.delivered_saleMan_name);
        saleMan_phone = itemView.findViewById(R.id.delivered_saleMan_phone);
        order_date = itemView.findViewById(R.id.delivered_order_date);
        order_time = itemView.findViewById(R.id.delivered_order_time);
        order_price = itemView.findViewById(R.id.delivered_order_price);
        order_status = itemView.findViewById(R.id.delivered_order_status);
    }
}
