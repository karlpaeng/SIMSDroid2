package com.simsdroid.app;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag3History#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag3History extends Fragment implements RecViewInterface, DatePickerDialog.OnDateSetListener {
    View v;

    EditText searchBar;
    Button search, clear, openDatePicker;
    RecyclerView recyclerView;

    ArrayList<ModelOrderView> orderViews = new ArrayList<>();

    String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag3History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag3History.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag3History newInstance(String param1, String param2) {
        Frag3History fragment = new Frag3History();
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
        v = inflater.inflate(R.layout.frag3_history, container, false);

        recyclerView = v.findViewById(R.id.rec_view_hist);
        searchBar = v.findViewById(R.id.etIdSearchHist);
        search = v.findViewById(R.id.btnSearchOnHist);
        clear = v.findViewById(R.id.btnClearOnHist);
        openDatePicker = v.findViewById(R.id.btnSearchDateHist);

        orderViews = ((MainActivity) getActivity()).dbHalp.getOrderHistory(30);
        updateRecView();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                ((MainActivity) getActivity()).hideKB();
                if(searchBar.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Enter a valid Order ID", Toast.LENGTH_SHORT).show();
                }else {

                    orderViews = ((MainActivity) getActivity()).dbHalp.searchOrderNumById(
                            Long.parseLong(searchBar.getText().toString())
                    );
                    updateRecView();
                    Toast.makeText(getContext(), "Found " + orderViews.size() + " result(s)", Toast.LENGTH_SHORT).show();
                }

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).hideKB();
                searchBar.setText("");
                orderViews = ((MainActivity) getActivity()).dbHalp.getOrderHistory(30);
                updateRecView();
                Toast.makeText(getContext(), "Cleared search", Toast.LENGTH_SHORT).show();
            }
        });
        openDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open date picker
                showDatePickerDialog();
            }
        });





        return v;
    }
    private void updateRecView(){
        //
        RecAdaptHistory adapter = new RecAdaptHistory(orderViews, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getContext(), OrderReceipt.class);
        intent.putExtra("order_num", orderViews.get(position).orderNumber);
        startActivity(intent);

    }
    private void showDatePickerDialog(){
        DatePickerDialog datePickDia = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickDia.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String zerostr = "";
        if(i2 < 10) zerostr = "0";
        String dateSelected = i + "-" + monthNames[i1] + "-" + zerostr + i2;
        orderViews = ((MainActivity) getActivity()).dbHalp.searchOrderNumByDate(dateSelected);
        updateRecView();
        Toast.makeText(getContext(), "Found " + orderViews.size() + " result(s)", Toast.LENGTH_SHORT).show();

    }
}