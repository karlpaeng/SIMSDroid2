package com.simsdroid.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragReceipt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragReceipt extends Fragment {
    View v;

    TextView storeName, storeAddress, dateTime, ordId, total, otherDeets;
    RecyclerView recviewReceipt;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragReceipt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragReceipt.
     */
    // TODO: Rename and change types and number of parameters
    public static FragReceipt newInstance(String param1, String param2) {
        FragReceipt fragment = new FragReceipt();
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
        v = inflater.inflate(R.layout.fragment_receipt, container, false);
        storeName = v.findViewById(R.id.tvStoreNameReceipt);
        storeAddress = v.findViewById(R.id.tvStoreAddrReceipt);
        dateTime = v.findViewById(R.id.tvDateTimeReceipt);
        ordId = v.findViewById(R.id.tvOrderIdReceipt);
        total = v.findViewById(R.id.tvTotalPayReceipt);
        otherDeets = v.findViewById(R.id.tvOtherDeets);

        recviewReceipt = v.findViewById(R.id.rec_view_receipt);

        //((OrderReceipt) getActivity()).
        dateTime.setText(((OrderReceipt) getActivity()).orderDeets.date + ", " + ((OrderReceipt) getActivity()).orderDeets.time);
        ordId.setText(((OrderReceipt) getActivity()).orderDeets.orderNumber+"");
        total.setText(String.valueOf(((OrderReceipt) getActivity()).orderDeets.total));

        //store

        storeName.setText(((OrderReceipt) getActivity()).dbHalp.getStoreName());
        storeAddress.setText(((OrderReceipt) getActivity()).ReadFromFile("simsdroid-file-01"));
        otherDeets.setText(((OrderReceipt) getActivity()).ReadFromFile("simsdroid-file-02"));


        RecAdaptReceipt adapter = new RecAdaptReceipt(((OrderReceipt) getActivity()).orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recviewReceipt.setLayoutManager(layoutManager);
        recviewReceipt.setItemAnimator(new DefaultItemAnimator());
        recviewReceipt.setAdapter(adapter);


        return v;
    }
}