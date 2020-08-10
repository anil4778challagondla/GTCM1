package com.gmi.gtcm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button login,register;
    EditText username,password;
    String usertext,pintext,token;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.loginbtnid);
        username=(EditText)findViewById(R.id.editText5);
        password=(EditText)findViewById(R.id.loginpinetid);
        register=(Button)findViewById(R.id.registerbtnid);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
token=sharedPreferences.getString(AppUrls.TOKENID,"");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usertext=username.getText().toString().trim();
                pintext=password.getText().toString().trim();
                if(usertext.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Username",Toast.LENGTH_LONG).show();
                }else if(pintext.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_LONG).show();

                }else if(pintext.length()<4){
                    Toast.makeText(getApplicationContext(),"Invalid Password",Toast.LENGTH_LONG).show();

                }else {
                    Login();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
    public void Login(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/CustomerLogin?oid=1&username="+usertext+"&password="+pintext+"&deviceid="+token+"&apptype=1";
        StringRequest data=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("login",response);
                if(response.contains("CustomerId")){
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String cid=jsonObject.getString("CustomerId");
                    String orgid=jsonObject.getString("orgid");
                    String GroupId=jsonObject.getString("GroupId");
                    String fname=jsonObject.getString("fname");
                    String Mobile=jsonObject.getString("Mobile");
                    String EmailId=jsonObject.getString("EmailId");
                    String Profession=jsonObject.getString("Profession");
                    String CountryId=jsonObject.getString("CountryId");
                    String CityId=jsonObject.getString("CityId");
                    String CustomerImagePath=jsonObject.getString("CustomerImagePath");
                    String SalvationInfo=jsonObject.getString("SalvationInfo");
                    String cmtmember=jsonObject.getString("IsCmtMember");
                    if(cid.equals("0")){
                        Toast.makeText(getApplicationContext(),"Wrong Password or Mobile number",Toast.LENGTH_LONG).show();

                    }else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(AppUrls.CUSTOMERID, cid);
                        editor.putString(AppUrls.ORGID, orgid);
                        editor.putString(AppUrls.GROUPID, GroupId);
                        editor.putString(AppUrls.CMTMEMBER, cmtmember);
                        editor.commit();
                    startActivity(new Intent(getApplicationContext(),Home.class));
                    finish();}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }else {
                    Toast.makeText(getApplicationContext(),"It seems your username and/or password do not match—please try again.",Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(data);
    }

}
