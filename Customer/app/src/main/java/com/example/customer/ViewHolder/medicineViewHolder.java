package com.example.customer.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer.R;
import com.example.customer.interfaces.ItemClickListner;

public class medicineViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener   {
    public TextView name,price;
  //  public RelativeLayout layout ;
    public ItemClickListner listner;


    public medicineViewHolder(@NonNull View itemView) {
        super(itemView);

      //  layout = itemView.findViewById(R.id.checkLayout);
        name=itemView.findViewById(R.id.lbl_medicine_name);
        price=itemView.findViewById(R.id.lbl_medicine_price);

    }

    public void setItemClickListner (ItemClickListner listner) {

        this.listner = listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);
    }
}
