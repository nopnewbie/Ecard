package com.example.lw.myecard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lw.myecard.JsonData.NewsJson;
import com.example.lw.myecard.R;

import java.util.List;

/**
 * Created by lw on 2016/11/19.
 */

public class ListViewAdapter extends ArrayAdapter<NewsJson> {

    private int mResourceId;

    public ListViewAdapter(Context context, int resource, List<NewsJson> objects) {
        super(context, resource, objects);
        mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsJson JsonData = getItem(position);

        View view = null;
        ViewHolder viewHolder = null;

        if(null == convertView) {
            view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.setImageView((ImageView) view.findViewById(R.id.item_image));
            viewHolder.setTitle((TextView) view.findViewById(R.id.item_title));
            viewHolder.setUpdateTime((TextView) view.findViewById(R.id.item_update_time));
            view.setTag(viewHolder);
        } else  {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.getTitle().setText("标题：" + JsonData.getName());
        viewHolder.getUpdateTime().setText("更新时间: " + JsonData.getDateTime());
        Glide
                .with(getContext())
                .load(JsonData.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.getImageView());
        return view;
    }

    private class ViewHolder {
        private ImageView mImageView;
        private TextView mTitle;
        private TextView mUpdateTime;

        public TextView getTitle() {
            return mTitle;
        }

        public TextView getUpdateTime() {
            return mUpdateTime;
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public void setTitle(TextView title) {
            mTitle = title;
        }

        public void setUpdateTime(TextView updateTime) {
            mUpdateTime = updateTime;
        }

        public void setImageView(ImageView imageView) {
            mImageView = imageView;

        }

    }
}
