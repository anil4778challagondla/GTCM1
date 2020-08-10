package com.gmi.gtcm.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmi.gtcm.Fragment.AllMessages;
import com.gmi.gtcm.Fragment.CommiteMembers;
import com.gmi.gtcm.Fragment.PostNewmessage;

public class Mesage2pageAdpater extends FragmentPagerAdapter {
    public Mesage2pageAdpater(FragmentManager fms) {
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
        if(position==2)
        {

            frags = new CommiteMembers();
        }

        return frags;
    }

    @Override
    public int getCount() {
        return 3;
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
        if(position==2)
        {
            ch="Community Message";
        }


        return ch;
    }

}
