package com.simsdroid.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag4Debt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag4Debt extends Fragment implements RecViewInterface{
    View v;

    RecyclerView recyclerView;

    RadioGroup radioGroup;
    RadioButton unpaid, paid, all;

    ArrayList<ModelDebts> debts = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag4Debt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag4Debt.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag4Debt newInstance(String param1, String param2) {
        Frag4Debt fragment = new Frag4Debt();
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
        v = inflater.inflate(R.layout.frag4_debt, container, false);

        recyclerView = v.findViewById(R.id.rec_view_debts);
        radioGroup = v.findViewById(R.id.rbGroupDebt);
        unpaid = v.findViewById(R.id.rbUnpaid);
        paid = v.findViewById(R.id.rbPaid);
        all = v.findViewById(R.id.rbAllDebt);

        radioGroup.check(R.id.rbUnpaid);

        debts = ((MainActivity) getActivity()).dbHalp.getDebtList("unpaid");
        updateRecView();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioId = radioGroup.getCheckedRadioButtonId();

                if(radioId == R.id.rbUnpaid){
                    //
                    debts = ((MainActivity) getActivity()).dbHalp.getDebtList("unpaid");
                }else if(radioId == R.id.rbPaid){
                    //
                    debts = ((MainActivity) getActivity()).dbHalp.getDebtList("paid");
                }else if(radioId == R.id.rbAllDebt){
                    //
                    debts = ((MainActivity) getActivity()).dbHalp.getDebtList("all");
                }
                updateRecView();
            }
        });



        return v;
    }
    private void updateRecView(){
        RecAdaptDebt adapter = new RecAdaptDebt(debts, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
        //
        Intent intent = new Intent(getContext(), DebtInfo.class);
        intent.putExtra("debt_id", debts.get(position).orderNumber);
        startActivity(intent);
    }
}