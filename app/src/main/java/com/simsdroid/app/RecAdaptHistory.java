package com.simsdroid.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdaptHistory extends RecyclerView.Adapter<RecAdaptHistory.MyViewHolder> {
    public final RecViewInterface recViewInterface;
    private ArrayList<ModelOrderView> ordersList;

    public RecAdaptHistory(ArrayList<ModelOrderView> ordersList, RecViewInterface recViewInterface) {
        this.ordersList = ordersList;
        this.recViewInterface = recViewInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id, dateTime, total;

        public MyViewHolder(final View view, RecViewInterface recViewInterface) {
            super(view);
            id = view.findViewById(R.id.tvOrdIdHist);
            dateTime = view.findViewById(R.id.tvDateHist);
            total = view.findViewById(R.id.tvTotalHist);

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
    public RecAdaptHistory.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_ord_hist, parent, false);
        return new RecAdaptHistory.MyViewHolder(itemView, recViewInterface);
    }
    @Override
    public void onBindViewHolder(@NonNull RecAdaptHistory.MyViewHolder holder, int position) {

        holder.id.setText("" + ordersList.get(position).orderNumber);
        holder.dateTime.setText(ordersList.get(position).date + " ; " + ordersList.get(position).time);
        holder.total.setText(""+ordersList.get(position).total);

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }
}
