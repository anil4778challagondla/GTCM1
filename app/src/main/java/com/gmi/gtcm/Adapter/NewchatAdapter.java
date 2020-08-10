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
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Interface.FriendInterface;
import com.gmi.gtcm.Model.FriendsModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewchatAdapter extends RecyclerView.Adapter<NewchatAdapter.MyViewHolder> {
        private Context mContext;
        private List<FriendsModel> mylist;
    public static ArrayList<String> selectedcids=new ArrayList<>();
    public List<String> tempselectList;
        private FriendInterface mInterface;
        String custid;
        SharedPreferences sharedPreferences;

        ArrayList<FriendsModel> arraylist;

        public List<FriendsModel> tempParkingList;

        public NewchatAdapter(Context mContext, List<FriendsModel> mylist) {
                this.mContext = mContext;
                this.mylist = mylist;
                arraylist = new ArrayList<FriendsModel>();
                arraylist.addAll(mylist);

                }

        @NonNull
        @Override
        public NewchatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view;
                LayoutInflater mInflater = LayoutInflater.from(mContext);
                view = mInflater.inflate(R.layout.friendsselectlist, viewGroup, false);

                return new NewchatAdapter.MyViewHolder(view);
                }

        @Override
        public void onBindViewHolder(@NonNull final NewchatAdapter.MyViewHolder myViewHolder, final int i) {
            selectedcids.clear();


            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            custid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");
            Picasso.with(mContext).load(mylist.get(i).getCustomerImagePath()).into(myViewHolder.profileimage);
            myViewHolder.profilename.setText(mylist.get(i).getFname()+""+mylist.get(i).getLname());
            myViewHolder.chatcard.setSelected(mylist.get(i).isSelected());
            myViewHolder.chatcard.setTag(i);
            myViewHolder.chatcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer pos=(Integer)myViewHolder.chatcard.getTag();

                    if(myViewHolder.chatcard.isSelected()){
                        myViewHolder.tick.setVisibility(View.GONE);
                        myViewHolder.chatcard.setSelected(false);
                        mylist.get(pos).setSelected(false);
                        selectedcids.remove(mylist.get(pos).getCustomerID().toString());
                        String list2 = Arrays.toString(selectedcids.toArray()).replace("[", "").replace("]", "");

                    }else {
                        myViewHolder.tick.setVisibility(View.VISIBLE);

                        myViewHolder.chatcard.setSelected(true);
                        mylist.get(pos).setSelected(true);
                        selectedcids.add(mylist.get(pos).getCustomerID().toString());
                        String list2 = Arrays.toString(selectedcids.toArray()).replace("[", "").replace("]", "");



                    }
                }
            });

        }

        public List<FriendsModel> getList() {
                return mylist;
                }
    public List<String> getitems(){
        return selectedcids;
    }

        public void setTempParkingList(List<FriendsModel> list) {
                tempParkingList = list;
                }

        @Override
        public int getItemCount() {
                return mylist.size();
                }



        public static class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView profileimage,tick;
            RelativeLayout chatcard;


            TextView profilename;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);


                profilename = (TextView) itemView.findViewById(R.id.profilenamenewchattvid);

                chatcard = (RelativeLayout) itemView.findViewById(R.id.newchatprofilecard);
                profileimage=(ImageView) itemView.findViewById(R.id.listprofileimagenewchat);
                tick=(ImageView) itemView.findViewById(R.id.statusimagenewchatid);


            }
        }

        }


