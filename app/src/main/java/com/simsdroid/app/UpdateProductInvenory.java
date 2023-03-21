package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateProductInvenory extends AppCompatActivity {
    EditText name, cost, price, amt;
    Button cancel, update;
    long id;

    DBHelper dbhalp = new DBHelper(UpdateProductInvenory.this);

    Calendar calendar;
    SimpleDateFormat simpleDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_invenory);
        name = findViewById(R.id.etUpdateProdName);
        cost = findViewById(R.id.etUpdateCost);
        price = findViewById(R.id.etUpdatePrice);
        amt = findViewById(R.id.etUpdateAmtStock);

        cancel = findViewById(R.id.btnCancelUpdate);
        update = findViewById(R.id.btnUpdate);

        id = Long.parseLong(getIntent().getStringExtra("id_val"));
        name.setText(getIntent().getStringExtra("name_val"));
        cost.setText(getIntent().getStringExtra("cost_val"));
        price.setText(getIntent().getStringExtra("price_val"));
        amt.setText(getIntent().getStringExtra("amt_val"));;

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
                calendar = Calendar.getInstance();
                simpleDate = new SimpleDateFormat("yyyy-MMM-dd");
                String dateStr = simpleDate.format(calendar.getTime());
                ModelProducts product = new ModelProducts(1,
                        name.getText().toString(),
                        "",
                        Double.parseDouble(cost.getText().toString()),
                        Double.parseDouble(price.getText().toString()),
                        Integer.parseInt(amt.getText().toString()),
                        dateStr);
                dbhalp.updateProduct(id, product);

                Toast.makeText(UpdateProductInvenory.this, "Product has been updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UpdateProductInvenory.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("frag", "mnge");
                startActivity(intent);
            }
        });


    }
}