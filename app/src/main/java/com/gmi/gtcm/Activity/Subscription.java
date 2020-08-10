package com.gmi.gtcm.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Subscription extends AppCompatActivity {
    Spinner groups,country,cities;
    ProgressDialog pDialog;
    ImageView go;
    EditText items;
    String slectitems,slectitemsid;
    int postion;
    ArrayAdapter<String> spinnerArrayAdapter;

    final ArrayList<String> countnameary = new ArrayList<String>();
    final ArrayList<String> countidary = new ArrayList<String>();
    final ArrayList<String> selectednameary = new ArrayList<String>();
    final ArrayList<String> selectedidary = new ArrayList<String>();
    final ArrayList<String> groupnameary = new ArrayList<String>();
    final ArrayList<String> groupidary = new ArrayList<String>();

    String contyids,customerid,contryname="",contryid="",cityname="",cityid="",groupname="",lname="",groupid="",name="",email="",mobile="",pin="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        items=(EditText)findViewById(R.id.edittextselectitems);


        country=(Spinner)findViewById(R.id.spinner2);
        go=(ImageView)findViewById(R.id.gotopaymentivid);
        contyids=getIntent().getStringExtra("countryid");
        customerid=getIntent().getStringExtra("customer");
        Country();
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupid.equals(0)){
                    Toast.makeText(getApplicationContext(),"Select your Group",Toast.LENGTH_LONG).show();
                }else {
                    Subscription();
                }

            }
        });
        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!groupnameary.isEmpty()){
                    alertMultipleChoiceItems();
                }else{
                    Toast.makeText(getApplicationContext(),"No Groups",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public void Groups(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/GetGroupsddl?oid=1&countryid="+contryid;
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
    public void Subscription(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/AddCustomerSubscription?orgid=1&cid="+customerid+"&grpsids="+slectitemsid+"&issub=0";
        StringRequest data=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    String statuscode=jsonObject.getString("StatusCode");
                    String StatusMessage=jsonObject.getString("StatusMessage");

                    int value=Integer.parseInt(statuscode);

                       if(StatusMessage.contains("Subscribed")) {

                        Toast toast = Toast.makeText(getApplicationContext(), "Subscribe Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Intent intent=new Intent(getApplicationContext(),Home.class);
                        startActivity(intent);
                        finish();
                    }else  {
                        Toast toast = Toast.makeText(getApplicationContext(), "Subscribed UnSuccessfull", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }


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
    public void Country(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/GetCountries";
        StringRequest data=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    Log.d("data","arraydata"+array);
                    for(int i=0;i<array.length();i++) {
                        JSONObject obj = array.getJSONObject(i);
                        countnameary.add(obj.get("CountryName").toString());
                        countidary.add(obj.get("CountryId").toString());
                    }
                    Log.d("fffff","fffff"+countnameary);
                    spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_spinner_item, countnameary);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.activity_spinner_item);
                    country.setAdapter(spinnerArrayAdapter);
                    country.setSelection(countidary.indexOf(contyids));
                    country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int position, long arg3) {
                            contryname=countnameary.get(position);
                            contryid = countidary.get(position);
                            Log.d("statevalue", "onItemSelected: "+contryid);
                            if(!contryid.equals(0)) {
                                Groups();
                                groupidary.clear();
                                groupnameary.clear();
                            }else {
                                Toast.makeText(getApplicationContext(), "Select Country", Toast.LENGTH_SHORT).show();
                            }
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
    public void alertMultipleChoiceItems(){
        final android.app.AlertDialog.Builder builderDialog = new android.app.AlertDialog.Builder(this);

        final CharSequence[] dialogList = groupnameary.toArray(new CharSequence[groupnameary.size()]);

        builderDialog.setTitle("Select Item");
        int count =dialogList.length;

        final boolean[] is_checked = new boolean[count];


        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton, boolean isChecked) {

                        if(isChecked==true){
                            selectedidary.add(String.valueOf(groupidary.get(whichButton)));
                            selectednameary.add(String.valueOf(groupnameary.get(whichButton)));
                            slectitems=Arrays.toString(selectednameary.toArray()).replace("[", "").replace("]", "");
                            slectitemsid=Arrays.toString(selectedidary.toArray()).replace("[", "").replace("]", "");

                        }else if(isChecked==false){
                            selectedidary.remove(String.valueOf(groupidary.get(whichButton)));
                            selectednameary.remove(String.valueOf(groupnameary.get(whichButton)));
                            slectitems=Arrays.toString(selectednameary.toArray()).replace("[", "").replace("]", "");
                            slectitemsid=Arrays.toString(selectedidary.toArray()).replace("[", "").replace("]", "");

                        }

                    }
                });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        items.setText(slectitems);
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        android.app.AlertDialog alert = builderDialog.create();
        alert.show();
    }



    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }



}
