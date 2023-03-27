package com.simsdroid.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdaptSetting2 extends RecyclerView.Adapter<RecAdaptSetting2.MyViewHolder> {
    public final RecViewInterface2 recViewInterface;
    private ArrayList<ModelItemSettings> itemSettings;

    public RecAdaptSetting2(ArrayList<ModelItemSettings> itemSettings, RecViewInterface2 recViewInterface){
        this.itemSettings = itemSettings;
        this.recViewInterface = recViewInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView text;

        public MyViewHolder(final View view, RecViewInterface2 recViewInterface){
            super(view);
            text = view.findViewById(R.id.tvSettingRecview4Mint);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recViewInterface.onClickItem2(pos);
                        }
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public RecAdaptSetting2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_settings, parent, false);
        return new RecAdaptSetting2.MyViewHolder(itemView, recViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdaptSetting2.MyViewHolder holder, int position) {

        holder.text.setText(itemSettings.get(position).text);


    }

    @Override
    public int getItemCount() {
        return itemSettings.size();
    }
}