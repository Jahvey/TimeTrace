package com.atomu.timetrace.location;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.atomu.timetrace.app.R;
import com.atomu.timetrace.database.TableLocationHelper;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.Calendar;

/**
 * Created by Atomu on 2014/7/19.
 * this class provides
 */
public class LocationInfoProvider {
    final private String COOR_TYPE = "bd09ll";
    private LocationClient mLocationClient;
    private LocationInfo locationInfo = null;

    public LocationInfoProvider(Context context, int scanSpan) {

        // 定位初始化
        mLocationClient = new LocationClient(context);

        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType(COOR_TYPE); // 设置坐标类型
        option.setProdName(context.getString(R.string.app_name));
        option.setScanSpan(scanSpan);
//        option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);

        locationInfo = new LocationInfo();

        TableLocationHelper locationHelper = new TableLocationHelper(context, TableLocationHelper.DATABASE_NAME);
        SQLiteDatabase rdb = locationHelper.getReadableDatabase();
        String latitude = TableLocationHelper.COL_LATITUDE;
        String longitude = TableLocationHelper.COL_LONGITUDE;
        String timeStr = TableLocationHelper.COL_TIME_STR;
        String time = TableLocationHelper.COL_TIME;
        String[] columns = new String[]{latitude, longitude, time, timeStr};
        Cursor cursor;
        if (rdb != null) {
            cursor = rdb.query(TableLocationHelper.TABLE_NAME, columns, null, null, null, null, null);
            if (cursor.moveToLast()) {
                locationInfo.setLongitude(cursor.getDouble(cursor.getColumnIndex(longitude)));
                locationInfo.setLatitude(cursor.getDouble(cursor.getColumnIndex(latitude)));
                locationInfo.setTime(cursor.getLong(cursor.getColumnIndex(time)));
                locationInfo.setTimeStr(cursor.getString(cursor.getColumnIndex(timeStr)));
            } else {
                locationInfo = null;
            }
        }

    }

    public void registerLocationListener(BDLocationListener listener) {
        mLocationClient.registerLocationListener(listener);
    }

    public void updateLocation(BDLocation location) {
        long time = Calendar.getInstance().getTimeInMillis();

        if (locationInfo != null) {
            LatLng lastPos = new LatLng(locationInfo.getLatitude(), locationInfo.getLongitude());
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            long diff = time - locationInfo.getTime();

            locationInfo.setSpeed((float) DistanceUtil.getDistance(lastPos, pos) * 1000.0f / diff);
            Log.d("map", "time diff: " + diff + "\nspeed: " + DistanceUtil.getDistance(lastPos, pos));
        } else {
            locationInfo = new LocationInfo();
            locationInfo.setSpeed(0);
        }
        locationInfo.setTime(time);

//        Log.d("map", "last: (" + locationInfo.getLatitude() + ", " + locationInfo.getLongitude() + ")\nnow:(" + location.getLatitude() + ", " + location.getLongitude()+")");

        locationInfo.setAccuracy(location.getRadius());
        locationInfo.setLatitude(location.getLatitude());
        locationInfo.setLongitude(location.getLongitude());
        locationInfo.setTimeStr(location.getTime());
        locationInfo.setStreet(location.getStreet());
        locationInfo.setCity(location.getCity());
        locationInfo.setAddr(location.getAddrStr());
        locationInfo.setAltitude(location.getAltitude());
        locationInfo.setCoorType(location.getCoorType());
        locationInfo.setDirect(location.getDirection());
        locationInfo.setDistrict(location.getDistrict());
        locationInfo.setRadius(location.getRadius());
        locationInfo.setProvince(location.getProvince());
    }

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public void onStart() {
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

//    public void onStop(){
//        mLocationClient.stop();
//    }

}
