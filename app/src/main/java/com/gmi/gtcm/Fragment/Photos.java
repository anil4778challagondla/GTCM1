package com.gmi.gtcm.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.PhotosFragmentAdapter;
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
public class Photos extends Fragment {

    RecyclerView menucycle;
    private List<PhotosModel> Photolist = new ArrayList<PhotosModel>();
    private PhotosFragmentAdapter adapter;
    String custid,merch;
    TextView nomessage;
    LinearLayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    public Photos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_photos, container, false);
        menucycle=(RecyclerView)v.findViewById(R.id.photosrecycleid);
        nomessage=(TextView)v.findViewById(R.id.nophotostvid);
        adapter=new PhotosFragmentAdapter(getActivity(),Photolist);
        menucycle.setLayoutManager(new GridLayoutManager(getActivity(),3));
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(getActivity(), R.dimen.columnspace);
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
                                    JSONArray photos= jsonObject.getJSONArray("Images");
                                    for (int i = 0; i < photos.length(); i++) {
                                        try {
                                            JSONObject obj = photos.getJSONObject(i);
                                            PhotosModel Event=new PhotosModel();
                                                Event.setImgpath(obj.getString("Imgpath"));
                                                Event.setMediaId(obj.getString("MediaId"));
                                                Event.setMediaimgId(obj.getString("MediaimgId"));
                                                Event.setFormatedCreatedDate(obj.getString("FormatedCreatedDate"));
                                                Event.setType(obj.getString("Type"));

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
