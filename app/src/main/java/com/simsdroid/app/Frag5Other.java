package com.simsdroid.app;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag5Other#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag5Other extends Fragment implements RecViewInterface, RecViewInterface2, RecViewInterface3, RecViewInterface4, DatePickerDialog.OnDateSetListener {
    View v;

    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    ArrayList<ModelItemSettings> genSett = new ArrayList<>();
    ArrayList<ModelItemSettings> invSett = new ArrayList<>();
    ArrayList<ModelItemSettings> ordDebt = new ArrayList<>();
    ArrayList<ModelItemSettings> others = new ArrayList<>();

    String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    Uri uri;
    String dateSelected, fileSelected;
    boolean proceedable;
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

        dateSelected = "Select a date";
        fileSelected = "Select a file";
        proceedable = false;

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
                alertDiaOkCancel(
                        "Saving inventory list to file",
                        "This will create an XLSX file containing all current inventory data, " +
                                "the file will be saved to \"Documents/SariSari POS\". Proceed?",
                        "exp_inv"
                );
                break;
            case 1:
                alertDiaPick(
                        "Updating Inventory information",
                        "This will replace all currently saved inventory items " +
                                "with the data in the file to be selected. " +
                                "\nNote: cells in the XLSX file should strictly be \"Text formatted\" cells.\n" +
                                "Tap on \"Select a file\" then tap \"Okay\" to continue. Proceed with caution.",
                        "file"
                );
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//
//                String[] mimetypes =
//                        { "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // .xlsx
//                        };
//                intent.setType("*/*");
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                resultLauncher.launch(intent);
                break;
        }
    }

    @Override
    public void onClickItem3(int position) {

        switch (position){
            case 0:
                alertDiaOkCancel(
                        "Saving orders history to file",
                        "This will create an XLSX file containing all records of product orders, " +
                                "the file will be saved to \"Documents/SariSari POS\". Proceed?",
                        "exp_ord"
                );
                break;
            case 1:
                alertDiaPick(
                        "Saving orders history to file",
                        "This will create an XLSX file containing product orders " +
                                "on a specified date, the file will be saved to \"Documents/SariSari POS\". " +
                                "Tap on \"Select a date\" then tap \"Okay\" to proceed.",
                        "date"
                );
                break;
            case 2:
                alertDiaOkCancel(
                        "Saving debt records to file",
                        "This will create an XLSX file containing all debt records, " +
                                "the file will be saved to \"Documents/SariSari POS\". Proceed?",
                        "exp_debt"
                );
                break;
        }
    }

    @Override
    public void onClickItem4(int position) {
        //Toast.makeText(getContext(), "position4:" + position, Toast.LENGTH_SHORT).show();
        switch (position){
            case 0:
                //
                alertDiaHelp();
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
                //((MainActivity) getActivity()).copyToClip("github.com/karlpaeng");
                Uri uri = Uri.parse("https://github.com/karlpaeng");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MainActivity) getActivity()).copyToClip("karlraphaelbrinas@gmail.com");
                String mailto = "mailto:karlraphaelbrinas@gmail.com" +
                        "?cc=" +
                        "&subject=" + Uri.encode(((MainActivity) getActivity()).dbHalp.getStoreName() + ", SariSari POS user") ;
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "Error, could not find email app", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity) getActivity()).copyToClip("linktr.ee/karlpaeng");
                Uri uri = Uri.parse("https://linktr.ee/karlpaeng");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    if(intent != null){
                        uri = intent.getData();
                        proceedable = true;
                        fileSelected = uri.getPath();
                        //Toast.makeText(getContext(), uri.getPath() , Toast.LENGTH_SHORT).show();

                    }
                    alertDiaPick(
                            "Updating Inventory information",
                            "This will replace all currently saved inventory items " +
                                    "with the data in the file to be selected. " +
                                    "Tap on \"Select a file\" then tap \"Okay\" to continue. Proceed with caution.",
                            "file"
                    );
                }

            });
    private void alertDiaPick(String buildTitle, String buildMessage, String action){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_picker, null);

        TextView top = v.findViewById(R.id.tvTopDiaPicker);
        TextView content = v.findViewById(R.id.tvContentDiaPicker);
        TextView pick = v.findViewById(R.id.tvBtnDiaPicker);
        Button canc = v.findViewById(R.id.btnCancelDiaPicker);
        Button okBtn = v.findViewById(R.id.btnOkDiaPicker);

        top.setText(buildTitle);
        content.setText(buildMessage);

        if(action.equals("date")){
            //
            pick.setText(dateSelected);
        }else if(action.equals("file")){
            //
            pick.setText(fileSelected);
        }

        builder.setView(v);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action.equals("date")){
                    //
                    alertDialog.dismiss();
                    showDatePicker();
                }else if(action.equals("file")){
                    alertDialog.dismiss();
                    //
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                    String[] mimetypes =
                            { "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // .xlsx
                            };
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    resultLauncher.launch(intent);
                }
            }
        });
        canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSelected = "Select a date";
                fileSelected = "Select a file";
                proceedable = false;
                alertDialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            String dateNow = new SimpleDateFormat("yyyyMMMdd-hhmmssa", Locale.getDefault()).format(new Date());
            XSSFWorkbook xwb;
            String fileName;
            String path;
            @Override
            public void onClick(View view) {
                if(action.equals("date") && proceedable){
                    //
                    ArrayList<ModelOrders> orders = ((MainActivity) getActivity()).dbHalp.getAllOrdersForExcel(dateSelected);
                    xwb = xcHalp.saveToOrdersWorkbook(orders, dateSelected);

                    fileName = "SariSari Order History during " + dateSelected + " exported on " + dateNow + ".xlsx";
                    try {
                        path = xcHalp.saveToFile(xwb, fileName, getContext());
                        Toast.makeText(getContext(), "File was created:" + path, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "File failed to create", Toast.LENGTH_SHORT).show();
                        Log.d("debugTag:", e.toString());
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }else if(action.equals("file") && proceedable){
                    //
                    try {
                        xcHalp.insertToDB(getContext(), uri);
                        Toast.makeText(getContext(), "Data successfully imported", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "File failed to import", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Select a " + action + " first" , Toast.LENGTH_SHORT).show();
                }
                fileSelected = "Select a file";
                dateSelected = "Select a date";
                proceedable = false;

            }
        });

    }
    private void alertDiaOkCancel(String buildTitle, String buildMessage, String action){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_ok_cancel, null);

        TextView top = v.findViewById(R.id.tvTopDiaCancel);
        TextView content = v.findViewById(R.id.tvContentDiaCancel);
        Button canc = v.findViewById(R.id.btnCancelDiaCancel);
        Button okBtn = v.findViewById(R.id.btnOkDiaCancel);

        top.setText(buildTitle);
        content.setText(buildMessage);
        builder.setView(v);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            String dateNow = new SimpleDateFormat("yyyyMMMdd-hhmmssa", Locale.getDefault()).format(new Date());
            XSSFWorkbook xwb;
            String fileName;
            String path;
            @Override
            public void onClick(View view) {
                if (action.equals("exp_inv")){
                    ((MainActivity) getActivity()).updateProductListOfInventory(0);
                    ArrayList<ModelProducts> prods = ((MainActivity) getActivity()).productListOfInventory;
                    ((MainActivity) getActivity()).updateProductListOfInventory(40);

                    xwb = xcHalp.saveToInvWorkbook(prods);
                    String dateNow = new SimpleDateFormat("yyyyMMMdd-hhmmssa", Locale.getDefault()).format(new Date());
                    fileName = "SariSari Inventory Information exported on " + dateNow + ".xlsx";

                    try {
                        path = xcHalp.saveToFile(xwb, fileName, getContext());
                        Toast.makeText(getContext(), "File was created:" + path, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "File failed to create", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else if(action.equals("exp_ord")){
                    ArrayList<ModelOrders> orders = ((MainActivity) getActivity()).dbHalp.getAllOrdersForExcel("all");
                    xwb = xcHalp.saveToOrdersWorkbook(orders, "all");

                    fileName = "SariSari Order History exported on " + dateNow + ".xlsx";
                    try {
                        path = xcHalp.saveToFile(xwb, fileName, getContext());
                        Toast.makeText(getContext(), "File was created:" + path, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "File failed to create", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else if(action.equals("exp_debt")){
                    ArrayList<ModelDebts> debts = ((MainActivity) getActivity()).dbHalp.getDebtList("all");
                    xwb = xcHalp.saveToDebtsWorkbook(debts);

                    fileName = "SariSari Customer Debts exported on " + dateNow + ".xlsx";
                    try {
                        path = xcHalp.saveToFile(xwb, fileName, getContext());
                        Toast.makeText(getContext(), "File was created:" + path, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "File failed to create", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                alertDialog.dismiss();
            }
        });
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String zerostr = "";
        if(i2 < 10) zerostr = "0";
        dateSelected = i + "-" + monthNames[i1] + "-" + zerostr + i2;
        proceedable = true;
        alertDiaPick(
                "Saving orders history to file",
                "This will create an XLSX file containing product orders " +
                        "on a specified date, the file will be saved to \"Documents/SariSari POS\". " +
                        "Tap on \"Select a date\" then tap \"Okay\" to proceed.",
                "date"
        );

    }
    private void showDatePicker(){
        DatePickerDialog datePickDia = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickDia.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                alertDiaPick(
                        "Saving orders history to file",
                        "This will create an XLSX file containing product orders " +
                                "on a specified date, the file will be saved to \"Documents/SariSari POS\". " +
                                "Tap on \"Select a date\" then tap \"Okay\" to proceed.",
                        "date"
                );
            }
        });
        datePickDia.show();
    }
    private void alertDiaHelp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_help, null);

        TextView appGuide = v.findViewById(R.id.tvViewAppGuide);
        TextView devContact = v.findViewById(R.id.tvContactDev);
        TextView close = v.findViewById(R.id.tvCloseDiaHelp);

        builder.setView(v);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        appGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                //
                ((MainActivity) getActivity()).alertDiaTut("Close");
            }
        });
        devContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailto = "mailto:karlraphaelbrinas@gmail.com" +
                        "?cc=" +
                        "&subject=" + Uri.encode("SariSari POS Bug Report: " + ((MainActivity) getActivity()).dbHalp.getStoreName()) +
                        "&body=" + Uri.encode("<Please fill in details, with screenshots if possible, thanks>");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "Error, could not find email app", Toast.LENGTH_SHORT).show();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
/*

*/


}