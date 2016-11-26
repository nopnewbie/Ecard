package com.example.lw.myecard.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lw.myecard.Fragments.CameraFragment;
import com.example.lw.myecard.Fragments.CoursesFragment;
import com.example.lw.myecard.Fragments.EmotionFragment;
import com.example.lw.myecard.Fragments.MessageFragment;
import com.example.lw.myecard.Fragments.PhoneFragment;
import com.example.lw.myecard.Fragments.TemperatureFragment;
import com.example.lw.myecard.R;

/**
 * Created by lw on 2016/11/26.
 */

public class ShowFragments extends Activity {

    private static final String FRAG_INDEX_KEY = "fragment_index_to_start";

    public static final int FRAG_EMOTION = 0;
    public static final int FRAG_TEMP = 1;
    public static final int FRAG_MSG = 2;
    public static final int FRAG_PHONE = 3;
    public static final int FRAG_CAMERA = 4;
    public static final int FRAG_COURSES = 5;


    public static void start(Context context, int indexOfFragment) {
        Intent intent = new Intent(context, ShowFragments.class);
        intent.putExtra(FRAG_INDEX_KEY, indexOfFragment);
        context.startActivity(intent);
    }

    private final Fragment[] mFragments = {new EmotionFragment(),new TemperatureFragment(), new MessageFragment(), new PhoneFragment(), new CameraFragment(), new CoursesFragment()};
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    /**
     * Initialize mFragmentManager, mCurrentFragment
     * */
    private void init() {
        Intent intent = getIntent();
        mFragmentManager = getFragmentManager();
        switch2Fragment(intent.getIntExtra(FRAG_INDEX_KEY, FRAG_EMOTION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fragments);

        //Initialize mFragmentManager, mCurrentFragment
        init();

        Button button1 = (Button) findViewById(R.id.show_emotion_frag_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Fragment(FRAG_EMOTION);
            }
        });

        Button button2 = (Button) findViewById(R.id.show_temp_frag_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Fragment(FRAG_TEMP);
            }
        });

        Button button3 = (Button) findViewById(R.id.show_msg_frag_button);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Fragment(FRAG_MSG);
            }
        });

        Button button4 = (Button) findViewById(R.id.show_phone_frag_button);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Fragment(FRAG_PHONE);
            }
        });

        Button button5 = (Button) findViewById(R.id.show_camera_frag_button);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Fragment(FRAG_CAMERA);
            }
        });

        Button button6 = (Button) findViewById(R.id.show_course_frag_button);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Fragment(FRAG_COURSES);
            }
        });

        Button button7 = (Button) findViewById(R.id.exit_frag_button);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void switch2Fragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if(null != mCurrentFragment) {
            transaction.hide(mCurrentFragment);
        }
        if(mFragments[index].isAdded()) {
            transaction.show(mFragments[index]);
        } else {
            transaction.add(R.id.fragment_container, mFragments[index]);
        }
        mCurrentFragment = mFragments[index];
        transaction.commit();
    }
}
