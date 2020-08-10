package com.gmi.gtcm.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.EventsAdapter;
import com.gmi.gtcm.Adapter.NoticeAdapter;
import com.gmi.gtcm.Adapter.PhotosFragmentAdapter;
import com.gmi.gtcm.GridSpacingItemDecoration;
import com.gmi.gtcm.Model.EventsModel;
import com.gmi.gtcm.Model.NoticeModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notices extends AppCompatActivity {
    private List<NoticeModel> movieList = new ArrayList<NoticeModel>();
    RecyclerView notices;
    private ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    private NoticeAdapter adapter;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        setActionBarTitle();
        notices = (RecyclerView) findViewById(R.id.noticesrecycleid);
        adapter = new NoticeAdapter(this, movieList);

        notices.setLayoutManager(new GridLayoutManager(this,2));
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(this, R.dimen.columnspace);
        notices.setAdapter(adapter);
        notices.setAdapter(adapter);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
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
        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Loading Notices...");
        pDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/GetCountries";
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("", response.toString());
                        hidePDialog();
                        if(response.toString().contains("CountryName")){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    NoticeModel movie = new NoticeModel();
                                    movie.setCountryName(obj.getString("CountryName"));
                                    movie.setCountryImagePath(obj.getString("CountryImagePath"));
                                    movie.setCountryId((obj.getString("CountryId")));
                                    movie.setCountryCode(obj.getString("CountryCode"));

                                    movieList.add(movie);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            dialog = new Dialog(Notices.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.alert1);
                            dialog.setCancelable(false);
                            TextView alertmessage=(TextView)dialog.findViewById(R.id.resultalerttvid);
                            alertmessage.setText("No Notices found.");
                            Button okbtn = (Button) dialog.findViewById(R.id.okbtnid);
                            okbtn.setText("Ok ");
                            Button cancel = (Button) dialog.findViewById(R.id.cancelbtn);
                            cancel.setVisibility(View.GONE);
                            okbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    startActivity(new Intent(Notices.this,Home.class));
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
        getSupportActionBar().setTitle("Notices");

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

