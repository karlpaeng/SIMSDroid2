package com.simsdroid.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdaptSetting3 extends RecyclerView.Adapter<RecAdaptSetting3.MyViewHolder> {
    public final RecViewInterface3 recViewInterface;
    private ArrayList<ModelItemSettings> itemSettings;

    public RecAdaptSetting3(ArrayList<ModelItemSettings> itemSettings, RecViewInterface3 recViewInterface){
        this.itemSettings = itemSettings;
        this.recViewInterface = recViewInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView text;

        public MyViewHolder(final View view, RecViewInterface3 recViewInterface){
            super(view);
            text = view.findViewById(R.id.tvSettingRecview4White);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recViewInterface.onClickItem3(pos);
                        }
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public RecAdaptSetting3.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rec_sett_2, parent, false);
        return new RecAdaptSetting3.MyViewHolder(itemView, recViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdaptSetting3.MyViewHolder holder, int position) {

        holder.text.setText(itemSettings.get(position).text);


    }

    @Override
    public int getItemCount() {
        return itemSettings.size();
    }
}
