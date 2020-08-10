package com.gmi.gtcm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmi.gtcm.Activity.Imageviews;
import com.gmi.gtcm.Model.PhotosModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosFragmentAdapter extends RecyclerView.Adapter<PhotosFragmentAdapter.MyViewHolder> {
    private Context mContext;
    private List<PhotosModel> mylist;




    public PhotosFragmentAdapter(Context context, List<PhotosModel> mylist) {
        this.mylist = mylist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.photoscustom, viewGroup, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {
        if (!mylist.get(i).getImgpath().equals("")){
            Picasso.with(mContext).load(mylist.get(i).getImgpath()).into(myViewHolder.photoimage);

        }
           myViewHolder.photoimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Imageviews.class);
                intent.putExtra("image",mylist.get(i).getImgpath());
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            photoimage=(ImageView)itemView.findViewById(R.id.photoimageid);
        }
    }



}
