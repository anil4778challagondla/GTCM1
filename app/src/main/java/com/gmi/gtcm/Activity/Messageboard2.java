package com.gmi.gtcm.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gmi.gtcm.Adapter.Mesage2pageAdpater;
import com.gmi.gtcm.Adapter.Message1pageAdapter;
import com.gmi.gtcm.CheckConnection;
import com.gmi.gtcm.R;

public class Messageboard2 extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageboard2);
        setActionBarTitle();
        tabLayout= findViewById(R.id.MyTabmessage2);
        viewPager= findViewById(R.id.myViewPagermessage2);
        Mesage2pageAdpater pager= new Mesage2pageAdpater(getSupportFragmentManager());
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
        if(CheckConnection.isInternetAvailable(Messageboard2.this)) //returns true if internet available
        {

        }
        else
        {
            Toast.makeText(Messageboard2.this,"No Internet Connection",1000).show();
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
