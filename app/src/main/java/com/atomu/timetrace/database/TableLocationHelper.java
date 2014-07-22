package com.atomu.timetrace.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Atomu on 2014/7/19.
 * this class helps to establish a database table that hold all the location information
 */
public class TableLocationHelper extends SQLiteOpenHelper {
    final static private int VERSION = 1;
    private Context context;

    final static public String DATABASE_NAME = "location";
    final static public String TABLE_NAME = "location";
    final static public String COL_ID = "id";
    final static public String COL_LONGITUDE = "longitude";
    final static public String COL_LATITUDE = "latitude";
    final static public String COL_SPEED = "speed";
    final static public String COL_TIME = "time";
    final static public String COL_ACCURACY = "accuracy";
    final static public String COL_ALTITUDE = "altitude";
    final static public String COL_DIRECT = "direct";
    final static public String COL_RADIUS = "radius";
    final static public String COL_TIME_STR = "timeStr";
    final static public String COL_STREET = "street";
    final static public String COL_CITY = "city";
    final static public String COL_ADDR = "addr";
    final static public String COL_COOR_TYPE = "coorType";
    final static public String COL_DISTRICT = "district";
    final static public String COL_LOC_TAG = "locTag";
    final static public String COL_ML_ACT_TAG = "mlActTag";
    final static public String COL_HL_ACT_TAG = "hlActTag";

    public TableLocationHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public TableLocationHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public TableLocationHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ").append(TABLE_NAME).append(" (")
                .append(COL_ID).append(" integer primary key autoincrement, ")
                .append(COL_TIME).append(" long, ")
                .append(COL_LONGITUDE).append(" double, ")
                .append(COL_LATITUDE).append(" double, ")
                .append(COL_ALTITUDE).append(" double, ")
                .append(COL_SPEED).append(" float, ")
                .append(COL_ACCURACY).append(" float, ")
                .append(COL_DIRECT).append(" float, ")
                .append(COL_RADIUS).append(" float, ")
                .append(COL_TIME_STR).append(" varchar[20], ")
                .append(COL_STREET).append(" varchar[20], ")
                .append(COL_CITY).append(" varchar[20], ")
                .append(COL_ADDR).append(" varchar[20], ")
                .append(COL_COOR_TYPE).append(" varchar[20], ")
                .append(COL_DISTRICT).append(" varchar[20], ")
                .append(COL_LOC_TAG).append(" int, ")
                .append(COL_ML_ACT_TAG).append(" int, ")
                .append(COL_HL_ACT_TAG).append(" int )");

        Log.i("db", sb.toString());
        sqLiteDatabase.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

}
