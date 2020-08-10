package com.gmi.gtcm.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.GamesAdapter;
import com.gmi.gtcm.GridSpacingItemDecoration;
import com.gmi.gtcm.Model.Gamesmodel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Gameslist extends AppCompatActivity {
    private RecyclerView gv=null;
    private GamesAdapter adapter;
    private List<Gamesmodel> EventList = new ArrayList<Gamesmodel>();
    ProgressDialog pDialog;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameslist);
        gv=(RecyclerView) findViewById(R.id.gamesrecyclerviewid);
        adapter=new GamesAdapter(this, EventList);
        gv.setLayoutManager(new GridLayoutManager(this,2));
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(this, R.dimen.columnspace);
        gv.addItemDecoration(itemDecoration);
        gv.setAdapter(adapter);
        Offerdata();
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Games");


    }
    public void Offerdata(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Games...\nPlease Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://151.106.38.222:92//api/GetGameByCustomer?orgid=1&gid=0&pid=0";
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("c", response.toString());

                        hidePDialog();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Gamesmodel Event=new Gamesmodel();
                                Event.setGameId(obj.getString("GameId"));
                                Event.setGameTitle(obj.getString("GameTitle"));
                                Event.setGameImagepath(obj.getString("GameImagepath"));
                                Event.setGameDescription(obj.getString("GameDescription"));
                                Event.setConditionsApply(obj.getString("ConditionsApply"));
                                Event.setStartDatestring(obj.getString("StartDatestring"));
                                Event.setEndDatestring(obj.getString("EndDatestring"));

                                Event.setScratchImagePath(obj.getString("ScratchImagePath"));
                                EventList.add(Event);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(movieReq);


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
