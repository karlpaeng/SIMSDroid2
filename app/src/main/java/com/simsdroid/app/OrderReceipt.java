package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderReceipt extends AppCompatActivity{
    TextView storeName, storeAddress, dateTime, ordId, total;
    RecyclerView recviewReceipt;
    Button genImage;
    ConstraintLayout clayout;

    DBHelper dbHalp = new DBHelper(OrderReceipt.this);

    ArrayList<ModelOrders> orders = new ArrayList<>();

    ModelOrderView orderDeets;

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_receipt);
        storeName = findViewById(R.id.tvStoreNameReceipt);
        storeAddress = findViewById(R.id.tvStoreAddrReceipt);
        dateTime = findViewById(R.id.tvDateTimeReceipt);
        ordId = findViewById(R.id.tvOrderIdReceipt);
        total = findViewById(R.id.tvTotalPayReceipt);
        recviewReceipt = findViewById(R.id.rec_view_receipt);
        genImage = findViewById(R.id.btnGenImgReceipt);
        clayout = findViewById(R.id.receiptLayout);

        id = getIntent().getLongExtra("order_num", 0);

//        storeName.setText(dbHalp.getStoreName());
//        storeAddress.setText(dbHalp.getStoreAddress());
        orderDeets = dbHalp.getSimpleOrderInfo(id);
        dateTime.setText(orderDeets.date + ", " + orderDeets.time);
        ordId.setText(orderDeets.orderNumber+"");
        total.setText(String.valueOf(orderDeets.total));

        orders = dbHalp.getOrderInfo(id);

        RecAdaptReceipt adapter = new RecAdaptReceipt(orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderReceipt.this);
        recviewReceipt.setLayoutManager(layoutManager);
        recviewReceipt.setItemAnimator(new DefaultItemAnimator());
        recviewReceipt.setAdapter(adapter);

    }
}