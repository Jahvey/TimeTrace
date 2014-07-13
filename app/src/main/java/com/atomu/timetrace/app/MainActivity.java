package com.atomu.timetrace.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.atomu.timetrace.location.LocationInfoMeta;
import com.atomu.timetrace.monitor.ActivityInfoMeta;
import com.atomu.timetrace.monitor.Monitor;

/**
 *
 */
public class MainActivity extends Activity {

    private RelativeLayout rl_monitor;
    private RelativeLayout rl_analyze;
    private RelativeLayout rl_setting;
    private ImageButton ib_monitor;
    private ImageButton ib_analyze;
    private ImageButton ib_setting;
    private Monitor monitor;
    private static int location = 0;
    private static int activity = 0;

    static public int getLocation(){
        return location;
    }

    static public int getActivity(){
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl_monitor = (RelativeLayout) findViewById(R.id.rl_monitor);
        rl_analyze = (RelativeLayout) findViewById(R.id.rl_analyze);
        rl_setting = (RelativeLayout) findViewById(R.id.rl_setting);
        ib_monitor = (ImageButton) findViewById(R.id.ib_monitor);
        ib_analyze = (ImageButton) findViewById(R.id.ib_analyze);
        ib_setting = (ImageButton) findViewById(R.id.ib_setting);
        ib_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_monitor.setVisibility(View.VISIBLE);
                rl_setting.setVisibility(View.GONE);
                rl_analyze.setVisibility(View.GONE);

                final Spinner sp_ask_location = new Spinner(MainActivity.this);
                final Spinner sp_ask_activity = new Spinner(MainActivity.this);
                String [] locationItems = getResources().getStringArray(R.array.location_tag_array);
                String [] activityItems = getResources().getStringArray(R.array.activity_tag_array);
                final ArrayAdapter locationAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, locationItems);
                ArrayAdapter activityAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, activityItems);
                sp_ask_location.setAdapter(locationAdapter);
                sp_ask_activity.setAdapter(activityAdapter);
                sp_ask_location.setPrompt(MainActivity.this.getString(R.string.location_tag_unknown));
                sp_ask_activity.setPrompt(MainActivity.this.getString(R.string.activity_tag_unknown));
                
                LinearLayout ll_ask = new LinearLayout(MainActivity.this);
                ll_ask.setOrientation(LinearLayout.VERTICAL);
                ll_ask.addView(sp_ask_location);
                ll_ask.addView(sp_ask_activity);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("input location & activity").setIcon(R.drawable.engine)
                        .setView(ll_ask)
                        .setPositiveButton("set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView tv_location = (TextView) sp_ask_location.getSelectedView();
                                TextView tv_activity = (TextView) sp_ask_activity.getSelectedView();
                                if (tv_location != null && tv_location.getText() != null){
                                    location = (new LocationInfoMeta()).getKeyFromTag(MainActivity.this, tv_location.getText().toString());
                                }
                                if (tv_activity != null && tv_activity.getText() != null){
                                    activity = (new ActivityInfoMeta()).getKeyFromTag(MainActivity.this, tv_activity.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("cancel", null)
                        .show();
            }
        });
        ib_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_monitor.setVisibility(View.GONE);
                rl_setting.setVisibility(View.GONE);
                rl_analyze.setVisibility(View.VISIBLE);
            }
        });
        ib_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_monitor.setVisibility(View.GONE);
                rl_setting.setVisibility(View.VISIBLE);
                rl_analyze.setVisibility(View.GONE);
            }
        });

        monitor = new Monitor(MainActivity.this, rl_monitor);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
