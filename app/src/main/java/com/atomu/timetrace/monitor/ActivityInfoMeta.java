package com.atomu.timetrace.monitor;

import android.content.Context;
import android.content.res.Resources;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/13.
 * transfer constant
 */
public class ActivityInfoMeta {

    public int hlGetKeyFromTag(Context context, String tag) {
        Resources res = context.getResources();

        if (tag.equals(context.getString(R.string.hl_act_tag_eat)))
            return res.getInteger(R.integer.hl_act_tag_eat);
        else if (tag.equals(context.getString(R.string.hl_act_tag_sleep)))
            return res.getInteger(R.integer.hl_act_tag_sleep);
        else if (tag.equals(context.getString(R.string.hl_act_tag_work)))
            return res.getInteger(R.integer.hl_act_tag_work);
        else if (tag.equals(context.getString(R.string.hl_act_tag_motion)))
            return res.getInteger(R.integer.hl_act_tag_motion);
        else if (tag.equals(context.getString(R.string.hl_act_tag_relax)))
            return res.getInteger(R.integer.hl_act_tag_relax);
        else
            return res.getInteger(R.integer.hl_act_tag_trifle);

    }

    public String hlGetTagFromKey(Context context, int key) {
        Resources res = context.getResources();

        if (key == res.getInteger(R.integer.hl_act_tag_eat))
            return context.getString(R.string.hl_act_tag_eat);
        else if (key == res.getInteger(R.integer.hl_act_tag_sleep))
            return context.getString(R.string.hl_act_tag_sleep);
        else if (key == res.getInteger(R.integer.hl_act_tag_work))
            return context.getString(R.string.hl_act_tag_work);
        else if (key == res.getInteger(R.integer.hl_act_tag_motion))
            return context.getString(R.string.hl_act_tag_motion);
        else if (key == res.getInteger(R.integer.hl_act_tag_relax))
            return context.getString(R.string.hl_act_tag_relax);
        else
            return context.getString(R.string.hl_act_tag_trifle);

    }
}
