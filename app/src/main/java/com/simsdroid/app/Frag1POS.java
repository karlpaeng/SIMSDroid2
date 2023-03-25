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
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag1POS#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag1POS extends Fragment implements RecViewInterface{
    View v;

    ArrayList<ModelOrders> orderList = new ArrayList<>();
    RecyclerView frag1RecyclerView;
    Button bcScan, noBCscan, checkOut;
    Switch debtSW;
    TextView total;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag1POS() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag1POS.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag1POS newInstance(String param1, String param2) {
        Frag1POS fragment = new Frag1POS();
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
        v = inflater.inflate(R.layout.frag1_pos, container, false);

        frag1RecyclerView = v.findViewById(R.id.rec_view_pos);

        bcScan = v.findViewById(R.id.btnScanOnPOS);
        noBCscan = v.findViewById(R.id.btnSearchOnPOS);
        checkOut = v.findViewById(R.id.btnCheckOut);

        debtSW = v.findViewById(R.id.swDebtIsTrue);

        total = v.findViewById(R.id.tvTotalPOS);

        updateRecView();
        total.setText(String.valueOf(((MainActivity) getActivity()).totalForPOS));

        bcScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).scanCode();
            }
        });
        noBCscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), SearchProductForPOS.class);
                startActivity(intent);
            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create order, check for debt entry, check if list is empty
                if(orderList.isEmpty()){
                    Toast.makeText(getContext(), "Try adding a product first", Toast.LENGTH_LONG).show();
                }else{
                    if(debtSW.isChecked()){
                        //add to debts
                    }else{
                        Intent intent = new Intent(getContext(), OrderReceipt.class);

                        ((MainActivity) getActivity()).checkOutOrder();
                        intent.putExtra("order_num", ((MainActivity) getActivity()).l);
                        updateRecView();
                        total.setText(String.valueOf(((MainActivity) getActivity()).totalForPOS));


                        startActivity(intent);
                    }
                }
            }
        });

        return v;
    }
    private void updateRecView(){
        orderList = ((MainActivity) getActivity()).orderListForPOS;

        RecAdaptPOS adapter = new RecAdaptPOS(orderList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        frag1RecyclerView.setLayoutManager(layoutManager);
        frag1RecyclerView.setItemAnimator(new DefaultItemAnimator());
        frag1RecyclerView.setAdapter(adapter);
    }
    @Override
    public void onClickItem(int position) {
        ////remove from array list, reacquire array list, updaterecview
        ((MainActivity) getActivity()).removeFromPosList(position);

        updateRecView();
        total.setText(String.valueOf(((MainActivity) getActivity()).totalForPOS));


    }
}