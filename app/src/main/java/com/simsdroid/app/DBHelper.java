package com.simsdroid.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
//-------------------------------     USER INFO TABLE
    public void createUserInfo(String storeName, int PIN){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", 1);
        cv.put("store_name", storeName);
        cv.put("pin", PIN);

        long i = db.insert("user_info", null, cv);

        db.close();
    }

    public boolean checkExistingUserInfo(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM user_info WHERE id = 1;", null);
        boolean b = cursor.moveToFirst();
        cursor.close();
        db.close();
        return b;
    }

    public void updateStoreName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE user_info SET store_name = '" + name + "' WHERE id = 1;", null);
        db.close();
    }
    public void updatePIN(int pin){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE user_info SET pin = " + pin + " WHERE id = 1;", null);
        db.close();
    }
    //--------------------------------     PRODUCTS TABLE

    public void addProduct(ModelProducts product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("prod_name", product.name);
        cv.put("barcode", product.barcode);
        cv.put("cost", product.cost);
        cv.put("retail_price", product.retailPrice);
        cv.put("amount", product.amountStock);
        cv.put("last_update", product.lastUpdate);

        long i = db.insert("products", null, cv);
    }
    public void updateProduct(long id, ModelProducts product){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE products " +
                "SET " +
                "prod_name = '" + product.name + "', " +
                "barcode = '" + product.barcode + "', " +
                "cost = '" + product.cost + "', " +
                "retail_price = '" + product.retailPrice + "', "+
                "amount = " + product.amountStock + ", " +
                "last_update = '" + product.lastUpdate + "'" +
                "WHERE prod_id = " + id + ";", null);
        db.close();
    }

}
