package com.gmi.gtcm.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Interface.FriendInterface;
import com.gmi.gtcm.Model.Wallmessages;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WallmessageAdapter extends RecyclerView.Adapter<WallmessageAdapter.MyViewHolder> {
    private Context mContext;
    private List<Wallmessages> mylist;
    private FriendInterface mInterface;
    String custid;
    SharedPreferences sharedPreferences;

    ArrayList<Wallmessages> arraylist;

    public List<Wallmessages> tempParkingList;

    public WallmessageAdapter(Context mContext, List<Wallmessages> mylist) {
        this.mContext = mContext;
        this.mylist = mylist;
        arraylist = new ArrayList<Wallmessages>();
        arraylist.addAll(mylist);
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public WallmessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.wallpostcustomdesign, viewGroup, false);

        return new WallmessageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WallmessageAdapter.MyViewHolder myViewHolder, final int i) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        Picasso.with(mContext).load(mylist.get(i).getCustomerImagePath()).into(myViewHolder.profileimage);
        myViewHolder.profilename.setText(mylist.get(i).getSenderName());
        myViewHolder.posttime.setText(mylist.get(i).getFormatedPostedDate());
        String postedimage=mylist.get(i).getImagepath();
        if(postedimage.contains("http://151.106.38.222:1000//content/postmessages")){
            myViewHolder.postimage.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(mylist.get(i).getImagepath()).into(myViewHolder.postimage);
        }else {
            myViewHolder.postimage.setVisibility(View.GONE);

        }
        myViewHolder.postmessage.setText(mylist.get(i).getMessage());


    }

    public List<Wallmessages> getList() {
        return mylist;
    }

    public void setTempParkingList(List<Wallmessages> list) {
        tempParkingList = list;
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profileimage,postimage;


        TextView profilename,posttime,postmessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            postimage = (ImageView) itemView.findViewById(R.id.postedimagebycustomer);
            postmessage = (TextView) itemView.findViewById(R.id.postmessagetvid);
            profilename = (TextView) itemView.findViewById(R.id.postprofilenametvid);
            posttime = (TextView) itemView.findViewById(R.id.posttimetvid);
            profileimage=(ImageView) itemView.findViewById(R.id.postcusprofileimageid);


        }
    }

}


