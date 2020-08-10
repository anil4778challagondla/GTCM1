package com.gmi.gtcm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmi.gtcm.Activity.Groupspage;
import com.gmi.gtcm.Model.NoticeModel;
import com.gmi.gtcm.Model.PhotosModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {
    private Context mContext;
    private List<NoticeModel> mylist;




    public NoticeAdapter(Context context, List<NoticeModel> mylist) {
        this.mylist = mylist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.noticecustomlist, viewGroup, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(mylist.get(i).getCountryName());
        if (!mylist.get(i).getCountryImagePath().equals("")) {
            Picasso.with(mContext).load(mylist.get(i).getCountryImagePath()).into(myViewHolder.photoimage);
        }
        myViewHolder.photoimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Groupspage.class);
                intent.putExtra("countryid",mylist.get(i).getCountryId());


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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            photoimage=(ImageView)itemView.findViewById(R.id.countyflagivid);
            name=(TextView) itemView.findViewById(R.id.countrynametvid);
        }
    }



}
