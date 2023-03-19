package com.simsdroid.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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
                "product_name TEXT, " +
                "product_id INTEGER, " +
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
        sqLiteDatabase.execSQL("CREATE TABLE order_numbers (" +
                "order_number INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, " +
                "time TEXT, " +
                "total TEXT)");
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

        db.close();
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
                "last_update = '" + product.lastUpdate + "' " +
                "WHERE prod_id = " + id + ";", null);
        db.close();
    }
    //products list, list search by name
    //inv value and retail value, profit

    public void createOrder(ArrayList<ModelOrders> orders, String xdate, String xtime){ //subtract from amount in stock, create records in orders table
        SQLiteDatabase db = this.getWritableDatabase();
        double total = 0;
        for (ModelOrders order : orders) {
            ContentValues cv = new ContentValues();

            cv.put("order_number", order.orderNumber);
            cv.put("product_name", order.productName);
            cv.put("product_id", order.productId);
            cv.put("retail_price", order.retailPrice);
            cv.put("amount", order.amount);
            cv.put("amountxprice", order.amountXprice);

            long i = db.insert("orders", null, cv);

            Cursor cursor = db.rawQuery("SELECT amount_stock FROM products WHERE prod_id = " + order.productId + ";", null);
            cursor.moveToFirst();
            int tempAmt = cursor.getInt(0);

            cursor.close();

            tempAmt = tempAmt - order.amount;

            if (tempAmt < 0) tempAmt = 0;

            db.execSQL("UPDATE products SET amount_stock = " + tempAmt + " WHERE prod_id = " + order.productId + ";");

            total += order.amountXprice;
        }
        //total
        db.execSQL("UPDATE order_numbers SET total = '" + total + "', date = '" + xdate + "', time = '" + xtime + "' WHERE order_number = " + orders.get(0).orderNumber + ";");
        db.close();
    }

    public long getOrderNumber(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("total", "");
        cv.put("date", "");
        cv.put("time", "");

        long i = db.insert("order_numbers", null, cv);

        Cursor cursor = db.rawQuery("SELECT order_number FROM order_numbers ORDER BY order_number DESC LIMIT 1;", null);
        cursor.moveToFirst();

        long returnLong = cursor.getLong(0);

        cursor.close();
        db.close();

        return returnLong;
    }

    public ArrayList<ModelOrders> getOrderInfo(long orderNumber){
        ArrayList<ModelOrders> ordersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE order_number = " + orderNumber + ";", null);
        if (cursor.moveToFirst()){
            do{
                int orderNum = cursor.getInt(1);
                String productName = cursor.getString(2);
                int productId = cursor.getInt(3);
                String retailPrice = cursor.getString(4);
                int amount = cursor.getInt(5);
                String amountXprice = cursor.getString(6);

                ModelOrders order = new ModelOrders(
                        orderNum,
                        productName,
                        productId,
                        Double.parseDouble(retailPrice),
                        amount,
                        Double.parseDouble(amountXprice)
                );

                ordersList.add(order);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ordersList;
    }

    public ModelOrderView getSimpleOrderInfo(long orderNum){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM order_numbers WHERE order_number = " + orderNum + ";", null);
        cursor.moveToFirst();

        String date = cursor.getString(1);
        String time = cursor.getString(2);
        double returnDoub = Double.parseDouble(cursor.getString(3));

        ModelOrderView orderView = new ModelOrderView(orderNum, date, time, returnDoub);
        cursor.close();
        db.close();

        return orderView;
    }
    public ArrayList<ModelOrderView> getOrderHistory(){
        ArrayList<ModelOrderView> orderViews = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM order_numbers;", null);
        if (cursor.moveToFirst()){
            do{
                int num = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                Double tot = Double.parseDouble(cursor.getString(3));

                ModelOrderView order = new ModelOrderView(num, date, time, tot);
                orderViews.add(order);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderViews;

    }
    public ArrayList<ModelOrderView> searchOrderNumByDate(String datestr){
        ArrayList<ModelOrderView> orderViews = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM order_numbers WHERE date = '" + datestr + "';", null);
        if (cursor.moveToFirst()){
            do{
                int num = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                Double tot = Double.parseDouble(cursor.getString(3));

                ModelOrderView order = new ModelOrderView(num, date, time, tot);
                orderViews.add(order);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderViews;
    }

}
