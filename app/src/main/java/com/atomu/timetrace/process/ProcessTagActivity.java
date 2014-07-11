package com.atomu.timetrace.process;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.atomu.timetrace.app.MainActivity;
import com.atomu.timetrace.app.R;
import com.atomu.timetrace.effect.TitleScrollListener;
import com.atomu.timetrace.monitor.ProcessInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Atomu on 2014/7/10.
 * test class: process tag module
 */
public class ProcessTagActivity extends Activity {

    private ListView lv_process_tag;
    private ImageButton ib_process_tag_ok;
    private ImageButton ib_process_tag_cancel;
    private TextView tv_process_tag_title;
    private List<ProcessInfo> processInfoList;

    public boolean isUserApp(PackageInfo pInfo) {
        if (pInfo.applicationInfo != null && (pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if (pInfo.applicationInfo != null && (pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }

    private String getAppName(ApplicationInfo applicationInfo, PackageManager pm) {
        CharSequence cs = applicationInfo.loadLabel(pm);

        if (cs != null)
            return cs.toString();
        return null;
    }

    private boolean isFirstTime() {
        SharedPreferences spf = ProcessTagActivity.this.getSharedPreferences("preference", Context.MODE_PRIVATE);
        return spf.getBoolean("isFirstTime", true);
    }

    private void savePreference(boolean flag) {
        SharedPreferences spf = ProcessTagActivity.this.getSharedPreferences("preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("isFirstTime", flag);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_tag);

        processInfoList = new ArrayList<ProcessInfo>();
        lv_process_tag = (ListView) findViewById(R.id.lv_process_tag);
        ib_process_tag_ok = (ImageButton) findViewById(R.id.ib_process_tag_ok);
        ib_process_tag_cancel = (ImageButton) findViewById(R.id.ib_process_tag_cancel);
        tv_process_tag_title = (TextView) findViewById(R.id.tv_process_tag_title);
        lv_process_tag.setOnScrollListener(new TitleScrollListener(tv_process_tag_title));

        PackageManager pm = ProcessTagActivity.this.getPackageManager();
        List<PackageInfo> packs = null;
        if (pm != null) {
            packs = pm.getInstalledPackages(0);
        }
        if (packs != null) {
            for (PackageInfo pi : packs) {
                ApplicationInfo applicationInfo = pi.applicationInfo;
                if (applicationInfo != null) {
                    ProcessInfo info = new ProcessInfo();
                    boolean isUserApp = isUserApp(pi);

                    info.setUserProcess(isUserApp);
                    if (isUserApp)
                        info.setTag(ProcessTagActivity.this.getResources().getInteger(R.integer.process_tag_key_lifestyle));
                    else
                        info.setTag(ProcessTagActivity.this.getResources().getInteger(R.integer.process_tag_key_lifestyle));

                    info.setAppName(getAppName(applicationInfo, pm));
                    info.setIcon(applicationInfo.loadIcon(pm));

                    if (info.getAppName() != null)
                        processInfoList.add(info);
                }

            }
        }

        ProcessTagItemAdapter adapter = new ProcessTagItemAdapter();
        lv_process_tag.setAdapter(adapter);

        ib_process_tag_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
                if (isFirstTime())
                    savePreference(false);
            }
        });
        ib_process_tag_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
                if (isFirstTime())
                    savePreference(true);
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(ProcessTagActivity.this, MainActivity.class);
        ProcessTagActivity.this.startActivity(intent);
        ProcessTagActivity.this.finish();
    }

    static class ProcessTagItemViewHolder {
        ImageView icon;
        TextView appName;
        TextView isUserApp;
        TextView tag;
    }

    private class ProcessTagItemAdapter extends BaseAdapter {

        private ProcessTagItemViewHolder holder = null;
        private View view;

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

            this.holder.icon.setImageDrawable(info.getIcon());
            this.holder.appName.setText(info.getAppName());
            this.holder.tag.setText(info.getTagString(ProcessTagActivity.this));
            if (info.isUserProcess())
                this.holder.isUserApp.setText("usr");
            else
                this.holder.isUserApp.setText("sys");

            this.view.setTag(this.holder);
            this.view.setOnTouchListener(new View.OnTouchListener() {
                float position = 0;

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Button btn_ignore = (Button) view.findViewById(R.id.btn_process_tag_ignore);

                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            position = motionEvent.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                            if (motionEvent.getX() < position){
                                btn_ignore.setVisibility(View.VISIBLE);
                            } else {
                                btn_ignore.setVisibility(View.GONE);
                            }
                           break;
                    }
                    return true;
                }
            });

            return this.view;
        }

        private void initViewHolder(View convertView) {
            if (convertView == null) {
                view = View.inflate(ProcessTagActivity.this, R.layout.process_tag_item, null);
                holder = new ProcessTagItemViewHolder();

                holder.icon = (ImageView) view.findViewById(R.id.iv_tag_item_icon);
                holder.appName = (TextView) view.findViewById(R.id.tv_process_tag_app_name);
                holder.isUserApp = (TextView) view.findViewById(R.id.tv_process_tag_user_app);
                holder.tag = (TextView) view.findViewById(R.id.tv_process_tag_tag);
            } else {
                view = convertView;
                holder = (ProcessTagItemViewHolder) view.getTag();
            }
        }

    }
}
