package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.GoodsrecyclerAdapter;
import com.gmi.gtcm.GridSpacingItemDecoration;
import com.gmi.gtcm.Model.OfferModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Promotions extends AppCompatActivity {
    private RecyclerView gv=null;
    private GoodsrecyclerAdapter adapter;
    private List<OfferModel> EventList = new ArrayList<OfferModel>();
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        setActionBarTitle();

        gv=(RecyclerView) findViewById(R.id.offersrecyclerviewid);
        adapter=new GoodsrecyclerAdapter(this, EventList);
        gv.setLayoutManager(new GridLayoutManager(this,2));
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(this, R.dimen.columnspace);
        gv.addItemDecoration(itemDecoration);
        gv.setAdapter(adapter);
        Offerdata();
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Promotions");


    }
    public void Offerdata(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Offers...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url="http://151.106.38.222:92/api/GetPromotionByApp";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("AppId","1");
            jsonBody.put("OrgId", "1");
            jsonBody.put("PartnerId", "0");
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Offer_data","response"+response);
                    try {
                        JSONArray array=new JSONArray(response);
                        Log.d("data","arraydata"+array);
                        for(int i=0;i<array.length();i++) {
                            JSONObject obj = array.getJSONObject(i);
                            hidePDialog();
                            OfferModel Event=new OfferModel();
                            Event.setPromotionName(obj.getString("PromotionName"));
                            Event.setPromotionImagePath(obj.getString("PromotionImagePath"));
                            Event.setPromotionId(obj.getString("PromotionId"));
                            Event.setDescription(obj.getString("Description"));
                            Event.setRetailPrice(obj.getString("RetailPrice"));
                            Event.setSellingPrice(obj.getString("SellingPrice"));
                            Event.setAttrlist(obj.getString("Attrlist"));
                            Event.setClientId(obj.getString("OrgId"));
                            Event.setBusinessId(obj.getString("GroupId"));
                            Event.setPartnerId(obj.getString("PartnerId"));
                            Event.setBusinessName(obj.getString("PartnerName"));
                            Event.setPartnerLogoPath(obj.getString("PartnerLogoPath"));

                            EventList.add(Event);
                        }
                        adapter.notifyDataSetChanged();



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
