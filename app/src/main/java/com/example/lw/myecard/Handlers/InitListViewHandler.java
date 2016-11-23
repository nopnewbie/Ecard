package com.example.lw.myecard.Handlers;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lw.myecard.Activities.WebActivity;
import com.example.lw.myecard.Adapters.ListViewAdapter;
import com.example.lw.myecard.JsonData.NewsJson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 2016/11/19.
 */

public class InitListViewHandler extends Handler {

    private final static int INIT_LIST_VIEW = 1;

    private WeakReference<Activity> mActivityWeakReference;

    private ArrayList<NewsJson> mNewsJsons;

    private int mListViewId;
    private int mResourceId;

    public InitListViewHandler(WeakReference<Activity> weakReference, int listViewId, int resource) {
        mActivityWeakReference = weakReference;
        mListViewId = listViewId;
        mResourceId = resource;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case INIT_LIST_VIEW:
                final Activity activity = mActivityWeakReference.get();
                if(null == activity) {     //activity has finished so no need to update UI
                    return ;
                }
                mNewsJsons = (ArrayList<NewsJson>) msg.obj;
                ListView listView = (ListView) activity.findViewById(mListViewId);
                ListViewAdapter adapter = new ListViewAdapter(activity, mResourceId, mNewsJsons);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        WebActivity.startActivity(activity, mNewsJsons.get(i).getDocumentPath());
                    }
                });

                break;

            default:
                break;
        }
    }
}
