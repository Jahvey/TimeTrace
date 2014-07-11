package com.atomu.timetrace.effect;

import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Atomu on 2014/7/11.
 * this listener will hide and display a title view with a list scrolling
 */
public class TitleScrollListener implements AbsListView.OnScrollListener {
    private int lastPosition = 0;
    private View title;

    public TitleScrollListener(View v) {
        super();
        setTitle(v);
    }

    public void setTitle(View t) {
        this.title = t;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        switch (i) {
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                lastPosition = absListView.getLastVisiblePosition();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                if (absListView.getLastVisiblePosition() < lastPosition) {
                    title.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }
}
