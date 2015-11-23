package com.jackbbb95.globe.countr.Handlers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "CountrsDB";
    private static final String COUNTR_TABLE = "Countrs";
    private static final String COUNTR_COLUMN = "CountrsCoulumn";
    private static final String DATABASE_CREATE = " CREATE TABLE " + COUNTR_TABLE +
            "(" + COUNTR_COLUMN + " TEXT NOT NULL)";
    private Cursor cs;


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COUNTR_TABLE);
        onCreate(db);
    }

    public boolean insertCountr(String arrayList, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COUNTR_COLUMN, arrayList);
        try{
            //db = this.getWritableDatabase();
            db.insert(COUNTR_TABLE,null,values);
            db.close();
        }catch(Exception e) {
            Log.d("InsertArray", e.getMessage());
        }
        return true;
    }

    public String getCountr(SQLiteDatabase db){
        String returnString = "";
        try {
            cs = db.rawQuery("SELECT * FROM " + COUNTR_TABLE, null);
            cs.moveToFirst();
            returnString = cs.getString(cs.getColumnIndex(COUNTR_COLUMN));
            cs.close();
            return  returnString;
        }catch(Exception e){
            Log.d("ReadArray",e.getMessage());
        }

        return returnString;
        }


}
