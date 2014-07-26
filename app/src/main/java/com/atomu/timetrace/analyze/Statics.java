package com.atomu.timetrace.analyze;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.atomu.timetrace.database.TableInstalledHelper;
import com.atomu.timetrace.database.TableRecordHelper;

import java.util.Calendar;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Statics {
	public static long Day=24*60*60*1000;
	public static long Week=7*24*60*60*1000;
	public static long Month=30*30*24*60*60*1000;
	public long[] activecount;
	public long[] alivecount;
    public long[] activity_alivecount;
	public long[][] activecountPerDay;
    public long[][] acitvityPerDay;
    public Context context;
	private long endtime;
	private long scale;
	private long starttime;
    private  long [] statistic;
    SQLiteDatabase installed;
    SQLiteDatabase record;

    //初始化，从millis时刻开始向前推进s区间进行统计
	public Statics(long millis, long s, Context cont){

		endtime=millis;
		scale=s;
		starttime=endtime-scale;
        context = cont;
        //打开数据库
        TableInstalledHelper tableInstalledHelper = new TableInstalledHelper(context,"record");
        installed = tableInstalledHelper.getReadableDatabase();
        TableRecordHelper tableRecordHelper = new TableRecordHelper(context,"record");
        record = tableRecordHelper.getReadableDatabase();

	}


	public void getData() throws SQLException{
		activecount= new long[18];   //tag活跃时间统计
		alivecount = new long[18];   //tag存活时间统计
        activity_alivecount = new long[11];    //活动统计
        activecountPerDay = new long[30][18];  //tag每天活跃时间
        acitvityPerDay = new long[30][11];     //活动每天活跃时间
        String column;
		long pretime;
		long addition;
		int appnumber=0;
        //分割天数
        long division;
        int pos;


        //根据起始结束时间读取record
        Cursor cursor_record = record.query("record",null,"time between ? and ?",new String[]{""+starttime,""+endtime},null,null,null);
        Cursor cursor_installed = installed.query("installed",new String[]{"id","tag"},null,null,null,null,null);
        Cursor cursor_identify = installed.query("activity_identify",null,"time between ? and ?",new String[]{""+starttime,""+endtime},null,null,null);
        appnumber=cursor_installed.getCount();

        //统计tag(细粒度)
        division=starttime+Day;
        pos=0;
		cursor_record.moveToNext();
		pretime=cursor_record.getLong(cursor_record.getColumnIndex("time"));
		while(cursor_record.moveToNext()){
			addition=cursor_record.getLong(cursor_record.getColumnIndex("time"))-pretime;
			pretime=cursor_record.getLong(cursor_record.getColumnIndex("time"));
            //记录每天tag活跃时间
            if (pretime>=division){
                for (int i=0;i<18;++i) {
                    activecountPerDay[pos][i] = activecount[i];
                }
                division+=Day;
                pos+=1;
            }
            //活跃时间统计activecount
			for (int i=1; i<=appnumber; i++){
				column="process_"+i+"_active";
				if (cursor_record.getInt(cursor_record.getColumnIndex(column))==1){
					cursor_installed.move(i);
					activecount[cursor_installed.getInt(cursor_installed.getColumnIndex("tag"))]+=addition;
				}
			}
            //存活时间统计alivecount
			for (int i=1; i<=appnumber; i++){
				column="process_"+i+"_alive";
                if (cursor_record.getInt(cursor_record.getColumnIndex(column))==1){
                    cursor_installed.move(i);
                    alivecount[cursor_installed.getInt(cursor_installed.getColumnIndex("tag"))]+=addition;
                }
            }
		}

        //统计activity(中粒度)
        division=starttime+Day;
        pos=0;
        int act_number=11;
        Cursor cursor_tempt = installed.query("activity_identify",null,"time<?",new String[]{""+starttime},null,null,null);
        cursor_tempt.moveToLast();
        //统计起始时间到第一条记录之前的活动
        addition=starttime-cursor_tempt.getLong(cursor_tempt.getColumnIndex("time"));
        for (int i=1; i<=act_number; i++){
            column="act_"+i;
            if (cursor_identify.getInt(cursor_identify.getColumnIndex(column))>0){
                activity_alivecount[i]+=addition;
            }
        }
        //统计从第一条记录到结束时间之间的活动
        cursor_identify.moveToNext();
        pretime=cursor_identify.getLong(cursor_identify.getColumnIndex("time"));
        while (cursor_identify.moveToNext()){
            addition=cursor_identify.getLong(cursor_identify.getColumnIndex("time"))-pretime;
            pretime=cursor_identify.getLong(cursor_identify.getColumnIndex("time"));
            //记录每天活动时间
            // 徐昊
            if (pretime>=division){
                for (int i=0;i<11;++i) {
                    activecountPerDay[pos][i] = activity_alivecount[i];
                }
                division+=Day;
                pos+=1;
            }
            for (int i=1; i<=act_number; i++){
                column="act_"+i;
                if (cursor_identify.getInt(cursor_identify.getColumnIndex(column))>0){
                    activity_alivecount[i]+=addition;
                }
            }
        }

	}
	

}
