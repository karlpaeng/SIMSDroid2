package com.simsdroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderReceipt extends AppCompatActivity{

    Button genImage;
    ScrollView scroll;

    DBHelper dbHalp = new DBHelper(OrderReceipt.this);

    ArrayList<ModelOrders> orders = new ArrayList<>();

    ModelOrderView orderDeets;

    Long id;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(OrderReceipt.this, R.color.white));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_receipt);

//        getWindow().setStatusBarColor(ContextCompat.getColor(OrderReceipt.this, R.color.darkblue));
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        getWindow().setStatusBarColor(ContextCompat.getColor(OrderReceipt.this, R.color.darkblue));


        scroll = findViewById(R.id.scrollOrder);
        genImage = findViewById(R.id.btnGenImgReceipt);

        id = getIntent().getLongExtra("order_num", 0);

//        storeName.setText(dbHalp.getStoreName());
//        storeAddress.setText(dbHalp.getStoreAddress());
        orderDeets = dbHalp.getSimpleOrderInfo(id);


        orders = dbHalp.getOrderInfo(id);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.scrollOrder, new FragReceipt());
        fragmentTransaction.commit();

        genImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int height = scroll.getChildAt(0).getHeight();
                int width = scroll.getChildAt(0).getWidth();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MMM-dd-hh:mm:ss-a");
                date = simpleDate.format(calendar.getTime());

//                clayout.setDrawingCacheEnabled(true);
//                clayout.buildDrawingCache();
//                Bitmap bitmap = clayout.getDrawingCache();

                Bitmap bitmap = Bitmap.createBitmap(width,height , Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Drawable bgDrawable = scroll.getBackground();
                if (bgDrawable != null) bgDrawable.draw(canvas);
                else canvas.drawColor(Color.WHITE);
                scroll.draw(canvas);

                //---
                try {
                    saveImageToStorage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });





    }
    public void saveImageToStorage(Bitmap bitmapObject) throws IOException {

        OutputStream imageOutStream;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "order"+id+"on"+date+".jpg");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            imageOutStream = getContentResolver().openOutputStream(uri);
        } else {
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File image = new File(imagePath, "order"+id+"on"+date+".jpg");
            imageOutStream = new FileOutputStream(image);
        }

        try {
            bitmapObject.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream);
        } finally {
            imageOutStream.close();
        }
        Toast.makeText(OrderReceipt.this, "Saved to Pictures", Toast.LENGTH_SHORT).show();
        onBackPressed();

    }
}