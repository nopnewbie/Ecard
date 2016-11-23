package com.example.lw.myecard.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;

import com.example.lw.myecard.Handlers.AutoScrollHandler;
import com.example.lw.myecard.Handlers.CardHandler;
import com.example.lw.myecard.Handlers.InitListViewHandler;
import com.example.lw.myecard.Handlers.InitViewPagerHandler;
import com.example.lw.myecard.JsonData.CheckJson;
import com.example.lw.myecard.JsonData.CheckTimeJson;
import com.example.lw.myecard.JsonData.NewsJson;
import com.example.lw.myecard.JsonData.StudentJson;
import com.example.lw.myecard.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by lw on 2016/11/18.
 */

public class MainActivity extends Activity {

    private final static String DEBUG_TAG = "MainActivity";
    //send by message object to initialize a ListView object and bind Adapter to this object.
    private final static int INIT_LIST_VIEW = 1;
    //send by message object to initialize a ViewPager object and bind Adapter to this object.
    private final static int INIT_VIEW_PAGER = 2;

    private String mHostIp;
    private String mPortNum;
    private String mDeviceMac;

    //URL for requesting Json
    private String mRequestURL;

//    private ArrayList<NewsJson> mNewsJsons;
    private InitListViewHandler mInitListViewHandler;

    private InitViewPagerHandler mInitViewPagerHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInitListViewHandler = new InitListViewHandler(new WeakReference<Activity>(this), R.id.list_view, R.layout.list_item);

        mInitViewPagerHandler = new InitViewPagerHandler(new WeakReference<Activity>(this), R.layout.page_content,
                R.id.view_pager, R.id.pager_title, R.id.page_content_image_view);

        ListView listView = (ListView) findViewById(R.id.list_view);

        //get hostIp, portNum，DeviceMAC from file and combine them to mRequestURL
        getInfoFromFile();
        parseJson();


    }


    /**
     * get hostIp, DeviceMAC and portNum from login file
     * **/
    private void getInfoFromFile() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.login_info_file), MODE_PRIVATE);

        String invalidIP = getString(R.string.preference_default_ip);
        mHostIp = sharedPreferences.getString(getString(R.string.preference_ip_key), invalidIP);
        mPortNum = sharedPreferences.getString(getString(R.string.preference_port_key), "80");
        mDeviceMac = sharedPreferences.getString(getString(R.string.preference_mac_address_key), "-1");
        mRequestURL = "http://"+ mHostIp +
                "/device/query/device_content_list?client_id=Device" + mDeviceMac;

        Log.d(DEBUG_TAG, mHostIp);
        Log.d(DEBUG_TAG, mPortNum);
        Log.d(DEBUG_TAG, mDeviceMac);
        Log.d(DEBUG_TAG, mRequestURL);
    }

    /**
     *request Json data asynchronously, then parse it to NewJson objects. When finished send a message to the caller.
     * */
    private void parseJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<NewsJson> jsonDataList = new ArrayList<>();
                try {
                    URL url = new URL(mRequestURL);
                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while (null != (line = reader.readLine())) {
                        response.append(line);
                    }

                    //get json objects
                    Gson gson = new Gson();
                    jsonDataList = gson.fromJson(response.toString(),
                            new TypeToken<ArrayList<NewsJson>>() {
                            }.getType());

                    Message msg = mInitListViewHandler.obtainMessage();
                    msg.what = INIT_LIST_VIEW;
                    msg.obj = jsonDataList;
                    msg.sendToTarget();

                    msg = mInitViewPagerHandler.obtainMessage();
                    msg.what = INIT_VIEW_PAGER;
                    msg.obj = jsonDataList;
                    msg.sendToTarget();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * before using mCheckInTimeStart, mCheckInTimeEnd, mCheckOutStart, mCheckOutEnd, you should call this method first.
     * get check in/out time form file.
     * */
    private int mCheckInTimeStart;
    private int mCheckInTimeEnd;
    private int mCheckOutStart;
    private int mCheckOutEnd;
    private void getCheckTime() {


        final String defaultTime = "08:00:00";

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.login_info_file), MODE_PRIVATE);

        String checkInStart = sharedPreferences.getString(getString(R.string.preference_checkin_start_key), defaultTime);
        mCheckInTimeStart = parseTime(checkInStart);

        String checkInEnd = sharedPreferences.getString(getString(R.string.preference_checkin_end_key), defaultTime);
        mCheckInTimeEnd = parseTime(checkInEnd);

        String checkOutStart = sharedPreferences.getString(getString(R.string.preference_checkout_start_key), defaultTime);
        mCheckOutStart = parseTime(checkOutStart);

        String checkOutEnd = sharedPreferences.getString(getString(R.string.preference_checkout_end_key), defaultTime);
        mCheckOutEnd = parseTime(checkOutEnd);

    }

    /**
     *@param time the formation of this parameter should be like “xx:xx:xx”
     * @return int seconds from "00:00:00"
     * */
    private int parseTime(String time) {
        int ret;
        String[] values = time.split(":");
        ret = Integer.parseInt(values[0]) * 3600 +
                Integer.parseInt(values[1]) * 60 +
                Integer.parseInt(values[2]);
        return ret;
    }


    public void studentCheck(final String studentId) {
        //getCheckTime();   // update check in/out time

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://" + mHostIp +
                            "/device/upload/student_checkin?client_id=Device" +
                            mDeviceMac + "&student_card=" + studentId);

                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
                    StringBuilder responese = new StringBuilder();
                    String line;
                    while(null != (line = reader.readLine())) {
                        responese.append(line);
                    }

                    Gson gson = new Gson();
                    CheckJson checkJson = gson.fromJson(responese.toString(), CheckJson.class);

                    Message msg = mCardHandler.obtainMessage();
                    if(0 != checkJson.getErrorCode()) { //failed to check
                        msg.what = CardHandler.CARD_HANDLER_FAILED;
                    } else if(CheckJson.CHECK_IN.equals(checkJson.getType())) {
                        msg.what = CardHandler.CARD_HANDLER_CHECK_IN;
                    } else if(CheckJson.CHEKC_OUT.equals(checkJson.getType())){
                        msg.what = CardHandler.CARD_HANDLER_CHECK_OUT;
                    } else {
                        msg.what = CardHandler.CARD_HANDLER_PERSONAL_CENTER;
                    }

                    msg.sendToTarget();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * this part is for monitoring swipe card
     * */
    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            String id = mInputStringBuilder.toString();
            mInputStringBuilder.delete(0, mInputStringBuilder.length());
            getStudentInfo(id);
        }
    };

    private long mLastInputTime = 0;
    private static final long INPUT_DELAY = 100;
    private final StringBuilder mInputStringBuilder = new StringBuilder();
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        long delay = event.getEventTime() - mLastInputTime;
        mLastInputTime = event.getEventTime();
        mInputStringBuilder.append((char)event.getUnicodeChar());
        if(delay < INPUT_DELAY) {
            mHandler.removeCallbacks(mRunnable);
        }
        mHandler.postDelayed(mRunnable, INPUT_DELAY);
        return super.onKeyUp(keyCode, event);
    }


    private final CardHandler mCardHandler = new CardHandler(new WeakReference<MainActivity>(this));
    /**
     * Asynchronously request student information and base on the returned information start check in/out or personal center activities.
     * */
    private void getStudentInfo(final String studentId) {
        Log.d("StudentId:", studentId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://" + mHostIp + "/device/query/student?client_id=Device" + mDeviceMac +"&student_card=" + studentId);
                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while(null != (line = reader.readLine())) {
                        response.append(line);
                    }

                    Gson gson = new Gson();
                    StudentJson studentInfo = gson.fromJson(response.toString(), StudentJson.class);
                    Message msg = mCardHandler.obtainMessage();
                    if(null == studentInfo.getErrorCode() || 0 != studentInfo.getErrorCode() ) {
                        msg.what = CardHandler.CARD_HANDLER_FAILED;
                    } else {
                        msg.what = CardHandler.CARD_HANDLER_SUCCESSED;
                        msg.obj = studentId;
                    }
                    msg.sendToTarget();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
