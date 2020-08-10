package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    ImageView go;
    ProgressDialog pDialog;
    EditText items;
    String slectitems,slectitemsid;

    Spinner groups,country,cities;
    final ArrayList<String> countnameary = new ArrayList<String>();
    final ArrayList<String> countidary = new ArrayList<String>();
    final ArrayList<String> citynameary = new ArrayList<String>();
    final ArrayList<String> cityidary = new ArrayList<String>();
    final ArrayList<String> groupnameary = new ArrayList<String>();
    final ArrayList<String> groupidary = new ArrayList<String>();
    final ArrayList<String> selectednameary = new ArrayList<String>();
    final ArrayList<String> selectedidary = new ArrayList<String>();
    EditText fname,lname,mobile,email,pin;
    String contryname="",contryid="",contryids,customerid,cityname="",cityid="",groupname="",lastname="",groupid="",Mname="",Memail="",Mmobile="",Mpin="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        go=(ImageView)findViewById(R.id.registergobtnid);
        fname=(EditText)findViewById(R.id.fnameetid);
        lname=(EditText)findViewById(R.id.lnameetid);
        mobile=(EditText)findViewById(R.id.mobileetid);
        email=(EditText)findViewById(R.id.emailetid);
        pin=(EditText)findViewById(R.id.pinetid);
        items=(EditText)findViewById(R.id.edittextselectitems);

        country=(Spinner)findViewById(R.id.spinner2);
        cities=(Spinner)findViewById(R.id.spinner3);
        Country();

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mname=fname.getText().toString().trim();
                lastname=lname.getText().toString().trim();
                Mmobile=mobile.getText().toString().trim();
                Memail=email.getText().toString().trim();
                Mpin=pin.getText().toString().trim();
                              if(Mname.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Firstame",Toast.LENGTH_LONG).show();

                }else if(Mmobile.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Mobile number",Toast.LENGTH_LONG).show();
                }else if(Mmobile.length()<0){
                    Toast.makeText(getApplicationContext(),"Enter 10 digit mobile number",Toast.LENGTH_LONG).show();
                }else if(Memail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_LONG).show();
                }else if(Mpin.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Pin",Toast.LENGTH_LONG).show();
                }else if(Mpin.length()<4){
                    Toast.makeText(getApplicationContext(),"Enter 4-digit Pin",Toast.LENGTH_LONG).show();
                }else if(contryid.equals(0)){
                    Toast.makeText(getApplicationContext(),"Select Country",Toast.LENGTH_LONG).show();
                }else if(cityid.equals(0)){
                    Toast.makeText(getApplicationContext(),"Select City",Toast.LENGTH_LONG).show();

                }else {
                    newmethod();
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
                    countnameary.add(0, "-Select Country -");
                    countidary.add(0, "0");
                    country.setSelection(0, false);
                    ArrayAdapter<String> spinnerArrayAdapter;
                    spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_spinner_item, countnameary);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.activity_spinner_item);
                    country.setAdapter(spinnerArrayAdapter);
                    country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int position, long arg3) {
                            contryname=countnameary.get(position);
                            contryid = countidary.get(position);
                            Log.d("statevalue", "onItemSelected: "+contryid);
                            if(!contryid.equals(0)) {
                                Cities();
                                Groups();
                                cityidary.clear();
                                citynameary.clear();
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
    public void Cities(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/GetCitybyCountryId?cnid="+contryid;
        StringRequest data=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    Log.d("data","arraydata"+array);
                    for(int i=0;i<array.length();i++) {
                        JSONObject obj = array.getJSONObject(i);
                        citynameary.add(obj.get("CityName").toString());
                        cityidary.add(obj.get("CityId").toString());
                    }
                    Log.d("fffff","fffff"+citynameary);
                    citynameary.add(0, "-Select City -");
                    cityidary.add(0, "0");
                    cities.setSelection(0, false);
                    ArrayAdapter<String> spinnerArrayAdapter;
                    spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_spinner_item, citynameary);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.activity_spinner_item);
                    cities.setAdapter(spinnerArrayAdapter);
                    cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int position, long arg3) {
                            cityname=citynameary.get(position);
                            cityid = cityidary.get(position);
                            Log.d("statevalue", "onItemSelected: "+cityid);

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
    public void newmethod(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Submiting Details...");
        pDialog.setCancelable(false);
        pDialog.show();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url="http://151.106.38.222:92/api/CustomerRegistration";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("OrgId", "1");
            jsonBody.put("fname",Mname);
            jsonBody.put("lname", lastname);
            jsonBody.put("Mobile", Mmobile);
            jsonBody.put("EmailId", Memail);
            jsonBody.put("Password", Mpin);
            jsonBody.put("CountryId", contryid);
            jsonBody.put("CityId",cityid);
            jsonBody.put("GroupIds", slectitemsid);
            jsonBody.put("IsCmtMember", "0");
            final String requestBody = jsonBody.toString();
              StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("register","respkonse"+response);
                    hidePDialog();
                    //Added Product,Update productcount,Removed Product
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String statuscode=jsonObject.getString("StatusCode");
                        String StatusMessage=jsonObject.getString("StatusMessage");
                         customerid=jsonObject.getString("CustomerId");
                         contryids=jsonObject.getString("CountryId");
                        int value=Integer.parseInt(statuscode);

                        if(StatusMessage.contains("Registered Successfully")) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            Intent intent=new Intent(getApplicationContext(),Home.class);
                            startActivity(intent);
                            finish();
                        }else if(StatusMessage.contains("Already Exists")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Mobile or Email Already Exists", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    hidePDialog();
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
                            slectitems= Arrays.toString(selectednameary.toArray()).replace("[", "").replace("]", "");
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
