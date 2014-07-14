package com.atomu.timetrace.monitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atomu.timetrace.app.MainActivity;
import com.atomu.timetrace.app.R;
import com.atomu.timetrace.database.TableInstalledHelper;
import com.atomu.timetrace.database.TableRecordHelper;
import com.atomu.timetrace.effect.ListScrollTitleListener;
import com.atomu.timetrace.location.LocationInfoMeta;
import com.atomu.timetrace.location.LocationInfoProvider;
import com.atomu.timetrace.process.ProcessInfo;
import com.atomu.timetrace.process.ProcessInfoProvider;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Atomu on 2014/7/6.
 * this class updates the monitor page
 */
public class Monitor {

    final static int REFRESH_VIEW_DELAY = 30 * 1000;
    final static int REFRESH_LOCATION_DELAY = 10 * 60 * 1000;
    final static int REFRESH_VIEW_MSG = 1000;
    final static int REFRESH_LOCATION_MSG = 2000;

    public Context context;
    public View rootView;
    private ProcessInfoProvider processInfoProvider;
    private List<ProcessInfo> processInfoList;
    private LocationInfoProvider locationInfoProvider;
    private ProcessItemAdapter processItemAdapter;
    private ListView lv_monitor_process;
    private TextView tv_monitor_title;

    private Handler refreshHandler;

    public Monitor(Context c, View v) {
        setContext(c);
        setRootView(v);

        lv_monitor_process = (ListView) rootView.findViewById(R.id.lv_monitor_process);
        tv_monitor_title = (TextView) rootView.findViewById(R.id.tv_monitor_title);
        lv_monitor_process.setOnScrollListener(new ListScrollTitleListener(tv_monitor_title));

        processInfoProvider = new ProcessInfoProvider(context);
        processInfoList = processInfoProvider.getProcessInfoList();
        processItemAdapter = new ProcessItemAdapter();
        lv_monitor_process.setAdapter(processItemAdapter);

        locationInfoProvider = new LocationInfoProvider(context);
        refreshHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Monitor.REFRESH_VIEW_MSG:
                        processInfoList = processInfoProvider.getProcessInfoList();
                        processItemAdapter.notifyDataSetChanged();

                        checkTableRecord();
                        break;
                    case Monitor.REFRESH_LOCATION_MSG:
                        locationInfoProvider.updateLocationInfo();
                        break;
                }
            }
        };
        refreshProcess();
        refreshLocation();

//        TableInstalledHelper installedHelper = new TableInstalledHelper(context, context.getString(R.string.database_installed));

//        TableRecordHelper recordHelper = new TableRecordHelper(context, context.getString(R.string.database_record));
//        SQLiteDatabase record = recordHelper.getReadableDatabase();
//        if (record != null) {
//            Cursor cursor = record.query(context.getString(R.string.table_record), null, null, null, null, null, null);
//            cursor.close();
//            record.close();
//        }
    }

    private void checkTableRecord(){
        long time = Calendar.getInstance().getTimeInMillis();
        int location = MainActivity.getLocation();
        double longitude = locationInfoProvider.getLocationInfo().getLongitude();
        double latitude = locationInfoProvider.getLocationInfo().getLatitude();
        double speed = locationInfoProvider.getLocationInfo().getSpeed();
        int activity = MainActivity.getActivity();

        Toast.makeText(context, "gps: (" + longitude + ", " + latitude + ")\nspeed: " + speed +
                "\nlocation: "+(new LocationInfoMeta()).getTagFromKey(context, location) +
                "\nactivity: "+(new ActivityInfoMeta()).getTagFromKey(context, activity), Toast.LENGTH_LONG).show();

        TableInstalledHelper installedHelper = new TableInstalledHelper(context, context.getString(R.string.database_installed));
        SQLiteDatabase rdb = installedHelper.getReadableDatabase();
        Cursor cursor;
        String[] columns = new String[]{context.getString(R.string.state_id), context.getString(R.string.process_info_pack_name)};
        String selection = context.getString(R.string.process_info_pack_name) + " = ? ";
        TableRecordHelper recordHelper = new TableRecordHelper(context, context.getString(R.string.database_record));
        SQLiteDatabase wdb = recordHelper.getWritableDatabase();

        if (rdb != null && wdb != null){
            String active =context.getString(R.string.process_info_active);
            String alive = context.getString(R.string.process_info_alive);
            ContentValues values = new ContentValues();

            values.put(context.getString(R.string.state_time), time);
            values.put(context.getString(R.string.state_location), location);
            values.put(context.getString(R.string.location_info_longitude), longitude);
            values.put(context.getString(R.string.location_info_latitude), latitude);
            values.put(context.getString(R.string.location_info_speed), speed);
            values.put(context.getString(R.string.state_activity), activity);
            for (ProcessInfo info : processInfoList){
                String [] selectionArgs = new String[] {info.getPackName()};
                cursor = rdb.query(context.getString(R.string.table_installed),columns, selection, selectionArgs, null, null, null);
                if (cursor.moveToNext()){
                    String prefix = "process_" + cursor.getInt(cursor.getColumnIndex(context.getString(R.string.state_id))) + "_";
                    cursor.close();

                    values.put(prefix+active, info.isActive());
                    values.put(prefix+alive, info.isAlive());
//                    Log.d("record", prefix + active + ": " + info.isActive() + "\n" + prefix + alive + ": " + info.isAlive());
                }
            }
            rdb.close();

            wdb.insert(context.getString(R.string.table_record), null, values);
            wdb.close();
        }
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context c) {
        this.context = c;
    }

    public View getRootView() {
        return this.rootView;
    }

    public void setRootView(View v) {
        this.rootView = v;
    }

    private void refreshProcess() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    refreshHandler.sendEmptyMessage(Monitor.REFRESH_VIEW_MSG);
                    try {
                        Thread.sleep(Monitor.REFRESH_VIEW_DELAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void refreshLocation() {

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
            itemViewHolder.category.setText(info.getTagString(context));
            itemViewHolder.startTime.setText(info.getStartTimeString(context));
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
