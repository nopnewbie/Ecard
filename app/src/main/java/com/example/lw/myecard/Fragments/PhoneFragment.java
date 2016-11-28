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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lw.myecard.Activities.ShowFragments;
import com.example.lw.myecard.Adapters.ListViewAdapter;
import com.example.lw.myecard.JsonData.TelNumJson;
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

public class PhoneFragment extends Fragment {

    private String mStudentId;
    private String mHostIp;
    private String mDeviceMac;
    private String mRequestUrl;

    private SharedPreferences mSharedPreferences;

    private ListView mPhoneListView;

    private PhoneListHandler mPhoneListHandler = new PhoneListHandler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.login_info_file), Context.MODE_PRIVATE);
        mHostIp = mSharedPreferences.getString(getString(R.string.preference_ip_key), getString(R.string.preference_default_ip));
        mDeviceMac = mSharedPreferences.getString(getString(R.string.preference_mac_address_key), getString(R.string.preference_default_mac));
        mStudentId = ((ShowFragments)getActivity()).getStudentId();
        mRequestUrl = "http://" + mHostIp + "/device/query/student_phone?client_id=Device" + mDeviceMac + "&student_card=" + mStudentId;
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        mPhoneListView = (ListView) view.findViewById(R.id.phone_list_view);

        downloadPhoneList();

        return view;
    }

    private void downloadPhoneList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mRequestUrl);
                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while(null != (line = reader.readLine())) {
                        response.append(line);
                    }

                    Gson gson = new Gson();
                    List<TelNumJson> telNumJsons = gson.fromJson(response.toString(), new TypeToken<List<TelNumJson>>(){}.getType());

                    Message msg = mPhoneListHandler.obtainMessage();
                    msg.what = PhoneListHandler.RECEIVED_PHONE_LIST;
                    msg.obj = telNumJsons;
                    msg.sendToTarget();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private class PhoneListHandler extends Handler {

        public static final int RECEIVED_PHONE_LIST = 1;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVED_PHONE_LIST:
                    List<TelNumJson> telNumJsons = (List<TelNumJson>) msg.obj;
                    PhoneListAdapter adapter = new PhoneListAdapter(getActivity(), R.layout.phone_list_item, telNumJsons);
                    mPhoneListView.setAdapter(adapter);
                    break;

                default:
                    break;
            }
        }
    }

    private class PhoneListAdapter extends ArrayAdapter<TelNumJson> {

        private int mResourceId;

        public PhoneListAdapter(Context context, int resource, List<TelNumJson> objects) {
            super(context, resource, objects);
            mResourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TelNumJson json = getItem(position);

            View view = null;
            ViewHolder viewHolder = null;

            if(null == convertView) {
                view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
                viewHolder = new ViewHolder();
                viewHolder.mNameTextView = (TextView) view.findViewById(R.id.contact_name_text_view);
                viewHolder.mTelNumTextView = (TextView) view.findViewById(R.id.contact_num_text_view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mNameTextView.setText("联系人：" +json.getName());
            viewHolder.mTelNumTextView.setText("电话：" + json.getTel());

            return view;
        }

        private class ViewHolder {
            public TextView mNameTextView;
            public TextView mTelNumTextView;
        }
    }

}
