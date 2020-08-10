package com.gmi.gtcm.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.NoticeAdapter;
import com.gmi.gtcm.Adapter.NoticegroupAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.GridSpacingItemDecoration;
import com.gmi.gtcm.Model.Groupsnoticesmodel;
import com.gmi.gtcm.Model.NoticeModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Groupspage extends AppCompatActivity {
    private List<Groupsnoticesmodel> movieList = new ArrayList<Groupsnoticesmodel>();
    RecyclerView notices;
    private ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    private NoticegroupAdapter adapter;
    Dialog dialog;LinearLayoutManager mLayoutManager;

    String cntyid,custid,orgid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupspage);
        setActionBarTitle();
        notices = (RecyclerView) findViewById(R.id.noticesgroupsrecycleid);
        adapter = new NoticegroupAdapter(this, movieList);
        cntyid=getIntent().getStringExtra("countryid");
        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        notices.setLayoutManager(mLayoutManager);
        notices.setAdapter(adapter);
        ImageView home = (ImageView) findViewById(R.id.homebtnid);
        ImageView profile = (ImageView) findViewById(R.id.profilebtnid);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Myprofile.class));
                finish();

            }
        });
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        orgid=sharedPreferences.getString(AppUrls.ORGID,"");

        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Loading Groups...");
        pDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/GetCustomerGroups?orgid=1&cunid="+cntyid+"&cid="+custid;
        Log.d("test", url);
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("test", response.toString());
                        hidePDialog();
                        if(response.toString().contains("GroupName")){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Groupsnoticesmodel movie = new Groupsnoticesmodel();
                                    movie.setOrgId(obj.getString("OrgId"));
                                    movie.setGroupId(obj.getString("GroupId"));
                                    movie.setGroupName((obj.getString("GroupName")));
                                    movie.setLogoPath(obj.getString("LogoPath"));

                                    movieList.add(movie);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            dialog = new Dialog(Groupspage.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.alert1);
                            dialog.setCancelable(false);
                            TextView alertmessage=(TextView)dialog.findViewById(R.id.resultalerttvid);
                            alertmessage.setText("No Groups found.");
                            Button okbtn = (Button) dialog.findViewById(R.id.okbtnid);
                            okbtn.setText("Ok ");
                            Button cancel = (Button) dialog.findViewById(R.id.cancelbtn);
                            cancel.setVisibility(View.GONE);
                            okbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    startActivity(new Intent(Groupspage.this,Notices.class));
                                    finish();

                                }
                            });


                            dialog.show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
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
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Groups");

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

