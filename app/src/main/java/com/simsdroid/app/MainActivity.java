package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.simsdroid.app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TextView actionBar;

    ArrayList<ModelProducts> productListOfInventory = new ArrayList<>();
    double invVal, retval, potProf;
    DBHelper dbHalp = new DBHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);// no dark mode

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = findViewById(R.id.actionBar);
        String extraStr="";
        if (getIntent().getStringExtra("frag") == null) {
            replaceFragment(new Frag1POS());
            binding.bottomNavigationView.setSelectedItemId(R.id.pos);
            actionBar.setText("POS");
            Toast.makeText(MainActivity.this, "null", Toast.LENGTH_LONG).show();
        }else {
            extraStr = getIntent().getStringExtra("frag");
            updateMngeData();
            //Toast.makeText(MainActivity.this, extraStr, Toast.LENGTH_LONG).show();
            if (extraStr == "pos"){
                //
            }else if (extraStr.equals("mnge")){
                //
                replaceFragment(new Frag2Manage());
                binding.bottomNavigationView.setSelectedItemId(R.id.mnge);
                actionBar.setText("Manage Inventory");
            }else if (extraStr.equals("hist")){
                //
            }else if (extraStr.equals("debt")){
                //
            }else if (extraStr.equals("othr")){
                //
            }
        }




        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.pos:

                    replaceFragment(new Frag1POS());
                    //set text
                    actionBar.setText("POS");
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
        potProf = retval - invVal;
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

}