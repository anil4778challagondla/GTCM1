package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.QuizlistAdapter;
import com.gmi.gtcm.Model.Quizmodel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SmartQuiz extends AppCompatActivity {
    private RecyclerView gv=null;
    private QuizlistAdapter adapter;
    private List<Quizmodel> EventList = new ArrayList<Quizmodel>();
    ProgressDialog pDialog;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_quiz);
        setActionBarTitle();
        gv=(RecyclerView) findViewById(R.id.Surveysrecyclerviewid);
        adapter=new QuizlistAdapter(this, EventList);
        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gv.setLayoutManager(mLayoutManager);
        gv.setAdapter(adapter);
        Offerdata();
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quiz");


    }
    public void Offerdata(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Quiz...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://151.106.38.222:92/api/GetSmartQuizList?orgid=1&pgindex=1&pgsize=-1&sortby=1&str=";
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("c", response.toString());

                        hidePDialog();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Quizmodel Event=new Quizmodel();
                                Event.setSmartQuizID(obj.getLong("SmartQuizId"));
                                Event.setSmartQuizImagepath(obj.getString("SmartQuizImagepath"));
                                Event.setSmartQuizName(obj.getString("SmartQuizName"));
                                EventList.add(Event);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
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

    }}
