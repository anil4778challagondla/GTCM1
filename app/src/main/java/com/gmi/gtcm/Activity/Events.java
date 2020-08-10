package com.gmi.gtcm.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.EventsAdapter;
import com.gmi.gtcm.CheckConnection;
import com.gmi.gtcm.Model.EventsModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Events extends AppCompatActivity {
    private List<EventsModel> movieList = new ArrayList<EventsModel>();
    private ListView listView;
    private ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    private EventsAdapter adapter;
    String grpid="0";
    Dialog dialog;
    ImageView home,profile;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        setActionBarTitle();
        listView = (ListView) findViewById(R.id.eventslistview);

        adapter = new EventsAdapter(this, movieList);
        listView.setAdapter(adapter);
        grpid=getIntent().getStringExtra("groupid");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Loading Events...");
        pDialog.show();
        connection();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/getevents?orgid=1&grpid="+grpid+"&pgindex=1&pgsize=-1&str=&sortby=1";
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("responsed", response.toString());
                        hidePDialog();
                        if(response.toString().contains("Title")){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    EventsModel movie = new EventsModel();
                                    movie.setTitle(obj.getString("Title"));
                                    movie.setDescription(obj.getString("Description"));
                                    movie.setStartDateString((obj.getString("StartDateString")));
                                    movie.setEventImagePath(obj.getString("EventImagePath"));
                                    movie.setEventId(obj.getString("EventId"));
                                    movie.setStartDateString(obj.getString("StartDateString"));
                                    movie.setEndDateString(obj.getString("EndDateString"));
                                    movie.setContactPerson(obj.getString("ContactPerson"));
                                    movie.setContactPhone(obj.getString("ContactPhone"));
                                    movie.setContactEmail(obj.getString("ContactEmail"));
                                    movie.setContactPerson(obj.getString("ContactPerson"));
                                    movie.setOrgName(obj.getString("OrgName"));
                                    movie.setLocation(obj.getString("Address"));
                                    movie.setSocialMedia(obj.getString("SocialMedia"));
                                    movie.setSponsorlist(obj.getJSONArray("Sponsorlist").toString());
                                    movieList.add(movie);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            dialog = new Dialog(Events.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.alert1);
                            dialog.setCancelable(false);
                            TextView alertmessage=(TextView)dialog.findViewById(R.id.resultalerttvid);
                            alertmessage.setText("No events found.");
                            Button okbtn = (Button) dialog.findViewById(R.id.okbtnid);
                            okbtn.setText("Ok ");
                            Button cancel = (Button) dialog.findViewById(R.id.cancelbtn);
                            cancel.setVisibility(View.GONE);
                            okbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    startActivity(new Intent(Events.this,Home.class));
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

    }
    @SuppressLint("WrongConstant")
    public void connection(){
        if(CheckConnection.isInternetAvailable(Events.this)) //returns true if internet available
        {

        }
        else
        {
            Toast.makeText(Events.this,"No Internet Connection",1000).show();
        }
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Events");

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

    @Override
    protected void onPause() {
        super.onPause();
        connection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        connection();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connection();
    }
}
