package com.simsdroid.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdaptSetting extends RecyclerView.Adapter<RecAdaptSetting.MyViewHolder> {
    public final RecViewInterface recViewInterface;
    private ArrayList<ModelItemSettings> itemSettings;

    public RecAdaptSetting(ArrayList<ModelItemSettings> itemSettings, RecViewInterface recViewInterface){
        this.itemSettings = itemSettings;
        this.recViewInterface = recViewInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView text;

        public MyViewHolder(final View view, RecViewInterface recViewInterface){
            super(view);
            text = view.findViewById(R.id.tvSettingRecview4White);

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
    public RecAdaptSetting.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_sett_2, parent, false);
        return new RecAdaptSetting.MyViewHolder(itemView, recViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdaptSetting.MyViewHolder holder, int position) {

        holder.text.setText(itemSettings.get(position).text);


    }

    @Override
    public int getItemCount() {
        return itemSettings.size();
    }
}
