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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag2Manage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag2Manage extends Fragment implements RecViewInterface{
    View v;
    ArrayList<ModelProducts> prodList = new ArrayList<>();

    RecyclerView frag2RecyclerView;

    Button addProduct, search, clear;
    TextView invVal, retVal, potProf;
    EditText searchField;

    String searchTerm;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Frag2Manage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag2Manage.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag2Manage newInstance(String param1, String param2) {
        Frag2Manage fragment = new Frag2Manage();
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
        v = inflater.inflate(R.layout.frag2_manage, container, false);

        frag2RecyclerView = v.findViewById(R.id.rec_view);

        addProduct = v.findViewById(R.id.btnAddAProduct);
        search = v.findViewById(R.id.btnSearchOnInventoryPage);
        clear = v.findViewById(R.id.btnClearOnInventoryPage);

        searchField = v.findViewById(R.id.etSearchInventoryPage);

        invVal = v.findViewById(R.id.tvTotalInvVal);
        retVal = v.findViewById(R.id.tvTotalRetailVal);
        potProf = v.findViewById(R.id.tvTotalPotVal);

        ((MainActivity) getActivity()).updateMngeData();

        invVal.setText(((MainActivity) getActivity()).invVal + "");
        retVal.setText(((MainActivity) getActivity()).retval + "");
        potProf.setText(((MainActivity) getActivity()).potProf + "");

        ((MainActivity) getActivity()).updateProductListOfInventory();;
        prodList = ((MainActivity) getActivity()).productListOfInventory;

        updateRecView();

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((MainActivity) getActivity(), AddProductInventory.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).hideKB();
                //
                searchTerm = searchField.getText().toString();

                if (checkIfStrValid(searchTerm)){
                    //
                    ((MainActivity) getActivity()).searchProdsByName(searchTerm);;
                    prodList = ((MainActivity) getActivity()).productListOfInventory;
                    updateRecView();
                }else{
                    //toast
                    Toast.makeText(getContext(), "Pls enter a valid product name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Cleared search term", Toast.LENGTH_SHORT).show();
                searchField.setText("");
                ((MainActivity) getActivity()).hideKB();
                ((MainActivity) getActivity()).updateProductListOfInventory();
                prodList = ((MainActivity) getActivity()).productListOfInventory;
                updateRecView();
            }
        });



        return v;
    }
    private void updateRecView(){
        //
        RecAdaptInventory adapter = new RecAdaptInventory(prodList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        frag2RecyclerView.setLayoutManager(layoutManager);
        frag2RecyclerView.setItemAnimator(new DefaultItemAnimator());
        frag2RecyclerView.setAdapter(adapter);
    }
    private boolean checkIfStrValid(String str){
        if(str.equals("")) return false;
        Pattern ps = Pattern.compile("[a-zA-Z0-9\\s]+");
        Matcher ms = ps.matcher(str);
        boolean out = ms.matches();
        return out;
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getContext(), UpdateProductInvenory.class);
        intent.putExtra("id_val", ""+prodList.get(position).id);
        intent.putExtra("name_val", ""+prodList.get(position).name);
        intent.putExtra("cost_val", ""+prodList.get(position).cost);
        intent.putExtra("price_val", ""+prodList.get(position).retailPrice);
        intent.putExtra("amt_val", ""+prodList.get(position).amountStock);

        startActivity(intent);
    }
}