package com.example.lw.myecard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;

import com.example.lw.myecard.R;

/**
 * Created by lw on 2016/11/19.
 */

public class WebActivity extends Activity {

    private final static String LOG_TAG = "WebActivity";

    private final static String DOC_KEY = "doc_path";

    private final static String URL_PREFIX = "http://121.40.118.7";
    private final static String URL_POSTFIX = "&client_id=Device94-a1-a2-a2-45-22";

    public static void startActivity(Context context, String docPath) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(DOC_KEY, docPath);
        context.startActivity(intent);
    }



    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        String docPath = intent.getStringExtra(DOC_KEY);
        Log.d(LOG_TAG, docPath);
        String requestUrl = URL_PREFIX + docPath + URL_POSTFIX;

        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.loadUrl(requestUrl);

    }
}
