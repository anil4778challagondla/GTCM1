package com.gmi.gtcm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.CommitteInterface;
import com.gmi.gtcm.Model.MessageModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CommitteemessageAdapteer extends BaseAdapter {
    Context mContext;
    private Activity activity;
    private LayoutInflater inflater;
    private List<MessageModel> Eventitems;
    SharedPreferences sharedPreferences;
    private CommitteInterface mInterface;
    String cmutid,custid,mssgid;
    public CommitteemessageAdapteer(Activity activity, List<MessageModel> eventitems,CommitteInterface mInterface) {
        this.activity = activity;
        this.Eventitems = eventitems;
        this.mInterface = mInterface;
    }

    @Override
    public int getCount() {
        return Eventitems.size();
    }

    @Override
    public Object getItem(int location) {
        return Eventitems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.messagelistcustom, null);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        cmutid=sharedPreferences.getString(AppUrls.CMTMEMBER,"");
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");


        TextView title = (TextView) convertView.findViewById(R.id.messagelistprofilenametvid);
        TextView date = (TextView) convertView.findViewById(R.id.messagelistdatetvid);
        TextView desc = (TextView) convertView.findViewById(R.id.messagelistmessagetvid);
        ImageView eventimage=(ImageView)convertView.findViewById(R.id.messagelistprofileimageid);
        LinearLayout delete=(LinearLayout)convertView.findViewById(R.id.deletelinearlayout) ;
       delete.setVisibility(View.VISIBLE);

        final MessageModel m = Eventitems.get(position);
        title.setText(m.getSenderName());
        date.setText(m.getFormatedPostedDate());
        desc.setText(m.getMessage());
        if (!m.getImage().equals("")){
            Picasso.with(activity).load(m.getImage()).into(eventimage);

        }
          delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mssgid=m.getGMSGId();
                sendmethod();
            }
        });

        return convertView;
    }
    public  void sendmethod(){
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = "http://151.106.38.222:92/api/removemessage?orgid=1&cid="+custid+"&msgid="+mssgid;
        StringRequest data=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj=new JSONObject(response);
                    String statuscode=obj.getString("StatusCode");
                    String statusmessage=obj.getString("StatusMessage");
                    if(statuscode.equals("1")){

                        Toast.makeText(activity,statusmessage,Toast.LENGTH_LONG).show();
                        mInterface.onclick();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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


