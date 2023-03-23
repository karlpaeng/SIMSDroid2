package com.simsdroid.app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
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
        addBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBarcode.setText("Rescan Barcode");

//                Button newBtn = new Button(new ContextThemeWrapper(getApplicationContext(), R.style.btn3theme));
//                newBtn = findViewById(R.id.btnAddBarcode);
                //setTheme(R.style.btn3theme);

                //addBarcode.setTextAppearance(R.style.btn3theme);

                scanCode();
            }
        });
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

    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Press volume up button to turn on flash");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->{
        if(result.getContents() != null){
            //result.getContents();
            Toast.makeText(AddProductInventory.this, "" + result.getContents(), Toast.LENGTH_SHORT).show();
            barcodeStr = result.getContents();
            warningBarcode.setText("The item will have a Barcode");

        }else{
            alertDia("Error", "Scan failed. Try again.");
        }
    });
    private void alertDia(String buildTitle, String buildMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductInventory.this);
        builder.setTitle(buildTitle);
        builder.setMessage(buildMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
}