package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.simsdroid.app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TextView actionBar;
    ArrayList<ModelProducts> productListOfInventory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);// no dark mode

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = findViewById(R.id.actionBar);
        replaceFragment(new Frag1POS());
        binding.bottomNavigationView.setSelectedItemId(R.id.pos);
        actionBar.setText("POS");
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
}