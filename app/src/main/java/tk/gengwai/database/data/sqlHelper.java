package tk.gengwai.database.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tk.gengwai.database.data.UserContract;

/**
 * Created by danielchan303 on 22/7/2016.
 */
public class sqlHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "testing.db";

    public sqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_CUSTOMER_TABLE =
            "CREATE TABLE " + UserContract.CustomerEntry.TABLE_NAME +
             "(" +
                    UserContract.CustomerEntry._ID + " INTEGER PRIMARY KEY," +
                    UserContract.CustomerEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE," +
                    UserContract.CustomerEntry.COLUMN_PHONE_NO + " NUMERIC NOT NULL UNIQUE" +
             ")";

    private static final String SQL_CREATE_ORDER_TABLE =
            "CREATE TABLE " + UserContract.OrderEntry.TABLE_NAME +
            "(" +
                    UserContract.OrderEntry._ID + " INTEGER PRIMARY KEY," +
                    UserContract.OrderEntry.CUSTOMER_ID + " INTEGER NOT NULL, " +
                    UserContract.OrderEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                    UserContract.OrderEntry.COLUMN_QUANTITY + " INTEGER NOT NULL," +
                    "CONSTRAINT CUSTOMER_ID_LINK FOREIGN KEY " +
                    "(" + UserContract.OrderEntry.CUSTOMER_ID + ")" + " REFERENCES " +
                    UserContract.CustomerEntry.TABLE_NAME + "(" + UserContract.CustomerEntry._ID + ")" +
           ")";

    public static final String SQL_DELETE_CUSTOMER_TABLE =
            "DROP TABLE  " + UserContract.CustomerEntry.TABLE_NAME;

    public static final String SQL_DELETE_ORDER_TABLE =
            "DROP TABLE  " + UserContract.OrderEntry.TABLE_NAME;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CUSTOMER_TABLE);
        db.execSQL(SQL_CREATE_ORDER_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CUSTOMER_TABLE);
        db.execSQL(SQL_DELETE_ORDER_TABLE);
        onCreate(db);
    }
}
