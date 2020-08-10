package com.gmi.gtcm.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmi.gtcm.Fragment.Photos;
import com.gmi.gtcm.Fragment.Videos;



public class Myprofileadapter extends FragmentPagerAdapter {
    public Myprofileadapter(FragmentManager fms) {
        super(fms);


    }

    @Override
    public Fragment getItem(int position) {
        Fragment frags= new Fragment();

        if(position==0)
        {

            frags = new Photos();
        }

        if(position==1)
        {

            frags = new Videos();
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
            ch="Photos";

        }
        if(position==1)
        {
            ch="Videos";
        }



        return ch;
    }
}