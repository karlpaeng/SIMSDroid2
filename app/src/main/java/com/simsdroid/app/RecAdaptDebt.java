package com.simsdroid.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdaptDebt extends RecyclerView.Adapter<RecAdaptDebt.MyViewHolder> {
    public final RecViewInterface recViewInterface;
    private ArrayList<ModelDebts> debts;

    public RecAdaptDebt(ArrayList<ModelDebts> debts, RecViewInterface recViewInterface) {
        this.debts = debts;
        this.recViewInterface = recViewInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id, date, name;

        public MyViewHolder(final View view, RecViewInterface recViewInterface) {
            super(view);
            id = view.findViewById(R.id.idOnDebtRec);
            date = view.findViewById(R.id.dateOnDebtRec);
            name = view.findViewById(R.id.nameOnDebtRec);

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
    public RecAdaptDebt.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_debts, parent, false);
        return new RecAdaptDebt.MyViewHolder(itemView, recViewInterface);
    }
    @Override
    public void onBindViewHolder(@NonNull RecAdaptDebt.MyViewHolder holder, int position) {

        holder.id.setText("" + debts.get(position).orderNumber);
        holder.date.setText(debts.get(position).dateCheckout);
        holder.name.setText(debts.get(position).customerName);

    }

    @Override
    public int getItemCount() {
        return debts.size();
    }
}