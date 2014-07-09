package com.atomu.timetrace.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.atomu.timetrace.app.MainActivity;
import com.atomu.timetrace.app.R;

/**
 * Created by Atomu on 2014/7/6.
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
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        });

    }
}
