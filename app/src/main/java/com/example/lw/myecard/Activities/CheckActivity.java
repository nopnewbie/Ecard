package com.example.lw.myecard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lw.myecard.R;

/**
 * Created by lw on 2016/11/22.
 */

public class CheckActivity extends Activity {

    public final static String CHECK_STATUS_KEY = "check_status";
    public final static String BACKGROUND_RES_ID = "background_id";

    public static void start(Context context, String checkStatus, int backgroundId) {
        Intent intent = new Intent(context, CheckActivity.class);
        intent.putExtra(CHECK_STATUS_KEY, checkStatus);
        intent.putExtra(BACKGROUND_RES_ID, backgroundId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent = getIntent();
        String checkStatus = intent.getStringExtra(CHECK_STATUS_KEY);
        int backgroundResId = intent.getIntExtra(BACKGROUND_RES_ID, R.drawable.background);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.check_activity_layout);
        linearLayout.setBackgroundResource(backgroundResId);

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(checkStatus);
    }
}
