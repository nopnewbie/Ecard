package com.example.lw.myecard.Layouts;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lw.myecard.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by lw on 2016/12/2.
 */

public class TitleLayout extends LinearLayout {

    private Button mBackButton;
    private Button mSettingButton;
    private TextView mTimeTextView;

    private ClockHandler mClockHandler = new ClockHandler();

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_title, this);

        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });

        mSettingButton = (Button) findViewById(R.id.setting_button);
        mSettingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You clicked the setting button", Toast.LENGTH_SHORT).show();
            }
        });

        mTimeTextView = (TextView) findViewById(R.id.time_text_view);
        mClockHandler.sendEmptyMessage(ClockHandler.UPDATE);
    }


    private class ClockHandler extends Handler {

        public static final int UPDATE = 1;
        private static final int DELAY = 1000;
        private final String[] weekday = {"", "日", "一", "二", "三", "四", "五", "六"};

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
                    String time = String.format("%02d月 %02d日 星期%s %02d:%02d:%02d",
//                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH),
                            weekday[calendar.get(Calendar.DAY_OF_WEEK)],
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            calendar.get(Calendar.SECOND)
                            );
                    mTimeTextView.setText(time);
                    this.sendEmptyMessageDelayed(UPDATE, DELAY);
                    break;

                default:
                    break;
            }
        }
    }

}
