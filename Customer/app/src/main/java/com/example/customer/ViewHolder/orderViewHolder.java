package com.example.customer.ViewHolder;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer.R;

public class orderViewHolder extends RecyclerView.ViewHolder {
    public TextView order_number , order_date,order_time,order_price , order_status;
    public TableLayout tableLayout;
    public orderViewHolder(@NonNull View itemView) {
        super(itemView);
        tableLayout  = itemView.findViewById(R.id.medicine_list_view);
        tableLayout.setStretchAllColumns(true);
        order_number = itemView.findViewById(R.id.order_number);
        order_date = itemView.findViewById(R.id.order_date);
        order_time = itemView.findViewById(R.id.order_time);
        order_price = itemView.findViewById(R.id.order_price);
        order_status = itemView.findViewById(R.id.order_status);

    }
}
