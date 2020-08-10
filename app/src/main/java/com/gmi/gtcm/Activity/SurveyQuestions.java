package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.SurveyQuestionModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SurveyQuestions extends AppCompatActivity {
    private TextView question,q;
    private RecyclerView gv=null;
    private RadioGroup options;
    private ImageView next,surveyimage;
    private ProgressDialog pDialog;
    LinearLayoutManager mLayoutManager;
    RadioButton rb;
    final ArrayList<String> answerary = new ArrayList<String>();
    final ArrayList<String> answeridary = new ArrayList<String>();
    private String custid,serid,questionid="0",answerid="0",iscorrect="0",IsquestionAvailable,SurveyquestionId="0";
    private SharedPreferences sharedPreferences;
    int i;
    RadioButton rb1,rb2,rb3,rb4;
    private final List<SurveyQuestionModel> EventList = new ArrayList<SurveyQuestionModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_questions);
        question=(TextView)findViewById(R.id.surveyquestiontvid) ;
        next=(ImageView)findViewById(R.id.surveynextquestionid) ;
        options=(RadioGroup) findViewById(R.id.radiogroup) ;
        options.setVisibility(View.GONE);
        q=(TextView)findViewById(R.id.qtvid);
        next.setVisibility(View.GONE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        serid=getIntent().getStringExtra("serid");
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                answerid=EventList.get(index).getSurveyanswerId();
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
                Offerdata();


            }
        });
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Survey");
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
        String url="http://151.106.38.222:92/api/InsertCustomerSurveyAnswer?sid="+serid+"&cid="+custid+"&qid="+SurveyquestionId+"&aid="+answerid+"&IsCorrect="+iscorrect+"&Duration=0";
        StringRequest movieReq = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("c", response.toString());
                        hidePDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            int IsFinished=obj.getInt("IsFinished");
                            if(IsFinished==0) {
                                EventList.clear();
                                q.setVisibility(View.VISIBLE);

                                options.setVisibility(View.VISIBLE);
                                String SurveyQuestion = obj.getString("SurveyQuestion");
                                JSONObject SurveyQuestionobj = new JSONObject(SurveyQuestion);
                                SurveyquestionId = SurveyQuestionobj.getString("SurveyquestionId");
                                String SurveyId = SurveyQuestionobj.getString("SurveyId");
                                String Question = SurveyQuestionobj.getString("Question");
                                question.setText(Question);
                                String QuestionNum = SurveyQuestionobj.getString("QuestionNum");
                                iscorrect = SurveyQuestionobj.getString("Correctanswer");
                                IsquestionAvailable = SurveyQuestionobj.getString("IsquestionAvailable");
                                String answers = SurveyQuestionobj.getString("answers");
                                JSONArray arry=new JSONArray(answers);
                                for ( i = 0; i < arry.length(); i++) {
                                    try {
                                        JSONObject array = arry.getJSONObject(i);
                                        SurveyQuestionModel Event=new SurveyQuestionModel();
                                        Event.setAnswer(array.getString("Answer"));
                                        Event.setSurveyanswerId(array.getString("SurveyanswerId"));
                                        EventList.add(Event);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    rb = new RadioButton(getApplicationContext());
                                    rb.setTextSize(18);
                                    rb.setText(EventList.get(i).getAnswer());
                                    options.addView(rb);
                                }

                            }else {
                                options.setVisibility(View.GONE);
                                q.setVisibility(View.GONE);
                                question.setText("Your Survey Finished.");
                                question.setGravity(Gravity.CENTER_HORIZONTAL);
                                next.setVisibility(View.GONE);

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
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
