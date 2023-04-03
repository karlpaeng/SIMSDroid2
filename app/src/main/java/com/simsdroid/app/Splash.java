package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
    DBHelper dbHalp = new DBHelper(Splash.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(Splash.this, R.color.green));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Splash.this, R.color.green));

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);// no dark mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //dbHalp.xxxResetDB();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(dbHalp.checkExistingUserInfo()){
                    Intent intent = new Intent(Splash.this, PIN.class);
                    intent.putExtra("pin_act_tag", "login");
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(Splash.this, AboutStore.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                finish();
            }
        }, 1024);
    }
}