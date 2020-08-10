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
import com.gmi.gtcm.Adapter.SurveylistAdapter;
import com.gmi.gtcm.Model.Surveylistmodel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Survey extends AppCompatActivity {
    private RecyclerView gv=null;
    private SurveylistAdapter adapter;
    private List<Surveylistmodel> EventList = new ArrayList<Surveylistmodel>();
    ProgressDialog pDialog;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        setActionBarTitle();

        gv=(RecyclerView) findViewById(R.id.Surveysrecyclerviewid);
        adapter=new SurveylistAdapter(this, EventList,"survey");
        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gv.setLayoutManager(mLayoutManager);
        gv.setAdapter(adapter);
        Offerdata();

    } private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Surveys");


    }
    public void Offerdata(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Surveys...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://151.106.38.222:92/api/GetSurveyList?pgsize=-1&pgindex=1&str&sortby=1&bid=1";
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("c", response.toString());

                        hidePDialog();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Surveylistmodel Event=new Surveylistmodel();
                                Event.setSurveyId(obj.getString("SurveyId"));
                                Event.setSurveyimage(obj.getString("Surveyimagepath"));
                                Event.setSurveyName(obj.getString("SurveyName"));
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

    }
}

