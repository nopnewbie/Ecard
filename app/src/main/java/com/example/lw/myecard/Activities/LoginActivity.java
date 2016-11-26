package com.example.lw.myecard.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.lw.myecard.JsonData.CheckTimeJson;
import com.example.lw.myecard.R;
import com.example.lw.myecard.JsonData.ClassRoomJson;
import com.example.lw.myecard.Receiver.NetworkChangeReceiver;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class LoginActivity extends AppCompatActivity {

    private String mHostIp;
    private String mPortNum = "80";
    private String mMac;


    private Button mLoginButton;
    private EditText mHostIpEditText;
    private EditText mPortNumEditText;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSPEditor;

    private NetworkChangeReceiver mNetworkChangeReceiver;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //register broadcast and monitor network changes
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver(this);
        registerReceiver(mNetworkChangeReceiver, mIntentFilter);

        mSharedPreferences = getSharedPreferences(getString(R.string.login_info_file), MODE_PRIVATE);
        mSPEditor = mSharedPreferences.edit();
        String InvalidIp = getString(R.string.preference_default_ip);
        mHostIp = mSharedPreferences.getString(getString(R.string.preference_ip_key), InvalidIp);

        mMac = getMAC();

        if(!mHostIp.equals(InvalidIp)) {
            loginAndSaveData();
        }

        //get controller
        mHostIpEditText = (EditText) findViewById(R.id.ip_edit_text);
        mPortNumEditText = (EditText) findViewById(R.id.port_edit_text);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //首次登录时，SharedPreference文件为空，故要初始化再调用loginAndSaveData()
                mHostIp = mHostIpEditText.getText().toString();
                String port = mPortNumEditText.getText().toString();
                mPortNum = (null == port || port.equals("")) ? "80" : port;
                loginAndSaveData();
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkChangeReceiver);
    }

    private String getMAC() {
//        String Mac;
//        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifiManager.getConnectionInfo();
//        Mac = info.getMacAddress();
//
//        return Mac.replace(':', '-');
        return "94-a1-a2-a2-45-22"; //测试用
    }

    /***
     * Before calling this method, you must initialize mHostIp,mMac,mPortNum.
     * When login successfully, it will start MainActivity and finish this activity.
     */
    private void loginAndSaveData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://" + mHostIp + "/device/query/classroom?client_id=Device" + mMac);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    Gson gson = new Gson();
                    ClassRoomJson classroom = gson.fromJson(response.toString(), ClassRoomJson.class);
                    if (null == classroom.getClassRoom() || classroom.getClassRoom().equals("")) {
                        Toast.makeText(LoginActivity.this, "输入IP地址或端口有误!", Toast.LENGTH_SHORT).show();
                    } else {
                        String ipKey = getString(R.string.preference_ip_key);
                        mSPEditor.putString(getString(R.string.preference_ip_key), mHostIp);
                        mSPEditor.putString(getString(R.string.preference_port_key), mPortNum);
                        mSPEditor.putString(getString(R.string.preference_classroom_key), classroom.getClassRoom());
                        mSPEditor.putString(getString(R.string.preference_mac_address_key), mMac);
                        mSPEditor.commit();

                        getCheckTime();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

//    private Handler mHandler = new Handler();
//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            startNextActivity();
//        }
//    };

    private void startNextActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    /**
     * get check in and check out time and save them into file.
     * */
    private void getCheckTime() {
        final String tag = "CheckTime";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + mHostIp + "/device/query/device_config?client_id=Device" + mMac);
                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while (null != (line = reader.readLine())) {
                        response.append(line);
                    }

                    Gson gson = new Gson();
                    CheckTimeJson checkTimeJson = gson.fromJson(response.toString(), CheckTimeJson.class);

                    Log.d(tag, "" + checkTimeJson.getId());
                    Log.d(tag, checkTimeJson.getCheckinTimeStart());
                    Log.d(tag, checkTimeJson.getCheckinTimeEnd());

                    mSPEditor.putString(getString(R.string.preference_checkin_start_key), checkTimeJson.getCheckinTimeStart());
                    mSPEditor.putString(getString(R.string.preference_checkin_end_key), checkTimeJson.getCheckinTimeEnd());
                    mSPEditor.putString(getString(R.string.preference_checkout_start_key), checkTimeJson.getCheckoutTimeStart());
                    mSPEditor.putString(getString(R.string.preference_checkout_end_key), checkTimeJson.getCheckinTimeEnd());
                    mSPEditor.putString(getString(R.string.preference_checkin_photo_key), checkTimeJson.getCheckinPhoto());
                    mSPEditor.putString(getString(R.string.preference_checkin_feeling_key),checkTimeJson.getCheckinFeeling());
                    mSPEditor.putInt(getString(R.string.preference_vclassroom_key), checkTimeJson.getVclassroomCheckinSpinner());
                    mSPEditor.commit();

                    startNextActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
