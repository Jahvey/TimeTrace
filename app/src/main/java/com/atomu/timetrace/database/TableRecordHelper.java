package com.atomu.timetrace.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/12.
 * this class is used to record all status
 */
public class TableRecordHelper extends SQLiteOpenHelper {

    final static private int VERSION = 1;
    private Context context;

    public TableRecordHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public TableRecordHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public TableRecordHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String table_record = context.getString(R.string.table_record);
//        String id = context.getString(R.string.state_id);
//        String time = context.getString(R.string.state_time);
//        String location = context.getString(R.string.state_location);
//        String longitude = context.getString(R.string.location_info_longitude);
//        String latitude = context.getString(R.string.location_info_latitude);
//        String speed = context.getString(R.string.location_info_speed);
//        String packName = context.getString(R.string.process_info_pack_name);
//        String active = context.getString(R.string.process_info_active);
//        String alive = context.getString(R.string.process_info_alive);

        StringBuilder sql = new StringBuilder();
        sql.append("create table  ").append(context.getString(R.string.table_record)).append(" ( ")
                .append(context.getString(R.string.state_id)).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(context.getString(R.string.state_time)).append(" INTEGER, ")
                .append(context.getString(R.string.state_location)).append(" INTEGER, ")
                .append(context.getString(R.string.location_info_longitude)).append(" FLOAT, ")
                .append(context.getString(R.string.location_info_latitude)).append(" FLOAT, ")
                .append(context.getString(R.string.location_info_speed)).append(" FLOAT, ");
        TableInstalledHelper installedHelper = new TableInstalledHelper(context, context.getString(R.string.database_installed));
        SQLiteDatabase rdb = installedHelper.getReadableDatabase();
        if (rdb != null){
            Cursor cursor = rdb.query(context.getString(R.string.table_installed), null, null, null, null, null, null);
            String active =context.getString(R.string.process_info_active);
            String alive = context.getString(R.string.process_info_alive);

            while (cursor.moveToNext()){
                String processId = "process_" + cursor.getInt(cursor.getColumnIndex(context.getString(R.string.state_id)));

                sql.append(processId).append("_").append(active).append(" INTEGER, ")
                        .append(processId).append("_").append(alive).append(" INTEGER, ");
//                        .append(processId).append("_").append(context.getString(R.string.process_info_pack_name)).append(" VARCHAR[20], ")
//                        .append(processId).append("_").append(context.getString(R.string.process_info_tag)).append(" INTEGER, ")

            }
            cursor.close();
            rdb.close();
        }
        sql.append(context.getString(R.string.state_activity)).append(" INTEGER)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
