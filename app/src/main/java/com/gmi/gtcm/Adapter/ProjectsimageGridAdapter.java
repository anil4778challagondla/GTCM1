package com.gmi.gtcm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmi.gtcm.Model.Projectsimagelistmodel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProjectsimageGridAdapter extends RecyclerView.Adapter<ProjectsimageGridAdapter.MyViewHolder> {
    private Context mContext;
    private List<Projectsimagelistmodel> mylist;
    String custid;




    public ProjectsimageGridAdapter(Context mContext, List<Projectsimagelistmodel> mylist) {
        this.mContext = mContext;
        this.mylist = mylist;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.projectsgridcustomdesign,viewGroup,false);return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
if (!mylist.get(i).getImgpath().equals("")) {
    Picasso.with(mContext).load(mylist.get(i).getImgpath()).into(myViewHolder.goodsimage);
}
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsimage = (ImageView) itemView.findViewById(R.id.customimagegrid);

        }
    }



}
