package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.ResultsModel;
import com.gmi.gtcm.Model.SurveyQuestionModel;
import com.gmi.gtcm.R;
import com.gmi.gtcm.db.DatabaseHelper;
import com.gmi.gtcm.db.QuizData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VoiceQuizQuestions extends AppCompatActivity {
    private TextView question, q, count, gamestatus, clue1txt, clue2txt,timer,anil;
    private RecyclerView gv = null;
    TextToSpeech t1;
    int duration;
    CountDownTimer countDownTimer;
    DatabaseHelper databaseHelper;
    private RadioGroup options;
    private ImageView  surveyimage;
    private ProgressDialog pDialog;
    LinearLayout quizlayout;
    public int counter = 0;
    LinearLayoutManager mLayoutManager;
    private ResultsAdapter adapter;
    private List<ResultsModel> ResultList = new ArrayList<ResultsModel>();
    RadioButton rb;
    private String custid, quizid, word = "";
    private SharedPreferences sharedPreferences;
    int i;
    LinearLayout timerlayout, resultslayout;
    Button speak, clue1, clue2 , next;
    RecyclerView recyclerview;
    int hintsused = 0;
    int points =0;
    Double millis;
    EditText answertxt;
    int isCorrect = 0 , answeredcount =0,correctansweredcount=0 , totalpoints=0;
    private final List<SurveyQuestionModel> EventList = new ArrayList<SurveyQuestionModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_quiz_questions);
        count = (TextView) findViewById(R.id.count);
        timer = (TextView) findViewById(R.id.timer);
        speak = (Button) findViewById(R.id.listen);
        clue1 = (Button) findViewById(R.id.clue1);
        clue2 = (Button) findViewById(R.id.clue2);
        next = (Button) findViewById(R.id.next);
        answertxt = (EditText) findViewById(R.id.answertxt);
        gamestatus = (TextView) findViewById(R.id.gamestatus);
        clue1txt = (TextView) findViewById(R.id.clue1txt);
        clue2txt = (TextView) findViewById(R.id.clue2txt);
        timerlayout = (LinearLayout) findViewById(R.id.timerlayout);
        resultslayout = (LinearLayout) findViewById(R.id.resultslayout);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        quizlayout = (LinearLayout) findViewById(R.id.quizlayout);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        try {
            quizid = getIntent().getStringExtra("serid");
        } catch (Exception e) {

        }
        adapter=new ResultsAdapter(this, ResultList);
        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(adapter);
        clue1txt.setVisibility(View.GONE);
        clue2txt.setVisibility(View.GONE);
        clue1.setVisibility(View.VISIBLE);
        clue2.setVisibility(View.VISIBLE);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(word, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        clue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintsused++;
                clue1txt.setVisibility(View.VISIBLE);
                clue1.setVisibility(View.GONE);
            }
        });
        clue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintsused++;
                clue2txt.setVisibility(View.VISIBLE);
                clue2.setVisibility(View.GONE);
            }
        });
        Offerdata();
    }

    public void Offerdata() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading  Questions...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92//api/GetSpellingBeeQuizById?sqid=" + quizid + "&cid="+custid;
        Log.d("c", url.toString());

        StringRequest movieReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("c", response.toString());
                        hidePDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            int IsFinished = obj.getInt("IsFinished");
                            int gameStatus = obj.getInt("GameStatus");
                            final String gametext = obj.getString("StatusMessage");
                            if (gameStatus == 1) {
                                quizlayout.setVisibility(View.GONE);
                                timerlayout.setVisibility(View.VISIBLE);
                                resultslayout.setVisibility(View.GONE);
                                int duration = obj.getInt("StartDuration");
                                startTimer(Double.valueOf(duration)*1000, gametext,"mid");
                            } else if (IsFinished == 0) {
                                duration = obj.getInt("Duration");
                                quizlayout.setVisibility(View.VISIBLE);
                                timerlayout.setVisibility(View.GONE);
                                resultslayout.setVisibility(View.GONE);
                                JSONArray questionarray = obj.getJSONArray("Questions");
                                if (databaseHelper.getDatabyQUizId(quizid) == null) {
                                    Toast.makeText(VoiceQuizQuestions.this, "entered", Toast.LENGTH_SHORT).show();
                                    databaseHelper.insertQuestion(quizid, questionarray.toString(), "0", "[]", String.valueOf(duration), "0","0","0");
                                }
                                final JSONArray  qarray = new JSONArray(databaseHelper.getDatabyQUizId(quizid).getQuestions().toString());
                                counter = Integer.parseInt(databaseHelper.getDatabyQUizId(quizid).getPosition());
                                final JSONObject qobje = qarray.getJSONObject(counter);
                                word = qobje.getString("Question");
                                clue1txt.setText(qobje.getString("Hint1"));
                                clue2txt.setText(qobje.getString("Hint2"));
                                Double time  = Double.parseDouble(databaseHelper.getDatabyQUizId(quizid).getTime());
                                startTimer(time*60*1000 , "Quiz Ends in","up");
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        answeredcount = Integer.parseInt(databaseHelper.getDatabyQUizId(quizid).getAnsweredcount());
                                        correctansweredcount = Integer.parseInt(databaseHelper.getDatabyQUizId(quizid).getCorrectansweredcount());
                                        totalpoints = Integer.parseInt(databaseHelper.getDatabyQUizId(quizid).getPoints());
                                        JSONArray ansarray = null;
                                        try {
                                            ansarray = new JSONArray(databaseHelper.getDatabyQUizId(quizid).getAnswers());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if(answertxt.getText().toString().trim().equalsIgnoreCase(word)){
                                            isCorrect=1;
                                            correctansweredcount++;
                                        }else {
                                            isCorrect = 0;
                                        }
                                        if (!answertxt.getText().toString().trim().equalsIgnoreCase("")){
                                            answeredcount++;
                                        }
                                        if (isCorrect==1){
                                            points = 4-hintsused;
                                        }else {
                                            points=0-hintsused;
                                        }
                                        totalpoints = totalpoints + points;
                                        if (counter<qarray.length()-1){
                                            databaseHelper.updatePositionData(String.valueOf(counter+1),quizid);
                                            JSONObject subobj=null;
                                            try {
                                                subobj = qarray.getJSONObject(counter);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            JSONObject objj = new JSONObject();
                                            try {
                                                objj.put("QuestionId",subobj.getString("SpellingBeeQuestionId"));
                                                objj.put("QuestionNumber",subobj.getString("QuestionNumber"));
                                                objj.put("Answer",answertxt.getText().toString().trim());
                                                objj.put("IsCorrect",String.valueOf(isCorrect));
                                                objj.put("UsedHints",hintsused);
                                                objj.put("Points",points);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            ansarray.put(objj);
                                            databaseHelper.updateAnswerData(ansarray.toString(),quizid);
                                            databaseHelper.updateAnsweredCount(String.valueOf(answeredcount),quizid);
                                            databaseHelper.updateCorrectCount(String.valueOf(correctansweredcount),quizid);
                                            databaseHelper.updatePoints(String.valueOf(totalpoints),quizid);
                                            databaseHelper.updateTime(String.valueOf((millis)/60000),quizid);
                                            Log.d("position", "onClick: "+databaseHelper.getDatabyQUizId(quizid).getPosition());
                                            Log.d("points", "onClick: "+databaseHelper.getDatabyQUizId(quizid).getPoints());
                                            Log.d("count", "onClick: "+databaseHelper.getDatabyQUizId(quizid).getAnsweredcount());
                                            Log.d("correctcount", "onClick: "+databaseHelper.getDatabyQUizId(quizid).getCorrectansweredcount());
                                            Log.d("Answer", "onClick: "+databaseHelper.getDatabyQUizId(quizid).getAnswers());
                                            counter++;
                                            JSONObject qobj = null;
                                            try {
                                                qobj = qarray.getJSONObject(counter);
                                                word = qobj.getString("Question");
                                                clue1txt.setText(qobj.getString("Hint1"));
                                                clue2txt.setText(qobj.getString("Hint2"));
                                                hintsused =0;
                                                isCorrect =0;
                                                answertxt.setText("");
                                                points=0;
                                                totalpoints =0;
                                                correctansweredcount =0;
                                                answeredcount =0;
                                                clue1txt.setVisibility(View.GONE);
                                                clue2txt.setVisibility(View.GONE);
                                                clue1.setVisibility(View.VISIBLE);
                                                clue2.setVisibility(View.VISIBLE);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }else{
                                            databaseHelper.updateAnswerData(ansarray.toString(),quizid);
                                            databaseHelper.updateAnsweredCount(String.valueOf(answeredcount),quizid);
                                            databaseHelper.updateCorrectCount(String.valueOf(correctansweredcount),quizid);
                                            databaseHelper.updatePoints(String.valueOf(totalpoints),quizid);
                                            databaseHelper.updateTime(String.valueOf((millis)/60000),quizid);
                                            try {
                                                JSONArray array = new JSONArray(databaseHelper.getDatabyQUizId(quizid).getAnswers());
                                                JSONObject result = new JSONObject();
                                                result.put("QuizId",quizid);
                                                result.put("CustomerId",custid);
                                                result.put("Duration",String.format("%.0f", (duration-Double.parseDouble(databaseHelper.getDatabyQUizId(quizid).getTime()))*60*1000));
                                                result.put("CorrectAnsweredCount",databaseHelper.getDatabyQUizId(quizid).getCorrectansweredcount());
                                                result.put("AnsweredCount",databaseHelper.getDatabyQUizId(quizid).getAnsweredcount());
                                                result.put("Points",databaseHelper.getDatabyQUizId(quizid).getPoints());
                                                result.put("Answers",array);
                                                Log.d("TAG", "onClick: " +result);
                                                SubmitAnswers(result.toString());
                                            } catch (JSONException  e) {
                                                e.printStackTrace();
                                            }

                                            Toast.makeText(VoiceQuizQuestions.this, "Questions Completed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                quizlayout.setVisibility(View.GONE);
                                timerlayout.setVisibility(View.GONE);
                                resultslayout.setVisibility(View.VISIBLE);
                                JSONArray resultsdata = obj.getJSONArray("Results");
                                gamestatus.setText(obj.getString("StatusMessage"));
                                for (int i = 0; i < resultsdata.length(); i++) {
                                    try {
                                        JSONObject objj = resultsdata.getJSONObject(i);
                                        ResultsModel result = new ResultsModel();
                                        result.setCustomerName(objj.getString("CustomerName"));
                                        result.setAnsweredCount(objj.getString("Points"));
                                        result.setDurationString(objj.getString("DurationString"));
                                        result.setRank(objj.getString("Rank"));
                                        result.setSno(String.valueOf(i + 1));
                                        ResultList.add(result);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                                Toast.makeText(getApplicationContext(), "Quiz Finished", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                    }
                });
        requestQueue.add(movieReq);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void startTimer(Double noOfMinutes, final String gametext, final String state) {
        countDownTimer = new CountDownTimer(noOfMinutes.intValue(), 1000) {
            public void onTick(long millisUntilFinished) {
                millis = Double.valueOf(millisUntilFinished);
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                if (state.equalsIgnoreCase("up")){
                    timer.setText(gametext + "\n" + hms);//set text
                }else {
                    count.setText(gametext + "\n" + hms);//set text
                }

            }

            public void onFinish() {
                if (state.equalsIgnoreCase("up")){
                    timer.setText("TIME'S UP!!");
                    try {
                        JSONObject result = new JSONObject();
                        JSONArray array = new JSONArray(databaseHelper.getDatabyQUizId(quizid).getAnswers());
                        result.put("QuizId",quizid);
                        result.put("CustomerId",custid);
                        result.put("Duration",(duration)*60*1000);
                        result.put("CorrectAnsweredCount",databaseHelper.getDatabyQUizId(quizid).getCorrectansweredcount());
                        result.put("AnsweredCount",databaseHelper.getDatabyQUizId(quizid).getAnsweredcount());
                        result.put("Points",databaseHelper.getDatabyQUizId(quizid).getPoints());
                        result.put("Answers",array);
                        Log.d("TAG", "onClick: " +result);
                        SubmitAnswers(result.toString());
                    } catch (JSONException  e) {
                        e.printStackTrace();
                    }
                }else {
                    count.setText("TIME'S UP!!");
                    Offerdata();
                }

                //On finish change timer text
            }
        }.start();

    }
    public void SubmitAnswers(String jsonBody){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(VoiceQuizQuestions.this);
        String url="http://151.106.38.222:92/api/InsertSpellBeeCustomerQuizAnswer";
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


    @Override
    protected void onDestroy() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (quizlayout.getVisibility()==View.VISIBLE){
            databaseHelper.updateTime(String.valueOf((millis)/60000),quizid);
        }
        try{

            countDownTimer.cancel();
        }catch (Exception e){

        }
        super.onPause();
    }
}
