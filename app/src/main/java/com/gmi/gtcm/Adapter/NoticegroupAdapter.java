package com.gmi.gtcm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmi.gtcm.Activity.Events;
import com.gmi.gtcm.Model.Groupsnoticesmodel;
import com.gmi.gtcm.Model.NoticeModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoticegroupAdapter extends RecyclerView.Adapter<NoticegroupAdapter.MyViewHolder> {
    private Context mContext;
    private List<Groupsnoticesmodel> mylist;




    public NoticegroupAdapter(Context context, List<Groupsnoticesmodel> mylist) {
        this.mylist = mylist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.groupscustomdesign, viewGroup, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(mylist.get(i).getGroupName());
        if (!mylist.get(i).getLogoPath().equals("")) {
            Picasso.with(mContext).load(mylist.get(i).getLogoPath()).into(myViewHolder.photoimage);
        }
        myViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Events.class);
                intent.putExtra("groupid",mylist.get(i).getGroupId());
                mContext.startActivity(intent);

            }
        });



    }
    @Override
    public int getItemCount() {

        return mylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView photoimage;
        TextView name;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            photoimage=(ImageView)itemView.findViewById(R.id.groupsiconivid);
            name=(TextView) itemView.findViewById(R.id.groupnametvid);
            layout=(LinearLayout) itemView.findViewById(R.id.grouplinerlayoutid);
        }
    }



}
