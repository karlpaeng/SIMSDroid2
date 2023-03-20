package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProductInventory extends AppCompatActivity {
    Button addBarcode, addThisProduct;
    EditText name, cost, price, amount;
    TextView warningBarcode;
    String nameStr, barcodeStr, costStr, priceStr, amtStr, dateStr;
    Double costDoub, priceDoub;
    int amtInt;

    Calendar calendar;
    SimpleDateFormat simpleDate;

    DBHelper dbHalp = new DBHelper(AddProductInventory.this);
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
        addThisProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameStr = name.getText().toString();
                costStr = cost.getText().toString();
                priceStr = price.getText().toString();
                amtStr = amount.getText().toString();

                calendar = Calendar.getInstance();
                simpleDate = new SimpleDateFormat("yyyy-MMM-dd");
                dateStr = simpleDate.format(calendar.getTime());

                if(checkIfStrValid(nameStr) && checkIfDoubleValid(costStr) && checkIfDoubleValid(priceStr) && checkIfIntValid(amtStr)){
                    costDoub = Double.parseDouble(costStr);
                    priceDoub = Double.parseDouble(priceStr);
                    amtInt = Integer.parseInt(amtStr);
                    ModelProducts product = new ModelProducts(1, nameStr, barcodeStr, costDoub, priceDoub, amtInt, dateStr);
                    dbHalp.addProduct(product);
                    Toast.makeText(AddProductInventory.this, "Producted added: " + product.toString(), Toast.LENGTH_LONG).show();

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