package com.simsdroid.app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.simsdroid.app.databinding.ActivityMainBinding;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TextView actionBar;

    ArrayList<ModelProducts> productListOfInventory = new ArrayList<>();
    BigDecimal invVal, retval, potProf;

    DBHelper dbHalp = new DBHelper(MainActivity.this);

    ArrayList<ModelOrders> orderListForPOS = new ArrayList<>();
    String barcodeStr;
    BigDecimal totalForPOS = new BigDecimal("0.0");

    long l;
    String date, time;

    ModelProducts prodForSpecAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);// no dark mode

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,

        }, PackageManager.PERMISSION_GRANTED);
        //move this later

        if(dbHalp.checkExistingUserInfo()){
            Intent intent = new Intent(MainActivity.this, PIN.class);
            intent.putExtra("pin_act_tag", "login");
            startActivity(intent);

        }else{
            Intent intent = new Intent(MainActivity.this, AboutStore.class);
            startActivity(intent);
        }

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = findViewById(R.id.actionBar);
        orderListForPOS = dbHalp.getAllTempOrder();
        totalForPOS = dbHalp.getTotalTempOrder();
        String extraStr="";
        if (getIntent().getStringExtra("frag") == null) {



            replaceFragment(new Frag1POS());
            binding.bottomNavigationView.setSelectedItemId(R.id.pos);
            actionBar.setText("POS (Point of Sale)");
            //Toast.makeText(MainActivity.this, "null", Toast.LENGTH_LONG).show();
        }else {
            extraStr = getIntent().getStringExtra("frag");

            //Toast.makeText(MainActivity.this, extraStr, Toast.LENGTH_LONG).show();
            if (extraStr.equals("pos")){
                //
                //Toast.makeText(MainActivity.this, "null", Toast.LENGTH_LONG).show();
                totalForPOS = dbHalp.getTotalTempOrder();
                ModelProducts productForPOS = dbHalp.searchProductsById(Long.parseLong(getIntent().getStringExtra("id_from_spec_amt")));
                int amtForPOS = Integer.parseInt(getIntent().getStringExtra("amount_from_spec_amount"));
                BigDecimal amtXprice = productForPOS.retailPrice.multiply(BigDecimal.valueOf(amtForPOS));
                totalForPOS = totalForPOS.add(amtXprice);

                ModelOrders oneOrder = new ModelOrders(
                        1L,
                        productForPOS.name,
                        productForPOS.id,
                        productForPOS.retailPrice,
                        amtForPOS,
                        amtXprice
                );
                dbHalp.addToTempOrder(oneOrder);
                orderListForPOS = dbHalp.getAllTempOrder();

                replaceFragment(new Frag1POS());
                binding.bottomNavigationView.setSelectedItemId(R.id.pos);
                actionBar.setText("POS (Point of Sale)");

            }else if (extraStr.equals("mnge")){
                //
                updateMngeData();
                replaceFragment(new Frag2Manage());
                binding.bottomNavigationView.setSelectedItemId(R.id.mnge);
                actionBar.setText("Manage Inventory");
            }else if (extraStr.equals("hist")){
                //
            }else if (extraStr.equals("debt")){
                //
                replaceFragment(new Frag4Debt());
                binding.bottomNavigationView.setSelectedItemId(R.id.debt);
                actionBar.setText("Customer Debt");
            }else if (extraStr.equals("othr")){
                //
            }
        }




        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.pos:

                    replaceFragment(new Frag1POS());
                    //set text
                    actionBar.setText("POS (Point of Sale)");
                    break;
                case R.id.mnge:

                    replaceFragment(new Frag2Manage());
                    //set text
                    actionBar.setText("Manage Inventory");
                    break;
                case R.id.hist:

                    replaceFragment(new Frag3History());
                    //set text
                    actionBar.setText("View Order History");
                    break;
                case R.id.debt:

                    replaceFragment(new Frag4Debt());
                    //set text
                    actionBar.setText("Customer Debt");
                    break;
                case R.id.othr:

                    replaceFragment(new Frag5Other());
                    //set text
                    actionBar.setText("Settings");
                    break;

            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, fragment);
        fragmentTransaction.commit();

    }
    public void updateMngeData(){
        invVal = dbHalp.computeInventoryValue();
        retval = dbHalp.computeRetailValue();
        potProf = retval.subtract(invVal);
    }
    public void updateProductListOfInventory(){
        productListOfInventory = dbHalp.allProductsInventory();
    }
    public void searchProdsByName(String name){
        productListOfInventory = dbHalp.searchProductByName(name);
    }
    public void hideKB(){
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public void scanCode(){
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
            Toast.makeText(MainActivity.this, "" + result.getContents(), Toast.LENGTH_SHORT).show();
            barcodeStr = result.getContents();
            //
            if(dbHalp.barcodeExists(barcodeStr)){
                //
                Intent intent = new Intent(MainActivity.this, SpecifyAmount.class);
                prodForSpecAmt = dbHalp.searchProductByBarcode(barcodeStr);
                intent.putExtra("prod_id", ""+prodForSpecAmt.id);
                startActivity(intent);

            }else{
                alertDia("UNKNOWN PRODUCT", "Scanned Barcode is not recognized");
            }


        }else{
            alertDia("Error", "Scan failed. Try again.");
        }
    });
    private void alertDia(String buildTitle, String buildMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(buildTitle);
        builder.setMessage(buildMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
    public void removeFromPosList(int position){
        Toast.makeText(MainActivity.this, "Removed: " + orderListForPOS.get(position).productName , Toast.LENGTH_SHORT).show();
        dbHalp.removeFromTempOrder(orderListForPOS.get(position).orderNumber);
        totalForPOS = totalForPOS.subtract(orderListForPOS.get(position).amountXprice);
        orderListForPOS = dbHalp.getAllTempOrder();

    }
    public void checkOutOrder(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MMM-dd");
        date = simpleDate.format(calendar.getTime());
        simpleDate = new SimpleDateFormat("hh:mm:ss a");
        time = simpleDate.format(calendar.getTime());
        l = dbHalp.getOrderNumber();
        dbHalp.createOrder(orderListForPOS, date, time, l);
        orderListForPOS.clear();
        dbHalp.clearTempOrder();
        totalForPOS = new BigDecimal("0.0");
        //goto receipt
        Toast.makeText(MainActivity.this, "Checked out " , Toast.LENGTH_SHORT).show();

    }


}