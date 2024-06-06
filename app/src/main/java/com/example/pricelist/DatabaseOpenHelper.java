package com.example.pricelist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();
    final private static Integer VERSION = 1;
    final static String TABLE_NAME = "products";
    final private static String NAME = "products_db";
    final static String KEY_ID = "_id";
    final static String PRODUCT_NAME = "name";
    final static String[] columns = {KEY_ID, PRODUCT_NAME};
    final private static String CREATE_CMD =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    PRODUCT_NAME + " TEXT );";

    final private Context mContext;
    private SQLiteDatabase mReadableDB;

    public DatabaseOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CMD);
        fillDatabase(sqLiteDatabase);
    }

    public void fillDatabase(SQLiteDatabase sqLiteDatabase) {
        String[] words = {
                "product1 1€",
                "product2 2€",
                "product3 1€",
                "product4 2€",
                "product5 1€",
                "product6 2€",
                "product7 1€",
                "product8 2€",
                "product9 1€",
                "product10 2€",
                "product11 1€",
                "product12 2€",
                "product13 1€",
                "product14 2€",
                "product15 1€",
                "product16 2€",
                "product17 1€",
                "product18 2€",
                "product19 1€",
                "product20 2€",
                "product21 1€",
                "product22 2€",
        };

        ContentValues values = new ContentValues();

        for (String word : words) {
            values.put(PRODUCT_NAME, word);
            sqLiteDatabase.insert(TABLE_NAME, null, values);
        }
    }

    public Cursor search(String searchString) {
        String[] columns = new String[]{ KEY_ID, PRODUCT_NAME};
        String where =  PRODUCT_NAME + " LIKE ?";
        searchString = "%" + searchString + "%";
        String[] whereArgs = new String[]{searchString};

        Cursor cursor = null;
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.query(TABLE_NAME, columns, where, whereArgs, null, null, null);
        } catch (Exception e) {
            Log.d(TAG, "SEARCH EXCEPTION! " + e);
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // ToDo..
    }
}
