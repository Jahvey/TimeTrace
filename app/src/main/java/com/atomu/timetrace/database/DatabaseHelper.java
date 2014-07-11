package com.atomu.timetrace.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/9.
 * database helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    final static private int VERSION = 1;
    private Context context;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        if (this.context == null) {
            this.context = context;
        }
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table_installed = context.getString(R.string.table_installed);
        String table_record = context.getString(R.string.table_record);
        String id = context.getString(R.string.state_id);
        String currentTime = context.getString(R.string.state_time);
        String appName = context.getString(R.string.process_info_app_name);
        String tag = context.getString(R.string.process_info_tag);
        String liveTime = context.getString(R.string.process_info_live_time);
        String packName = context.getString(R.string.process_info_pack_name);
        String startTime = context.getString(R.string.process_info_start_time);
        String active = context.getString(R.string.process_info_active);
        String alive = context.getString(R.string.process_info_alive);
        String isUser = context.getString(R.string.process_info_is_user_process);


        sqLiteDatabase.execSQL("CREATE TABLE  " + table_installed + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                appName + "VARCHAR[20], " + packName + "VARCHAR[20], " + tag + "INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE " + table_record + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                currentTime + "INTEGER");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
