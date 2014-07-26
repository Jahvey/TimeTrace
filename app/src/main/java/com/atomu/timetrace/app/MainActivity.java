package com.atomu.timetrace.app;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.atomu.timetrace.database.TableLocationHelper;
import com.atomu.timetrace.location.LocationInfo;
import com.atomu.timetrace.location.LocationInfoProvider;
import com.atomu.timetrace.monitor.Monitor;
import com.atomu.timetrace.preference.SettingManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.Calendar;

public class MainActivity extends Activity {
    final private int distError = 30;
    final private int timeError = 30 * 60 * 1000;
    final private int scanSpan = 5000;
    private RelativeLayout rl_monitor;
    private RelativeLayout rl_analyze;
    private RelativeLayout rl_setting;
    private Monitor monitor;
    private SettingManager settingManager;
    private LocationInfoProvider locationInfoProvider;
    private TableLocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        rl_monitor = (RelativeLayout) findViewById(R.id.rl_monitor);
        rl_analyze = (RelativeLayout) findViewById(R.id.rl_analyze);
        rl_setting = (RelativeLayout) findViewById(R.id.rl_setting);
        ImageButton ib_monitor = (ImageButton) findViewById(R.id.ib_monitor);
        ImageButton ib_analyze = (ImageButton) findViewById(R.id.ib_analyze);
        ImageButton ib_setting = (ImageButton) findViewById(R.id.ib_setting);

        ib_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_monitor.setVisibility(View.VISIBLE);
                rl_setting.setVisibility(View.GONE);
                rl_analyze.setVisibility(View.GONE);
            }
        });
        ib_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_monitor.setVisibility(View.GONE);
                rl_setting.setVisibility(View.GONE);
                rl_analyze.setVisibility(View.VISIBLE);
            }
        });
        ib_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_monitor.setVisibility(View.GONE);
                rl_setting.setVisibility(View.VISIBLE);
                rl_analyze.setVisibility(View.GONE);
            }
        });

        monitor = new Monitor(MainActivity.this, rl_monitor);
        settingManager = new SettingManager(MainActivity.this, rl_setting);
        locationInfoProvider = new LocationInfoProvider(getApplicationContext(), scanSpan);
        locationInfoProvider.registerLocationListener(new MyLocationListener());

        // cpu休眠自动唤醒，某些机型需要
//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent collectIntent = new Intent(MainActivity.this, LocationInfoProvider.class);
//        PendingIntent collectSender = PendingIntent.getService(MainActivity.this, 0, collectIntent, 0);
//        am.cancel(collectSender);
//        am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), scanSpan, collectSender);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onStart() {
        locationInfoProvider.onStart();
        super.onStart();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null)
                return;

            if (locationHelper == null)
                locationHelper = new TableLocationHelper(getApplicationContext(), TableLocationHelper.DATABASE_NAME);

            LocationInfo lastLocation = locationInfoProvider.getLocationInfo();
            SQLiteDatabase wdb = locationHelper.getWritableDatabase();

            long timeDiff;
            double dist;

            if (lastLocation == null){
                timeDiff = 24 * 60 * 60 * 1000;
                dist = 0;
            }
            else{
                timeDiff = Calendar.getInstance().getTimeInMillis() - lastLocation.getTime();
                dist = DistanceUtil.getDistance(new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
            }

            if (wdb != null && (lastLocation == null || (dist * 1000.0 / timeDiff) < 400 && (dist > distError || timeDiff > timeError))) {

                locationInfoProvider.updateLocation(location);

                LocationInfo curLocation = locationInfoProvider.getLocationInfo();
                double longitude = curLocation.getLongitude();
                double latitude = curLocation.getLatitude();
                float speed = curLocation.getSpeed();
                String timeStr = curLocation.getTimeStr();

                String str = "insert (" + longitude + ", " + latitude + ")\n" + speed + "\n" + timeStr;
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                Log.d("map", str);

                ContentValues values = new ContentValues();
                values.put(TableLocationHelper.COL_TIME, curLocation.getTime());
                values.put(TableLocationHelper.COL_LONGITUDE, longitude);
                values.put(TableLocationHelper.COL_LATITUDE, latitude);
                values.put(TableLocationHelper.COL_SPEED, speed);
                values.put(TableLocationHelper.COL_ALTITUDE, curLocation.getAltitude());
                values.put(TableLocationHelper.COL_ACCURACY, curLocation.getAccuracy());
                values.put(TableLocationHelper.COL_DIRECT, curLocation.getDirect());
                values.put(TableLocationHelper.COL_RADIUS, curLocation.getRadius());
                values.put(TableLocationHelper.COL_STREET, curLocation.getStreet());
                values.put(TableLocationHelper.COL_CITY, curLocation.getCity());
                values.put(TableLocationHelper.COL_ADDR, curLocation.getAddr());
                values.put(TableLocationHelper.COL_COOR_TYPE, curLocation.getCoorType());
                values.put(TableLocationHelper.COL_DISTRICT, curLocation.getDistrict());

                wdb.insert(TableLocationHelper.TABLE_NAME, null, values);
                wdb.close();
            }
        }

    }
}
