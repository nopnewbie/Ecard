package com.example.lw.myecard.Handlers;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lw.myecard.Adapters.ViewPagerAdapter;
import com.example.lw.myecard.JsonData.NewsJson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by lw on 2016/11/19.
 */

public class InitViewPagerHandler extends Handler {

    private final static int INIT_VIEW_PAGER = 2;
    private final static int UPDATE_PAGE = 3;

    private final static int MSG_DELAY = 2000;

    private WeakReference<Activity> mActivityWeakReference;

    private ArrayList<NewsJson> mNewsJsons;

    private int mViewLayoutId;
    private int mViewPagerId;
    private int mTextViewId;
    private int mImageViewId;

    private ViewPager mViewPager;

    private int pageIndex = 0;

    public InitViewPagerHandler(WeakReference<Activity> activityWeakReference, int viewLayoutId, int viewPagerId, int textViewId, int imageViewId) {
        mActivityWeakReference = activityWeakReference;
        mViewLayoutId = viewLayoutId;
        mViewPagerId = viewPagerId;
        mTextViewId = textViewId;
        mImageViewId = imageViewId;
    }

    @Override
    public void handleMessage(Message msg) {

        switch (msg.what) {
            case INIT_VIEW_PAGER:
                final Activity activity = mActivityWeakReference.get();
                if(null == activity) {
                    return;
                }

                mNewsJsons = (ArrayList<NewsJson>) msg.obj;
                ArrayList<View> viewArrayList = new ArrayList<>();
                for(NewsJson data : mNewsJsons) {
                    View view = View.inflate(activity, mViewLayoutId, null);

                    //set title
                    TextView textView = (TextView) view.findViewById(mTextViewId);
                    textView.setText(data.getName());

                    //load image
                    ImageView imageView = (ImageView) view.findViewById(mImageViewId);

                    Glide
                        .with(activity)
                        .load(data.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);

                    viewArrayList.add(view);
                }
                mViewPager = (ViewPager) activity.findViewById(mViewPagerId);
                ViewPagerAdapter adapter = new ViewPagerAdapter(viewArrayList);
                mViewPager.setAdapter(adapter);

                msg = obtainMessage();
                msg.what = UPDATE_PAGE;
                msg.sendToTarget();
                break;

            case UPDATE_PAGE:   //auto scroll to next page
                if(null != mViewPager) {
                    pageIndex = (pageIndex + 1) % Integer.MAX_VALUE;
                    mViewPager.setCurrentItem(pageIndex);

                    sendEmptyMessageDelayed(UPDATE_PAGE, MSG_DELAY);
                }
                break;

            default:
                break;
        }
    }
}
