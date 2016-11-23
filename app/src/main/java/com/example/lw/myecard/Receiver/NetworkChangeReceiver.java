package com.example.lw.myecard.Receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by lw on 2016/11/18.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String NET_VALID = "网络已连接";
    private static final String NET_INVALID = "网络已断开";

    private Activity mActivity;

    public NetworkChangeReceiver(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(null != networkInfo && networkInfo.isAvailable()) {
            Toast.makeText(context, NET_VALID, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, NET_INVALID, Toast.LENGTH_SHORT).show();
        }

    }
}
