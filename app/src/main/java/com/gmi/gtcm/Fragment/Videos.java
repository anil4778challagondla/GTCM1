package com.gmi.gtcm.Fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.PhotosFragmentAdapter;
import com.gmi.gtcm.Adapter.VideoAdapter;
import com.gmi.gtcm.GridSpacingItemDecoration;
import com.gmi.gtcm.Model.PhotosModel;
import com.gmi.gtcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Videos extends Fragment {
    RecyclerView menucycle;
    private List<PhotosModel> Photolist = new ArrayList<PhotosModel>();
    private VideoAdapter adapter;
    String custid,merch,mediaid;
    TextView nomessage;
    LinearLayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;

    public Videos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_videos, container, false);
        menucycle=(RecyclerView)v.findViewById(R.id.videosrecycleid);
        nomessage=(TextView)v.findViewById(R.id.novideostvid);
        adapter = new VideoAdapter(getActivity(),Photolist);
        mLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        menucycle.setLayoutManager(mLayoutManager);
        menucycle.setAdapter(adapter);
        Photos();
        return v;
    }
    private void Photos(){
        if(Photolist.size()!=0){
            Photolist.clear();
        }
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url = "http://151.106.38.222:92/api/getmediadetails?mediaid=1";
        Log.d("sss","url:"+url);
        StringRequest movieReq = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("c", response);
                        if(response.toString().contains("MediaDetails")){
                            nomessage.setVisibility(View.GONE);
                            menucycle.setVisibility(View.VISIBLE);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                JSONArray photos= jsonObject.getJSONArray("Videos");
                                for (int i = 0; i < photos.length(); i++) {
                                    try {
                                        JSONObject obj = photos.getJSONObject(i);
                                        PhotosModel Event=new PhotosModel();
                                        Event.setImgpath(obj.getString("Imgpath"));
                                        Event.setMediaId(obj.getString("MediaId"));
                                        Event.setMediaimgId(obj.getString("MediaimgId"));
                                        Event.setFormatedCreatedDate(obj.getString("FormatedCreatedDate"));
                                        Event.setType(obj.getString("Type"));
                                        Event.setTitle(obj.getString("Title"));
                                        Event.setThumbnailpath(obj.getString("Thumbnailpath"));

                                        Photolist.add(Event);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }else {
                            nomessage.setVisibility(View.VISIBLE);
                            menucycle.setVisibility(View.GONE);
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
