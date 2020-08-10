package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.CustomRedeemAdapter;
import com.gmi.gtcm.Adapter.GamescartAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.Gamescart;
import com.gmi.gtcm.Model.Redeemmodel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Reedem extends AppCompatActivity {
    LinearLayout offerslayout,gameslayout;
    LinearLayout offers,games;
    private ProgressDialog pDialog;
    private List<Redeemmodel> EventList = new ArrayList<Redeemmodel>();
    private List<Gamescart> gamelist = new ArrayList<Gamescart>();
    private RecyclerView listView,listView1;
    private CustomRedeemAdapter adapter1;
    private GamescartAdapter adapter;
    LinearLayoutManager mlayoutManager,mlayoutManager1;
    SharedPreferences sharedPreferences;
    private String custid;
    TextView tabpromo,tabgames;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reedem);
        listView = (RecyclerView)findViewById(R.id.offercartrecyclerviewid);
        listView1= (RecyclerView)findViewById(R.id.gamescartrecyclerviewid);
        adapter1 = new CustomRedeemAdapter(this, EventList);
        mlayoutManager1=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        listView.setLayoutManager(mlayoutManager1);
        listView.setAdapter(adapter1);
        adapter = new GamescartAdapter(this, gamelist);
        mlayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        listView1.setLayoutManager(mlayoutManager);
        listView1.smoothScrollToPosition(listView.getBottom());

        listView1.setAdapter(adapter);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        offerslayout=(LinearLayout)findViewById(R.id.offerslayourid);
        offers=(LinearLayout)findViewById(R.id.offertabbtn);
        gameslayout=(LinearLayout)findViewById(R.id.gameslayourid);
        games=(LinearLayout)findViewById(R.id.gamestabbtn);
        tabpromo=(TextView) findViewById(R.id.tabpromotvid);
        tabgames=(TextView) findViewById(R.id.tabgamestvid);
        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerslayout.setVisibility(View.VISIBLE);
                gameslayout.setVisibility(View.GONE);
                tabpromo.setTextColor(getResources().getColor(R.color.logocolor));
                tabgames.setTextColor(getResources().getColor(R.color.white));
                offers.setBackgroundColor(getResources().getColor(R.color.white));
                games.setBackgroundColor(getResources().getColor(R.color.logocolor));


            }
        });
        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerslayout.setVisibility(View.GONE);
                gameslayout.setVisibility(View.VISIBLE);
                tabpromo.setTextColor(getResources().getColor(R.color.white));
                tabgames.setTextColor(getResources().getColor(R.color.logocolor));
                offers.setBackgroundColor(getResources().getColor(R.color.logocolor));
                games.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        redeemdata();
        gamesdata();
    }
    private void redeemdata(){
        gamelist.clear();
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Loading your Games...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92//api/GetCustomerWonPrizes?cid="+custid+"&orgid=1";
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("data","data:"+response);
                        hidePDialog();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Gamescart Event = new Gamescart();
                                Event.setPrizeId(obj.getString("PrizeId"));
                                Event.setFinished(obj.getString("Finished"));
                                Event.setPrizeName(obj.getString("PrizeName"));
                                Event.setPrizeImagePath(obj.getString("PrizeImagePath"));
                                Event.setCustomerGameFrequencyId(obj.getString("CustomerGameFrequencyId"));
                                Event.setPrizeNumber(obj.getString("PrizeNumber"));
                                Event.setFrequencyId(obj.getString("FrequencyId"));
                                gamelist.add(Event);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();

            }
        });
        requestQueue.add(movieReq);
    }

    private void gamesdata(){
        EventList.clear();
        RequestQueue requestQueue1 = Volley.newRequestQueue((this));

        String url = "http://151.106.38.222:92/api/GetCustomerRedeemPromotions?orgid=1&cid="+custid;
        JsonArrayRequest movieReq1 = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hidePDialog();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Redeemmodel Event = new Redeemmodel();
                                Event.setPromotionName(obj.getString("PromotionName"));
                                Event.setPromotionImagePath(obj.getString("PromotionImagePath"));
                                Event.setPromotionId(obj.getString("PromotionId"));
                                Event.setRedeemCode(obj.getString("RedeemCode"));
                                Event.setCreatedDatestring(obj.getString("CreatedDatestring"));
                                Event.setAttributes(obj.getString("Attributes"));

                                Event.setItemCost(obj.getString("ItemCost"));
                                EventList.add(Event);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter1.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue1.add(movieReq1);
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
