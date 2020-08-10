package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.QuizoptionsAdapter;
import com.gmi.gtcm.Adapter.ResultsAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.GridSpacingItemDecoration;
import com.gmi.gtcm.Model.ResultsModel;
import com.gmi.gtcm.Model.SurveyQuestionModel;
import com.gmi.gtcm.Quizinterface;
import com.gmi.gtcm.R;
import com.gmi.gtcm.db.DatabaseHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.codeshuffle.scratchcardlayout.listener.ScratchListener;
import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;

public class QuizQuestuions extends AppCompatActivity implements ScratchListener, Quizinterface {
    ImageView quizimage,sciv1,sciv2,sciv3;
    ScratchCardLayout scratchCardLayout1;
    ScratchCardLayout scratchCardLayout2;
    ScratchCardLayout scratchCardLayout3;
    private ProgressDialog pDialog;
    TextView question , count,gamestatus;
    ScrollView quiz_layout;
    SharedPreferences sharedPreferences;
    String opt1,opt2,opt3,opt1id,opt2id,opt3id,ans1,ans2,ans3;
    QuizoptionsAdapter adapter;
    RecyclerView quizlist;
    private ResultsAdapter adapterr;
    LinearLayoutManager mLayoutManager;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerview;
    LinearLayout timerlayout,resultslayout;
    private List<ResultsModel> ResultList = new ArrayList<ResultsModel>();
    private String custid,quizimagepath,serid,questions,CorrectAnswerId,questionid="0",answerid="0",iscorrect="0",IsquestionAvailable,SurveyquestionId="0",SmartQuizId="0",SmartQuizQuestionId="0",ansid="0";
    private final List<SurveyQuestionModel> EventList = new ArrayList<SurveyQuestionModel>();
    Drawable d,f,g;
    int i =0;
    Button nxt_btn;
    public int pos , correctanswercount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questuions);
        question=(TextView)findViewById(R.id.questiontvid) ;
        count=(TextView)findViewById(R.id.count) ;
        gamestatus=(TextView)findViewById(R.id.gamestatus) ;
        databaseHelper = new DatabaseHelper(this);
        nxt_btn=(Button) findViewById(R.id.nxt_btn) ;
        quizimage=(ImageView) findViewById(R.id.quizimageid) ;
        sciv1=(ImageView) findViewById(R.id.quizimage1id) ;
        sciv2=(ImageView) findViewById(R.id.quizimage2id) ;
        sciv3=(ImageView) findViewById(R.id.quizimage3id) ;
        timerlayout=(LinearLayout) findViewById(R.id.timerlayout) ;
        resultslayout=(LinearLayout) findViewById(R.id.resultslayout) ;
        recyclerview=(RecyclerView) findViewById(R.id.recyclerview) ;
        quiz_layout=(ScrollView) findViewById(R.id.quiz_layout) ;
        quizlist=(RecyclerView) findViewById(R.id.quizoptionsrecyclerid) ;
        scratchCardLayout1 = findViewById(R.id.scratchCard1);
        scratchCardLayout2 = findViewById(R.id.scratchCard2);
        scratchCardLayout3 = findViewById(R.id.scratchCard3);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        serid=sharedPreferences.getString("serid","");
        quizimagepath=sharedPreferences.getString("quizimage","");
        Offerdata();
        quizimagepath=sharedPreferences.getString("quizimage","");
        SmartQuizQuestionId=sharedPreferences.getString("SmartQuizQuestionId","");
        if (!quizimagepath.equals("")) {

            Picasso.with(getApplicationContext()).load(quizimagepath).fit().into(quizimage);
        }
        adapter=new QuizoptionsAdapter(this, EventList);
        adapterr=new ResultsAdapter(this, ResultList);
        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(adapterr);
        quizlist.setLayoutManager(new GridLayoutManager(this,3));
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(this, R.dimen.columnspace);
        quizlist.addItemDecoration(itemDecoration);
        quizlist.setAdapter(adapter);
        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos= Integer.parseInt(databaseHelper. getImagequizdata(serid).getImposition());
                databaseHelper.updateImagePosition(String.valueOf(pos+1),serid);
                try {
                    JSONObject ansobj = new JSONObject();
                    ansobj.put("AnswerId",answerid);
                    ansobj.put("AnswerNumber",ansid);
                    ansobj.put("IsCorrect",iscorrect);
                    try {
                        JSONArray questionarray = new JSONArray(databaseHelper.getImagequizdata(serid).getImquestions());
                        JSONObject obj = questionarray.getJSONObject(pos);
                        SmartQuizQuestionId = questionarray.getJSONObject(pos).getString("SmartQuizQuestionId");
                        String quesnum = questionarray.getJSONObject(pos).getString("QuestionNum");
                        JSONArray optionsarray = obj.getJSONArray("answers");
                        ansobj.put("QuestionId",SmartQuizQuestionId);
                        ansobj.put("QuestionNumber",quesnum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray ansarray= null;
                    if (databaseHelper.getImagequizdata(serid).getImanswer().contains("[")){
                         ansarray= new JSONArray(databaseHelper.getImagequizdata(serid).getImanswer());

                    }else {
                         ansarray = new JSONArray();
                    }
                       ansarray.put(ansobj);
                    databaseHelper.updateImageanswer(ansarray.toString(),serid);
                    Log.d("TAG", "onClick: "+ databaseHelper.getImagequizdata(serid).getImanswer());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                datarefresh(serid);

            }
        });


    }
    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }

    @Override
    public void onScratchStarted() {
    }

    @Override
    public void onScratchProgress(ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent) {
        if(scratchCardLayout.equals(scratchCardLayout1)){
            if(atLeastScratchedPercent>=0.1){
                scratchCardLayout.onFullReveal();
//                Toast.makeText(getApplicationContext(),"Reaveled",Toast.LENGTH_LONG).show();
                scratchCardLayout3.setScratchEnabled(false);
                scratchCardLayout2.setScratchEnabled(false);
                scratchCardLayout1.setScratchEnabled(false);
                if(CorrectAnswerId.equals(ans1)){
                    iscorrect="1";
                }else {
                    iscorrect="0";
                }
                answerid=opt1id;
                ansid = ans1;
                nxt_btn.setEnabled(true);

            }
        }else if(scratchCardLayout.equals(scratchCardLayout2)){
            if(atLeastScratchedPercent>=0.1){
                scratchCardLayout.onFullReveal();
//                Toast.makeText(getApplicationContext(),"Reaveled",Toast.LENGTH_LONG).show();
                scratchCardLayout3.setScratchEnabled(false);
                scratchCardLayout1.setScratchEnabled(false);
                scratchCardLayout2.setScratchEnabled(false);
                answerid=opt2id;
                if(CorrectAnswerId.equals(ans2)){
                    iscorrect="1";
                }else {
                    iscorrect="0";
                }    nxt_btn.setEnabled(true);
ansid= ans2;
            }
        }else if(scratchCardLayout.equals(scratchCardLayout3)){
            if(atLeastScratchedPercent>=0.1){
                scratchCardLayout.onFullReveal();
//                Toast.makeText(getApplicationContext(),"Reaveled",Toast.LENGTH_LONG).show();
                scratchCardLayout1.setScratchEnabled(false);
                scratchCardLayout2.setScratchEnabled(false);
                scratchCardLayout3.setScratchEnabled(false);
                answerid=opt3id;
                if(CorrectAnswerId.equals(ans3)){
                    iscorrect="1";
                }else {
                    iscorrect="0";
                }
                nxt_btn.setEnabled(true);
                answerid = ans3;

            }
        }


    }
    @Override
    public void onScratchComplete() {

    }
    @Override
    public void onclick(String sqid, String cid, String qid, String aid, String iscorrect, String duration) {

    }

    public void Offerdata(){
final ProgressDialog pd = new ProgressDialog(this);
pd.setMessage("please wait");
pd.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://151.106.38.222:92/api/GetSmartQuizStatusByCustomer?sqid="+serid+"&cid="+custid;
        Log.d("quixurl "," quizurl : "+url);
        StringRequest movieReq = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("c", response.toString());
                        try {
                            JSONObject obj = new JSONObject(response);
                            int IsFinished=obj.getInt("IsFinished");
                            int gameStatus=obj.getInt("GameStatus");
                            final String gametext=obj.getString("StatusMessage");
                            if (gameStatus==1){
                                quiz_layout.setVisibility(View.GONE);
                                timerlayout.setVisibility(View.VISIBLE);
                                resultslayout.setVisibility(View.GONE);
                                int duration=obj.getInt("StartedIn");
                                startTimer(duration*1000,gametext);
                            }else  if(IsFinished==0) {
                                quiz_layout.setVisibility(View.VISIBLE);
                                timerlayout.setVisibility(View.GONE);
                                resultslayout.setVisibility(View.GONE);

                                try {
                                    JSONObject object = new JSONObject(response);
                                    JSONArray  questionarray = object.getJSONArray("Questions");
                                    if (databaseHelper.getImagequizdata(serid) == null) {
                                        Toast.makeText(QuizQuestuions.this, "entered", Toast.LENGTH_SHORT).show();
                                        databaseHelper.insertImageQuestion(serid,questionarray.toString(),"0","0",String.valueOf(System.currentTimeMillis()));
                                    }
                                    datarefresh(serid);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                quiz_layout.setVisibility(View.GONE);
                                timerlayout.setVisibility(View.GONE);
                                resultslayout.setVisibility(View.VISIBLE);
                                JSONArray resultsdata = obj.getJSONArray("Results");
                                gamestatus.setText((obj.getString("StatusMessage")));
                                for (int i = 0; i < resultsdata.length(); i++) {
                                    try {
                                        JSONObject objj = resultsdata.getJSONObject(i);
                                        ResultsModel result=new ResultsModel();
                                        result.setCustomerName(objj.getString("CustomerName"));
                                        result.setAnsweredCount(objj.getString("CorrectAnswerCount"));
                                        result.setDurationString(objj.getString("DurationString"));
                                        result.setRank(objj.getString("Rank"));
                                        result.setSno(String.valueOf(i+1));
                                        ResultList.add(result);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    adapterr.notifyDataSetChanged();
                                }

                                Toast.makeText(getApplicationContext(),"Quiz Finished",Toast.LENGTH_LONG).show();         Toast.makeText(getApplicationContext(),"Your Quiz Completed",Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.dismiss();
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



    private void startTimer(int noOfMinutes, final String gametext) {
        CountDownTimer countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                count.setText(gametext+"\n"+hms);//set text
            }
            public void onFinish() {
                count.setText("TIME'S UP!!");
                Offerdata();
                //On finish change timer text
            }
        }.
                start();

    }

    private void datarefresh(String s) {
        nxt_btn.setEnabled(false);
        pos= Integer.parseInt(databaseHelper.getImagequizdata(serid).getImposition());
        try {
            JSONArray questionarray = new JSONArray(databaseHelper.getImagequizdata(s).getImquestions());
                     if (pos>=questionarray.length()){
                try {
                    JSONArray array = new JSONArray(databaseHelper.getImagequizdata(serid).getImanswer());
                    JSONObject result = new JSONObject();
                    result.put("QuizId",serid);
                    result.put("CustomerId",custid);
                    result.put("Duration",System.currentTimeMillis()-Long.parseLong(databaseHelper.getImagequizdata(serid).getImduration()));
                    int count =0;
                    for (int i=0 ;i<array.length();i++){
                        JSONObject obj = array.getJSONObject(i);
                        if (obj.getString("IsCorrect").equals("1")){
                            count = count+1;
                        }
                    }
                    result.put("CorrectAnsweredCount",count);
                    result.put("AnsweredCount",10);
                    result.put("Answers",array);
                    Log.d("TAG", "onClick: " +result);
                    SubmitAnswers(result.toString());
                } catch (JSONException  e) {
                    e.printStackTrace();
                }

            }else {
                JSONObject obj = questionarray.getJSONObject(pos);
                CorrectAnswerId = questionarray.getJSONObject(pos).getString("CorrectAnswerId");
                JSONArray optionsarray = obj.getJSONArray("answers");
                opt1 =  optionsarray.getJSONObject(0).getString("AnswerImagePath");
                opt2 =  optionsarray.getJSONObject(1).getString("AnswerImagePath");
                opt3 =  optionsarray.getJSONObject(2).getString("AnswerImagePath");
                opt1id =  optionsarray.getJSONObject(0).getString("SmartQuizAnswerId");
                opt2id =  optionsarray.getJSONObject(1).getString("SmartQuizAnswerId");
                opt3id =  optionsarray.getJSONObject(2).getString("SmartQuizAnswerId");
                ans1 =  optionsarray.getJSONObject(0).getString("AnswerNumber");
                ans2 =  optionsarray.getJSONObject(1).getString("AnswerNumber");
                ans3 =  optionsarray.getJSONObject(2).getString("AnswerNumber");
                question.setText(obj.getString("Question"));
                serid=sharedPreferences.getString("serid","");
//        adapter=new QuizoptionsAdapter(this, EventList);
//        adapterr=new ResultsAdapter(this, ResultList);
//        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerview.setLayoutManager(mLayoutManager);
//        recyclerview.setAdapter(adapterr);
//        quizlist.setLayoutManager(new GridLayoutManager(this,3));
//        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(this, R.dimen.columnspace);
//        quizlist.addItemDecoration(itemDecoration);
//        quizlist.setAdapter(adapter);


                if(CorrectAnswerId.contains("1")){
                    sciv1.setImageDrawable(getResources().getDrawable(R.drawable.right));
                    sciv2.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                    sciv3.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                }else
                if(CorrectAnswerId.contains("2")){
                    sciv1.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                    sciv2.setImageDrawable(getResources().getDrawable(R.drawable.right));
                    sciv3.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                }else
                if(CorrectAnswerId.contains("3")){
                    sciv1.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                    sciv2.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                    sciv3.setImageDrawable(getResources().getDrawable(R.drawable.right));
                }

                if (android.os.Build.VERSION.SDK_INT > 9)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                try {
                    d = drawableFromUrl(opt1);

                } catch (IOException e) {
                    d = getResources().getDrawable(R.drawable.applogo);
                    e.printStackTrace();

                }
                try {
                    f = drawableFromUrl(opt2);

                } catch (IOException e) {
                    f = getResources().getDrawable(R.drawable.applogo);
                    e.printStackTrace();

                }
                try {
                    g = drawableFromUrl(opt3);

                } catch (IOException e) {
                    g = getResources().getDrawable(R.drawable.applogo);
                    e.printStackTrace();

                }


                scratchCardLayout1.setScratchDrawable(d);
                scratchCardLayout1.setScratchWidthDip(50f);
                scratchCardLayout1.setScratchEnabled(true);
                scratchCardLayout1.resetScratch();
                scratchCardLayout1.setScratchListener(this);

                scratchCardLayout2.setScratchDrawable(f);
                scratchCardLayout2.setScratchWidthDip(50f);
                scratchCardLayout2.setScratchEnabled(true);
                scratchCardLayout2.resetScratch();
                scratchCardLayout2.setScratchListener(this);

                scratchCardLayout3.setScratchDrawable(g);
                scratchCardLayout3.setScratchWidthDip(50f);
                scratchCardLayout3.setScratchEnabled(true);
                scratchCardLayout3.resetScratch();
                scratchCardLayout3.setScratchListener(this);
            }

//             pos = Integer.parseInt(databaseHelper.getImageData(s).getAnsweredcount());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void SubmitAnswers(String jsonBody){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(QuizQuestuions.this);
        String url="http://151.106.38.222:92/api/InsertSmartQuizCustomerAllAnswers";
        final String requestBody = jsonBody.toString();
        Log.d("offers","response"+requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","response"+response);
                pDialog.dismiss();
                Offerdata();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                pDialog.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }




}
