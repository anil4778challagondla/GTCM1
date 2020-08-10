package com.gmi.gtcm;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.SurveylistAdapter;
import com.gmi.gtcm.Adapter.VoiceQuizAdapter;
import com.gmi.gtcm.Model.Surveylistmodel;
import com.gmi.gtcm.Model.VoiceQuizModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VoiceQuizActivity extends AppCompatActivity {
ProgressDialog pDialog;
RecyclerView recyclerView;
    private List<VoiceQuizModel> Voicelist = new ArrayList<VoiceQuizModel>();
VoiceQuizAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_quiz);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        adapter=new VoiceQuizAdapter(this, Voicelist,"");
       LinearLayoutManager mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        VoiceQuestions();
    }

    public void VoiceQuestions(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Surveys...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://151.106.38.222:92//api/GetSpellingBeeQuizList?orgid=1&gid=0&pid=0&pgindex=1&pgsize=-1&str=&sortby=1";
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("c", response.toString());
pDialog.dismiss();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                VoiceQuizModel Event=new VoiceQuizModel();
                                Event.setSpellingBeeQuizId(obj.getString("SpellingBeeQuizId"));
                                Event.setSpellingBeeImagepath(obj.getString("SpellingBeeImagepath"));
                                Event.setQuizName(obj.getString("QuizName"));
                                Voicelist.add(Event);
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
pDialog.dismiss();
                    }
                });
        requestQueue.add(movieReq);


    }
}
