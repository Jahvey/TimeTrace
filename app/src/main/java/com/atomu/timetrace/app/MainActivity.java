package com.atomu.timetrace.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.atomu.timetrace.monitor.Monitor;


public class MainActivity extends Activity {

    private RelativeLayout rl_monitor = null;
    private RelativeLayout rl_analyze = null;
    private RelativeLayout rl_setting = null;
    private ImageButton ib_monitor = null;
    private ImageButton ib_analyze = null;
    private ImageButton ib_setting = null;
    private Monitor monitor = null;

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
                rl_analyze.setVisibility(View.GONE);
                rl_setting.setVisibility(View.GONE);
            }
        });

        ib_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_monitor.setVisibility(View.GONE);
                rl_analyze.setVisibility(View.VISIBLE);
                rl_setting.setVisibility(View.GONE);
            }
        });

        ib_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_monitor.setVisibility(View.GONE);
                rl_analyze.setVisibility(View.GONE);
                rl_setting.setVisibility(View.VISIBLE);
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
