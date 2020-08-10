package com.gmi.gtcm.Adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Activity.QuizQuestuions;
import com.gmi.gtcm.Activity.SmartQuiz;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.Quizmodel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class QuizlistAdapter extends RecyclerView.Adapter<QuizlistAdapter.MyViewHolder> {
    private SmartQuiz mContext;
    private List<Quizmodel> mylist;
    private String question;
    private String custid;
    private String quizimagepath;
    private long serid;
    private String CorrectAnswerId;
    private String questionid = "0";
    private String answerid = "0";
    private String iscorrect = "0";
    private String IsquestionAvailable;
    private String SurveyquestionId = "0";
    private String SmartQuizId = "0";
    private String SmartQuizQuestionId = "0";
    private String Correctanswer;

    String merch, mtoid, Mmsgid, opt1, opt2, opt3, opt1id, opt2id, opt3id, ans1, ans2, ans3;
    SharedPreferences sharedPreferences;

    public QuizlistAdapter(SmartQuiz ctx, List<Quizmodel> mssglist) {
        this.mylist = mssglist;
        this.mContext = ctx;

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
        myViewHolder.surveyname.setText(mylist.get(i).getSmartQuizName());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        custid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");
        if (!mylist.get(i).getSmartQuizImagepath().equals("")) {
            Picasso.with(mContext).load(mylist.get(i).getSmartQuizImagepath()).into(myViewHolder.surveyimage);
        }
        myViewHolder.surveygo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serid = mylist.get(i).getSmartQuizID();
                quizimagepath = mylist.get(i).getSmartQuizImagepath();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("serid", String.valueOf(serid));
                editor.putString("quizimage", quizimagepath);
                editor.commit();
                Intent intent = new Intent(mContext, QuizQuestuions.class);
                mContext.startActivity(intent);

//                Offerdata();

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

            surveyimage = (ImageView) itemView.findViewById(R.id.surveyslistimageid);
            surveyname = (TextView) itemView.findViewById(R.id.surveylistnameid);
            surveygo = (ImageView) itemView.findViewById(R.id.surveygoid);


        }
    }

    public void Offerdata() {
       final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage("pleasewait");
        pd.show();

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String url = "http://151.106.38.222:92/api/InsertCustomerSmartQuizAnswer?sqid=" + serid + "&cid=" + custid + "&qid=0&aid=0&IsCorrect=0&Duration=0";
        StringRequest movieReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("c", response.toString());

                        try {
                            JSONObject obj = new JSONObject(response);
                            int IsFinished = obj.getInt("IsFinished");
                            if (IsFinished == 0) {
                                String SurveyQuestion = obj.getString("SmartQuizQuestion");
                                JSONObject SurveyQuestionobj = new JSONObject(SurveyQuestion);
                                question = SurveyQuestionobj.getString("Question");
                                CorrectAnswerId = SurveyQuestionobj.getString("CorrectAnswerId");
                                SmartQuizId = SurveyQuestionobj.getString("SmartQuizId");
                                Correctanswer = SurveyQuestionobj.getString("Correctanswer");
                                SmartQuizQuestionId = SurveyQuestionobj.getString("SmartQuizQuestionId");
                                IsquestionAvailable = SurveyQuestionobj.getString("IsquestionAvailable");
                                String answers = SurveyQuestionobj.getString("answers");
                                JSONArray arry = new JSONArray(answers);
                                try {
                                    JSONObject option1 = arry.getJSONObject(0);
                                    opt1 = option1.getString("AnswerImagePath");
                                    opt1id = option1.getString("SmartQuizAnswerId");
                                    ans1 = option1.getString("AnswerNumber");
                                    Log.d("opt1image", " opt1image : " + opt1);

                                    JSONObject option2 = arry.getJSONObject(1);
                                    opt2 = option2.getString("AnswerImagePath");
                                    opt2id = option2.getString("SmartQuizAnswerId");
                                    ans2 = option2.getString("AnswerNumber");
                                    Log.d("opt2image", " opt2image : " + opt2);

                                    JSONObject option3 = arry.getJSONObject(2);
                                    opt3 = option3.getString("AnswerImagePath");
                                    opt3id = option3.getString("SmartQuizAnswerId");
                                    ans3 = option3.getString("AnswerNumber");
                                    Log.d("opt3image", " opt3image : " + opt3);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("opt1", opt1);
                                editor.putString("opt2", opt2);
                                editor.putString("opt3", opt3);
                                editor.putString("serid", String.valueOf(serid));
                                editor.putString("quizimage", quizimagepath);
                                editor.putString("questions", question);
                                editor.putString("CorrectAnswerId", CorrectAnswerId);
                                editor.putString("SmartQuizId", SmartQuizId);
                                editor.putString("SmartQuizQuestionId", SmartQuizQuestionId);
                                editor.putString("Correctanswer", Correctanswer);
                                editor.putString("IsquestionAvailable", IsquestionAvailable);
                                editor.putString("opt1id", opt1id);
                                editor.putString("opt2id", opt2id);
                                editor.putString("opt3id", opt3id);
                                editor.putString("ans1", ans1);
                                editor.putString("ans2", ans2);
                                editor.putString("ans3", ans3);
                                editor.commit();


                                Intent intent = new Intent(mContext, QuizQuestuions.class);
                                mContext.startActivity(intent);

                            } else {
                                Toast.makeText(mContext, "Your Quiz Completed", Toast.LENGTH_LONG).show();

                            }
                            pd.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(mContext,"Error Occured",Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
pd.dismiss();
                    }
                });
        requestQueue.add(movieReq);
    }


}

