package com.simsdroid.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag2Manage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag2Manage extends Fragment {
    View v;

    Button addProduct, search, clear;
    TextView invVal, retVal, potProf;
    EditText searchField;
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

        addProduct = v.findViewById(R.id.btnAddAProduct);
        search = v.findViewById(R.id.btnSearchOnInventoryPage);
        clear = v.findViewById(R.id.btnClearOnInventoryPage);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((MainActivity) getActivity(), AddProductInventory.class);
                startActivity(intent);
            }
        });



        return v;
    }
}