package com.example.customer.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer.R;

public class deliveredOrderViewHolder extends RecyclerView.ViewHolder {

    public TextView saleMan_name,saleMan_phone , order_date,order_time,order_price , order_status;
    public Button btn_confirmed;
    public TableLayout tableLayout;
    public deliveredOrderViewHolder(@NonNull View itemView) {


        super(itemView);

        tableLayout  = itemView.findViewById(R.id.delivered_medicine_list_view);
        tableLayout.setStretchAllColumns(true);

        btn_confirmed = itemView.findViewById(R.id.btn_confirmed);
        saleMan_name = itemView.findViewById(R.id.delivered_saleMan_name);
        saleMan_phone = itemView.findViewById(R.id.delivered_saleMan_phone);
        order_date = itemView.findViewById(R.id.delivered_order_date);
        order_time = itemView.findViewById(R.id.delivered_order_time);
        order_price = itemView.findViewById(R.id.delivered_order_price);
        order_status = itemView.findViewById(R.id.delivered_order_status);
    }
}
