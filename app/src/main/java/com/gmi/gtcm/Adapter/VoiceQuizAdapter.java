package com.gmi.gtcm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmi.gtcm.Activity.SurveyQuestions;
import com.gmi.gtcm.Activity.TextQuizQuestions;
import com.gmi.gtcm.Activity.VoiceQuizQuestions;
import com.gmi.gtcm.Model.Quizmodel;
import com.gmi.gtcm.Model.Surveylistmodel;
import com.gmi.gtcm.Model.VoiceQuizModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VoiceQuizAdapter extends RecyclerView.Adapter<VoiceQuizAdapter.MyViewHolder> {
    private Context mContext;
    private List<VoiceQuizModel> mylist;

    String custid,merch,mtoid,Mmsgid , type;
    SharedPreferences sharedPreferences;

    public VoiceQuizAdapter(Context ctx, List<VoiceQuizModel> mssglist, String type) {
        this.mylist = mssglist;
        this.mContext = ctx;
        this.type = type;

    }


    @NonNull
    @Override
    public VoiceQuizAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.surveylistcustom, viewGroup, false);

        return new VoiceQuizAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull VoiceQuizAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.surveyname.setText(mylist.get(i).getQuizName());
        if (!mylist.get(i).getSpellingBeeImagepath().equals("")){
            Picasso.with(mContext).load(mylist.get(i).getSpellingBeeImagepath()).into(myViewHolder.surveyimage);
        }

        myViewHolder.surveygo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(mContext, VoiceQuizQuestions.class);
                    intent.putExtra("serid",mylist.get(i).getSpellingBeeQuizId());
                    mContext.startActivity(intent);

            }
        });




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

            surveyimage=(ImageView)itemView.findViewById(R.id.surveyslistimageid);
            surveyname=(TextView)itemView.findViewById(R.id.surveylistnameid);
            surveygo=(ImageView)itemView.findViewById(R.id.surveygoid);



        }
    }


}
