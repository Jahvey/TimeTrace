package com.atomu.timetrace.location;

import android.content.Context;
import android.content.res.Resources;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/20.
 * translate between location key and tag
 */
public class LocationInfoMeta {

    public int getKeyWithTag(Context context, String tag){
        Resources res = context.getResources();

        if (tag.equals(context.getString(R.string.loc_tag_classroom)))
            return res.getInteger(R.integer.loc_tag_classroom);
        else if (tag.equals(context.getString(R.string.loc_tag_dinning)))
            return res.getInteger(R.integer.loc_tag_dinning);
        else if (tag.equals(context.getString(R.string.loc_tag_dorm)))
            return res.getInteger(R.integer.loc_tag_dorm);
        else if (tag.equals(context.getString(R.string.loc_tag_home)))
            return res.getInteger(R.integer.loc_tag_home);
        else if (tag.equals(context.getString(R.string.loc_tag_lab)))
            return res.getInteger(R.integer.loc_tag_lab);
        else if (tag.equals(context.getString(R.string.loc_tag_library)))
            return res.getInteger(R.integer.loc_tag_library);
        else if (tag.equals(context.getString(R.string.loc_tag_mall)))
            return res.getInteger(R.integer.loc_tag_mall);
        else if (tag.equals(context.getString(R.string.loc_tag_office)))
            return res.getInteger(R.integer.loc_tag_office);
        else if (tag.equals(context.getString(R.string.loc_tag_park)))
            return res.getInteger(R.integer.loc_tag_park);
        else if (tag.equals(context.getString(R.string.loc_tag_restaurant)))
            return res.getInteger(R.integer.loc_tag_restaurant);
        else if (tag.equals(context.getString(R.string.loc_tag_snack)))
            return res.getInteger(R.integer.loc_tag_snack);
        else if (tag.equals(context.getString(R.string.loc_tag_stadium)))
            return res.getInteger(R.integer.loc_tag_stadium);
        else if (tag.equals(context.getString(R.string.loc_tag_street)))
            return res.getInteger(R.integer.loc_tag_street);
        else if (tag.equals(context.getString(R.string.loc_tag_theater)))
            return res.getInteger(R.integer.loc_tag_theater);
        else
            return res.getInteger(R.integer.loc_tag_unknown);
    }

    public String getTagWithKey(Context context, int key){
        Resources res = context.getResources();

        if (key == res.getInteger(R.string.loc_tag_classroom))
            return context.getString(R.integer.loc_tag_classroom);
        else if (key == res.getInteger(R.string.loc_tag_dinning))
            return context.getString(R.integer.loc_tag_dinning);
        else if (key == res.getInteger(R.string.loc_tag_dorm))
            return context.getString(R.integer.loc_tag_dorm);
        else if (key == res.getInteger(R.string.loc_tag_home))
            return context.getString(R.integer.loc_tag_home);
        else if (key == res.getInteger(R.string.loc_tag_lab))
            return context.getString(R.integer.loc_tag_lab);
        else if (key == res.getInteger(R.string.loc_tag_library))
            return context.getString(R.integer.loc_tag_library);
        else if (key == res.getInteger(R.string.loc_tag_mall))
            return context.getString(R.integer.loc_tag_mall);
        else if (key == res.getInteger(R.string.loc_tag_office))
            return context.getString(R.integer.loc_tag_office);
        else if (key == res.getInteger(R.string.loc_tag_park))
            return context.getString(R.integer.loc_tag_park);
        else if (key == res.getInteger(R.string.loc_tag_restaurant))
            return context.getString(R.integer.loc_tag_restaurant);
        else if (key == res.getInteger(R.string.loc_tag_snack))
            return context.getString(R.integer.loc_tag_snack);
        else if (key == res.getInteger(R.string.loc_tag_stadium))
            return context.getString(R.integer.loc_tag_stadium);
        else if (key == res.getInteger(R.string.loc_tag_street))
            return context.getString(R.integer.loc_tag_street);
        else if (key == res.getInteger(R.string.loc_tag_theater))
            return context.getString(R.integer.loc_tag_theater);
        else
            return context.getString(R.integer.loc_tag_unknown);
    }
}
