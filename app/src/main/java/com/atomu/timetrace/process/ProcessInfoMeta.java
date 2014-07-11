package com.atomu.timetrace.process;

import android.content.Context;
import android.content.res.Resources;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/9.
 * this class is consisted of some constant variables
 */
final public class ProcessInfoMeta {
//    final static public String _ACTIVE = "active";
//    final static public String _ALIVE = "alive";
//    final static public String _APPNAME = "appName";
//    final static public String _TAG = "tag";
//    final static public String _ID = "id";
//    final static public String _LIVETIME = "liveTime";
//    final static public String _MEMSIZE = "memSize";
//    final static public String _PACKNAME = "packName";
//    final static public String _PID = "pid";
//    final static public String _STARTTIME = "startTime";

    //    final static public String _TAG_BEAUTIFICATION = "beautification";
//    final static public String _TAG_BUSINESS = "business";
//    final static public String _TAG_COMMUNICATION = "communication";
//    final static public String _TAG_EDUCATION = "education";
//    final static public String _TAG_FINANCE = "finance";
//    final static public String _TAG_LIFESTYLE = "lifestyle";
//    final static public String _TAG_MEDIA = "media";
//    final static public String _TAG_NEWSSTAND = "newsstand";
//    final static public String _TAG_ONLINE_SHOPPING = "onlineShopping";
//    final static public String _TAG_OPTIMIZATION = "optimization";
//    final static public String _TAG_READING = "reading";
//    final static public String _TAG_SOCIAL_NETWORK = "socialNetwork";
//    final static public String _TAG_SYSTEM = "system";
//    final static public String _TAG_TOOL = "tool";
//    final static public String _TAG_TRAVEL = "travel";
//    final static public String _TAG_UNKNOWN = "unknown";
//    final static public String DATE_FORMAT = "dd, HH:mm:sss";
//    final static public String TIME_FORMAT = "%d, %d:%d:%d";

//    final static public int _TAG_KEY_BEAUTIFICATION = 0;
//    final static public int _TAG_KEY_BUSINESS = 1;
//    final static public int _TAG_KEY_COMMUNICATION = 2;
//    final static public int _TAG_KEY_EDUCATION = 3;
//    final static public int _TAG_KEY_FINANCE = 4;
//    final static public int _TAG_KEY_LIFESTYLE = 5;
//    final static public int _TAG_KEY_MEDIA = 6;
//    final static public int _TAG_KEY_NEWSSTAND = 7;
//    final static public int _TAG_KEY_ONLINE_SHOPPING = 8;
//    final static public int _TAG_KEY_OPTIMIZATION = 9;
//    final static public int _TAG_KEY_READING = 10;
//    final static public int _TAG_KEY_SOCIAL_NETWORK = 11;
//    final static public int _TAG_KEY_SYSTEM = 12;
//    final static public int _TAG_KEY_TOOL = 13;
//    final static public int _TAG_KEY_TRAVEL = 14;
//    final static public int _TAG_KEY_UNKNOWN = 15;

    public int getKeyFromTag(Context context, String tag) {
        Resources res = context.getResources();

        if (tag.equals(context.getString(R.string.process_tag_beautification))) {
            return res.getInteger(R.integer.process_tag_key_beautification);
        } else if (tag.equals(context.getString(R.string.process_tag_business))) {
            return res.getInteger(R.integer.process_tag_key_business);
        } else if (tag.equals(context.getString(R.string.process_tag_communication))) {
            return res.getInteger(R.integer.process_tag_key_communication);
        } else if (tag.equals(context.getString(R.string.process_tag_education))) {
            return res.getInteger(R.integer.process_tag_key_education);
        } else if (tag.equals(context.getString(R.string.process_tag_finance))) {
            return res.getInteger(R.integer.process_tag_key_finance);
        } else if (tag.equals(context.getString(R.string.process_tag_lifestyle))) {
            return res.getInteger(R.integer.process_tag_key_lifestyle);
        } else if (tag.equals(context.getString(R.string.process_tag_media))) {
            return res.getInteger(R.integer.process_tag_key_media);
        } else if (tag.equals(context.getString(R.string.process_tag_newsstand))) {
            return res.getInteger(R.integer.process_tag_key_newsstand);
        } else if (tag.equals(context.getString(R.string.process_tag_online_shopping))) {
            return res.getInteger(R.integer.process_tag_key_online_shopping);
        } else if (tag.equals(context.getString(R.string.process_tag_optimization))) {
            return res.getInteger(R.integer.process_tag_key_optimization);
        } else if (tag.equals(context.getString(R.string.process_tag_reading))) {
            return res.getInteger(R.integer.process_tag_key_reading);
        } else if (tag.equals(context.getString(R.string.process_tag_social_network))) {
            return res.getInteger(R.integer.process_tag_key_social_network);
        } else if (tag.equals(context.getString(R.string.process_tag_system))) {
            return res.getInteger(R.integer.process_tag_key_system);
        } else if (tag.equals(context.getString(R.string.process_tag_tool))) {
            return res.getInteger(R.integer.process_tag_key_tool);
        } else if (tag.equals(context.getString(R.string.process_tag_travel))) {
            return res.getInteger(R.integer.process_tag_key_travel);
        } else {
            return res.getInteger(R.integer.process_tag_key_unknown);
        }
    }

    public String getTagFromKey(Context context, int key) {
        Resources res = context.getResources();

        if (key == res.getInteger(R.integer.process_tag_key_beautification))
            return context.getString(R.string.process_tag_beautification);
        else if (key == res.getInteger(R.integer.process_tag_key_business))
            return context.getString(R.string.process_tag_business);
        else if (key == res.getInteger(R.integer.process_tag_key_communication))
            return context.getString(R.string.process_tag_communication);
        else if (key == res.getInteger(R.integer.process_tag_key_education))
            return context.getString(R.string.process_tag_education);
        else if (key == res.getInteger(R.integer.process_tag_key_finance))
            return context.getString(R.string.process_tag_finance);
        else if (key == res.getInteger(R.integer.process_tag_key_lifestyle))
            return context.getString(R.string.process_tag_lifestyle);
        else if (key == res.getInteger(R.integer.process_tag_key_media))
            return context.getString(R.string.process_tag_media);
        else if (key == res.getInteger(R.integer.process_tag_key_social_network))
            return context.getString(R.string.process_tag_social_network);
        else if (key == res.getInteger(R.integer.process_tag_key_online_shopping))
            return context.getString(R.string.process_tag_online_shopping);
        else if (key == res.getInteger(R.integer.process_tag_key_optimization))
            return context.getString(R.string.process_tag_optimization);
        else if (key == res.getInteger(R.integer.process_tag_key_reading))
            return context.getString(R.string.process_tag_reading);
        else if (key == res.getInteger(R.integer.process_tag_key_social_network))
            return context.getString(R.string.process_tag_social_network);
        else if (key == res.getInteger(R.integer.process_tag_key_system))
            return context.getString(R.string.process_tag_system);
        else if (key == res.getInteger(R.integer.process_tag_key_tool))
            return context.getString(R.string.process_tag_tool);
        else if (key == res.getInteger(R.integer.process_tag_key_travel))
            return context.getString(R.string.process_tag_travel);
        else
            return context.getString(R.string.process_tag_unknown);

    }
}

