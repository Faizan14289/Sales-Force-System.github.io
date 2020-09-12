package com.example.salesman.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesman.R;

public class customerViewHolder extends RecyclerView.ViewHolder {
    public TextView customer_name,customer_phone,customer_address;

    public customerViewHolder(@NonNull View itemView) {
        super(itemView);
        customer_name = itemView.findViewById(R.id.customer_name);
        customer_phone = itemView.findViewById(R.id.customer_phone);
        customer_address = itemView.findViewById(R.id.customer_address);
    }
}
