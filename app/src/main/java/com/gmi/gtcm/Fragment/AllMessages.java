package com.gmi.gtcm.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.MessageAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.CheckConnection;
import com.gmi.gtcm.Model.CommitteInterface;
import com.gmi.gtcm.Model.MessageModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AllMessages extends Fragment implements CommitteInterface {
    private List<MessageModel> movieList = new ArrayList<MessageModel>();
    private ListView listView;
    private ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    private MessageAdapter adapter;
    String grpid="0",custid;
    Dialog dialog;

    public AllMessages() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_all_messages, container, false);
        listView = (ListView)v.findViewById(R.id.Messageboardlistid);
        adapter = new MessageAdapter(getActivity(), movieList,this);
        listView.setAdapter(adapter);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        grpid=sharedPreferences.getString(AppUrls.GROUPID,"");
        connection();
        movieList.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url = "http://151.106.38.222:92/api/getcustomergroupmessages?orgid=1&grpid="+AppUrls.TEMPGRP+"&cid="+custid+"&pgsize=-1&pgindex=1&str&sortby=1";
        Log.d("testlink", url);
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("test", response.toString());
                        hidePDialog();
                        if(response.toString().contains("Message")){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    MessageModel movie = new MessageModel();
                                    movie.setGMSGId(obj.getString("GMSGId"));
                                    movie.setGroupId(obj.getString("GroupId"));
                                    movie.setOrgId((obj.getString("OrgId")));
                                    movie.setCustomerId(obj.getString("CustomerId"));
                                    movie.setMessage(obj.getString("Message"));
                                    movie.setPostedDate(obj.getString("PostedDate"));
                                    movie.setFormatedPostedDate(obj.getString("FormatedPostedDate"));
                                    movie.setApprovedDate(obj.getString("ApprovedDate"));
                                    movie.setFosmatedApprovedDate(obj.getString("FosmatedApprovedDate"));
                                    movie.setStatus(obj.getString("Status"));
                                    movie.setSenderName(obj.getString("SenderName"));
                                    movie.setSender(obj.getString("Sender"));
                                    movie.setImage(obj.getString("CustomerImagePath"));
                                    movie.setMssgimage(obj.getString("Imagepath"));

                                    movieList.add(movie);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.alert1);
                            dialog.setCancelable(false);
                            TextView alertmessage=(TextView)dialog.findViewById(R.id.resultalerttvid);
                            alertmessage.setText("No Messges.");
                            Button okbtn = (Button) dialog.findViewById(R.id.okbtnid);
                            okbtn.setText("Ok ");
                            Button cancel = (Button) dialog.findViewById(R.id.cancelbtn);
                            cancel.setVisibility(View.GONE);
                            okbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                }
                            });
                            dialog.show(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                hidePDialog();
            }
        });
        requestQueue.add(movieReq);
        return v;
    }


    @SuppressLint("WrongConstant")
    public void connection(){
        if(CheckConnection.isInternetAvailable(getActivity())) //returns true if internet available
        {
        }
        else
        {
            Toast.makeText(getActivity(),"No Internet Connection",1000).show();
        }
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        connection();
    }

    @Override
    public void onResume() {
        super.onResume();
        connection();
    }


    @Override
    public void onclick() {
        movieList.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url = "http://151.106.38.222:92/api/getcustomergroupmessages?orgid=1&grpid=0&cid="+custid+"&pgsize=-1&pgindex=1&str&sortby=1";
        Log.d("testlink", url);
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("test", response.toString());
                        hidePDialog();
                        if(response.toString().contains("Message")){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    MessageModel movie = new MessageModel();
                                    movie.setGMSGId(obj.getString("GMSGId"));
                                    movie.setGroupId(obj.getString("GroupId"));
                                    movie.setOrgId((obj.getString("OrgId")));
                                    movie.setCustomerId(obj.getString("CustomerId"));
                                    movie.setMessage(obj.getString("Message"));
                                    movie.setPostedDate(obj.getString("PostedDate"));
                                    movie.setFormatedPostedDate(obj.getString("FormatedPostedDate"));
                                    movie.setApprovedDate(obj.getString("ApprovedDate"));
                                    movie.setFosmatedApprovedDate(obj.getString("FosmatedApprovedDate"));
                                    movie.setStatus(obj.getString("Status"));
                                    movie.setSenderName(obj.getString("SenderName"));
                                    movie.setSender(obj.getString("Sender"));
                                    movie.setImage(obj.getString("CustomerImagePath"));
                                    movie.setMssgimage(obj.getString("Imagepath"));

                                    movieList.add(movie);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.alert1);
                            dialog.setCancelable(false);
                            TextView alertmessage=(TextView)dialog.findViewById(R.id.resultalerttvid);
                            alertmessage.setText("No Messges.");
                            Button okbtn = (Button) dialog.findViewById(R.id.okbtnid);
                            okbtn.setText("Ok ");
                            Button cancel = (Button) dialog.findViewById(R.id.cancelbtn);
                            cancel.setVisibility(View.GONE);
                            okbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                }
                            });
                            dialog.show(); }
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
}
