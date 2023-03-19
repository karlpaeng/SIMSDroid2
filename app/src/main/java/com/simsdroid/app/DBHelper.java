package com.simsdroid.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "SariSari.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE products (" +
                "prod_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "prod_name TEXT, " +
                "barcode TEXT, " +
                "cost TEXT, " +
                "retail_price TEXT, " +
                "amount_stock INTEGER, " +
                "last_update TEXT)"
        );
        sqLiteDatabase.execSQL("CREATE TABLE orders (" +
                "order_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "order_number INTEGER, " +
                "date TEXT, " +
                "time TEXT, " +
                "product_name TEXT, " +
                "retail_price TEXT, " +
                "amount INTEGER, " +
                "amountxprice TEXT)"
        );
        sqLiteDatabase.execSQL("CREATE TABLE debts (" +
                "debt_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "order_number INTEGER, " +
                "cust_name TEXT, " +
                "cust_contact TEXT, " +
                "date_checkout TEXT, " +
                "date_paid TEXT)"
        );
        sqLiteDatabase.execSQL("CREATE TABLE user_info (" +
                "id INTEGER PRIMARY KEY, " +
                "store_name TEXT, " +
                "pin INTEGER)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
