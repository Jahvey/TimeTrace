package com.atomu.timetrace.monitor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.atomu.timetrace.app.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Atomu on 2014/7/6.
 * this class aims to grasp running processes
 */
public class ProcessInfoProvider {

    private boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }

    private boolean isBackgroundProcess(ActivityManager.RunningAppProcessInfo info) {
        return info.importance == ActivityManager.RunningAppProcessInfo.
                IMPORTANCE_BACKGROUND;
    }

    public List<ProcessInfo> getProcessInfoList() {
        ActivityManager am = (ActivityManager) Monitor.getContext().
                getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = Monitor.getContext().getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        runningAppProcesses = am.getRunningAppProcesses();
        List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();

        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
                ProcessInfo processInfo = new ProcessInfo();

                processInfo.setPid(info.pid);
                long memorySize = am.getProcessMemoryInfo(new int[]{info.pid})[0].getTotalPrivateDirty();
                processInfo.setMemSize(memorySize);
                String packageName = info.processName;
                processInfo.setPackName(packageName);
                processInfo.setAlive(true);
                processInfo.setActive(isBackgroundProcess(info));

                try {
                    ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);

                    processInfo.setUserProcess(filterApp(applicationInfo));
                    processInfo.setIcon(applicationInfo.loadIcon(pm));
                    processInfo.setAppName(applicationInfo.loadLabel(pm).toString());
                } catch (Exception e) {
                    processInfo.setUserProcess(false);
                    processInfo.setIcon(Monitor.getContext().getResources().getDrawable(R.drawable.ic_launcher));
                    processInfo.setAppName(packageName);
                }

                // to be determined later
                processInfo.setCategory(ProcessInfoMeta._TAG_BUSINESS);
                processInfo.setStartTime(Calendar.getInstance());
                processInfo.setLiveTime(0);

                processInfoList.add(processInfo);
            }
        }

        return processInfoList;
    }

}
