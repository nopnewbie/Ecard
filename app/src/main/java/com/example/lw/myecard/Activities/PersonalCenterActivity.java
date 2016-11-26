package com.example.lw.myecard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lw.myecard.JsonData.CheckJson;
import com.example.lw.myecard.JsonData.StudentJson;
import com.example.lw.myecard.R;

import org.w3c.dom.Text;

/**
 * Created by lw on 2016/11/22.
 */

public class PersonalCenterActivity extends Activity {

    private final static String STUDENT_NAME_KEY = "student_name";
    private final static String CHECK_STATUS_KEY = "check_status";

    public static void start(final Context context, CheckJson checkJson) {
        Intent intent = new Intent(context, PersonalCenterActivity.class);
        intent.putExtra(STUDENT_NAME_KEY, checkJson.getStudentName());
        intent.putExtra(CHECK_STATUS_KEY, checkJson.getType());
        context.startActivity(intent);
    }


    private TextView mCheckStatusTextView;
    private TextView mStudentNameTextView;
    private TextView mClassroomTextView;

    private ImageButton mPhoneCallImgBtn;
    private ImageButton mMessageImgBtn;
    private ImageButton mCameraImgBtn;

    private Button mTempButton;
    private Button mCoursesButton;
    private Button mEmotionButton;

    private SharedPreferences mSharedPreferences;

    private String mStudentName;
    private String mCheckStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        mSharedPreferences = getSharedPreferences(getString(R.string.login_info_file), MODE_PRIVATE);
        mClassroomTextView = (TextView) findViewById(R.id.classroom_text_view);
        String classroom = mSharedPreferences.getString(getString(R.string.preference_classroom_key) , "");
        mClassroomTextView.setText(classroom);

        Intent intent = getIntent();
        mCheckStatus = intent.getStringExtra(CHECK_STATUS_KEY);
        mCheckStatusTextView = (TextView) findViewById(R.id.check_status_text_veiw);
        if(mCheckStatus.equals(CheckJson.CHECK_IN)) {
            mCheckStatusTextView.setText("已签到");
        } else if (mCheckStatus.equals(CheckJson.CHEKC_OUT)){
            mCheckStatusTextView.setText("已签退");
        } else {
            mCheckStatusTextView.setText("未签到");
        }

        mStudentName = intent.getStringExtra(STUDENT_NAME_KEY);
        mStudentNameTextView = (TextView) findViewById(R.id.student_name_text_view);
        mStudentNameTextView.setText(mStudentName);



        mPhoneCallImgBtn = (ImageButton) findViewById(R.id.phone_image_button);
        mPhoneCallImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testButton("Phone call");
                ShowFragments.start(PersonalCenterActivity.this, ShowFragments.FRAG_PHONE);
                PersonalCenterActivity.this.finish();
            }
        });

        mMessageImgBtn = (ImageButton) findViewById(R.id.message_image_button);
        mMessageImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testButton("Message");
                ShowFragments.start(PersonalCenterActivity.this, ShowFragments.FRAG_MSG);
                PersonalCenterActivity.this.finish();
            }
        });

        mCameraImgBtn = (ImageButton) findViewById(R.id.camera_image_button);
        mCameraImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testButton("Camera");
                ShowFragments.start(PersonalCenterActivity.this, ShowFragments.FRAG_CAMERA);
                PersonalCenterActivity.this.finish();
            }
        });

        mTempButton = (Button) findViewById(R.id.temperature_button);
        mTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testButton("temperature");
                ShowFragments.start(PersonalCenterActivity.this, ShowFragments.FRAG_TEMP);
                PersonalCenterActivity.this.finish();
            }
        });

        mCoursesButton = (Button) findViewById(R.id.courses_button);
        mCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testButton("Courses");
                ShowFragments.start(PersonalCenterActivity.this, ShowFragments.FRAG_COURSES);
                PersonalCenterActivity.this.finish();
            }
        });

        mEmotionButton = (Button) findViewById(R.id.emotion_button);
        mEmotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testButton("emotion");
                ShowFragments.start(PersonalCenterActivity.this, ShowFragments.FRAG_EMOTION);
                PersonalCenterActivity.this.finish();
            }
        });

    }

    private void testButton(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
