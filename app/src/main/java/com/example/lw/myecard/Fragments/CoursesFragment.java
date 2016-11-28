package com.example.lw.myecard.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lw.myecard.JsonData.CoursesJson;
import com.example.lw.myecard.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 2016/11/26.
 */

public class CoursesFragment extends Fragment {

    private final static String RELATIVE_PATH = "/device/query/classroom_schedule?client_id=Device";
    private final static int[][] ITEM_ID = {
            {R.id.Mon_1_text_view, R.id.Tue_1_text_view, R.id.Wed_1_text_view, R.id.Thu_1_text_view, R.id.Fri_1_text_view},
            {R.id.Mon_2_text_view, R.id.Tue_2_text_view, R.id.Wed_2_text_view, R.id.Thu_2_text_view, R.id.Fri_2_text_view},
            {R.id.Mon_3_text_view, R.id.Tue_3_text_view, R.id.Wed_3_text_view, R.id.Thu_3_text_view, R.id.Fri_3_text_view},
            {R.id.Mon_4_text_view, R.id.Tue_4_text_view, R.id.Wed_4_text_view, R.id.Thu_4_text_view, R.id.Fri_4_text_view},
            {R.id.Mon_5_text_view, R.id.Tue_5_text_view, R.id.Wed_5_text_view, R.id.Thu_5_text_view, R.id.Fri_5_text_view},};

    private TextView[][] mTextViews = new TextView[5][5];

    private SharedPreferences mSharedPreferences;
    private String mHostIp;
    private String mDeviceMac;

    private String mRequestUrl;

    private FullfillTableHandler mFullfillTableHandler = new FullfillTableHandler();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.login_info_file), Context.MODE_PRIVATE);
        mHostIp = mSharedPreferences.getString(getString(R.string.preference_ip_key), getString(R.string.preference_default_ip));
        mDeviceMac = mSharedPreferences.getString(getString(R.string.preference_mac_address_key), getString(R.string.preference_default_mac));

        mRequestUrl = "http://" + mHostIp + RELATIVE_PATH + mDeviceMac;

        View view = inflater.inflate(R.layout.fragment_course, container, false);

        for(int i = 0; i < ITEM_ID.length; ++i) {
            for(int j = 0; j < ITEM_ID[i].length; ++j) {
                mTextViews[i][j] = (TextView) view.findViewById(ITEM_ID[i][j]);
            }
        }

        parseCourses();

        return view;
    }

    private void parseCourses() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(mRequestUrl);
                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while(null != (line = reader.readLine())) {
                        response.append(line);
                    }

                    Gson gson = new Gson();
                    ArrayList<CoursesJson> coursesJsons = gson.fromJson(response.toString(), new TypeToken<List<CoursesJson>>(){}.getType());

                    Message msg = mFullfillTableHandler.obtainMessage();
                    msg.what = FullfillTableHandler.PARESD_OK;
                    msg.obj = coursesJsons;
                    msg.sendToTarget();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private class FullfillTableHandler extends Handler {

        public static final int PARESD_OK = 1;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PARESD_OK:
                    ArrayList<CoursesJson> coursesJsons = (ArrayList<CoursesJson>) msg.obj;
                    for(int i = 0; i < 5 && i < coursesJsons.size(); ++i) {
                        CoursesJson data = coursesJsons.get(i);
                        mTextViews[i][0].setText(data.getMonday());
                        mTextViews[i][1].setText(data.getTuesday());
                        mTextViews[i][2].setText(data.getWednesday());
                        mTextViews[i][3].setText(data.getThursday());
                        mTextViews[i][4].setText(data.getFriday());
                    }
                    break;

                default:
                    break;
            }
        }
    }

}
