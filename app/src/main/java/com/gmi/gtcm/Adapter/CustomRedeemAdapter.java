package com.gmi.gtcm.Adapter;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.gmi.gtcm.Activity.Reedem;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.Redeemmodel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomRedeemAdapter extends RecyclerView.Adapter<CustomRedeemAdapter.MyViewHolder> {
    private Reedem mContext;
    private List<Redeemmodel> mylist;
    String custid, size, colors;
    static ArrayList<String> numberlist = new ArrayList<String>();

    SharedPreferences sharedPreferences;

    ArrayList<Redeemmodel> arraylist;


    public CustomRedeemAdapter(Reedem mContext, List<Redeemmodel> mylist) {
        this.mContext = mContext;
        this.mylist = mylist;
        arraylist = new ArrayList<Redeemmodel>();
        arraylist.addAll(mylist);
    }

    @NonNull
    @Override
    public CustomRedeemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.offerscartlistcustom, viewGroup, false);
        return new CustomRedeemAdapter.MyViewHolder(view);
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void onBindViewHolder(@NonNull CustomRedeemAdapter.MyViewHolder myViewHolder, final int i) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        custid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");

        if (!mylist.get(i).getPromotionImagePath().equals("")) {
            Picasso.with(mContext).load(mylist.get(i).getPromotionImagePath()).into(myViewHolder.goodsimage);

        }

        myViewHolder.goodsname.setText(mylist.get(i).getPromotionName());
        myViewHolder.goodsellingprice.setText("$" + mylist.get(i).getItemCost());
        myViewHolder.goodsdate.setText("Date :" + mylist.get(i).getCreatedDatestring());
        String attributes = mylist.get(i).getAttributes();

        myViewHolder.goodsize.setText(mylist.get(i).getSize());
        myViewHolder.goodscolor.setText(mylist.get(i).getColor());
        numberlist.clear();
        for (int j = 0; j <= 100; j++) {
            numberlist.add("" + j);
            ArrayAdapter<String> spinnerArrayAdapter;
            spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.activity_spinner_item, numberlist);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.activity_spinner_item);

            myViewHolder.offerlayout.setVerticalScrollBarEnabled(false);
            myViewHolder.offerlayout.setAdapter(spinnerArrayAdapter);
            myViewHolder.offerlayout.setSelection(1);

            myViewHolder.offerlayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                    String catename = numberlist.get(position);

                    Log.d("statevalue", "onItemSelected: " + catename);


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsimage;
        TextView goodsname, goodsize, goodscolor, goodsdate, goodsellingprice;
        Spinner offerlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsimage = (ImageView) itemView.findViewById(R.id.offersimageid);
            goodsname = (TextView) itemView.findViewById(R.id.offercartnametvid);
            goodsellingprice = (TextView) itemView.findViewById(R.id.offercartcosttvid);
            goodsize = (TextView) itemView.findViewById(R.id.offercartsizetvid);
            goodscolor = (TextView) itemView.findViewById(R.id.offercartcolortvid);
            goodsdate = (TextView) itemView.findViewById(R.id.offercartdatetvid);
            offerlayout = (Spinner) itemView.findViewById(R.id.numbercountdropdown);
        }


    }


}
