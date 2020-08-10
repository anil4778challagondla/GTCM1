package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.gmi.gtcm.Adapter.ResultsAdapter;
import com.gmi.gtcm.Adapter.SurveylistAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.ResultsModel;
import com.gmi.gtcm.Model.SurveyQuestionModel;
import com.gmi.gtcm.Model.Surveylistmodel;
import com.gmi.gtcm.R;
import com.gmi.gtcm.db.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TextQuizQuestions extends AppCompatActivity {
    private TextView question,q,count,gamestatus;
    private RecyclerView gv=null;
    private RadioGroup options;
    private ImageView next,surveyimage;
    private ProgressDialog pDialog;
    public int counter , pos;
    DatabaseHelper databaseHelper;
    LinearLayoutManager mLayoutManager;
    private ResultsAdapter adapter;
    private List<ResultsModel> ResultList = new ArrayList<ResultsModel>();
    RadioButton rb;
    final ArrayList<String> answerary = new ArrayList<String>();
    final ArrayList<String> answeridary = new ArrayList<String>();
    private String custid,serid,questionid="0",answerid="0",answerno="0",iscorrect="0",IsquestionAvailable,SurveyquestionId="0",CorrectAnswerId;
    private SharedPreferences sharedPreferences;
    int i ,  index =0;
    ScrollView quizlayout;
    LinearLayout timerlayout,resultslayout;
    RadioButton rb1,rb2,rb3,rb4;
    RecyclerView recyclerview;
    private final List<SurveyQuestionModel> EventList = new ArrayList<SurveyQuestionModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_quiz_questions);
        question=(TextView)findViewById(R.id.surveyquestiontvid) ;
        count=(TextView)findViewById(R.id.count) ;
        gamestatus=(TextView)findViewById(R.id.gamestatus) ;
        next=(ImageView)findViewById(R.id.surveynextquestionid) ;
        options=(RadioGroup) findViewById(R.id.radiogroup) ;
        timerlayout=(LinearLayout) findViewById(R.id.timerlayout) ;
        resultslayout=(LinearLayout) findViewById(R.id.resultslayout) ;
        recyclerview=(RecyclerView) findViewById(R.id.recyclerview) ;
        quizlayout=(ScrollView) findViewById(R.id.quizlayout) ;
        databaseHelper = new DatabaseHelper(this);
        options.setVisibility(View.GONE);
        q=(TextView)findViewById(R.id.qtvid);
        next.setVisibility(View.GONE);
        adapter=new ResultsAdapter(this, ResultList);
        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(adapter);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        serid=getIntent().getStringExtra("serid");
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                index = radioGroup.indexOfChild(radioButton);
                answerid=EventList.get(index).getSurveyanswerId();
                answerno=EventList.get(index).getAnswerNumber();
                next.setVisibility(View.VISIBLE);
            }
        });


        setActionBarTitle();
        Offerdata();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EventList.clear();
                EventList.removeAll(EventList);
                options.removeAllViews();
                pos= Integer.parseInt(databaseHelper.getTextquizdata(serid).getTposition());
                databaseHelper.updateTextPosition(String.valueOf(pos+1),serid);
                try {
                    JSONObject ansobj = new JSONObject();
                    ansobj.put("AnswerId",answerno);
                    ansobj.put("AnswerNumber",answerid);
                    if (CorrectAnswerId.equals(String.valueOf(index+1))){
                        ansobj.put("IsCorrect","1");
                    }else {
                        ansobj.put("IsCorrect","0");
                    }

                    try {
                        JSONArray questionarray = new JSONArray(databaseHelper.getTextquizdata(serid).getTquestions());
                        JSONObject obj = questionarray.getJSONObject(pos);
                        SurveyquestionId = questionarray.getJSONObject(pos).getString("QuizQuestionId");
                        String quesnum = questionarray.getJSONObject(pos).getString("QuestionNum");
                        JSONArray optionsarray = obj.getJSONArray("answers");
                        ansobj.put("QuestionId",SurveyquestionId);
                        ansobj.put("QuestionNumber",quesnum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray ansarray= null;
                    if (databaseHelper.getTextquizdata(serid).getTanswer().contains("[")){
                        ansarray= new JSONArray(databaseHelper.getTextquizdata(serid).getTanswer());

                    }else {
                        ansarray = new JSONArray();
                    }
                    ansarray.put(ansobj);
                    databaseHelper.updateTextanswer(ansarray.toString(),serid);
                    Log.d("TAG", "onClick: "+ databaseHelper.getTextquizdata(serid).getTanswer());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                datarefresh(serid);

            }
        });
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quiz");
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Offerdata(){
        EventList.clear();
        options.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Survey Questions...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://151.106.38.222:92/api/GetQuizStatusByCustomer?qzid="+serid+"&cid="+custid;
        Log.d("c", url.toString());

        StringRequest movieReq = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("c", response.toString());
                        hidePDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            int IsFinished=obj.getInt("IsFinished");
                            int gameStatus=obj.getInt("GameStatus");
                            final String gametext=obj.getString("StatusMessage");
                            if (gameStatus==1){
                                quizlayout.setVisibility(View.GONE);
                                timerlayout.setVisibility(View.VISIBLE);
                                resultslayout.setVisibility(View.GONE);
                                int duration=obj.getInt("Duration");
                                startTimer(duration*1000,gametext);
                            }else if(IsFinished==0) {
                                quizlayout.setVisibility(View.VISIBLE);
                                timerlayout.setVisibility(View.GONE);
                                resultslayout.setVisibility(View.GONE);
                                EventList.clear(); try {
                                    JSONObject object = new JSONObject(response);
                                    JSONArray  questionarray = object.getJSONArray("Questions");
                                    if (databaseHelper.getTextquizdata(serid) == null) {
                                        Toast.makeText(TextQuizQuestions.this, "entered", Toast.LENGTH_SHORT).show();
                                        databaseHelper.insertTextQuestion(serid,questionarray.toString(),"0","0",String.valueOf(System.currentTimeMillis()));
                                    }
                                    datarefresh(serid);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }else {
                                quizlayout.setVisibility(View.GONE);
                                timerlayout.setVisibility(View.GONE);
                                resultslayout.setVisibility(View.VISIBLE);
//                                options.setVisibility(View.GONE);
//                                q.setVisibility(View.GONE);
//                                question.setText("Your Survey Finished.");
//                                question.setGravity(Gravity.CENTER_HORIZONTAL);
//                                next.setVisibility(View.GONE);
                                JSONArray resultsdata = obj.getJSONArray("Results");
                                gamestatus.setText(obj.getString("StatusMessage"));
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
                                    adapter.notifyDataSetChanged();
                                }

                                Toast.makeText(getApplicationContext(),"Survey Finished",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(movieReq);
    }

    private void datarefresh(String serid) {
        q.setVisibility(View.VISIBLE);
        options.setVisibility(View.VISIBLE);
        pos= Integer.parseInt(databaseHelper.getTextquizdata(serid).getTposition());
        try {
            JSONArray questionarray = new JSONArray(databaseHelper.getTextquizdata(serid).getTquestions());
            if (pos>=questionarray.length()){
                try {
                    JSONArray array = new JSONArray(databaseHelper.getTextquizdata(serid).getTanswer());
                    JSONObject result = new JSONObject();
                    result.put("QuizId",serid);
                    result.put("CustomerId",custid);
                    result.put("Duration",System.currentTimeMillis()-Long.parseLong(databaseHelper.getTextquizdata(serid).getTduration()));
                    int count =0;
                    for (int i=0 ;i<array.length();i++){
                        JSONObject obj = array.getJSONObject(i);
                        if (obj.getString("IsCorrect").equals("1")){
                            count = count+1;
                        }
                    }
                    result.put("CorrectAnsweredCount",count);
                    result.put("AnsweredCount",questionarray.length());
                    result.put("Answers",array);
                    Log.d("TAG", "onClick: " +result);
                    SubmitAnswers(result.toString());
                } catch (JSONException  e) {
                    e.printStackTrace();
                }

            }else {
                JSONObject obj = questionarray.getJSONObject(pos);
                CorrectAnswerId = questionarray.getJSONObject(pos).getString("CorrectAnswerId");
//                JSONArray optionsarray = obj.getJSONArray("answers");
//                question.setText(obj.getString("Question"));
                serid=sharedPreferences.getString("serid","");
//                String SurveyQuestion = obj.getString("Questions");
//                JSONObject SurveyQuestionobj = new JSONObject(SurveyQuestion);
                SurveyquestionId = obj.getString("QuizQuestionId");
                String SurveyId = obj.getString("QuizId");
                String Question = obj.getString("Question");
                question.setText(Question);
                String QuestionNum = obj.getString("QuestionNum");
//                iscorrect = obj.getString("Correctanswer");
                String answers = obj.getString("answers");
                JSONArray arry=new JSONArray(answers);
                for ( i = 0; i < arry.length(); i++) {
                    try {
                        JSONObject array = arry.getJSONObject(i);
                        SurveyQuestionModel Event = new SurveyQuestionModel();
                        Event.setAnswer(array.getString("Answer"));
                        Event.setSurveyanswerId(array.getString("QuizAnswerId"));
                        Event.setAnswerNumber(array.getString("AnswerNumber"));
                        EventList.add(Event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rb = new RadioButton(getApplicationContext());
                    rb.setTextSize(18);
                    rb.setText(EventList.get(i).getAnswer());
                    options.addView(rb);
                }
            }

//             pos = Integer.parseInt(databaseHelper.getImageData(s).getAnsweredcount());

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void startTimer(int noOfMinutes, final String gametext) {
        CountDownTimer  countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
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
        }.start();

    }
    public void SubmitAnswers(String jsonBody){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(TextQuizQuestions.this);
        String url="http://151.106.38.222:92/api/InsertQuizCustomerAllAnswers ";
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
