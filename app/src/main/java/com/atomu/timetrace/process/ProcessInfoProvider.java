package com.atomu.timetrace.process;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Debug;

import com.atomu.timetrace.app.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Atomu on 2014/7/6.
 * this class aims to grasp running processes
 */
public class ProcessInfoProvider {

    private Context context;

    public ProcessInfoProvider(Context c) {
        setContext(c);
    }

    private boolean isUserApp(ApplicationInfo info) {
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

    private long getMemSize(ActivityManager am, int pid) {
        long memorySize = 0;
        Debug.MemoryInfo[] memoryInfoArr = am.getProcessMemoryInfo(new int[]{pid});

        if (memoryInfoArr != null && memoryInfoArr.length > 0)
            memorySize = memoryInfoArr[0].getTotalPrivateDirty();
        return memorySize;
    }

    private String getAppName(ApplicationInfo applicationInfo, PackageManager pm) {
        if (applicationInfo == null)
            return null;

        CharSequence cs = applicationInfo.loadLabel(pm);
        if (cs != null)
            return cs.toString();
        return null;
    }

    public List<ProcessInfo> getProcessInfoList() {
        ActivityManager am = (ActivityManager) context.
                getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        runningAppProcesses = am.getRunningAppProcesses();
        List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();

        if (runningAppProcesses != null && pm != null && am != null)
            for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
                ProcessInfo processInfo = new ProcessInfo();
                String packageName = info.processName;

                processInfo.setPid(info.pid);
                processInfo.setMemSize(getMemSize(am, info.pid));
                processInfo.setPackName(packageName);
                processInfo.setAlive(true);
                processInfo.setActive(isBackgroundProcess(info));
                try {
                    PackageInfo pi = pm.getPackageInfo(packageName, 0);
                    ApplicationInfo applicationInfo = pi.applicationInfo;
                    if (applicationInfo != null) {
                        boolean isUserApp = isUserApp(applicationInfo);

                        processInfo.setUserProcess(isUserApp);
                        processInfo.setIcon(applicationInfo.loadIcon(pm));
                        processInfo.setAppName(getAppName(applicationInfo, pm));

                        Resources res = context.getResources();
                        if (isUserApp)
                            processInfo.setTag(res.getInteger(R.integer.process_tag_key_lifestyle));
                        else
                            processInfo.setTag(res.getInteger(R.integer.process_tag_key_system));

                        // to be determined later
                        processInfo.setStartTime(Calendar.getInstance());
                        processInfo.setLiveTime(0);

                        processInfoList.add(processInfo);
                    }
                } catch (Exception ignored) {
                }

            }

        return processInfoList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
