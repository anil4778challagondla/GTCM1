package com.gmi.gtcm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;

public class MainActivity extends AppCompatActivity {
    String userid="";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userid == "") {
                    Intent mainIntent = new Intent(MainActivity.this, Proceed.class);
                    startActivity(mainIntent);
                    finish();
                }else {
                    Intent mainIntent = new Intent(MainActivity.this, Home.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, 1500);
    }
}
