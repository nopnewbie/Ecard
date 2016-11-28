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
import android.widget.ListView;
import android.widget.TextView;

import com.example.lw.myecard.Activities.ShowFragments;
import com.example.lw.myecard.JsonData.ParentMessageJson;
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

public class MessageFragment extends Fragment {

    /**
     * example of request url:
     * http://121.40.118.7/device/query/parent_message?client_id=Device94-a1-a2-a2-45-22&student_card=3281192538
     * */

    private ListView mMsgList;

    private String mHostIp;
    private String mDeviceMac;
    private String mStudentId;
    private SharedPreferences mSharedPreferences;

    private String mRequestUrl;

    private MessageHandler mMessageHandler = new MessageHandler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.login_info_file), Context.MODE_PRIVATE);
        mHostIp = mSharedPreferences.getString(getString(R.string.preference_ip_key), getString(R.string.preference_default_ip));
        mDeviceMac = mSharedPreferences.getString(getString(R.string.preference_mac_address_key), getString(R.string.preference_default_mac));
        mStudentId = ((ShowFragments)getActivity()).getStudentId();
        mRequestUrl = "http://" + mHostIp + "/device/query/parent_message?client_id=Device" + mDeviceMac + "&student_card=" + mStudentId;

        mMsgList = (ListView) view.findViewById(R.id.messages_list_view);

        downloadMessage();


        return view;
    }

    private void downloadMessage() {
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
                    ArrayList<ParentMessageJson> msgs = gson.fromJson(response.toString(),
                            new TypeToken<ArrayList<ParentMessageJson>>(){}.getType());

                    Message msg = mMessageHandler.obtainMessage();
                    msg.what = MessageHandler.RECEIVED_MSG;
                    msg.obj = msgs;
                    msg.sendToTarget();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class MessageAdapter extends ArrayAdapter<ParentMessageJson> {

        private int mResourceId;

        public MessageAdapter(Context context, int resource, List<ParentMessageJson> objects) {
            super(context, resource, objects);
            mResourceId = resource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ParentMessageJson json = getItem(position);

            View view = null;
            ViewHolder viewHolder = null;

            if(null == convertView) {
                view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
                viewHolder = new ViewHolder();
                viewHolder.setContentTextView((TextView) view.findViewById(R.id.msg_content_text_view));
                viewHolder.setUpdateTimeTextView((TextView) view.findViewById(R.id.msg_update_time_text_view));
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                view = convertView;
            }

            viewHolder.getContentTextView().setText("消息：" + json.getMessage());
            viewHolder.getUpdateTimeTextView().setText("发送时间：" + json.getDateTime());

            return view;
        }

        private class ViewHolder {
            private TextView mContentTextView;
            private TextView mUpdateTimeTextView;

            public TextView getContentTextView() {
                return mContentTextView;
            }

            public void setContentTextView(TextView contentTextView) {
                mContentTextView = contentTextView;
            }

            public TextView getUpdateTimeTextView() {
                return mUpdateTimeTextView;
            }

            public void setUpdateTimeTextView(TextView updateTimeTextView) {
                mUpdateTimeTextView = updateTimeTextView;
            }
        }
    }



    private class MessageHandler extends Handler {

        public final static int RECEIVED_MSG = 1;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVED_MSG:
                    ArrayList<ParentMessageJson> messages = (ArrayList<ParentMessageJson>) msg.obj;
                    MessageAdapter adapter = new MessageAdapter(getActivity(), R.layout.parent_messages, messages);
                    mMsgList.setAdapter(adapter);

                    break;

                default:
                    break;
            }
        }
    }

}
