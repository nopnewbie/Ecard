package com.example.lw.myecard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lw.myecard.R;

/**
 * Created by lw on 2016/11/22.
 */

public class CheckActivity extends Activity {

    public final static String CHECK_STATUS_KEY = "check_status";
    public static void start(Context context, String checkStatus) {
        Intent intent = new Intent(context, CheckActivity.class);
        intent.putExtra(CHECK_STATUS_KEY, checkStatus);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent = getIntent();
        String checkStatus = intent.getStringExtra(CHECK_STATUS_KEY);

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(checkStatus);
    }
}
