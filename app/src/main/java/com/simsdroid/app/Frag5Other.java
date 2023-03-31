package com.simsdroid.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag5Other#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag5Other extends Fragment implements RecViewInterface, RecViewInterface2, RecViewInterface3, RecViewInterface4{
    View v;

    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    ArrayList<ModelItemSettings> genSett = new ArrayList<>();
    ArrayList<ModelItemSettings> invSett = new ArrayList<>();
    ArrayList<ModelItemSettings> ordDebt = new ArrayList<>();
    ArrayList<ModelItemSettings> others = new ArrayList<>();

    String[][] texts = {
            {
                "Change Store Name",
                "Change Address",
                "Add other Details",
                "Change PIN"
            },
            {
                "Export Inventory List",
                "Import Inventory List"
            },
            {
                "Export Orders",
                "Export Orders by date",
                "Export Debt Records"
            },
            {
                    "Help",
                    "About"
            }

    };
    //data
    String name, address, info;

    ExcelHelper xcHalp = new ExcelHelper();

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
        recyclerView4 = v.findViewById(R.id.otherRecView);

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
        for (String s: texts[3]){
            others.add(new ModelItemSettings(s,i));
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
        //
        RecAdaptSetting4 adapter4 = new RecAdaptSetting4(others, this);
        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getContext());
        recyclerView4.setLayoutManager(layoutManager4);
        recyclerView4.setItemAnimator(new DefaultItemAnimator());
        recyclerView4.setAdapter(adapter4);



        return v;
    }

    @Override
    public void onClickItem(int position) {

        //Toast.makeText(getContext(), "position:" + position, Toast.LENGTH_SHORT).show();
        switch (position){
            case 0:
                name = ((MainActivity) getActivity()).dbHalp.getStoreName();
                alertDiaText("Update store name", name, "name");
                break;
            case 1:
                address = ((MainActivity) getActivity()).ReadFromFile("simsdroid-file-01");
                alertDiaText("Update store address", address, "addr");
                break;
            case 2:
                info = ((MainActivity) getActivity()).ReadFromFile("simsdroid-file-02");
                alertDiaTextMulti("Update store info", info);
                break;
            case 3:
                Intent intent = new Intent(getContext(), PIN.class);
                intent.putExtra("pin_act_tag", "reset");
                startActivity(intent);
                break;
        }
    }



    @Override
    public void onClickItem2(int position) {
        switch (position){
            case 0:
                ((MainActivity) getActivity()).updateProductListOfInventory(0);
                ArrayList<ModelProducts> prods = ((MainActivity) getActivity()).productListOfInventory;
                ((MainActivity) getActivity()).updateProductListOfInventory(40);

                XSSFWorkbook xwb = xcHalp.saveToInvWorkbook(prods);
                String dateNow = new SimpleDateFormat("yyyyMMMdd-hhmmssa", Locale.getDefault()).format(new Date());
                String fileName = "SariSari Inventory Information exported on" + dateNow + ".xlsx";
                String path;
                try {
                    path = xcHalp.saveToFile(xwb, fileName, getContext());
                    Toast.makeText(getContext(), "File was created:" + path, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "File failed to create", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 1:
                break;
        }
    }

    @Override
    public void onClickItem3(int position) {
        String dateNow = new SimpleDateFormat("yyyyMMMdd-hhmmssa", Locale.getDefault()).format(new Date());
        XSSFWorkbook xwb;
        String fileName;
        String path;
        switch (position){
            case 0:
                ArrayList<ModelOrders> orders = ((MainActivity) getActivity()).dbHalp.getAllOrdersForExcel();
                xwb = xcHalp.saveToOrdersWorkbook(orders);

                fileName = "SariSari Order History exported on" + dateNow + ".xlsx";
                try {
                    path = xcHalp.saveToFile(xwb, fileName, getContext());
                    Toast.makeText(getContext(), "File was created:" + path, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "File failed to create", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 1:
                break;
            case 2:
                ArrayList<ModelDebts> debts = ((MainActivity) getActivity()).dbHalp.getDebtList("all");
                xwb = xcHalp.saveToDebtsWorkbook(debts);

                fileName = "SariSari Customer Debts exported on" + dateNow + ".xlsx";
                try {
                    path = xcHalp.saveToFile(xwb, fileName, getContext());
                    Toast.makeText(getContext(), "File was created:" + path, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "File failed to create", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onClickItem4(int position) {
        //Toast.makeText(getContext(), "position4:" + position, Toast.LENGTH_SHORT).show();
        switch (position){
            case 0:
                //
                break;
            case 1:
                //
                alertDiaAbout();
                break;
        }
    }
    //
    public boolean checkIfStrValid(String str){
        if(str.equals("")) return false;
        Pattern ps = Pattern.compile("[a-zA-Z0-9\\s]+");
        Matcher ms = ps.matcher(str);
        boolean out = ms.matches();
        return out;
    }

    private void alertDiaText(String title, String field, String tag){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_text, null);
//        ViewGroup viewGroup = findViewById(R.id.content)
//        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_generic, );
        TextView top = v.findViewById(R.id.tvTopDiaText);
        EditText content = v.findViewById(R.id.etFieldDiaText);
        Button okBtn = v.findViewById(R.id.btnOkDiaText);
        Button cancBtn = v.findViewById(R.id.btnCancelDiaText);

        if (tag.equals("name")){
            content.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }else if(tag.equals("addr")){
            content.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
            content.setHint("Address");
        }

        top.setText(title);
        content.setText(field);

        builder.setView(v);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //

                if (tag.equals("name")){
                    name = content.getText().toString();
                    if(checkIfStrValid(name)){
                        ((MainActivity) getActivity()).dbHalp.updateStoreName(name);
                    }else{
                        Toast.makeText(getContext(), "Sorry, this is not accepted", Toast.LENGTH_SHORT).show();
                    }
                }else if(tag.equals("addr")){
                    address = content.getText().toString();
                    if(address.equals("")){
                        Toast.makeText(getContext(), "Sorry, you cannot leave this blank", Toast.LENGTH_SHORT).show();
                    }else{
                        ((MainActivity) getActivity()).WriteToFile("simsdroid-file-01", address);
                    }
                }
                alertDialog.dismiss();

            }
        });
        cancBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void alertDiaTextMulti(String title, String field) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_text_multi, null);
//        ViewGroup viewGroup = findViewById(R.id.content)
//        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_generic, );
        TextView top = v.findViewById(R.id.tvTopDiaTextMulti);
        EditText content = v.findViewById(R.id.etFieldDiaTextMulti);
        Button okBtn = v.findViewById(R.id.btnOkDiaTextMulti);
        Button cancBtn = v.findViewById(R.id.btnCancelDiaTextMulti);

        top.setText(title);
        content.setText(field);

        builder.setView(v);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                info = content.getText().toString();
                ((MainActivity) getActivity()).WriteToFile("simsdroid-file-02", info);
                alertDialog.dismiss();

            }
        });
        cancBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }
    private void alertDiaContri(String str){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_contri, null);

        ImageView img = v.findViewById(R.id.ivContri);
        TextView name = v.findViewById(R.id.tvNameContri);
        TextView desc = v.findViewById(R.id.tvDescriContri);

        if (str.equals("dars")){
            img.setImageResource(R.drawable.dario);
            name.setText("John Dherie Tuyay");
            desc.setText("Digital Artist, Programmer, bleeds coffee when stabbed(probably not true)");
        }else if(str.equals("twirl")){
            img.setImageResource(R.drawable.twirly);
            name.setText("Twirly Joy Paballa");
            desc.setText("Graphic Designer, \nCat Mommie, Hot GF");
        }
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                alertDiaAbout();
            }
        });
    }
    private void alertDiaAbout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_about, null);
        TextView version = v. findViewById(R.id.tvVersion);
        TextView git = v.findViewById(R.id.tvGitHub);
        TextView mail = v.findViewById(R.id.tvEmail);
        TextView link = v.findViewById(R.id.tvLinkedIn);
        TextView tree = v.findViewById(R.id.tvLinkTree);

        ImageView dario = v.findViewById(R.id.icDario);
        ImageView twirl = v.findViewById(R.id.icTwirly);

        version.setText("   Version: " + BuildConfig.VERSION_NAME);

        builder.setView(v);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setGravity(Gravity.TOP);
        alertDialog.show();
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).copyToClip("github.com/karlpaeng");
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).copyToClip("karlraphaelbrinas@gmail.com");
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).copyToClip("linkedin.com/in/karl-raphael-brinas/");
            }
        });
        tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).copyToClip("linktr.ee/karlpaeng");
            }
        });
        dario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                alertDiaContri("dars");
            }
        });
        twirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                alertDiaContri("twirl");
            }
        });
    }



}