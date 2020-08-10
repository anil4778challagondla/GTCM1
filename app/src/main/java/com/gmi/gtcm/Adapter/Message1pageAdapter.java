package com.gmi.gtcm.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmi.gtcm.Fragment.AllMessages;
import com.gmi.gtcm.Fragment.Photos;
import com.gmi.gtcm.Fragment.PostNewmessage;
import com.gmi.gtcm.Fragment.Videos;

public class Message1pageAdapter extends FragmentPagerAdapter {
    public Message1pageAdapter(FragmentManager fms) {
        super(fms);


    }

    @Override
    public Fragment getItem(int position) {
        Fragment frags= new Fragment();

        if(position==0)
        {

            frags = new AllMessages();
        }

        if(position==1)
        {

            frags = new PostNewmessage();
        }

        return frags;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        CharSequence ch="";

        if(position==0)
        {
            ch="Messages";

        }
        if(position==1)
        {
            ch="Post Message";
        }



        return ch;
    }

}
