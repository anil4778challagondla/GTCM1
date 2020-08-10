package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;

public class Mywebview extends AppCompatActivity {
    private WebView mWebView;
    private String mWebLink,userid,urlparams;
    SharedPreferences sharedPreferences;
    private int mSelectionType;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywebview);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");
        mWebView = (WebView) findViewById(R.id.WebView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.logocolor)));
        getIntentData();
        setActionBarTitle();
        mWebView.setWebViewClient(new myWebCliente());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.loadUrl(mWebLink);
    }


    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mSelectionType == 1) {
            getSupportActionBar().setTitle("facebook");
        }  else if (mSelectionType == 3) {
            getSupportActionBar().setTitle("locations");
        } else if (mSelectionType == 4) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (mSelectionType == 5) {
            getSupportActionBar().setTitle("Info");
        } else if (mSelectionType == 6) {
            getSupportActionBar().setTitle("Board of trustees");
        } else if (mSelectionType == 7) {
            getSupportActionBar().setTitle("Vision");
        } else if (mSelectionType == 8) {
            getSupportActionBar().setTitle("Mission");
        } else if (mSelectionType == 9) {
            getSupportActionBar().setTitle("Statement of Faith");
        } else if (mSelectionType == 10) {
            getSupportActionBar().setTitle("Ministry Focus");
        } else if (mSelectionType == 11) {
            getSupportActionBar().setTitle("Donate");
        } else if (mSelectionType == 12) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (mSelectionType == 13) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (mSelectionType == 14) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (mSelectionType == 15) {
            getSupportActionBar().setTitle("Back");
        } else if (mSelectionType == 16) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (mSelectionType == 17) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (mSelectionType == 18) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (mSelectionType == 19) {
            getSupportActionBar().setTitle("youtube");
        } else if (mSelectionType == 200) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else if (mSelectionType == 21) {
            getSupportActionBar().setTitle("Business");
        } else if (mSelectionType == 30) {
            getSupportActionBar().setTitle("Refresh");
        } else if (mSelectionType == 31) {
            getSupportActionBar().setTitle("Where do we start");
        } else if (mSelectionType == 32) {
            getSupportActionBar().setTitle("Trust the process");
        } else if (mSelectionType == 33) {
            getSupportActionBar().setTitle("Blog");
        } else if (mSelectionType == 34) {
            getSupportActionBar().setTitle("Home");
        } else if (mSelectionType == 35) {
            getSupportActionBar().setTitle("Ticket Booking");
        } else {
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void getIntentData() {
        mWebLink = getIntent().getExtras().getString(AppUrls.INTENT_WEBVIEW_LINK);
        mSelectionType = getIntent().getExtras().getInt(AppUrls.INTENT_SELECTION_TYPE);
    }

    private class myWebCliente extends WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mProgressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);

            }
    }

    private void hideLoader() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showLoader() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }



}
