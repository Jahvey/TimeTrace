package com.atomu.timetrace.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.atomu.timetrace.app.MainActivity;
import com.atomu.timetrace.app.R;
import com.atomu.timetrace.process.ProcessTagActivity;

/**
 * Created by Atomu on 2014/7/6.
 * this activity will serve as an introduction
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        RelativeLayout rl_welcome = (RelativeLayout) findViewById(R.id.rl_welcome);
        rl_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFirstTime = isFirstTime();
                Intent intent;

                if (isFirstTime){
                    intent = new Intent(WelcomeActivity.this, ProcessTagActivity.class);
                } else {
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                }
                WelcomeActivity.this.startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });

    }

    private boolean isFirstTime(){
        SharedPreferences spf = WelcomeActivity.this.getSharedPreferences("preference", Context.MODE_PRIVATE);
        return spf.getBoolean("isFirstTime", true);
    }
}
