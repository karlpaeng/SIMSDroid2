package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DebtInfo extends AppCompatActivity {
    Button orderId, update;
    TextView date, total, paidDate;
    EditText name, contact;
    Switch paid;

    Long debtId, orderNumber;

    DBHelper dbHalp = new DBHelper(DebtInfo.this);

    ModelDebts modelDebts;
    ModelOrderView modelOrderView;
    String dateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_info);

        orderId.findViewById(R.id.btnViewOrderDebtInfo);
        update.findViewById(R.id.btnUpdateDebtInfo);
        date.findViewById(R.id.tvDateCheckedDebtInfo);
        total.findViewById(R.id.tvTotalDebtInfo);
        paidDate.findViewById(R.id.tvPaidDateDebtInfo);
        name.findViewById(R.id.etNameDebtInfo);
        contact.findViewById(R.id.etContactDebtInfo);
        paid.findViewById(R.id.swMarkPaidDebtInfo);

        debtId = getIntent().getLongExtra("debt_id", 0);

        modelDebts = dbHalp.getDebtInfo(debtId);

        date.setText(modelDebts.dateCheckout);
        modelOrderView = dbHalp.getSimpleOrderInfo(debtId);
        total.setText(modelOrderView.total+"");

        //paid.setClickable(false);

        if(modelDebts.customerName.equals("-")){
            name.setText("");
        }
        if(modelDebts.customerContact.equals("-")){
            contact.setText("");
        }

        if (modelDebts.datePaid.equals("-")){
            paidDate.setText("Not yet paid!");
            paid.setChecked(false);
        }else{
            paidDate.setText(modelDebts.datePaid);
            paid.setChecked(true);
            paid.setClickable(false);
        }

        orderId.setText(orderId.getText().toString() + debtId);

        orderId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebtInfo.this, OrderReceipt.class);
                intent.putExtra("order_num", debtId);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                if(contact.getText().toString().equals("") || checkIfStrValid(name.getText().toString())){
                    Toast.makeText(DebtInfo.this, "Don't leave anything blank!", Toast.LENGTH_LONG).show();
                }else{
                    dateStr = modelDebts.datePaid;
                    if(paid.isClickable() && paid.isChecked()){
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MMM-dd");
                        dateStr = simpleDate.format(calendar.getTime());
                    }
                }
                dbHalp.updateDebt(
                        debtId,
                        dateStr,
                        name.getText().toString(),
                        contact.getText().toString()
                        );
                Intent intent = new Intent(DebtInfo.this, MainActivity.class);
                intent.putExtra("frag", "debt");
                startActivity(intent);

            }
        });

    }
    private boolean checkIfStrValid(String str){
        if(str.equals("")) return false;
        Pattern ps = Pattern.compile("[a-zA-Z0-9\\s]+");
        Matcher ms = ps.matcher(str);
        boolean out = ms.matches();
        return out;
    }
}