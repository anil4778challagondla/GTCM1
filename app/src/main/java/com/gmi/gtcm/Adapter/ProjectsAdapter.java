package com.gmi.gtcm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmi.gtcm.Activity.ProjectsDetails;
import com.gmi.gtcm.Model.Partnersmodel;
import com.gmi.gtcm.Model.Projectmodel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProjectsAdapter extends BaseAdapter {
    Context mContext;
    private Activity activity;
    private LayoutInflater inflater;
    private List<Projectmodel> Eventitems;

    public ProjectsAdapter(Activity activity, List<Projectmodel> eventitems) {
        this.activity = activity;
        this.Eventitems = eventitems;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.partnerscustomlist, null);
        TextView title = (TextView) convertView.findViewById(R.id.partnertitletvid);
        TextView city = (TextView) convertView.findViewById(R.id.partnercitytvid);
        TextView country = (TextView) convertView.findViewById(R.id.partnercountrytvid);
        TextView pincode = (TextView) convertView.findViewById(R.id.partnerpincodetvid);
        ImageView eventimage=(ImageView)convertView.findViewById(R.id.icon_header_logo);
        ImageView view=(ImageView)convertView.findViewById(R.id.viewivid);

         Projectmodel m = Eventitems.get(position);
        title.setText(m.getPartnerName());
        city.setText(m.getCityName());
        country.setText(m.getCountryName());
        pincode.setText(m.getPostCode());
        if (!m.getLogoPath().equals("")){
            Picasso.with(activity).load(m.getLogoPath()).fit().into(eventimage);
        }
          view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Projectmodel m = Eventitems.get(position);
                Intent intent=new Intent(activity, ProjectsDetails.class);
                intent.putExtra("projectid",m.getPartnerId());
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

}

