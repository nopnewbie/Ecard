package com.example.lw.myecard.Adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lw on 2016/11/19.
 */

public class ViewPagerAdapter extends PagerAdapter {
    List<View> mViewList;

    public ViewPagerAdapter(List<View> views) {
        mViewList = views;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int listSize = mViewList.size() == 0 ? 1 : mViewList.size();

        position %= listSize;

        if(position < 0) {
            position += listSize;
        }

        View view = mViewList.get(position);

        container.addView(view);
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        int listSize = mViewList.size() == 0 ? 1 : mViewList.size();

        position %= listSize;

        if(position < 0) {
            position += listSize;
        }

        container.removeView(mViewList.get(position % listSize));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
