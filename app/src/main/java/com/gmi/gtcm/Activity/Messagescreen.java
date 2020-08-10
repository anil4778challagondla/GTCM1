package com.gmi.gtcm.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gmi.gtcm.Adapter.MessagePagerAdapter;
import com.gmi.gtcm.R;


public class Messagescreen extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagescreen);
        setActionBarTitle();
        MessagePagerAdapter pager= new MessagePagerAdapter(getSupportFragmentManager());
        tabLayout= findViewById(R.id.MyTabmessage);
        viewPager= findViewById(R.id.myViewPagermessage);
        viewPager.setAdapter(pager);


        tabLayout.setupWithViewPager(viewPager);
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Messages");
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
