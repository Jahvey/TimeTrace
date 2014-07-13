package com.atomu.timetrace.location;

import android.content.Context;
import android.content.res.Resources;

import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/13.
 * transfer constant
 */
public class LocationInfoMeta {

    public int getKeyFromTag(Context context, String tag) {
        Resources res = context.getResources();

        if (tag.equals(context.getString(R.string.location_tag_classroom)))
            return res.getInteger(R.integer.location_key_classroom);
        else if (tag.equals(context.getString(R.string.location_tag_dinning_hall)))
            return res.getInteger(R.integer.location_key_dinning_hall);
        else if (tag.equals(context.getString(R.string.location_tag_dorm)))
            return res.getInteger(R.integer.location_key_dorm);
        else if (tag.equals(context.getString(R.string.location_tag_home)))
            return res.getInteger(R.integer.location_key_home);
        else if (tag.equals(context.getString(R.string.location_tag_lab)))
            return res.getInteger(R.integer.location_key_lab);
        else if (tag.equals(context.getString(R.string.location_tag_library)))
            return res.getInteger(R.integer.location_key_library);
        else if (tag.equals(context.getString(R.string.location_tag_office)))
            return res.getInteger(R.integer.location_key_office);
        else if (tag.equals(context.getString(R.string.location_tag_restaurant)))
            return res.getInteger(R.integer.location_key_restaurant);
        else if (tag.equals(context.getString(R.string.location_tag_snack_bar)))
            return res.getInteger(R.integer.location_key_snack_bar);
        else if (tag.equals(context.getString(R.string.location_tag_stadium)))
            return res.getInteger(R.integer.location_key_stadium);
        else
            return res.getInteger(R.integer.location_key_unknown);

    }

    public String getTagFromKey(Context context, int key) {
        Resources res = context.getResources();

        if (key == res.getInteger(R.integer.location_key_classroom))
            return context.getString(R.string.location_tag_classroom);
        else if (key == res.getInteger(R.integer.location_key_dinning_hall))
            return context.getString(R.string.location_tag_dinning_hall);
        else if (key == res.getInteger(R.integer.location_key_dorm))
            return context.getString(R.string.location_tag_dorm);
        else if (key == res.getInteger(R.integer.location_key_home))
            return context.getString(R.string.location_tag_home);
        else if (key == res.getInteger(R.integer.location_key_lab))
            return context.getString(R.string.location_tag_lab);
        else if (key == res.getInteger(R.integer.location_key_library))
            return context.getString(R.string.location_tag_library);
        else if (key == res.getInteger(R.integer.location_key_office))
            return context.getString(R.string.location_tag_office);
        else if (key == res.getInteger(R.integer.location_key_restaurant))
            return context.getString(R.string.location_tag_restaurant);
        else if (key == res.getInteger(R.integer.location_key_snack_bar))
            return context.getString(R.string.location_tag_snack_bar);
        else if (key == res.getInteger(R.integer.location_key_stadium))
            return context.getString(R.string.location_tag_stadium);
        else
            return context.getString(R.string.location_tag_unknown);
    }
}
