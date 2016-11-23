package com.example.lw.myecard.Handlers;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lw on 2016/11/19.
 */

public class AutoScrollHandler extends Handler {

    private final static int FINISHED_VIEW_PAGER = 3;
    private final static int UPDATE_PAGE = 4;

    private final static int MSG_DELAY = 2000;

    private int pageIndex = 0;

    private ViewPager mViewPager;

//    private TimerTask mTimerTask = new TimerTask() {
//        @Override
//        public void run() {
//            Message msg = AutoScrollHandler.this.obtainMessage();
//            msg.what = UPDATE_PAGE;
//            msg.sendToTarget();
//        }
//    };
//
//    private Timer mTimer;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case FINISHED_VIEW_PAGER:
                mViewPager = (ViewPager) msg.obj;
                this.sendEmptyMessage(UPDATE_PAGE);
                break;

            case UPDATE_PAGE:
                if(null != mViewPager) {
                    pageIndex = (pageIndex + 1) % Integer.MAX_VALUE;
                    mViewPager.setCurrentItem(pageIndex);
                    this.sendEmptyMessageDelayed(UPDATE_PAGE,MSG_DELAY);
                }

            default:
                break;
        }
    }
}
