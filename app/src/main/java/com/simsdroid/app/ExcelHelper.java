package com.simsdroid.app;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ExcelHelper {
    public ExcelHelper(){}

    public String saveToFile(XSSFWorkbook xwb, String fileName, Context context)throws IOException {
        OutputStream fileOutputStream;
        //File filePath;
        String retStr;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ContentValues values = new ContentValues();

            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);       //file name
            values.put(MediaStore.MediaColumns.MIME_TYPE, "document/xlsx");        //file extension, will automatically add to file
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/SariSari POS/");     //end "/" is not mandatory

            Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);      //important!

            fileOutputStream = context.getContentResolver().openOutputStream(uri);
            retStr = Environment.DIRECTORY_DOCUMENTS + "/" + fileName;

        }else{
            String docsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/SariSari POS/";
            File file = new File(docsDir, fileName);
            fileOutputStream = new FileOutputStream(file);

//            filePath = new File(getContext().getExternalFilesDir(null) + "/" + fileName);;
//            if(filePath.exists()) filePath.createNewFile();
//            else filePath = new File(getContext().getExternalFilesDir(null) + "/" + fileName);
//            fileOutputStream = new FileOutputStream(filePath);
            retStr = docsDir + "/" + fileName;

        }
        try {
            xwb.write(fileOutputStream);
            fileOutputStream.flush();

        } catch (Exception e) {

            e.printStackTrace();

        }finally {
            fileOutputStream.close();
        }
        return retStr;
    }

    public XSSFWorkbook saveToInvWorkbook(ArrayList<ModelProducts> prodList){
        XSSFWorkbook xwb = new XSSFWorkbook();
        XSSFSheet xsheet = xwb.createSheet("SariSari Products Info");
        xsheet.setColumnWidth(0, 20 * 256);
        xsheet.setColumnWidth(1, 15 * 256);
        xsheet.setColumnWidth(2, 10 * 256);
        xsheet.setColumnWidth(3, 10 * 256);
        xsheet.setColumnWidth(4, 10 * 256);
        xsheet.setColumnWidth(5, 15 * 256);
        int listSize = prodList.size();
        for (int q = 0 ; q < listSize ; q++){
            XSSFRow xRow = xsheet.createRow(q);

            XSSFCell xCell = xRow.createCell(0);
            xCell.setCellValue(prodList.get(q).name);

            xCell = xRow.createCell(1);
            xCell.setCellValue(prodList.get(q).barcode);

            xCell = xRow.createCell(2);
            xCell.setCellValue(String.valueOf(prodList.get(q).cost)+"");

            xCell = xRow.createCell(3);
            xCell.setCellValue(String.valueOf(prodList.get(q).retailPrice)+"");

            xCell = xRow.createCell(4);
            xCell.setCellValue(prodList.get(q).amountStock + "");

            xCell = xRow.createCell(5);
            xCell.setCellValue(prodList.get(q).lastUpdate);

        }
        return xwb;
    }
    public XSSFWorkbook saveToDebtsWorkbook(ArrayList<ModelDebts> debtList){
        XSSFWorkbook xwb = new XSSFWorkbook();
        XSSFSheet xsheet = xwb.createSheet("Customer Debt");
        xsheet.setColumnWidth(0, 15 * 256);
        xsheet.setColumnWidth(1, 20 * 256);
        xsheet.setColumnWidth(2, 15 * 256);
        xsheet.setColumnWidth(3, 15 * 256);
        xsheet.setColumnWidth(4, 15 * 256);
        int listSize = debtList.size();

        XSSFRow xRow = xsheet.createRow(0);
        short height = 500;
        xRow.setHeight(height);

        XSSFCell xCell = xRow.createCell(0);
        xCell.setCellValue("Order \nNumber");
        xCell = xRow.createCell(1);
        xCell.setCellValue("Customer \nName");
        xCell = xRow.createCell(2);
        xCell.setCellValue("Contact");
        xCell = xRow.createCell(3);
        xCell.setCellValue("Date \nChecked out");
        xCell = xRow.createCell(4);
        xCell.setCellValue("Date paid");
        for (int q = 1 ; q <= listSize ; q++){
            xRow = xsheet.createRow(q);

            xCell = xRow.createCell(0);
            xCell.setCellValue(debtList.get(q-1).orderNumber + "");

            xCell = xRow.createCell(1);
            xCell.setCellValue(debtList.get(q-1).customerName);

            xCell = xRow.createCell(2);
            xCell.setCellValue(debtList.get(q-1).customerContact);

            xCell = xRow.createCell(3);
            xCell.setCellValue(debtList.get(q-1).dateCheckout);

            xCell = xRow.createCell(4);
            xCell.setCellValue(debtList.get(q-1).datePaid);

        }
        return xwb;
    }
    public XSSFWorkbook saveToOrdersWorkbook(ArrayList<ModelOrders> orders){
        XSSFWorkbook xwb = new XSSFWorkbook();
        XSSFSheet xsheet = xwb.createSheet("SariSari all Orders");
        xsheet.setColumnWidth(0, 15 * 256);
        xsheet.setColumnWidth(1, 20 * 256);
        xsheet.setColumnWidth(2, 15 * 256);
        xsheet.setColumnWidth(3, 10 * 256);
        xsheet.setColumnWidth(4, 10 * 256);
        int listSize = orders.size();

        XSSFRow xRow = xsheet.createRow(0);
        short height = 500;
        xRow.setHeight(height);

        XSSFCell xCell = xRow.createCell(0);
        xCell.setCellValue("Order \nNumber");
        xCell = xRow.createCell(1);
        xCell.setCellValue("Product \nName");
        xCell = xRow.createCell(2);
        xCell.setCellValue("Retail Price \nper item");
        xCell = xRow.createCell(3);
        xCell.setCellValue("Amount");
        xCell = xRow.createCell(4);
        xCell.setCellValue("Amount \nx Price");
        for (int q = 1 ; q < listSize ; q++){
            xRow = xsheet.createRow(q);

            xCell = xRow.createCell(0);
            xCell.setCellValue(orders.get(q).orderNumber+"");

            xCell = xRow.createCell(1);
            xCell.setCellValue(orders.get(q).productName);

            xCell = xRow.createCell(2);
            xCell.setCellValue(String.valueOf(orders.get(q).retailPrice+""));


            xCell = xRow.createCell(3);
            xCell.setCellValue(orders.get(q).amount + "");

            xCell = xRow.createCell(4);
            xCell.setCellValue(String.valueOf(orders.get(q).amountXprice)+"");

        }
        return xwb;
    }
    public void insertToDB(Context context, Uri uri) throws Exception {
        DBHelper dbHalp = new DBHelper(context);

        InputStream inStream;
        inStream = context.getContentResolver().openInputStream(uri);

        Workbook wb = new XSSFWorkbook(inStream);
        Sheet sh = wb.getSheetAt(0);
        int rowNum = sh.getLastRowNum();

        dbHalp.clearAllProducts();
        for (int q = 1 ; q <= rowNum ; q++){
            ModelProducts product = new ModelProducts(
                    1,
                    sh.getRow(q).getCell(0).toString(),
                    sh.getRow(q).getCell(1).toString(),
                    new BigDecimal(sh.getRow(q).getCell(2).toString()),
                    new BigDecimal(sh.getRow(q).getCell(3).toString()),
                    Integer.parseInt(sh.getRow(q).getCell(4).toString()),
                    sh.getRow(q).getCell(5).toString()
            );

            long lInput = dbHalp.addProduct(product);

        }
        Toast.makeText(context, "File imported with " + rowNum + " rows of data", Toast.LENGTH_SHORT).show();
    }
}
