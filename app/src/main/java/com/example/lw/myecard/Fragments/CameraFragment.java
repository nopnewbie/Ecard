package com.example.lw.myecard.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lw.myecard.R;

/**
 * Created by lw on 2016/11/26.
 */

public class CameraFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        Button button = (Button) view.findViewById(R.id.take_picture_button);
        /**
         * here you should take a picture and post it to the server
         * */
        return view;
    }
}
