package com.simsdroid.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecAdaptInventory extends RecyclerView.Adapter<RecAdaptInventory.MyViewHolder> {
    private ArrayList<ModelProducts> productsList;

    public RecAdaptInventory(ArrayList<ModelProducts> productsList) {
        this.productsList = productsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, cost, price, amt;

        public MyViewHolder(final View view) {
            super(view);
            name = view.findViewById(R.id.rec_inven_prod_name);
            cost = view.findViewById(R.id.rec_inven_cost);
            price = view.findViewById(R.id.rec_inven_price);
            amt = view.findViewById(R.id.rec_inven_stock);
        }
    }

    @NonNull
    @Override
    public RecAdaptInventory.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_inventory, parent, false);
        return new RecAdaptInventory.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull RecAdaptInventory.MyViewHolder holder, int position) {

        holder.name.setText("" + productsList.get(position).name);
        holder.cost.setText(""+productsList.get(position).cost);
        holder.price.setText(""+productsList.get(position).retailPrice);
        holder.amt.setText(""+productsList.get(position).amountStock);

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
