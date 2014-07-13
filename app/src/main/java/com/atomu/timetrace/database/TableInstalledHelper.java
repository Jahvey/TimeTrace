package com.atomu.timetrace.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/9.
 * database helper
 */
public class TableInstalledHelper extends SQLiteOpenHelper {

    final static private int VERSION = 1;
    private Context context;

    public TableInstalledHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public TableInstalledHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public TableInstalledHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table_installed = context.getString(R.string.table_installed);
        String id = context.getString(R.string.state_id);
        String appName = context.getString(R.string.process_info_app_name);
        String tag = context.getString(R.string.process_info_tag);
        String packName = context.getString(R.string.process_info_pack_name);
        String liveTime = context.getString(R.string.process_info_live_time);
        String startTime = context.getString(R.string.process_info_start_time);
        String activeTime = context.getString(R.string.process_info_active_time);
        String isUser = context.getString(R.string.process_info_is_user_process);

        sqLiteDatabase.execSQL("create table  " + table_installed + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                appName + " VARCHAR[20], " + packName + " VARCHAR[20], " + tag + " INTEGER, " +
                startTime + " INTEGER, " + liveTime + " INTEGER, " + activeTime + " INTEGER, " +
                isUser + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
