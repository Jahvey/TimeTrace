package com.atomu.timetrace.preference;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.atomu.timetrace.app.R;
import com.atomu.timetrace.location.LocationTagActivity;
import com.atomu.timetrace.process.ProcessTagActivity;

/**
 * Created by Atomu on 2014/7/6.
 * setting manager
 */
public class SettingManager {
    private Context context;
    private View layout;

    private Button btn_app_tag;
    private Button btn_location_tag;

    public SettingManager(final Context context, View view){
        this.context = context;
        this.layout = view;

        btn_app_tag = (Button) layout.findViewById(R.id.btn_app_tag);
        btn_location_tag = (Button) layout.findViewById(R.id.btn_location_tag);

        btn_app_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProcessTagActivity.class);
                context.startActivity(intent);
            }
        });
        btn_location_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LocationTagActivity.class);
                context.startActivity(intent);
            }
        });
    }

}
