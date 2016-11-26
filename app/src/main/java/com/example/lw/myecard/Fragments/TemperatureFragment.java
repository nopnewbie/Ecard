package com.example.lw.myecard.Fragments;

import android.app.AlertDialog;
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

public class TemperatureFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        Button button = (Button) view.findViewById(R.id.fragment_temp_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("测量结果");
                dialog.setMessage("额温正常");
                dialog.show();
            }
        });

        return view;
    }
}
