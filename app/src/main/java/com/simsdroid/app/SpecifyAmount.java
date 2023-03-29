package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SpecifyAmount extends AppCompatActivity {
    Button done;
    TextView prodName;
    EditText amount;

    DBHelper dbHalp = new DBHelper(SpecifyAmount.this);
    ModelProducts product = new ModelProducts();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(SpecifyAmount.this, R.color.mintish));
        getWindow().setNavigationBarColor(ContextCompat.getColor(SpecifyAmount.this, R.color.mintish));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_amount);
        done = findViewById(R.id.btnSpecAmtDone);
        prodName = findViewById(R.id.tvSpecAmtProdName);
        amount = findViewById(R.id.etSpecAmtNumber);

        //conditional
        product = dbHalp.searchProductsById(Long.parseLong(getIntent().getStringExtra("prod_id")));

        prodName.setText(product.name);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecifyAmount.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("id_from_spec_amt", ""+product.id);
                intent.putExtra("amount_from_spec_amount", amount.getText().toString());
                intent.putExtra("frag", "pos");
                //Toast.makeText(SpecifyAmount.this, "null", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

    }
}