package com.gmi.gtcm.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import in.codeshuffle.scratchcardlayout.listener.ScratchListener;
import in.codeshuffle.scratchcardlayout.ui.ScratchCardLayout;

public class GameDetails extends AppCompatActivity {
        ImageView back, playnow;
        ImageView offerimage;
        TextView offername, desc, cond, sellingprice, findus;
        String strname,strscratchimage,custid,PrizeNumber,Interval,IntervalId,CustomerGameFrequencyId,strattributname,strcolrname="",strsize="", strimage, strbuystyle, strdesc, strretprice, strsellprice, strpromid, strclientid, strbussid, strattribute;
        RecyclerView recyclerView;
        ProgressDialog pDialog;
        Dialog alertdialog;
    Drawable d,f,g;

    ScratchCardLayout scratchCardLayout1;
        int Statuscode,PrizeId;
        String statusmessage,WinMessage,PrizeImagePath,ScratchImagePath;
        SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        back = (ImageView) findViewById(R.id.gamedetailbackbtnid);
        offerimage = findViewById(R.id.gameimagedetailsid);
        playnow = (ImageView) findViewById(R.id.gamedetailsplaynowbtnid);
        offername = (TextView) findViewById(R.id.gamenamedetailsid);
        desc = (TextView) findViewById(R.id.gamedetailsdescid);
        cond = (TextView) findViewById(R.id.gamedetailscondid);
        alertdialog=new Dialog(this);

        strname = getIntent().getStringExtra("gamename");
        strdesc = getIntent().getStringExtra("gamedesc");
        strpromid = getIntent().getStringExtra("gameid");
        strclientid = getIntent().getStringExtra("clientid");
        strbussid = getIntent().getStringExtra("bussinessid");
        strretprice = getIntent().getStringExtra("conditon");
        strimage = getIntent().getStringExtra("image");
        strscratchimage = getIntent().getStringExtra("scratchimage");
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        playnow.setVisibility(View.GONE);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            d = drawableFromUrl(strscratchimage);

        } catch (IOException e) {
            d = getResources().getDrawable(R.drawable.applogo);
            e.printStackTrace();

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        playnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Statuscode>0){
                    alertdialog.setContentView(R.layout.customalertdialog);

                   final TextView message=(TextView)alertdialog.findViewById(R.id.gametvid);
                   final ImageView image=(ImageView) alertdialog.findViewById(R.id.gameimageid);
                    CardView collect=(CardView) alertdialog.findViewById(R.id.collectcardView);
                    scratchCardLayout1 = alertdialog.findViewById(R.id.scratchCard2);

                    scratchCardLayout1.setScratchDrawable(d);
                    scratchCardLayout1.setScratchWidthDip(70f);
                    scratchCardLayout1.setScratchEnabled(true);
                    scratchCardLayout1.resetScratch();
                    scratchCardLayout1.setScratchListener(new ScratchListener() {
                        @Override
                        public void onScratchStarted() {

                        }

                        @Override
                        public void onScratchProgress(ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent) {
                            if(atLeastScratchedPercent>=20){
                                scratchCardLayout.onFullReveal();
                                scratchCardLayout1.setScratchEnabled(false);
                                if (!PrizeImagePath.equals("")){
                                    Picasso.with(getApplicationContext()).load(PrizeImagePath).into(image);

                                }
                                   message.setText(WinMessage);
                                Revealed();

                            }
                        }

                        @Override
                        public void onScratchComplete() {

                        }
                    });
                    if(PrizeId>0){
                        collect.setVisibility(View.VISIBLE);
                        alertdialog.setCanceledOnTouchOutside(false);
                    }else {
                        collect.setVisibility(View.GONE);
                        alertdialog.setCanceledOnTouchOutside(true);
                    }
                    collect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertdialog.dismiss();
                        }
                    });

                    alertdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertdialog.show();
                }else {
                    Toast.makeText(getApplicationContext(),statusmessage,Toast.LENGTH_LONG).show();
                }
            }
        });

        Offerdata();
        getSupportActionBar().setTitle("Game Details");
    }
    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }
    private void display(){
        offername.setText(strname);
        desc.setText(strdesc);
        cond.setText( strretprice);
        playnow.setVisibility(View.VISIBLE);
        if (!strimage.equals("")){
            Picasso.with(getApplicationContext()).load(strimage).into(offerimage);
        }

    }

    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Game Details");


    }
    public void Offerdata(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Game Details...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://151.106.38.222:92//api/GetCustomerGameDetails?gid="+strpromid+"&cid="+custid;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                display();
                hidePDialog();
                try {
                    JSONObject data=new JSONObject(response);
                    String Result=data.getString("Result");
                    JSONObject resultdata=new JSONObject(Result);
                    Statuscode=resultdata.getInt("StatusCode");
                    statusmessage=resultdata.getString("StatusMessage");
                    String Details=data.getString("Details");
                    JSONObject detailsdata=new JSONObject(Details);
                    PrizeId=detailsdata.getInt("PrizeId");
                    PrizeImagePath=detailsdata.getString("PrizeImagePath");
                    ScratchImagePath=detailsdata.getString("ScratchImagePath");
                    WinMessage=detailsdata.getString("WinMessage");
                    PrizeNumber=detailsdata.getString("PrizeNumber");
                    Interval=detailsdata.getString("Interval");
                    IntervalId=detailsdata.getString("IntervalId");
                    CustomerGameFrequencyId=detailsdata.getString("CustomerGameFrequencyId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
            }
        });

        requestQueue.add(stringRequest);


    }


    public void Revealed(){

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url="http://151.106.38.222:92//api/UpdateCustomerFrequency";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("CustomerGameFrequencyId",CustomerGameFrequencyId);
            jsonBody.put("CustomerId", custid);
            jsonBody.put("GameId", strpromid);
            jsonBody.put("PrizeNumber", PrizeNumber);
            jsonBody.put("Interval", Interval);
            jsonBody.put("IntervalId", IntervalId);
            jsonBody.put("PrizeId", PrizeId);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Offer_data","response"+response);
                    //                    {
                    //                        "Id": 0,
                    //                            "StatusCode": 27,
                    //                            "StatusMessage": "You Got Prize"
                    //                    }
                    try {
                        JSONObject data=new JSONObject(response);
                        String StatusCode=data.getString("StatusCode");
                        String StatusMessage=data.getString("StatusMessage");
                        //                        Toast.makeText(getApplicationContext(),StatusMessage,Toast.LENGTH_LONG).show();
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
