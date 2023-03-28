package com.simsdroid.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag5Other#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag5Other extends Fragment implements RecViewInterface, RecViewInterface2, RecViewInterface3{
    View v;

    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    ArrayList<ModelItemSettings> genSett = new ArrayList<>();
    ArrayList<ModelItemSettings> invSett = new ArrayList<>();
    ArrayList<ModelItemSettings> ordDebt = new ArrayList<>();

    String[][] texts = {
            {
                "Change Store Name",
                "Change Address",
                "Add other Details",
                "Change PIN"
            },
            {
                "Export Inventory Information",
                "Import Inventory Information"
            },
            {
                "Export Order History",
                "Export Debt Records"
            }
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag5Other() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag5Other.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag5Other newInstance(String param1, String param2) {
        Frag5Other fragment = new Frag5Other();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.frag5_other, container, false);

        recyclerView1 = v.findViewById(R.id.genSettRecView);
        recyclerView2 = v.findViewById(R.id.invSettRecView);
        recyclerView3 = v.findViewById(R.id.orderDebtRecView);
        int i = 0;
        for (String s: texts[0]){

            genSett.add(new ModelItemSettings(s,i));
            i++;
        }
        for (String s: texts[1]){
            invSett.add(new ModelItemSettings(s,i));
            i++;
        }
        for (String s: texts[2]){
            ordDebt.add(new ModelItemSettings(s,i));
            i++;
        }
        //
        RecAdaptSetting adapter = new RecAdaptSetting(genSett, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(adapter);
        //
        RecAdaptSetting2 adapter2 = new RecAdaptSetting2(invSett, this);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(adapter2);
        //
        RecAdaptSetting3 adapter3 = new RecAdaptSetting3(ordDebt, this);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getContext());
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        recyclerView3.setAdapter(adapter3);




        return v;
    }

    @Override
    public void onClickItem(int position) {

        Toast.makeText(getContext(), "position:" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickItem2(int position) {
        Toast.makeText(getContext(), "position2:" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickItem3(int position) {
        Toast.makeText(getContext(), "position3:" + position, Toast.LENGTH_SHORT).show();
    }
}