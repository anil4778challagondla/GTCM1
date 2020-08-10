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

import com.gmi.gtcm.Activity.EventDetails;
import com.gmi.gtcm.Model.EventsModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsAdapter extends BaseAdapter {
    Context mContext;
    private Activity activity;
    private LayoutInflater inflater;
    private List<EventsModel> Eventitems;
    private String MerchantId,socialmedia,MerchantStoreId,CustomerId,EventId,OrganisationName,EventTitle,Description,
            EventImage,Location,Latitude,Longitude,StartDate,EndDate,ContactPerson,ContactEmail,ContactPhone,
            EventTypeId,EventTypeName,Status,CreatedDate,ModifiedDate,Bookingneeded,ticketprice,ticketsoldout,bookingavaliable;


    public EventsAdapter(Activity activity, List<EventsModel> eventitems) {
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
        EventsModel m = Eventitems.get(position);
        title.setText(m.getTitle());
        desc.setText(m.getDescription());
        date.setText("Date:"+m.getStartDateString());
        if (!m.getEventImagePath().equals("")) {
            Picasso.with(activity).load(m.getEventImagePath()).fit().into(eventimage);
        }
        viewimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsModel m = Eventitems.get(position);
                EventTitle = m.getTitle();
                EventImage = m.getEventImagePath();
                Location = m.getLocation();
                Description = m.getDescription();
                StartDate =m.getStartDateString();
                EndDate = m.getEndDateString();
                EventId = m.getEventId();
                OrganisationName = m.getOrgName();
                ContactPerson = m.getContactPerson();
                ContactPhone = m.getContactPhone();
                ContactEmail = m.getContactEmail();
                socialmedia=m.getSocialMedia();


                Intent intent = new Intent(activity, EventDetails.class);
                intent.putExtra("OrganisationName", OrganisationName);
                intent.putExtra("EventTitle", EventTitle);
                intent.putExtra("Description", Description);
                intent.putExtra("EventImage", EventImage);
                intent.putExtra("StartDate", StartDate);
                intent.putExtra("EndDate", EndDate);
                intent.putExtra("Eventid", EventId);
                intent.putExtra("Location", Location);
                intent.putExtra("ContactPerson", ContactPerson);
                intent.putExtra("ContactEmail", ContactEmail);
                intent.putExtra("ContactPhone", ContactPhone);
                intent.putExtra("Socialmedia", socialmedia);
                intent.putExtra("Sponsorlist", m.getSponsorlist());


                activity.startActivity(intent);


            }
        });
        return convertView;
    }

}

