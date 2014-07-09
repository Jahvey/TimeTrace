package com.atomu.timetrace.monitor;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.atomu.timetrace.app.R;

import java.util.List;

/**
 * Created by Atomu on 2014/7/6.
 * this class updates the monitor page
 */
public class Monitor {

    final static int REFRESH_PROCESS_DELAY = 10000;
    final static int REFRESH_LOCATION_DELAY = 10000;
    final static int REFRESH_PROCESS_MSG = 1000;
    final static int REFRESH_LOCATION_MSG = 2000;

    static public Context context;
    static public View rootView;
    private ProcessInfoProvider provider;
    private List<ProcessInfo> processInfoList;
    private ProcessItemAdapter adapter;
    private ListView lv_monitor_process;
    private TextView tv_monitor_title;

    private Handler refreshHandler;

    public Monitor(Context c, View v) {
        setContext(c);
        setRootView(v);

        lv_monitor_process = (ListView) rootView.findViewById(R.id.lv_monitor_process);
        tv_monitor_title = (TextView) rootView.findViewById(R.id.tv_monitor_title);
        lv_monitor_process.setOnScrollListener(new TitleListener());

        provider = new ProcessInfoProvider();
        processInfoList = provider.getProcessInfoList();
        adapter = new ProcessItemAdapter();
        lv_monitor_process.setAdapter(adapter);
        refreshHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Monitor.REFRESH_PROCESS_MSG:
                        processInfoList = provider.getProcessInfoList();
                        adapter.notifyDataSetChanged();
                        break;
                    case Monitor.REFRESH_LOCATION_MSG:
                        LocationManager gpsManager = (LocationManager) getContext().
                                getSystemService(Context.LOCATION_SERVICE);
                        Location location = gpsManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                        if (location  == null){
//                            gpsManager.requestLocationUpdates("best_provider", 60000, 1, new GpsLocationListener());
//                        }
//                        location = gpsManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        printGpsLocation(location);
//                        Log.d("monitor", location.getLongitude()+", "+location.getLatitude());
                        break;
                }
            }
        };
        refreshProcess();
        refreshLocation();
    }

    private void printGpsLocation(Location location){
        if( location != null ) {
            /*
            location.getAccuracy();    精度
            location.getAltitude();    高度 : 海拔
            location.getBearing();     导向
            location.getSpeed();       速度
            location.getLatitude();    纬度
            location.getLongitude();   经度
            location.getTime();        UTC时间 以毫秒计
            */

            Log.d("monitor", "Accuracy : " + location.getAccuracy() +
                    "\nAltitude : " + location.getAltitude() +
                    "\nBearing : " + location.getBearing() +
                    "\nSpeed : " + location.getSpeed() +
                    "\nLatitude ：" + location.getLatitude() +
                    "\nLongitude : " + location.getLongitude() +
                    "\nTime : " + location.getTime());
        }
    }

    private class GpsLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            printGpsLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d("monitor", "ProviderDisabled: " + s + i);
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d("monitor", "ProviderEnabled: "+s);
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d("monitor", "ProviderDisabled: "+s);
        }
    }

    static public Context getContext() {
        return context;
    }

    static public void setContext(Context c) {
        context = c;
    }

    static public View getRootView() {
        return rootView;
    }

    static public void setRootView(View v) {
        rootView = v;
    }

    private void refreshProcess() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    refreshHandler.sendEmptyMessage(Monitor.REFRESH_PROCESS_MSG);
                    try {
                        Thread.sleep(Monitor.REFRESH_PROCESS_DELAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void refreshLocation(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    refreshHandler.sendEmptyMessage(Monitor.REFRESH_LOCATION_MSG);
                    try {
                        Thread.sleep(Monitor.REFRESH_LOCATION_DELAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    static class ProcessItemViewHolder {
        ImageView icon;
        TextView appName;
        TextView category;
        TextView startTime;
        TextView liveTime;
        ProcessInfo info;
    }

    private class TitleListener implements AbsListView.OnScrollListener {
        int lastPosition = 0;

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
            switch (i) {
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    lastPosition = absListView.getLastVisiblePosition();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    if (absListView.getLastVisiblePosition() < lastPosition) {
                        tv_monitor_title.setVisibility(View.VISIBLE);
                    } else {
                        tv_monitor_title.setVisibility(View.GONE);
                    }
                    break;
            }

        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {

        }
    }

    private class ProcessItemAdapter extends BaseAdapter {

        private ProcessItemViewHolder itemViewHolder = null;
        private View itemView;

        @Override
        public int getCount() {
            return processInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return processInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            initViewHolder(view);

            ProcessInfo info = processInfoList.get(i);

            itemViewHolder.info = info;
            itemViewHolder.icon.setImageDrawable(info.getIcon());
            itemViewHolder.appName.setText(info.getAppName());
            itemViewHolder.category.setText(info.getCategory());
            itemViewHolder.startTime.setText(info.getStartTimeString());
            itemViewHolder.liveTime.setText(info.getLiveTimeString());

            itemView.setTag(itemViewHolder);

            // listener here

            return itemView;
        }

        private void initViewHolder(View convertView) {
            if (convertView == null) {
                itemView = View.inflate(context, R.layout.process_item, null);
                itemViewHolder = new ProcessItemViewHolder();

                itemViewHolder.icon = (ImageView) itemView.findViewById(R.id.iv_item_icon);
                itemViewHolder.appName = (TextView) itemView.findViewById(R.id.tv_item_appName);
                itemViewHolder.category = (TextView) itemView.findViewById(R.id.tv_item_category);
                itemViewHolder.startTime = (TextView) itemView.findViewById(R.id.tv_item_startTime);
                itemViewHolder.liveTime = (TextView) itemView.findViewById(R.id.tv_item_live_time);
            } else {
                itemView = convertView;
                itemViewHolder = (ProcessItemViewHolder) itemView.getTag();
            }
        }
    }
}
