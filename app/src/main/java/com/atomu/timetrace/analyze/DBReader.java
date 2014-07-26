package com.atomu.timetrace.analyze;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.atomu.timetrace.database.TableInstalledHelper;


public class DBReader {

	Context context;
	TableInstalledHelper tableInstalledHelper = new TableInstalledHelper(context,"record");
	SQLiteDatabase db = tableInstalledHelper.getReadableDatabase();
    long [] statistic;
    

}