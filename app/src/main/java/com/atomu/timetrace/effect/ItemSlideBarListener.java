package com.atomu.timetrace.effect;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Atomu on 2014/7/12.
 * hide and show a button with sliding an item
 */
public class ItemSlideBarListener implements View.OnTouchListener {
    private View bar;
    private float position = 0;

    public ItemSlideBarListener(View view) {
        bar = view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                position = motionEvent.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() < position) {
                    bar.setVisibility(View.VISIBLE);
                } else {
                    bar.setVisibility(View.GONE);
                }
                break;
        }
        return true;
    }
}
