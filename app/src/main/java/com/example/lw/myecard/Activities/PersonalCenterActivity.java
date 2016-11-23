package com.example.lw.myecard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.lw.myecard.R;

/**
 * Created by lw on 2016/11/22.
 */

public class PersonalCenterActivity extends Activity {
    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalCenterActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
    }
}
