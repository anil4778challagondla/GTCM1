package com.gmi.gtcm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gmi.gtcm.Activity.GameDetails;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.Gamesmodel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Gamesmodel> mylist;
    String custid;
    SharedPreferences sharedPreferences;


    public GamesAdapter(Context mContext, List<Gamesmodel> mylist) {
        this.mContext = mContext;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.offerscustomlayout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        custid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");

        myViewHolder.goodsdealprice.setVisibility(View.GONE);
        myViewHolder.goodsellingprice.setVisibility(View.GONE);
        myViewHolder.desc.setVisibility(View.GONE);
        myViewHolder.partnerimage.setVisibility(View.GONE);
        myViewHolder.goodsname.setText(mylist.get(i).getGameTitle());
        if (!mylist.get(i).getGameImagepath().equals("")) {
            Picasso.with(mContext).load(mylist.get(i).getGameImagepath()).placeholder(R.drawable.logo).into(myViewHolder.goodsimage);

        }
        myViewHolder.offerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GameDetails.class);

                intent.putExtra("gamename", mylist.get(i).getGameTitle());
                intent.putExtra("gamedesc", mylist.get(i).getGameDescription());
                intent.putExtra("gameid", mylist.get(i).getGameId());
                intent.putExtra("clientid", mylist.get(i).getClientId());
                intent.putExtra("bussinessid", mylist.get(i).getBusinessId());
                intent.putExtra("conditon", mylist.get(i).getConditionsApply());
                intent.putExtra("image", mylist.get(i).getGameImagepath());
                intent.putExtra("scratchimage", mylist.get(i).getScratchImagePath());

                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView goodsimage;
        CardView partnerimage;
        TextView goodsname, goodsdealprice, goodsellingprice, desc;
        LinearLayout offerlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            goodsimage = itemView.findViewById(R.id.offersimageid);
            partnerimage = itemView.findViewById(R.id.partnerimagcrad);
            goodsname = (TextView) itemView.findViewById(R.id.offernameid);
            goodsdealprice = (TextView) itemView.findViewById(R.id.offerretailerpriceid);
            goodsellingprice = (TextView) itemView.findViewById(R.id.offersellingpriceid);
            offerlayout = (LinearLayout) itemView.findViewById(R.id.offerlayoutid);
            desc = (TextView) itemView.findViewById(R.id.offerdescid);


        }
    }
}




