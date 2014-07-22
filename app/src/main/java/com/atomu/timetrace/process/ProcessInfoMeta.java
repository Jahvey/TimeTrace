package com.atomu.timetrace.process;

import android.content.Context;
import android.content.res.Resources;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/9.
 * this class is consisted of some constant variables
 */
final public class ProcessInfoMeta {

    public int getKeyFromTag(Context context, String tag) {
        Resources res = context.getResources();

        if (tag.equals(context.getString(R.string.process_tag_beautification))) {
            return res.getInteger(R.integer.process_key_beautification);
        } else if (tag.equals(context.getString(R.string.process_tag_business))) {
            return res.getInteger(R.integer.process_key_business);
        } else if (tag.equals(context.getString(R.string.process_tag_communication))) {
            return res.getInteger(R.integer.process_key_communication);
        } else if (tag.equals(context.getString(R.string.process_tag_education))) {
            return res.getInteger(R.integer.process_key_education);
        } else if (tag.equals(context.getString(R.string.process_tag_finance))) {
            return res.getInteger(R.integer.process_key_finance);
        } else if (tag.equals(context.getString(R.string.process_tag_lifestyle))) {
            return res.getInteger(R.integer.process_key_lifestyle);
        } else if (tag.equals(context.getString(R.string.process_tag_media))) {
            return res.getInteger(R.integer.process_key_media);
        } else if (tag.equals(context.getString(R.string.process_tag_news_information))) {
            return res.getInteger(R.integer.process_key_news_information);
        } else if (tag.equals(context.getString(R.string.process_tag_online_shopping))) {
            return res.getInteger(R.integer.process_key_online_shopping);
        } else if (tag.equals(context.getString(R.string.process_tag_optimization))) {
            return res.getInteger(R.integer.process_key_optimization);
        } else if (tag.equals(context.getString(R.string.process_tag_reading))) {
            return res.getInteger(R.integer.process_key_reading);
        } else if (tag.equals(context.getString(R.string.process_tag_social_network))) {
            return res.getInteger(R.integer.process_key_social_network);
        } else if (tag.equals(context.getString(R.string.process_tag_system))) {
            return res.getInteger(R.integer.process_key_system);
        } else if (tag.equals(context.getString(R.string.process_tag_tool))) {
            return res.getInteger(R.integer.process_key_tool);
        } else if (tag.equals(context.getString(R.string.process_tag_travel))) {
            return res.getInteger(R.integer.process_key_travel);
        } else if (tag.equals(context.getString(R.string.process_tag_ignore))) {
            return res.getInteger(R.integer.process_key_ignore);
        } else if (tag.equals(context.getString(R.string.process_tag_game))) {
            return res.getInteger(R.integer.process_key_game);
        } else {
            return res.getInteger(R.integer.process_key_unknown);
        }
    }

    public String getTagFromKey(Context context, int key) {
        Resources res = context.getResources();

        if (key == res.getInteger(R.integer.process_key_beautification))
            return context.getString(R.string.process_tag_beautification);
        else if (key == res.getInteger(R.integer.process_key_business))
            return context.getString(R.string.process_tag_business);
        else if (key == res.getInteger(R.integer.process_key_communication))
            return context.getString(R.string.process_tag_communication);
        else if (key == res.getInteger(R.integer.process_key_education))
            return context.getString(R.string.process_tag_education);
        else if (key == res.getInteger(R.integer.process_key_finance))
            return context.getString(R.string.process_tag_finance);
        else if (key == res.getInteger(R.integer.process_key_lifestyle))
            return context.getString(R.string.process_tag_lifestyle);
        else if (key == res.getInteger(R.integer.process_key_media))
            return context.getString(R.string.process_tag_media);
        else if (key == res.getInteger(R.integer.process_key_social_network))
            return context.getString(R.string.process_tag_social_network);
        else if (key == res.getInteger(R.integer.process_key_online_shopping))
            return context.getString(R.string.process_tag_online_shopping);
        else if (key == res.getInteger(R.integer.process_key_optimization))
            return context.getString(R.string.process_tag_optimization);
        else if (key == res.getInteger(R.integer.process_key_reading))
            return context.getString(R.string.process_tag_reading);
        else if (key == res.getInteger(R.integer.process_key_social_network))
            return context.getString(R.string.process_tag_social_network);
        else if (key == res.getInteger(R.integer.process_key_system))
            return context.getString(R.string.process_tag_system);
        else if (key == res.getInteger(R.integer.process_key_tool))
            return context.getString(R.string.process_tag_tool);
        else if (key == res.getInteger(R.integer.process_key_travel))
            return context.getString(R.string.process_tag_travel);
        else if (key == res.getInteger(R.integer.process_key_ignore))
            return context.getString(R.string.process_tag_ignore);
        else if (key == res.getInteger(R.integer.process_key_game))
            return context.getString(R.string.process_tag_game);
        else
            return context.getString(R.string.process_tag_unknown);

    }
}

