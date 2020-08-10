package com.gmi.gtcm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gmi.gtcm.Activity.PromotionDetails;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.OfferModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GoodsrecyclerAdapter extends RecyclerView.Adapter<GoodsrecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<OfferModel> mylist;
    String custid;
    SharedPreferences sharedPreferences;

    ArrayList<OfferModel> arraylist;

    public List<OfferModel> tempParkingList;

    public GoodsrecyclerAdapter(Context mContext, List<OfferModel> mylist) {
        this.mContext = mContext;
        this.mylist = mylist;
        arraylist = new ArrayList<OfferModel>();
        arraylist.addAll(mylist);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater= LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.offerscustomlayout,viewGroup,false);return new MyViewHolder(view);
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");


        myViewHolder.goodsdealprice.setText("$"+mylist.get(i).getRetailPrice());
        myViewHolder.goodsdealprice.setPaintFlags(myViewHolder.goodsdealprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        myViewHolder.goodsellingprice.setText("$"+mylist.get(i).getSellingPrice());
        myViewHolder.goodsname.setText(mylist.get(i).getPromotionName());
        myViewHolder.desc.setText(mylist.get(i).getDescription());
        if (!mylist.get(i).getPromotionImagePath().equals("")){
            Picasso.with(mContext).load(mylist.get(i).getPromotionImagePath()).placeholder(R.drawable.logo).into(myViewHolder.goodsimage);

        }
        if (!mylist.get(i).getPartnerLogoPath().equals("")){
            Picasso.with(mContext).load(mylist.get(i).getPartnerLogoPath()).placeholder(R.drawable.logo).into(myViewHolder.partnerimage);

        }
            myViewHolder.offerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PromotionDetails.class);
                intent.putExtra("dealname",mylist.get(i).getPromotionName());
                intent.putExtra("dealimage",mylist.get(i).getPromotionImagePath());
                intent.putExtra("partnerimage",mylist.get(i).getPartnerLogoPath());
                intent.putExtra("disc",mylist.get(i).getDescription());
                intent.putExtra("conditions",mylist.get(i).getConditions());
                intent.putExtra("dprice",mylist.get(i).getRetailPrice());
                intent.putExtra("sellingprice",mylist.get(i).getSellingPrice());
                intent.putExtra("attributename",mylist.get(i).getAttrlist());
                intent.putExtra("bname",mylist.get(i).getBusinessName());



                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsimage,partnerimage;
        TextView goodsname,goodsdealprice,goodsellingprice,desc;
        LinearLayout offerlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsimage = (ImageView) itemView.findViewById(R.id.offersimageid);
            partnerimage = (ImageView) itemView.findViewById(R.id.partnerimage);
            goodsname = (TextView) itemView.findViewById(R.id.offernameid);
            desc = (TextView) itemView.findViewById(R.id.offerdescid);
            goodsdealprice = (TextView) itemView.findViewById(R.id.offerretailerpriceid);
            goodsellingprice = (TextView) itemView.findViewById(R.id.offersellingpriceid);
            offerlayout = (LinearLayout) itemView.findViewById(R.id.offerlayoutid);
        }
    }



}
