package com.gmi.gtcm.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.Image;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PromotionDetails extends AppCompatActivity {
    String dealname,dealimage,merchantiamge,dprice,strcolrname="",strsize="", strimage,strattributname,sprice,dealtype,disc,cond,bsname,strattribute,bsaddress,urls,dealid,attributename;
    ImageView merchantimage,dealsimage;
    TextView dealsname,discs,condition,bname,baddress,url,dealPrice,sellPrice;
    Button getitnow;
    LinearLayout sizeslayout;
    String custid,merch,catename,cateid,name;
    Spinner sizes;
    final ArrayList<String> cateAry = new ArrayList<String>();
    final ArrayList<String> colorAry = new ArrayList<String>();
    final ArrayList<String> sizeAry = new ArrayList<String>();
    SharedPreferences sharedPreferences;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Promotions");
        dealname=getIntent().getStringExtra("dealname");
        dealimage=getIntent().getStringExtra("dealimage");
        merchantiamge=getIntent().getStringExtra("partnerimage");
        disc=getIntent().getStringExtra("disc");
        cond=getIntent().getStringExtra("conditions");
        dprice=getIntent().getStringExtra("dprice");
        sprice=getIntent().getStringExtra("sellingprice");
        attributename=getIntent().getStringExtra("attributename");
        bsname=getIntent().getStringExtra("bname");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");

        merchantimage=(ImageView)findViewById(R.id.imageViewMerchant);
        dealsimage=(ImageView)findViewById(R.id.imageViewDeal);
        dealsname=(TextView)findViewById(R.id.textDealname);
        discs=(TextView)findViewById(R.id.txtdescription);
        condition=(TextView)findViewById(R.id.txtconditions);
        bname=(TextView)findViewById(R.id.businessnameText);
        dealPrice=(TextView)findViewById(R.id.dealPrice);
        sellPrice=(TextView)findViewById(R.id.sellingpkrice);
        getitnow=(Button)findViewById(R.id.btngetitnow);

        dealsname.setText(dealname);
        discs.setText(disc);
        condition.setText(cond);
        bname.setText(bsname);

        dealPrice.setText(dprice);
        dealPrice.setPaintFlags(dealPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        sellPrice.setText(sprice);
        if (!merchantiamge.equals("")){
            Picasso.with(this).load(merchantiamge).into(merchantimage);

        }
        if (!dealimage.equals("")){
            Picasso.with(this).load(dealimage).into(dealsimage);

        }
              try {
            JSONArray array=new JSONArray(attributename);
            for(int i=0;i<array.length();i++){
                JSONObject obj=array.getJSONObject(i);
                strattributname=obj.getString("Attribute");
                if(strattributname.equalsIgnoreCase("Color")){
                    String colodata= obj.get("values").toString();
                    JSONArray colorsarray=new JSONArray(colodata);
                    for(int j=0;j<colorsarray.length();j++){
                        String sizedatas=colorsarray.getString(j);
                        colorAry.add(sizedatas);

                    }



                }else if(strattributname.equalsIgnoreCase("Mens Size")){
                    String sizedata= obj.get("values").toString();
                    JSONArray colorsarray=new JSONArray(sizedata);
                    for(int k=0;k<colorsarray.length();k++){
                        String sizedatas=colorsarray.getString(k);
                        sizeAry.add(sizedatas);

                    }
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getitnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attributename.contains("Mens Size")&&attributename.contains("Color")){

                    buildDialog1(R.style.DialogAnimation, "Up - Down Animation!");

                }else if(strattribute.contains("Mens Size")&&!strattribute.contains("Color")){
                    buildDialog3(R.style.DialogAnimation, "Up - Down Animation!");

                }else if(strattribute.contains("Color")&&!strattribute.contains("Mens Size")){
                    buildDialog(R.style.DialogAnimation, "Up - Down Animation!");

                }else {
                    Offerdata();
                }
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildDialog(int animationSource, String type) {
        final Dialog dialog = new Dialog(PromotionDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog);
        ImageView close=(ImageView) dialog.findViewById(R.id.alertcloseid);
        TextView title=(TextView) dialog.findViewById(R.id.alerttitleid);
        title.setText("Select Color");
        ListView listView = (ListView)dialog.findViewById(R.id.alertlistid);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.mylist, R.id.textViewpop,colorAry);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                strcolrname=colorAry.get(position);
                Log.d("statevalue", "onItemSelected: "+strcolrname);
                if(!strcolrname.isEmpty()){
                    Offerdata();
                    dialog.dismiss();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window wn=dialog.getWindow();
        wn.setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = animationSource;

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
    private void buildDialog1(int animationSource, String type) {
        final Dialog dialog = new Dialog(PromotionDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog);
        ImageView close=(ImageView) dialog.findViewById(R.id.alertcloseid);
        TextView title=(TextView) dialog.findViewById(R.id.alerttitleid);
        title.setText("Select Size");
        ListView listView = (ListView)dialog.findViewById(R.id.alertlistid);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.mylist, R.id.textViewpop,sizeAry);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                strsize=sizeAry.get(position);
                Log.d("statevalue", "onItemSelected: "+strsize);
                if(!strsize.isEmpty()){
                    buildDialog(R.style.DialogAnimation, "Up - Down Animation!");
                    dialog.dismiss();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window wn=dialog.getWindow();
        wn.setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = animationSource;

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
    private void buildDialog3(int animationSource, String type) {
        final Dialog dialog = new Dialog(PromotionDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog);
        ImageView close=(ImageView) dialog.findViewById(R.id.alertcloseid);
        TextView title=(TextView) dialog.findViewById(R.id.alerttitleid);
        title.setText("Select Size");
        ListView listView = (ListView)dialog.findViewById(R.id.alertlistid);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.mylist, R.id.textViewpop,sizeAry);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                strsize=sizeAry.get(position);
                Log.d("statevalue", "onItemSelected: "+strsize);
                if(!strsize.isEmpty()){
                    Offerdata();
                    dialog.dismiss();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window wn=dialog.getWindow();
        wn.setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = animationSource;

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
    @SuppressLint("UnsafeExperimentalUsageError")
    private void buildDialog2(int animationSource, String type) {
        final Dialog dialog = new Dialog(PromotionDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogcartadded);
        float radius = getResources().getDimension(R.dimen.dp_50);


        TextView title=(TextView) dialog.findViewById(R.id.alerttitleid);
        ImageView offerimages=(ImageView) dialog.findViewById(R.id.alertofferimagedetailsid);
        TextView offername=(TextView) dialog.findViewById(R.id.offeralertnametvid);
        offername.setText(dealname);
        TextView offercost=(TextView) dialog.findViewById(R.id.offeralertcosttvid);
        offercost.setText("$"+sprice);
        if (!dealimage.equals("")) {
            Picasso.with(getApplicationContext()).load(dealimage).into(offerimages);
        }
        title.setText("REDEEM SUCCESSFULLY");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
        Window wn=dialog.getWindow();
        wn.setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = animationSource;

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
    public void Offerdata(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Adding to Cart...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url="http://151.106.38.222:92/api/RedeemPromotion";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("CustomerId", custid);
            jsonBody.put("AppId","1");
            jsonBody.put("PartnerId", "1");
            jsonBody.put("OrgId", "0");
            jsonBody.put("GroupId", "0");
            jsonBody.put("RedeemCode", "100pm");
            jsonBody.put("PromotionId","1" );
            jsonBody.put("ItemCost", sellPrice);
            jsonBody.put("ItemQty", "1");
            jsonBody.put("ItemTotalCost", sellPrice);
            jsonBody.put("Attributes", "size:"+strsize+";color:"+strcolrname+";");
            final String requestBody = jsonBody.toString();
            Log.d("Offer_data","requestdata"+requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Offer_data","responses"+response);
                    hidePDialog();
//                    {
//                        "Id": 8,
//                            "StatusCode": 1,
//                            "StatusMessage": null
//                    }
                    try {
                        JSONObject obj = new JSONObject(response);
                        int statuscode=obj.getInt("StatusCode");
                        String statusmessage=obj.getString("StatusMessage");

                        if(statuscode>0){
                            buildDialog2(R.style.DialogAnimation, "Up - Down Animation!");
                            sizeAry.clear();
                            colorAry.clear();
                        }else {
                            Toast.makeText(getApplicationContext(),statusmessage,Toast.LENGTH_LONG).show();

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
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
