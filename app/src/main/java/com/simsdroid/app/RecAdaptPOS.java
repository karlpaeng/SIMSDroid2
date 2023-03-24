package com.simsdroid.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecAdaptPOS extends RecyclerView.Adapter<RecAdaptPOS.MyViewHolder> {
    public final RecViewInterface recViewInterface;
    private ArrayList<ModelOrders> ordersList;

    public RecAdaptPOS(ArrayList<ModelOrders> ordersList, RecViewInterface recViewInterface) {
        this.ordersList = ordersList;
        this.recViewInterface = recViewInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, amt, amtXprice;

        public MyViewHolder(final View view, RecViewInterface recViewInterface) {
            super(view);
            //-----
            name = view.findViewById(R.id.tvPosProdName);
            amt = view.findViewById(R.id.tvPosItemAmt);
            amtXprice = view.findViewById(R.id.tvPosAmtXPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recViewInterface.onClickItem(pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecAdaptPOS.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_pos, parent, false);
        return new RecAdaptPOS.MyViewHolder(itemView, recViewInterface);
    }
    @Override
    public void onBindViewHolder(@NonNull RecAdaptPOS.MyViewHolder holder, int position) {

        //-------
        holder.name.setText("" + ordersList.get(position).productName);
        holder.amt.setText(""+ ordersList.get(position).amount);
        holder.amtXprice.setText("" + ordersList.get(position).amountXprice);

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }
}
