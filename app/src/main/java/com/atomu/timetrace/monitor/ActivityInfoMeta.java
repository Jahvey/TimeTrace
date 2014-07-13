package com.atomu.timetrace.monitor;

import android.content.Context;
import android.content.res.Resources;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/13.
 * transfer constant
 */
public class ActivityInfoMeta {

    public int getKeyFromTag(Context context, String tag) {
        Resources res = context.getResources();

        if (tag.equals(context.getString(R.string.activity_tag_communication)))
            return res.getInteger(R.integer.activity_key_communication);
        else if (tag.equals(context.getString(R.string.activity_tag_eat)))
            return res.getInteger(R.integer.activity_key_eat);
        else if (tag.equals(context.getString(R.string.activity_tag_entertainment)))
            return res.getInteger(R.integer.activity_key_entertainment);
        else if (tag.equals(context.getString(R.string.activity_tag_game)))
            return res.getInteger(R.integer.activity_key_game);
        else if (tag.equals(context.getString(R.string.activity_tag_motion)))
            return res.getInteger(R.integer.activity_key_motion);
        else if (tag.equals(context.getString(R.string.activity_tag_news_information)))
            return res.getInteger(R.integer.activity_key_news_information);
        else if (tag.equals(context.getString(R.string.activity_tag_sleep)))
            return res.getInteger(R.integer.activity_key_sleep);
        else if (tag.equals(context.getString(R.string.activity_tag_social_network)))
            return res.getInteger(R.integer.activity_key_social_network);
        else if (tag.equals(context.getString(R.string.activity_tag_sports)))
            return res.getInteger(R.integer.activity_key_sports);
        else if (tag.equals(context.getString(R.string.activity_tag_work_study)))
            return res.getInteger(R.integer.activity_key_work_study);
        else
            return res.getInteger(R.integer.activity_key_unknown);

    }

    public String getTagFromKey(Context context, int key) {
        Resources res = context.getResources();

        if (key == res.getInteger(R.integer.activity_key_communication))
            return context.getString(R.string.activity_tag_communication);
        else if (key == res.getInteger(R.integer.activity_key_eat))
            return context.getString(R.string.activity_tag_eat);
        else if (key == res.getInteger(R.integer.activity_key_entertainment))
            return context.getString(R.string.activity_tag_entertainment);
        else if (key == res.getInteger(R.integer.activity_key_game))
            return context.getString(R.string.activity_tag_game);
        else if (key == res.getInteger(R.integer.activity_key_motion))
            return context.getString(R.string.activity_tag_motion);
        else if (key == res.getInteger(R.integer.activity_key_news_information))
            return context.getString(R.string.activity_tag_news_information);
        else if (key == res.getInteger(R.integer.activity_key_sleep))
            return context.getString(R.string.activity_tag_sleep);
        else if (key == res.getInteger(R.integer.activity_key_social_network))
            return context.getString(R.string.activity_tag_social_network);
        else if (key == res.getInteger(R.integer.activity_key_sports))
            return context.getString(R.string.activity_tag_sports);
        else if (key == res.getInteger(R.integer.activity_key_work_study))
            return context.getString(R.string.activity_tag_work_study);
        else
            return context.getString(R.string.activity_tag_unknown);

    }
}
