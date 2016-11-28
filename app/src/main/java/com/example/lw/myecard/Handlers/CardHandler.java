package com.example.lw.myecard.Handlers;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.lw.myecard.Activities.CheckActivity;
import com.example.lw.myecard.Activities.MainActivity;
import com.example.lw.myecard.Activities.PersonalCenterActivity;
import com.example.lw.myecard.JsonData.CheckJson;
import com.example.lw.myecard.JsonData.StudentJson;
import com.example.lw.myecard.R;

import java.lang.ref.WeakReference;

/**
 * Created by lw on 2016/11/22.
 */

public class CardHandler extends Handler {
    public final static int CARD_HANDLER_FAILED = 1;
    public final static int CARD_HANDLER_SUCCESSED = 2;
    public final static int CARD_HANDLER_CHECK_IN = 3;
    public final static int CARD_HANDLER_CHECK_OUT = 4;
    public final static int CARD_HANDLER_PERSONAL_CENTER = 5;

    private WeakReference<MainActivity> mActivityWeakReference;

    private String mStudentId;

    public CardHandler(WeakReference<MainActivity> activityWeakReference) {
        mActivityWeakReference = activityWeakReference;
    }

    @Override
    public void handleMessage(Message msg) {
        MainActivity activity;
        switch (msg.what) {
            case CARD_HANDLER_FAILED:
                activity = mActivityWeakReference.get();
                if(null != activity) {
                    Toast.makeText(activity, "刷卡失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case CARD_HANDLER_SUCCESSED:
                activity = mActivityWeakReference.get();
                if(null != activity) {
                   // StudentJson studentInfo = (StudentJson) msg.obj;
                    String studentId = (String) msg.obj;
                    activity.studentCheck(studentId);
                    mStudentId = studentId;
                }
                break;

            case CARD_HANDLER_CHECK_IN:
                activity = mActivityWeakReference.get();
                if(null != activity) {
                    CheckActivity.start(activity, "签到成功", R.drawable.checkin);
                }
                break;

            case CARD_HANDLER_CHECK_OUT:
                activity = mActivityWeakReference.get();
                if(null != activity) {
                    CheckActivity.start(activity, "签退成功", R.drawable.checkout);
                }
                break;

            case CARD_HANDLER_PERSONAL_CENTER:
                activity = mActivityWeakReference.get();
                if(null != activity) {
                    /*start personal center activity*/
                    CheckJson checkJson = (CheckJson) msg.obj;
                    PersonalCenterActivity.start(activity, mStudentId, checkJson);
                }
            default:
                break;
        }
        super.handleMessage(msg);
    }
}
