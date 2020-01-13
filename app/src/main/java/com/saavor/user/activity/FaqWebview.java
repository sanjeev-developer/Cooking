package com.saavor.user.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.saavor.user.R;

public class FaqWebview extends BaseActivity implements View.OnClickListener {

    private WebView wv1;
    ImageView backk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_webview);

        basicfetch();

        backk= (ImageView) findViewById(R.id.tool_back_about);
        backk.setOnClickListener(this);
        String url= getIntent().getStringExtra("webdata");
        String title =getIntent().getStringExtra("titledata");
        TextView titledata = (TextView) findViewById(R.id.txt_title);
        titledata.setText(title);

        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);

        wv1.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                load_dialog.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                load_dialog.cancel();

            }

        });
    }

    @Override
    public void onClick(View v)
    {
        finish();
        }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    @Override
    public void onBackPressed() {

        finish();
    }
}