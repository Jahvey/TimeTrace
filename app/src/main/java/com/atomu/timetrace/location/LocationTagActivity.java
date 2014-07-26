package com.atomu.timetrace.location;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.atomu.timetrace.app.R;
import com.atomu.timetrace.database.TableLocationHelper;
import com.atomu.timetrace.monitor.ActivityInfoMeta;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocationTagActivity extends Activity {

    final private String COOR_TYPE = "bd09ll";

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private boolean isFirstLocation = true;

    private LocationClient locationClient = null;
    private List<LatLng> track = null;
    private List<Long> trackTime = null;
    private List<Float> trackSpeed = null;
    private List<Integer> trackId = null;
    private int[] trackActArr = null;
    private int[] trackLocArr = null;
    private int trackIndex = 0;

    private SeekBar sb_track = null;
    private Spinner sp_act = null;
    private Spinner sp_loc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tag);

        initClient();

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        sp_act = (Spinner) findViewById(R.id.sp_act_tag);
        sp_loc = (Spinner) findViewById(R.id.sp_loc_tag);

        sp_act.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showPos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showPos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getTrack();
        showTrack();

        sb_track = (SeekBar) findViewById(R.id.sb_track);
        sb_track.setMax(track.size() - 1);
        sb_track.setProgress(0);
        sb_track.setSecondaryProgress(0);
        sb_track.setOnSeekBarChangeListener(new TrackBarListener());

        trackActArr = new int[track.size()];
        trackLocArr = new int[track.size()];
        for (int i = 0; i < track.size(); ++i) {
            trackActArr[i] = 0;
            trackLocArr[i] = 0;
        }
        Button btn_back = (Button) findViewById(R.id.btn_pop_up_back);
        Button btn_motion = (Button) findViewById(R.id.btn_pop_up_motion);
        Button btn_next = (Button) findViewById(R.id.btn_pop_up_next);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackIndex = (trackIndex - 1 >= 0) ? trackIndex - 1 : 0;
                sb_track.setProgress(trackIndex);
                sb_track.setSecondaryProgress(trackIndex);
            }
        });
        btn_motion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loc = ((TextView) sp_loc.getSelectedView()).getText().toString();

                int l = new LocationInfoMeta().getKeyWithTag(LocationTagActivity.this, loc);
                int a = LocationTagActivity.this.getResources().getInteger(R.integer.hl_act_tag_motion);

                trackLocArr[trackIndex] = l;
                trackActArr[trackIndex] = a;
                trackIndex = (trackIndex + 1 <= sb_track.getMax()) ? trackIndex + 1 : sb_track.getMax();
                sb_track.setProgress(trackIndex);
                sb_track.setSecondaryProgress(trackIndex);
                Log.d("map", "loc: " + l + "\nact: " + a);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String act = ((TextView) sp_act.getSelectedView()).getText().toString();
                String loc = ((TextView) sp_loc.getSelectedView()).getText().toString();

                int l = new LocationInfoMeta().getKeyWithTag(LocationTagActivity.this, loc);
                int a = new ActivityInfoMeta().hlGetKeyFromTag(LocationTagActivity.this, act);

                trackLocArr[trackIndex] = l;
                trackActArr[trackIndex] = a;
                trackIndex = (trackIndex + 1 <= sb_track.getMax()) ? trackIndex + 1 : sb_track.getMax();
                sb_track.setProgress(trackIndex);
                sb_track.setSecondaryProgress(trackIndex);
            }
        });
    }

    private void initClient() {
        locationClient = new LocationClient(getApplicationContext());
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType(COOR_TYPE); // 设置坐标类型
        option.setProdName(getString(R.string.app_name));
//        option.setPriority(LocationClientOption.GpsFirst);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new ShowTrackListener());
    }

    private long getLastCheckTime() {
        SharedPreferences spf = getSharedPreferences("location_tag", MODE_PRIVATE);
        return spf.getLong("lastCheckTime", 0);
    }

    private void saveLastCheckTime(long time) {
        SharedPreferences spf = getSharedPreferences("location_tag", MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();

        editor.putLong("lastCheckTime", time);
        editor.commit();
    }

    private void getTrack() {
        track = new ArrayList<LatLng>();
        trackTime = new ArrayList<Long>();
        trackSpeed = new ArrayList<Float>();
        trackId = new ArrayList<Integer>();

        TableLocationHelper locationHelper = new TableLocationHelper(LocationTagActivity.this, TableLocationHelper.DATABASE_NAME);

        SQLiteDatabase rdb = locationHelper.getReadableDatabase();
        if (rdb != null) {
            String longitude = TableLocationHelper.COL_LONGITUDE;
            String latitude = TableLocationHelper.COL_LATITUDE;
            String timeStr = TableLocationHelper.COL_TIME;
            String speedStr = TableLocationHelper.COL_SPEED;
            String idStr = TableLocationHelper.COL_ID;

            String[] columns = new String[]{idStr, timeStr, longitude, latitude, speedStr};
            Cursor cursor;

            long lastCheckTime = getLastCheckTime();
            if (lastCheckTime == 0) {
                cursor = rdb.query(TableLocationHelper.TABLE_NAME, columns, null, null, null, null, null);
            } else {
                String selection = timeStr + " >= ?";
                String[] selectionArgs = new String[]{String.valueOf(lastCheckTime)};
                cursor = rdb.query(TableLocationHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            }

            // 过滤掉时间空间都相邻的点
            while (cursor.moveToNext()) {
                LatLng pos = new LatLng(cursor.getDouble(cursor.getColumnIndex(latitude)), cursor.getDouble(cursor.getColumnIndex(longitude)));
                float speed = cursor.getFloat(cursor.getColumnIndex(speedStr));
                long time = cursor.getLong(cursor.getColumnIndex(timeStr));
                int id = cursor.getInt(cursor.getColumnIndex(idStr));

                track.add(pos);
                trackSpeed.add((speed < 0.01) ? 0.00f : speed);
                trackTime.add(time);
                trackId.add(id);
            }
            cursor.close();
            rdb.close();
        }
    }

    private void showTrack() {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker2);

        for (LatLng pos : track) {
            //定义Maker坐标点
            Log.d("map", pos.longitude + "," + pos.latitude);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions markerOptions = new MarkerOptions()
                    .position(pos)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(markerOptions);
        }

        if (track.size() >= 2 && track.size() < 10000) {
            OverlayOptions polylineOptions = new PolylineOptions()
                    .points(track)
                            // 首两位代表透明度
                    .color(0xAA00bfff);
            mBaiduMap.addOverlay(polylineOptions);
        }
    }

    private void showPos() {
        if (isFirstLocation)
            return;

        MyLocationData locData = new MyLocationData.Builder()
                .latitude(track.get(trackIndex).latitude)
                .longitude(track.get(trackIndex).longitude)
                .build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        //创建InfoWindow展示的view
        View windowView = getLayoutInflater().inflate(R.layout.marker_pop_up, null);

        String act = ((TextView) sp_act.getSelectedView()).getText().toString();
        String loc = ((TextView) sp_loc.getSelectedView()).getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(trackTime.get(trackIndex));
        String timStr = String.valueOf(sdf.format(ca.getTime()));

        StringBuilder sb = new StringBuilder();
        sb.append("    <  ").append(act).append(" o(≧v≦)o~~  > 模式\n我在 <  ").append(loc)
                .append("╮(╯▽╰)╭  >\n时间 ").append(timStr).append("\n速度 ").append(trackSpeed.get(trackIndex));
        //setText("如何让我遇见你\n在我最美丽的时刻\n为这\n我已在佛前求了五百年\n求佛把我化作一棵树\n长在你必经的路边");

        if (!(trackSpeed.get(trackIndex) < 0.01))
            sb.append("\n猜你在 移动 ? (请戳 \"motion\")");

        ((TextView) windowView.findViewById(R.id.tv_pop_up_title)).setText(sb.toString());

        InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                Log.d("map", "info window listener is active");
            }
        };

        //创建InfoWindow
        InfoWindow mInfoWindow = new InfoWindow(windowView, track.get(trackIndex), listener);

        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(track.get(trackIndex));
        mBaiduMap.animateMapStatus(u);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_tag, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.mi_normal_map:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mi_satellite_map:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mi_following:
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(track.get(trackIndex));
                mBaiduMap.animateMapStatus(u);
//                LatLng pos = new LatLng(locationInfoProvider.getLocationInfo().getLatitude(), locationInfoProvider.getLocationInfo().getLongitude());
//                showPos(pos);
                break;
            case R.id.mi_save_track:
                TableLocationHelper locationHelper = new TableLocationHelper(LocationTagActivity.this, TableLocationHelper.DATABASE_NAME);
                SQLiteDatabase wdb = locationHelper.getWritableDatabase();

                if (wdb != null) {
                    int length = track.size();
                    String whereClause = TableLocationHelper.COL_ID + " = ?";

                    for (int i = 0; i < length; ++i) {
                        String[] whereArgs = new String[]{String.valueOf(trackId.get(i))};

                        ContentValues values = new ContentValues();
                        values.put(TableLocationHelper.COL_LOC_TAG, trackLocArr[i]);
                        values.put(TableLocationHelper.COL_HL_ACT_TAG, trackActArr[i]);
                        wdb.update(TableLocationHelper.TABLE_NAME, values, whereClause, whereArgs);
                    }

                    wdb.close();
                    saveLastCheckTime(trackTime.get(trackTime.size() - 1));
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted()) {
            locationClient.start();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    public class ShowTrackListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            Log.d("map", "track listener is awake");
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;

            MyLocationData locData = null;
            // 第一次定位时，将地图位置移动到当前位置
            if (isFirstLocation) {
                // 构造定位数据
                locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();

                isFirstLocation = false;
            }

            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);

            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);

            // 设置自定义图标
//            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker1);
//            MyLocationConfigeration config = new MyLocationConfigeration(mCurrentMode, true, BaiduMap.);
//            mBaiduMap.setMyLocationConfigeration(config);

        }

//        @Override
//        public void onReceivePoi(BDLocation bdLocation) {
//
//        }

    }

    private class TrackBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            seekBar.setSecondaryProgress(i);
            trackIndex = i;
            showPos();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
