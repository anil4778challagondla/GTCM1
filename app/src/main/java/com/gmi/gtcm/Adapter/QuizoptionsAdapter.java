package com.gmi.gtcm.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmi.gtcm.Activity.QuizQuestuions;
import com.gmi.gtcm.Activity.SmartQuiz;
import com.gmi.gtcm.Model.Quizmodel;
import com.gmi.gtcm.Model.SurveyQuestionModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import in.codeshuffle.scratchcardlayout.listener.ScratchListener;
import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;

import static com.gmi.gtcm.Activity.QuizQuestuions.drawableFromUrl;

public class QuizoptionsAdapter extends RecyclerView.Adapter<QuizoptionsAdapter.MyViewHolder> implements ScratchListener {
    private QuizQuestuions mContext;
    private List<SurveyQuestionModel> mylist;
    String custid,merch,mtoid,Mmsgid;
    SharedPreferences sharedPreferences;

    public QuizoptionsAdapter(QuizQuestuions ctx, List<SurveyQuestionModel> mssglist) {
        this.mylist = mssglist;
        this.mContext = ctx;

    }

    @NonNull
    @Override
    public QuizoptionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.customscratchcard, viewGroup, false);



        return new QuizoptionsAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull QuizoptionsAdapter.MyViewHolder myViewHolder, final int i) {

        Drawable d;
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            d = drawableFromUrl(mylist.get(i).getAnswerImagePath());

        } catch (IOException e) {
            d = mContext.getResources().getDrawable(R.drawable.applogo);
            e.printStackTrace();

        }
        myViewHolder.scratchCardLayout.setScratchDrawable(d);
        myViewHolder.scratchCardLayout.setScratchWidthDip(50f);
        myViewHolder.scratchCardLayout.setScratchEnabled(true);
        myViewHolder.scratchCardLayout.resetScratch();
        myViewHolder.scratchCardLayout.setScratchListener(mContext  );



    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ScratchCardLayout scratchCardLayout;
        ImageView scartchimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            scartchimage=(ImageView)itemView.findViewById(R.id.quizimageid);
            scratchCardLayout=(ScratchCardLayout) itemView.findViewById(R.id.scratchCard);



        }
    }
    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }

    public void onScratchStarted() {
    }

    public void onScratchProgress(ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent) {
        if(scratchCardLayout.equals(mylist.get(0))){

            if(atLeastScratchedPercent>=0.1){
                scratchCardLayout.onFullReveal();
                Toast.makeText(mContext,"Reaveled",Toast.LENGTH_LONG).show();
                scratchCardLayout.setScratchEnabled(false);

            }}else
                if(scratchCardLayout.equals(mylist.get(1))){

            if(atLeastScratchedPercent>=1){
                scratchCardLayout.onFullReveal();
                Toast.makeText(mContext,"Reaveled",Toast.LENGTH_LONG).show();
                scratchCardLayout.setScratchEnabled(false);

            }}else if(scratchCardLayout.equals(mylist.get(2))){

            if(atLeastScratchedPercent>=1){
                scratchCardLayout.onFullReveal();
                Toast.makeText(mContext,"Reaveled",Toast.LENGTH_LONG).show();
                scratchCardLayout.setScratchEnabled(false);

            }}

    }


    public void onScratchComplete() {

    }



}

