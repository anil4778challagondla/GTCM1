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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmi.gtcm.Activity.Chatscreen;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Interface.FriendInterface;
import com.gmi.gtcm.Model.ChatModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private Context mContext;
    private List<ChatModel> mylist;
    private FriendInterface mInterface;
    String custid;
    SharedPreferences sharedPreferences;

    ArrayList<ChatModel> arraylist;

    public List<ChatModel> tempParkingList;

    public ChatAdapter(Context mContext, List<ChatModel> mylist) {
        this.mContext = mContext;
        this.mylist = mylist;
        arraylist = new ArrayList<ChatModel>();
        arraylist.addAll(mylist);

    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.chatlistcustomdesign, viewGroup, false);

        return new ChatAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatAdapter.MyViewHolder myViewHolder, final int i) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        custid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");
        myViewHolder.profilename.setText(mylist.get(i).getName());
        myViewHolder.posttime.setText(mylist.get(i).getLastMessageDatestring());
        myViewHolder.postmessage.setText(mylist.get(i).getMessageText());


        Picasso.with(mContext).load(mylist.get(i).getProfileImage()).into(myViewHolder.profileimage);
        myViewHolder.chatcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Chatscreen.class);
                String chatid= String.valueOf(mylist.get(i).getChatID());
                String fnid= String.valueOf(mylist.get(i).getCustomerID());
                intent.putExtra("chatid",chatid);
                intent.putExtra("customerid",fnid);
                intent.putExtra("name",mylist.get(i).getName());
                intent.putExtra("image",mylist.get(i).getProfileImage());
                mContext.startActivity(intent);
            }
        });


    }

    public List<ChatModel> getList() {
        return mylist;
    }

    public void setTempParkingList(List<ChatModel> list) {
        tempParkingList = list;
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profileimage;
        LinearLayout chatcard;


        TextView profilename,posttime,postmessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            postmessage = (TextView) itemView.findViewById(R.id.chatmessagetvid);
            profilename = (TextView) itemView.findViewById(R.id.chatprofilenametvid);
            posttime = (TextView) itemView.findViewById(R.id.chattimetvid);
            chatcard = (LinearLayout) itemView.findViewById(R.id.chatcard);
            profileimage=(ImageView) itemView.findViewById(R.id.chatcusprofileimageid);


        }
    }

}


