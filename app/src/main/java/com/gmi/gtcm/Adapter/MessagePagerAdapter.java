package com.gmi.gtcm.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmi.gtcm.Fragment.Chat;
import com.gmi.gtcm.Fragment.Message;

public class MessagePagerAdapter extends FragmentPagerAdapter {
    public MessagePagerAdapter(FragmentManager fms) {
        super(fms);


    }

    @Override
    public Fragment getItem(int position) {
        Fragment frags= new Fragment();

        if(position==0)
        {

            frags = new Message();
        }

        if(position==1)
        {

            frags = new Chat();
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
            ch="Chat";
        }



        return ch;
    }

}

