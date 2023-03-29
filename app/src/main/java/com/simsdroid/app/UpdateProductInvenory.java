package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorStateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProductInvenory extends AppCompatActivity {
    EditText name, cost, price, amt;
    Button cancel, update;
    TextView lastUp;
    long id;

    DBHelper dbhalp = new DBHelper(UpdateProductInvenory.this);

    Calendar calendar;
    SimpleDateFormat simpleDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(UpdateProductInvenory.this, R.color.mintish));
        getWindow().setNavigationBarColor(ContextCompat.getColor(UpdateProductInvenory.this, R.color.mintish));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_invenory);
        name = findViewById(R.id.etUpdateProdName);
        cost = findViewById(R.id.etUpdateCost);
        price = findViewById(R.id.etUpdatePrice);
        amt = findViewById(R.id.etUpdateAmtStock);

        cancel = findViewById(R.id.btnCancelUpdate);
        update = findViewById(R.id.btnUpdate);

        lastUp = findViewById(R.id.tvLastUpdateInfo);

        id = Long.parseLong(getIntent().getStringExtra("id_val"));
        name.setText(getIntent().getStringExtra("name_val"));
        cost.setText(getIntent().getStringExtra("cost_val"));
        price.setText(getIntent().getStringExtra("price_val"));
        amt.setText(getIntent().getStringExtra("amt_val"));;
        lastUp.setText("Last update: " + getIntent().getStringExtra("last_up_val"));
        //lastUp.setTextColor(ContextCompat.getColor(UpdateProductInvenory.this, R.color.orange));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                UpdateProductInvenory.this.onBackPressed();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                String nameStr, costStr, priceStr, amtStr;
                nameStr = name.getText().toString();
                costStr = cost.getText().toString();
                priceStr = price.getText().toString();
                amtStr = amt.getText().toString();
                if(checkIfStrValid(nameStr) && checkIfDoubleValid(costStr) && checkIfDoubleValid(priceStr) && checkIfIntValid(amtStr)){
                    calendar = Calendar.getInstance();
                    simpleDate = new SimpleDateFormat("yyyy-MMM-dd");
                    String dateStr = simpleDate.format(calendar.getTime());
                    ModelProducts product = new ModelProducts(1,
                            nameStr,
                            "",
                            new BigDecimal(costStr),
                            new BigDecimal(priceStr),
                            Integer.parseInt(amtStr),
                            dateStr);
                    dbhalp.updateProduct(id, product);

                    Toast.makeText(UpdateProductInvenory.this, "Product has been updated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UpdateProductInvenory.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("frag", "mnge");
                    startActivity(intent);
                }else{
                    //toast
                    Toast.makeText(UpdateProductInvenory.this, "Pls enter valid data", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    public boolean checkIfStrValid(String str){
        if(str.equals("")) return false;
        Pattern ps = Pattern.compile("[a-zA-Z0-9\\s]+");
        Matcher ms = ps.matcher(str);
        boolean out = ms.matches();
        return out;
    }
    public boolean checkIfDoubleValid(String str){
        if(str.equals("")) return false;
        Pattern ps = Pattern.compile("^\\d{1,5}$|(?=^.{1,5}$)^\\d+\\.\\d{0,2}$");
        Matcher ms = ps.matcher(str);
        boolean out = ms.matches();
        return out;
        //^\d{1,5}$|(?=^.{1,5}$)^\d+\.\d{0,2}$
    }
    public boolean checkIfIntValid(String str){
        //[0-9]+
        if(str.equals("")) return false;
        Pattern ps = Pattern.compile("[0-9]+");
        Matcher ms = ps.matcher(str);
        boolean out = ms.matches();
        return out;
    }
}