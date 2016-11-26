package com.example.lw.myecard.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lw.myecard.R;

/**
 * Created by lw on 2016/11/26.
 */

public class PhoneFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        /**
         * here you should get contacts from server and display them by list.
         * Click a list item can make a call.
         * */
        return view;
    }
}
