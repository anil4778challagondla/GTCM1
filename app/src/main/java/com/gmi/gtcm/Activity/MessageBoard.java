package com.gmi.gtcm.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.EventsAdapter;
import com.gmi.gtcm.Adapter.Message1pageAdapter;
import com.gmi.gtcm.Adapter.MessageAdapter;
import com.gmi.gtcm.Adapter.Myprofileadapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.CheckConnection;
import com.gmi.gtcm.Model.EventsModel;
import com.gmi.gtcm.Model.MessageModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageBoard extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);
        setActionBarTitle();
        tabLayout= findViewById(R.id.MyTabmessage1);
        viewPager= findViewById(R.id.myViewPagermessage1);
        Message1pageAdapter pager= new Message1pageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pager);
        tabLayout.setupWithViewPager(viewPager);
        connection();
        ImageView home = (ImageView) findViewById(R.id.homebtnid);
        ImageView profile = (ImageView) findViewById(R.id.profilebtnid);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Myprofile.class));
                finish();

            }
        });


    }
    @SuppressLint("WrongConstant")
    public void connection(){
        if(CheckConnection.isInternetAvailable(MessageBoard.this)) //returns true if internet available
        {

        }
        else
        {
            Toast.makeText(MessageBoard.this,"No Internet Connection",1000).show();
        }
    }

    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Message Board");

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPause() {
        super.onPause();
        connection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        connection();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connection();
    }
}
