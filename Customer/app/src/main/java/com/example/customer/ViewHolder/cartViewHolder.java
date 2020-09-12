package com.example.customer.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer.R;
import com.example.customer.interfaces.ItemClickListner;

public class cartViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

    public TextView names,price,quantity;
    public ItemClickListner listner;
  //  public RecyclerView checkList;
  //  public RecyclerView.LayoutManager checkListManager;


    public cartViewHolder(@NonNull View itemView) {
        super(itemView);

      //  checkList=itemView.findViewById(R.id.checkList);
//        checkList.setHasFixedSize(true);
//        checkListManager=new LinearLayoutManager(this);
//        checkList.setLayoutManager(checkListManager);
//        checkList.setAdapter(checkListAdapter);

        names = itemView.findViewById(R.id.cart_product_name);
        price = itemView.findViewById(R.id.cart_product_price);
        quantity = itemView.findViewById(R.id.cart_product_quantity);

    }

    public void setItemClickListner (ItemClickListner listner) {

        this.listner = listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);
    }
}
