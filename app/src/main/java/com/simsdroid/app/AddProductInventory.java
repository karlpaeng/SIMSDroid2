package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProductInventory extends AppCompatActivity {
    Button addBarcode, addThisProduct;
    EditText name, cost, price, amount;
    TextView warningBarcode;
    String nameStr, barcodeStr, costStr, priceStr, amtStr, dateStr;
    BigDecimal costDoub, priceDoub;
    int amtInt;

    Calendar calendar;
    SimpleDateFormat simpleDate;

    DBHelper dbHalp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_inventory);
        addBarcode = findViewById(R.id.btnAddBarcode);
        addThisProduct = findViewById(R.id.btnAddProductToInv);

        name = findViewById(R.id.etProductName);
        cost = findViewById(R.id.etCost);
        price = findViewById(R.id.etRetPrice);
        amount = findViewById(R.id.etAmtStock);

        warningBarcode = findViewById(R.id.tvWarningForBarcode);

        barcodeStr = "";
        dbHalp = new DBHelper(AddProductInventory.this);
        addThisProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hides kyeboard
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                nameStr = name.getText().toString();
                costStr = cost.getText().toString();
                priceStr = price.getText().toString();
                amtStr = amount.getText().toString();

                calendar = Calendar.getInstance();
                simpleDate = new SimpleDateFormat("yyyy-MMM-dd");
                dateStr = simpleDate.format(calendar.getTime());

                if(checkIfStrValid(nameStr) && checkIfDoubleValid(costStr) && checkIfDoubleValid(priceStr) && checkIfIntValid(amtStr)){
                    costDoub = new BigDecimal(costStr);
                    priceDoub = new BigDecimal(priceStr);
                    amtInt = Integer.parseInt(amtStr);
                    ModelProducts product = new ModelProducts(1, nameStr, barcodeStr, costDoub, priceDoub, amtInt, dateStr);
                    long i = dbHalp.addProduct(product);
                    //Toast.makeText(AddProductInventory.this, i+" "+product.cost, Toast.LENGTH_LONG).show();
                    //AddProductInventory.this.onBackPressed();
                    Intent intent = new Intent(AddProductInventory.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("frag", "mnge");
                    startActivity(intent);

                }else{
                    //toast
                    Toast.makeText(AddProductInventory.this, "Pls enter valid data", Toast.LENGTH_LONG).show();
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