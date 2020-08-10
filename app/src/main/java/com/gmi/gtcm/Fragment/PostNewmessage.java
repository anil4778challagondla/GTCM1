package com.gmi.gtcm.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Activity.Imagepostmessage;
import com.gmi.gtcm.Activity.MessageBoard;
import com.gmi.gtcm.Activity.Messageboard2;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostNewmessage extends Fragment {
    Spinner groups;
    EditText message;
    Button send;
    private Uri mCropImageUri;
    String imgString;

    private ImageView cesetimage,ceimagevie;
    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_FILE = 1889;
    SharedPreferences sharedPreferences;
    final ArrayList<String> groupnameary = new ArrayList<String>();
    final ArrayList<String> groupidary = new ArrayList<String>();
    String Groupname="",groupid="",messagetext,custid;
    public PostNewmessage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_post_newmessage, container, false);
        groups=(Spinner)v.findViewById(R.id.groupsdropdownidmessage);
        message=(EditText)v.findViewById(R.id.messageedtid);
        send=(Button)v.findViewById(R.id.messagesendbtnid);
        cesetimage=(ImageView)v.findViewById(R.id.createevent_uploadimagebtn);
        ceimagevie=(ImageView)v.findViewById(R.id.createevent_setimageview);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
//        Groups();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagetext=message.getText().toString().trim();
//                if(groupid.equals("0")) {
//                    Toast.makeText(getActivity(), "Select Group", Toast.LENGTH_SHORT).show();
//                }else if(messagetext.equals("")) {
//                    Toast.makeText(getActivity(), "Enter Message", Toast.LENGTH_SHORT).show();
//
//                }else {
                    sendmethod();
//                }
            }
        });
        cesetimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagetext=message.getText().toString().trim();
                Intent intent=new Intent(getActivity(), Imagepostmessage.class);
                intent.putExtra("grpid",AppUrls.TEMPGRP);
                intent.putExtra("cid",custid);
                intent.putExtra("msg",messagetext);
                intent.putExtra("Pimage","");
                intent.putExtra("Pvideo","");
                intent.putExtra("imgextension","");
                intent.putExtra("vidextension","");
                startActivity(intent);

            }
        });
        ceimagevie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagetext=message.getText().toString().trim();
                Intent intent=new Intent(getActivity(), Imagepostmessage.class);
                intent.putExtra("grpid",AppUrls.TEMPGRP);
                intent.putExtra("cid",custid);
                intent.putExtra("msg",messagetext);
                intent.putExtra("Pimage","");
                intent.putExtra("Pvideo","");
                intent.putExtra("imgextension","");
                intent.putExtra("vidextension","");
                startActivity(intent);

            }
        });


        return v;
    }

    public void Groups(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://151.106.38.222:92/api/GetCustomerGroups?orgid=1&cunid=0&cid="+custid;
        StringRequest data=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    Log.d("data","arraydata"+array);
                    for(int i=0;i<array.length();i++) {
                        JSONObject obj = array.getJSONObject(i);
                        groupnameary.add(obj.get("GroupName").toString());
                        groupidary.add(obj.get("GroupId").toString());
                    }
                    Log.d("fffff","fffff"+groupnameary);
                    groupnameary.add(0, "-Select Group -");
                    groupidary.add(0, "0");
                    groups.setSelection(0, true);
                    ArrayAdapter<String> spinnerArrayAdapter;
                    spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_spinner_item,groupnameary);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.activity_spinner_item);
                    groups.setAdapter(spinnerArrayAdapter);
                    groups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int position, long arg3) {
                            Groupname=groupnameary.get(position);
                            groupid = groupidary.get(position);
                            Log.d("statevalue", "onItemSelected: "+groupid);

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(data);
    }

    public void sendmethod(){

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String url="http://151.106.38.222:92/api/sendgroupnewmessage";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("orgid","1");
            jsonBody.put("grpid",AppUrls.TEMPGRP);
            jsonBody.put("cid",custid);
            jsonBody.put("msg",messagetext);
            jsonBody.put("Pimage","");
            jsonBody.put("Pvideo","");
            jsonBody.put("imgextension","");
            jsonBody.put("vidextension","");
            final String requestBody = jsonBody.toString();
            Log.d("offers","response"+requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("offers","response"+response);
                    try {
                        JSONObject data=new JSONObject(response);
                        String StatusMessage= data.getString("StatusMessage");
                        String StatusCode= data.getString("StatusCode");
                        if(StatusMessage.contains("Posted Successfully")){
                            Toast.makeText(getActivity(),StatusMessage,Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(getActivity(),"Try again please",Toast.LENGTH_LONG).show();

                        }








                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
