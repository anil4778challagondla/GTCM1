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
import com.gmi.gtcm.Model.MessageModel1;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter1 extends RecyclerView.Adapter<MessageAdapter1.MyViewHolder> {
    private Context mContext;
    public List<MessageModel1> mylist;
    String custid;
    SharedPreferences sharedPreferences;
    ArrayList<MessageModel1> arraylist;
public static  final int MSG_TYPE_LEFT= 0;
    public static  final int MSG_TYPE_RIGHT= 1;
    public List<MessageModel1> tempParkingList;

    public MessageAdapter1(Context mContext, List<MessageModel1> mylist) {
        this.mContext = mContext;
        this.mylist = mylist;
        arraylist = new ArrayList<MessageModel1>();
        arraylist.addAll(mylist);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int Viewtype) {
        if(Viewtype==MSG_TYPE_RIGHT){
            View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MyViewHolder(view);
        }else {
            View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MyViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        custid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");

        if(mylist.get(i).getImagePath().equals("")){
            myViewHolder.imagemssg.setVisibility(View.GONE);
        }else {
            myViewHolder.imagemssg.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(mylist.get(i).getImagePath()).into(myViewHolder.imagemssg);
        }
        if(mylist.get(i).getMessageText().equals("")){
            myViewHolder.showmessage.setVisibility(View.GONE);
        }else {
            myViewHolder.showmessage.setVisibility(View.VISIBLE);
            myViewHolder.showmessage.setText(mylist.get(i).getMessageText());
        }
myViewHolder.date.setText(mylist.get(i).getCreatedDateString());



    }

    public List<MessageModel1> getList() {
        return mylist;
    }

    public void setTempParkingList(List<MessageModel1> list) {
        tempParkingList = list;
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {




        TextView showmessage,date;
        ImageView imagemssg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            showmessage=itemView.findViewById(R.id.showmessage);
            imagemssg=itemView.findViewById(R.id.messageimageid);
            date=itemView.findViewById(R.id.date);




        }
    }

    @Override
    public int getItemViewType(int position) {

        if(mylist.get(position).getIsSender()==MSG_TYPE_RIGHT){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;

        }
    }
}


