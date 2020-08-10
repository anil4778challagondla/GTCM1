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


import com.gmi.gtcm.Activity.Survey;
import com.gmi.gtcm.Activity.SurveyQuestions;
import com.gmi.gtcm.Activity.TextQuiz;
import com.gmi.gtcm.Activity.TextQuizQuestions;
import com.gmi.gtcm.Model.Surveylistmodel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SurveylistAdapter extends RecyclerView.Adapter<SurveylistAdapter.MyViewHolder> {
    private Context mContext;
    private List<Surveylistmodel> mylist;

    String custid,merch,mtoid,Mmsgid , type;
    SharedPreferences sharedPreferences;

    public SurveylistAdapter(Context ctx, List<Surveylistmodel> mssglist,String type) {
        this.mylist = mssglist;
        this.mContext = ctx;
        this.type = type;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.surveylistcustom, viewGroup, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.surveyname.setText(mylist.get(i).getSurveyName());
if (!mylist.get(i).getSurveyimage().equals("")){
    Picasso.with(mContext).load(mylist.get(i).getSurveyimage()).into(myViewHolder.surveyimage);
}

        myViewHolder.surveygo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("survey")){
                    Intent intent=new Intent(mContext, SurveyQuestions.class);
                    intent.putExtra("serid",mylist.get(i).getSurveyId());
                    mContext.startActivity(intent);
                }else {
                    Intent intent=new Intent(mContext, TextQuizQuestions.class);
                    intent.putExtra("serid",mylist.get(i).getSurveyId());
                    mContext.startActivity(intent);
                }

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
