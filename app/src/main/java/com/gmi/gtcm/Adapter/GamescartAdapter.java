package com.gmi.gtcm.Adapter;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.gmi.gtcm.Model.Gamescart;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GamescartAdapter extends RecyclerView.Adapter<GamescartAdapter.MyViewHolder> {
    private FragmentActivity mContext;
    private List<Gamescart> mylist;
    String custid,merch,mtoid,Mmsgid;
    SharedPreferences sharedPreferences;

    public GamescartAdapter(FragmentActivity ctx, List<Gamescart> mssglist) {
        this.mylist = mssglist;
        this.mContext = ctx;

    }

    @NonNull
    @Override
    public GamescartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gamescartdesign, viewGroup, false);



        return new GamescartAdapter.MyViewHolder(itemView);

    }

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void onBindViewHolder(@NonNull GamescartAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.surveyname.setText(mylist.get(i).getPrizeName());
        float radius = mContext.getResources().getDimension(R.dimen.dp_50);
if (!mylist.get(i).getPrizeImagePath().equals("")) {
    Picasso.with(mContext).load(mylist.get(i).getPrizeImagePath()).into(myViewHolder.surveyimage);
}
//        myViewHolder.surveygo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(mContext, SurveyQuestions.class);
//                intent.putExtra("serid",mylist.get(i).getSurveyId());
//                mContext.startActivity(intent);
//            }
//        });




    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView surveyimage;

        TextView surveyname;
        ImageView surveygo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            surveyimage=(ImageView) itemView.findViewById(R.id.gameslistimageid);
            surveyname=(TextView)itemView.findViewById(R.id.gamelistnameid);
            surveygo=(ImageView)itemView.findViewById(R.id.gameclaimid);



        }
    }


}
