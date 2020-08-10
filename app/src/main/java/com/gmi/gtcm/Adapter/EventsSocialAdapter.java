package com.gmi.gtcm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.gmi.gtcm.Activity.Mywebview;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.SocialMediaModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class EventsSocialAdapter extends RecyclerView.Adapter<EventsSocialAdapter.MyViewHolder> {
    private Context mContext;
    private List<SocialMediaModel> mylist;
    Bitmap bmThumbnail;


    public EventsSocialAdapter(List<SocialMediaModel> movieList, Context applicationContext) {
        this.mylist = movieList;
        this.mContext = applicationContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.socialcustomlistevents, viewGroup, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        if (!mylist.get(i).getSocialMediaLogopath().equals("")) {
            Picasso.with(mContext).load(mylist.get(i).getSocialMediaLogopath()).into(myViewHolder.social);
        }
        myViewHolder.social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = mylist.get(i).getSocialMediaLink().trim();
                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setData(Uri.parse(link));
                Intent chooserIntent = Intent.createChooser(sharingIntent, "Open With");
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(chooserIntent);
//
            }
        });

    }

    @Override
    public int getItemCount() {

        return mylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView social;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            social = (ImageView) itemView.findViewById(R.id.socialcustiomivid);
        }
    }

    private void navigateToPage(String link, int type) {
        Intent webViewIntent = new Intent(mContext, Mywebview.class);
        webViewIntent.putExtra(AppUrls.INTENT_WEBVIEW_LINK, link);
        webViewIntent.putExtra(AppUrls.INTENT_SELECTION_TYPE, type);
        mContext.startActivity(webViewIntent);
    }

}
