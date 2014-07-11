package com.atomu.timetrace.monitor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.atomu.timetrace.app.R;
import com.atomu.timetrace.effect.TitleScrollListener;

import java.util.List;

/**
 * Created by Atomu on 2014/7/6.
 * this class updates the monitor page
 */
public class Monitor {

    final static int REFRESH_VIEW_DELAY = 10 * 1000;
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
        lv_monitor_process.setOnScrollListener(new TitleScrollListener(tv_monitor_title));

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
//                        updateTitle(locationInfoProvider.getLocationInfo());
                        break;
                    case Monitor.REFRESH_LOCATION_MSG:
                        locationInfoProvider.updateLocationInfo();
                        break;
                }
            }
        };
        refreshProcess();
        refreshLocation();

    }

//    private void updateTitle(LocationInfo locationInfo) {
//        if (locationInfo != null) {
//            StringBuffer sb = new StringBuffer();
//            sb.append("location: (");
//            sb.append(new DecimalFormat("0.0000").format(locationInfo.getLongitude()));
//            sb.append(", ");
//            sb.append(new DecimalFormat("0.0000").format(locationInfo.getLatitude()));
//            sb.append(")");//
//                    ((TextView) getRootView().findViewById(R.id.tv_monitor_title)).setText(sb.toString());
//        }
//
//    }

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
