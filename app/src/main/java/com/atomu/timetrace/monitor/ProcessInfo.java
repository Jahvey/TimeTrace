package com.atomu.timetrace.monitor;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.atomu.timetrace.app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Atomu on 2014/7/6.
 * this class is used to encapsulate information about a process
 */
public class ProcessInfo {
    private String appName;
    private int tag;
    private Drawable icon;
    private long liveTime;
    private long memSize;
    private String packName;
    private int pid;
    private long startTime;
    private boolean active;
    private boolean alive;
    private boolean userProcess;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setTag(Context context, String tag) {
        this.tag = new ProcessInfoMeta().getKeyFromTag(context, tag);
    }

    public String getTagString(Context context) {
        return new ProcessInfoMeta().getTagFromKey(context, this.tag);
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public long getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(long liveTime) {
        this.liveTime = liveTime;
    }

    public long getMemSize() {
        return memSize;
    }

    public void setMemSize(long memSize) {
        this.memSize = memSize;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime.getTimeInMillis();
    }

    public boolean isUserProcess() {
        return userProcess;
    }

    public void setUserProcess(boolean userProcess) {
        this.userProcess = userProcess;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean a) {
        this.alive = a;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean a) {
        this.active = a;
    }

    public String getStartTimeString(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.process_info_date_format));
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(this.startTime);

        return sdf.format(start.getTime());
    }

    public String getLiveTimeString() {
        long diffSecs = this.liveTime / 1000 % 60;
        long diffMinutes = this.liveTime / (60 * 1000) % 60;
        long diffHours = this.liveTime / (60 * 60 * 1000) % 24;
        long diffDays = this.liveTime / (24 * 60 * 60 * 1000);

        return String.format("%d,  %d:%d:%d", diffDays, diffHours, diffMinutes, diffSecs);
    }

}
