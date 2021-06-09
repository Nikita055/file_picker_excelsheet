package com.rajkamal.assettrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vaibhav on 22-jan-2020.

 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 3;
    Context context;
    // Database Name
    private static final String DATABASE_NAME = "FWDb";

    private static final String ITEM_ID = "ItemID";
    private static final String ITEM_CODE = "ItemCode";
    private static final String DESCRIPTION = "Description";
    private static final String CDate = "CDate";
    private static final String UDate = "UDate";
    private static final String IS_DELETED = "IsDeleted";
    private static final String IS_SCANNED = "IsScanned";









    // Table Names

    public static String ITEM_MASTER = "ItemMaster";
    public static String FINAL_TABLE = "FinalDetails";
    public static String TEST_TABLE = "TestData";
    public static String DISPATCH_TABLE = "DispatchData";
    public  static String RECEIVED_TABLE="ReceivedData";








    //Create Table Statements

    private static final String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS " + ITEM_MASTER + "("+
            ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +ITEM_CODE + " TEXT," +DESCRIPTION + " TEXT," +CDate + " TEXT,"+UDate+"TEXT ," +IS_SCANNED + " INTEGER ," +IS_DELETED + " TEXT);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating table
        db.execSQL(CREATE_TABLE1);

    }

    //new user
    public void AddItemDetails( String itemCode,String desc) throws SQLiteException {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String datetime = dateformat.format(c.getTime());

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_CODE,itemCode);
        cv.put(DESCRIPTION,desc);
        cv.put(CDate,datetime);
        cv.put(IS_SCANNED,0);

        database.insert(ITEM_MASTER, null, cv);
        System.out.println("DATA ADDED SUCCESSFULLY");

    }
    public ArrayList<String[]> getItemMasterData(){
        ArrayList<String[]> listData = new ArrayList();

        String QUERY = "SELECT * FROM  "+ITEM_MASTER ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery(QUERY, null);
        if (c != null && c.moveToFirst())
        {
            do {
                String a0=c.getString(c.getColumnIndex(ITEM_CODE));
                String a1=c.getString(c.getColumnIndex(DESCRIPTION));
                String a2=c.getString(c.getColumnIndex(CDate));
                int a3=c.getInt(c.getColumnIndex(IS_SCANNED));
                listData.add(new String[]{a0,a1,a2, String.valueOf(a3)});

            }while (c.moveToNext());
        }
        c.close();
        return listData ;
    }
    public ArrayList<String[]> getScannedData(){
        ArrayList<String[]> scannedData = new ArrayList();
        String QUERY = "SELECT * FROM  "+ITEM_MASTER + " WHERE IsScanned='1' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery(QUERY, null);
        if (c != null && c.moveToFirst())
        {
            do {
                String a0=c.getString(c.getColumnIndex(ITEM_CODE));
                String a1=c.getString(c.getColumnIndex(DESCRIPTION));
                String a2=c.getString(c.getColumnIndex(CDate));
                int a3=c.getInt(c.getColumnIndex(IS_SCANNED));
                scannedData.add(new String[]{a0,a1,a2, String.valueOf(a3)});

            }while (c.moveToNext());
        }
        c.close();
        return scannedData ;
    }

    public List<String> getSingleItemData(String  serial){
        List<String> singleData = new ArrayList<>();
        String QUERY = "SELECT * FROM  "+ITEM_MASTER + " WHERE ItemCode='"+serial+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery(QUERY, null);
        if (c != null && c.moveToFirst())
        {
            do {
                String a0=c.getString(c.getColumnIndex(ITEM_CODE));
                String a1=c.getString(c.getColumnIndex(DESCRIPTION));
                String a2=c.getString(c.getColumnIndex(CDate));
                int a3=c.getInt(c.getColumnIndex(IS_SCANNED));
                singleData.add(a0);
                singleData.add(a1);
                singleData.add(a2);


            }while (c.moveToNext());
        }
        c.close();
        return singleData ;
    }

    public ArrayList<String[]> getMissedData(){
        ArrayList<String[]> MissingData = new ArrayList();
        String QUERY = "SELECT * FROM  "+ITEM_MASTER + " WHERE IsScanned='0' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery(QUERY, null);
       if (c != null && c.moveToFirst())
        {
            do {
                String a0=c.getString(c.getColumnIndex(ITEM_CODE));
                String a1=c.getString(c.getColumnIndex(DESCRIPTION));
                String a2=c.getString(c.getColumnIndex(CDate));
                int a3=c.getInt(c.getColumnIndex(IS_SCANNED));
                MissingData.add(new String[]{a0,a1,a2, String.valueOf(a3)});

            }while (c.moveToNext());
        }
        c.close();
        return MissingData ;
    }

    public  boolean updateDetails(String itemcode) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        database.execSQL("UPDATE ItemMaster SET IsScanned=1 WHERE ItemCode='"+itemcode+"' ");
      return true;
    }


    public boolean checkSerialNoExists(String serial) {
        // String query = "Select * from "+TEST_TABLE+" where SerialNo like '"+serial+"'";
        String QUERY = "SELECT * FROM  "+ITEM_MASTER + " WHERE ItemCode='"+serial+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY,
                null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean checkItemScanned(String serial) {
        // String query = "Select * from "+TEST_TABLE+" where SerialNo like '"+serial+"'";
        String QUERY = "SELECT * FROM  "+ITEM_MASTER + " WHERE ItemCode='"+serial+"'AND IsScanned='1' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY,
                null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
