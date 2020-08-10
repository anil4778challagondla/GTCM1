package com.gmi.gtcm.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
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
import com.gmi.gtcm.Activity.Newchatmessage;
import com.gmi.gtcm.Adapter.ChatAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.ChatModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Chat extends Fragment {
    private ChatAdapter adapter;
    RecyclerView Eventsrec;
    SharedPreferences sharedPreferences;

    String custid,merch;
    FloatingActionButton newchat;
    LinearLayoutManager mLayoutManager;
    private List<ChatModel> Eventlist = new ArrayList<ChatModel>();
    public Chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_chat, container, false);
        Eventsrec = (RecyclerView) v.findViewById(R.id.gridViewchat);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        newchat=v.findViewById(R.id.fab);
        newchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Newchatmessage.class);
                startActivity(intent);
            }
        });

        adapter=new ChatAdapter(getContext(), Eventlist);
        mLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        Eventsrec.setLayoutManager(mLayoutManager);
        Eventsrec.setAdapter(adapter);
//        final Handler handler = new Handler();
//        final int delay = 60000; //milliseconds
//
//        handler.postDelayed(new Runnable(){
//            public void run(){
//                GETEVENTS();
//                handler.postDelayed(this, delay);
//            }
//        }, delay);

        GETEVENTS();
        return v;
    }
    public void GETEVENTS(){
        Eventlist.clear();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url = "http://151.106.38.222:1000/api/GetChatList?cid=11";
        Log.d("sss","url:"+url);
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("c", response.toString());


                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                ChatModel Members=new ChatModel();
                                Members.setChatID(obj.getLong("ChatId"));
                                Members.setCustomerID(obj.getLong("CustomerId"));
                                Members.setLastMessageID(obj.getLong("LastMessageId"));
                                Members.setMessageText(obj.getString("MessageText"));
                                Members.setName(obj.getString("Name"));
                                Members.setImagePath(obj.getString("ImagePath"));
                                Members.setVideoPath(obj.getString("VideoPath"));
                                Members.setProfileImage(obj.getString("ProfileImage"));
                                Members.setLastMessageDatestring(obj.getString("LastMessageDatestring"));
                                Members.setCreatedDateString(obj.getString("CreatedDateString"));

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
