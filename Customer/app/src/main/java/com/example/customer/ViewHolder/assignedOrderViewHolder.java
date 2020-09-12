package com.example.customer.ViewHolder;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer.R;

public class assignedOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView saleMan_name,saleMan_phone , order_date,order_time,order_price , order_status;
    public TableLayout tableLayout;
    public assignedOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        tableLayout  = itemView.findViewById(R.id.assigned_medicine_list_view);
        tableLayout.setStretchAllColumns(true);

        saleMan_name = itemView.findViewById(R.id.saleMan_name);
        saleMan_phone = itemView.findViewById(R.id.saleMan_phone);
        order_date = itemView.findViewById(R.id.assigned_order_date);
        order_time = itemView.findViewById(R.id.assigned_order_time);
        order_price = itemView.findViewById(R.id.assigned_order_price);
        order_status = itemView.findViewById(R.id.assigned_order_status);
    }
}
