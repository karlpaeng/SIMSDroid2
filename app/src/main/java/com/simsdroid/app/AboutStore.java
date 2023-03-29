package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AboutStore extends AppCompatActivity {
    EditText name, addr, other;
    Button next;

    String nameStr, addrStr, otherStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_store);

        name = findViewById(R.id.etStoreName);
        addr = findViewById(R.id.etStoreAddress);
        other = findViewById(R.id.etOtherInfo);
        next = findViewById(R.id.btnNextAboutStoreAct);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                nameStr = name.getText().toString();
                addrStr = addr.getText().toString();
                otherStr = other.getText().toString();

                if(checkIfStrValid(nameStr) && !addrStr.equals("")){
                    //
                    Intent intent = new Intent(AboutStore.this, PIN.class);
                    intent.putExtra("name", nameStr);
                    intent.putExtra("addr", addrStr);
                    intent.putExtra("other", otherStr);
                    intent.putExtra("pin_act_tag", "setup");

                    startActivity(intent);

                    //goto next act
                }else{
                    String toastStr = "Invalid store ";
                    if(!checkIfStrValid(nameStr)){
                        toastStr = toastStr + "name ";
                        if (addrStr.equals("")){
                            toastStr = toastStr + "and address ";
                        }
                    }else if (addrStr.equals("")){
                        toastStr = toastStr + "address ";
                    }
                    Toast.makeText(AboutStore.this, toastStr, Toast.LENGTH_LONG).show();
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
}