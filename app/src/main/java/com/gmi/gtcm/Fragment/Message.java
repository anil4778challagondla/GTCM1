package com.gmi.gtcm.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.WallmessageAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.Wallmessages;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Message extends Fragment {
    private WallmessageAdapter adapter;
    RecyclerView Eventsrec;
    SharedPreferences sharedPreferences;

    String custid,merch;
    LinearLayoutManager mLayoutManager;
    private List<Wallmessages> Eventlist = new ArrayList<Wallmessages>();
    public Message() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_message, container, false);
        Eventsrec = (RecyclerView) v.findViewById(R.id.gridViewmessage);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");

        adapter=new WallmessageAdapter(getContext(), Eventlist);
        mLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        Eventsrec.setLayoutManager(mLayoutManager);
        Eventsrec.setAdapter(adapter);
        GETEVENTS();
        return v;
    }
    public void GETEVENTS(){

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url = "http://151.106.38.222:1000/api/getcustomerwallmessages?orgid=1&cid=11&pgsize=-1&pgindex=1&str=&sortby=1";
        Log.d("sss","url:"+url);
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("c", response.toString());


                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Wallmessages Members=new Wallmessages();
                                Members.setGmsgID(obj.getLong("GMSGId"));
                                Members.setCustomerID(obj.getLong("CustomerId"));
                                Members.setMessage(obj.getString("Message"));
                                Members.setCustomerImagePath(obj.getString("CustomerImagePath"));
                                Members.setFormatedPostedDate(obj.getString("FormatedPostedDate"));
                                Members.setStatus(obj.getLong("Status"));
                                Members.setSenderName(obj.getString("SenderName"));
                                Members.setSender(obj.getLong("Sender"));
                                Members.setImagepath(obj.getString("Imagepath"));
                                Members.setVideopath(obj.getString("Videopath"));
                                Eventlist.add(Members);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(movieReq);
    }
}
