package com.simsdroid.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdaptReceipt extends RecyclerView.Adapter<RecAdaptReceipt.MyViewHolder> {
    private ArrayList<ModelOrders> orderList;

    public RecAdaptReceipt(ArrayList<ModelOrders> orderList){
        this.orderList = orderList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name, amt, price;

        public MyViewHolder(final View view){
            super(view);
            name = view.findViewById(R.id.tvReceiptProdName);
            amt = view.findViewById(R.id.tvReceiptItemAmt);
            price = view.findViewById(R.id.tvReceiptAmtXPrice);
        }
    }
    @NonNull
    @Override
    public RecAdaptReceipt.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_receipt, parent, false);
        return new RecAdaptReceipt.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdaptReceipt.MyViewHolder holder, int position) {

        holder.name.setText("" + orderList.get(position).productName);
        holder.amt.setText("" + orderList.get(position).amount);
        holder.price.setText("" + orderList.get(position).amountXprice);


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
