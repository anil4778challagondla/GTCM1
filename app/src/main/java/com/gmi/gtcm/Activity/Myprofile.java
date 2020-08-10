package com.gmi.gtcm.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.CheckConnection;
import com.gmi.gtcm.Model.EventsModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Myprofile extends AppCompatActivity {
    ImageView profileimage;
    TextView profilename,mobile,email,dateob,loaction;
    Button editprofile;
    private ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String custid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        profileimage=(ImageView)findViewById(R.id.profileimageid);
        profilename=(TextView)findViewById(R.id.profilenametvid);
        mobile=(TextView)findViewById(R.id.profilenumbertvid);
        email=(TextView)findViewById(R.id.profileemailtvid);
        dateob=(TextView)findViewById(R.id.profilebirthdaytvid);
        loaction=(TextView)findViewById(R.id.profilelocationtvid);
        editprofile=(Button)findViewById(R.id.editprofilebtnid);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        ImageView home = (ImageView) findViewById(R.id.homebtnid);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();

            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Loading Profile");
        pDialog.show();
        connection();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/GetCustomerDetails?cid="+custid;
        JsonObjectRequest movieReq = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {
                        Log.d("", obj.toString());
                        hidePDialog();

                                try {
                                    profilename.setText(obj.getString("fname")+""+obj.getString("lname"));
                                    mobile.setText(obj.getString("Mobile"));
                                    email.setText(obj.getString("EmailId"));
                                    dateob.setText(obj.getString("fname")+""+obj.getString("lname"));
                                    loaction.setText(obj.getString("CountryName"));
                                    if (!obj.getString("CustomerImagePath").equals("")){
                                        Picasso.with(getApplicationContext()).load(obj.getString("CustomerImagePath")).into(profileimage);

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error: " + error.getMessage());
                hidePDialog();
            }
        });
        requestQueue.add(movieReq);

    }
    @SuppressLint("WrongConstant")
    public void connection(){
        if(CheckConnection.isInternetAvailable(Myprofile.this)) //returns true if internet available
        {

        }
        else
        {
            Toast.makeText(Myprofile.this,"No Internet Connection",1000).show();
        }
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
