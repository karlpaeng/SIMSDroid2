package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PIN extends AppCompatActivity {
    TextView topText, dot1, dot2, dot3, dot4, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, clear;

    String pinStr, pinStr2nd, actTag;

    String name, addr, other;
    //act tag: setup, login, reset
    int numCtr;
    boolean phase2nd;

    DBHelper dbHalp = new DBHelper(PIN.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(PIN.this, R.color.white));
        getWindow().setNavigationBarColor(ContextCompat.getColor(PIN.this, R.color.white));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        topText = findViewById(R.id.tvPINact);
        dot1 = findViewById(R.id.tvDot1);
        dot2 = findViewById(R.id.tvDot2);
        dot3 = findViewById(R.id.tvDot3);
        dot4 = findViewById(R.id.tvDot4);

        btn1 = findViewById(R.id.tvBtn1);
        btn2 = findViewById(R.id.tvBtn2);
        btn3 = findViewById(R.id.tvBtn3);
        btn4 = findViewById(R.id.tvBtn4);
        btn5 = findViewById(R.id.tvBtn5);
        btn6 = findViewById(R.id.tvBtn6);
        btn7 = findViewById(R.id.tvBtn7);
        btn8 = findViewById(R.id.tvBtn8);
        btn9 = findViewById(R.id.tvBtn9);
        btn0 = findViewById(R.id.tvBtn0);
        clear = findViewById(R.id.tvbtnClr);

        actTag = getIntent().getStringExtra("pin_act_tag");

        if(actTag.equals("setup")) {
            name = getIntent().getStringExtra("name");
            addr = getIntent().getStringExtra("addr");
            other = getIntent().getStringExtra("other");
        }
        //
        //actTag = "setup";
        //actTag = "login";
        //actTag = "setup";
        if(actTag.equals("setup")){
            topText.setText("Next, let's set up your PIN");
        }else if (actTag.equals("login")){
            topText.setText("Enter your PIN to log in");
        }else if (actTag.equals("reset")){
            topText.setText("Enter a new PIN");
        }

        pinStr = "";
        pinStr2nd = "";
        numCtr = 0;
        phase2nd = false;

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "1";
                else pinStr = pinStr + "1";
                pressBtn();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "2";
                else pinStr = pinStr + "2";
                pressBtn();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "3";
                else pinStr = pinStr + "3";
                pressBtn();

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "4";
                else pinStr = pinStr + "4";
                pressBtn();

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "5";
                else pinStr = pinStr + "5";
                pressBtn();

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "6";
                else pinStr = pinStr + "6";
                pressBtn();

            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "7";
                else pinStr = pinStr + "7";
                pressBtn();

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "8";
                else pinStr = pinStr + "8";
                pressBtn();

            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "9";
                else pinStr = pinStr + "9";
                pressBtn();

            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phase2nd) pinStr2nd = pinStr2nd + "0";
                else pinStr = pinStr + "0";
                pressBtn();

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numCtr > 0){
                    if(phase2nd){
                        StringBuilder sb = new StringBuilder(pinStr2nd);
                        sb.deleteCharAt(numCtr-1);
                        pinStr2nd = sb.toString();
                        //pinStr2nd = pinStr2nd.substring(0, numCtr);
                    }else {
                        StringBuilder sb = new StringBuilder(pinStr);
                        sb.deleteCharAt(numCtr-1);
                        pinStr = sb.toString();
                        //pinStr = pinStr.substring(0,numCtr);
                    }
                }
                switch (numCtr){
                    case 3:
                        numCtr--;
                        dot3.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        break;
                    case 2:
                        numCtr--;
                        dot2.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        break;
                    case 1:
                        numCtr--;
                        dot1.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        break;
                }


            }
        });

    }
    private void pressBtn(){
        numCtr++;
        switch (numCtr){
            case 1:
                dot1.setTextColor(ContextCompat.getColor(PIN.this, R.color.darkblue));
                break;
            case 2:
                dot2.setTextColor(ContextCompat.getColor(PIN.this, R.color.darkblue));
                break;
            case 3:
                dot3.setTextColor(ContextCompat.getColor(PIN.this, R.color.darkblue));
                break;
            case 4:
                //
                dot4.setTextColor(ContextCompat.getColor(PIN.this, R.color.darkblue));
                //
                if (actTag.equals("login")){
                    if (pinStr.equals(dbHalp.getPIN())) {//dbHalp.getPIN()
                        Intent intent = new Intent(PIN.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(PIN.this, "login success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PIN.this, "PIN incorrect, try again", Toast.LENGTH_SHORT).show();
                        dot1.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot2.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot3.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot4.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        topText.setText("PIN is incorrect, try again");
                        pinStr = "";
                        numCtr = 0;
                    }
                }else if (actTag.equals("setup")){
                    if (phase2nd){
                        if (pinStr.equals(pinStr2nd)){
                            //set on the db
                            dbHalp.createUserInfo(name, pinStr);
                            //set on file
                            WriteToFile("simsdroid-file-01", addr);
                            WriteToFile("simsdroid-file-02", other);
                            //toast
                            Toast.makeText(PIN.this, "PIN is set up", Toast.LENGTH_SHORT).show();
                            //
                            Intent intent = new Intent(PIN.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //goto next act

                        }else{
                            Toast.makeText(PIN.this, "PIN needs to match during reenter, try again", Toast.LENGTH_SHORT).show();
                            phase2nd = false;
                            dot1.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                            dot2.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                            dot3.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                            dot4.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                            topText.setText("PIN does not match, try again");
                            pinStr = "";
                            pinStr2nd = "";
                            numCtr = 0;
                        }

                    }else {
                        topText.setText("Re-enter your PIN");
                        dot1.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot2.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot3.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot4.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        phase2nd = true;
                        numCtr = 0;
                    }
                }else if (actTag.equals("reset")){
                    if (phase2nd){
                        if (pinStr.equals(pinStr2nd)){
                            //set on the db
                            dbHalp.updatePIN(pinStr);
                            Toast.makeText(PIN.this, "New PIN is set up", Toast.LENGTH_SHORT).show();
                            onBackPressed();

                        }else{
                            //Toast.makeText(PIN.this, "wrong"+pinStr+":"+pinStr2nd, Toast.LENGTH_SHORT).show();
                            Toast.makeText(PIN.this, "PIN needs to match during re-enter", Toast.LENGTH_SHORT).show();
                            phase2nd = false;
                            dot1.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                            dot2.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                            dot3.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                            dot4.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                            topText.setText("PIN does not match, try again");
                            pinStr = "";
                            pinStr2nd = "";
                            numCtr = 0;
                        }
                    }else{
                        topText.setText("Re-enter your new PIN");
                        dot1.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot2.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot3.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        dot4.setTextColor(ContextCompat.getColor(PIN.this, R.color.mintish));
                        phase2nd = true;
                        pinStr2nd = "";
                        numCtr = 0;
                    }
                }
                break;
        }


    }
    public void WriteToFile(String fileName, String content){
        File filePath = new File(PIN.this.getExternalFilesDir(null) + "/" + fileName);
        try{
            if(filePath.exists()) filePath.createNewFile();
            else filePath = new File(PIN.this.getExternalFilesDir(null) + "/" + fileName);

            FileOutputStream writer = new FileOutputStream(filePath);
            writer.write(content.getBytes());
            writer.flush();
            writer.close();
            //Log.e("TAG", "Wrote to file: "+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}