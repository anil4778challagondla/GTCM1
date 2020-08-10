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

import com.gmi.gtcm.Activity.Media;
import com.gmi.gtcm.Model.EventsModel;
import com.gmi.gtcm.Model.MediaModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MediaAdapter extends BaseAdapter {
    Context mContext;
    private Activity activity;
    private LayoutInflater inflater;
    private List<MediaModel> Eventitems;

    public MediaAdapter(Activity activity, List<MediaModel> eventitems) {
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
            convertView = inflater.inflate(R.layout.eventscustomlayout, null);



        TextView title = (TextView) convertView.findViewById(R.id.eventtitletvid);
        TextView date = (TextView) convertView.findViewById(R.id.eventdatetvid);
        TextView desc = (TextView) convertView.findViewById(R.id.desctvid);

        ImageView eventimage=(ImageView)convertView.findViewById(R.id.icon_header_logo);
        ImageView viewimg=(ImageView)convertView.findViewById(R.id.viewivid);


        MediaModel m = Eventitems.get(position);


        title.setText(m.getTitle());
        desc.setText(m.getDescription());
        date.setText("Date:"+m.getFormatedCreatedDate());
        if (!m.getMediaImage().equals("")) {
            Picasso.with(activity).load(m.getMediaImage()).fit().into(eventimage);
        }
        viewimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity.getApplicationContext(), Media.class);
                intent.putExtra("mediaid",Eventitems.get(position).getMediaId());
                activity.startActivity(intent);
            }
        });


        return convertView;
    }

}

