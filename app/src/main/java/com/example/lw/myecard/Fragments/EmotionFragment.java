package com.example.lw.myecard.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lw.myecard.R;

/**
 * Created by lw on 2016/11/26.
 */

public class EmotionFragment extends Fragment {


    private final int[] mOriginalImagesId = {R.drawable.happy, R.drawable.smile, R.drawable.sad, R.drawable.cry};
    private final int[] mSelectedImagesId = {R.drawable.happy2, R.drawable.smile2, R.drawable.sad2, R.drawable.cry2};
    private int mCurrentImage = -1;

    private ImageView[] mImageViews = new ImageView[4];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emotion, container, false);

        mImageViews[0] = (ImageView) view.findViewById(R.id.emotion_happy_image_view);
        mImageViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Emotion(0);
            }
        });

        mImageViews[1] = (ImageView) view.findViewById(R.id.emotion_smile_image_view);
        mImageViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Emotion(1);
            }
        });

        mImageViews[2] = (ImageView) view.findViewById(R.id.emotion_sad_image_view);
        mImageViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Emotion(2);
            }
        });

        mImageViews[3] = (ImageView) view.findViewById(R.id.emotion_cry_image_view);
        mImageViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Emotion(3);
            }
        });

        return view;
    }

    private void switch2Emotion(int index) {

        if(mCurrentImage != -1) {
            mImageViews[mCurrentImage].setImageResource(mOriginalImagesId[mCurrentImage]);
        }
        mImageViews[index].setImageResource(mSelectedImagesId[index]);
        mCurrentImage = index;
    }

}
