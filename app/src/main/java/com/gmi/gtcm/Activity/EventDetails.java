package com.gmi.gtcm.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmi.gtcm.Adapter.EventsSocialAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.SocialMediaModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventDetails extends AppCompatActivity {
    private TextView title,description,when,location,contactperson,contactmobile,contactemail,organistaion;
    String eventtitle,eventimage,descrip,eventid,startdate,enddate,person,mobile,email,organ,loc,socialmeda, sponsorlist;
    SharedPreferences sharedPreferences;
    private ImageView imageView;
    private EventsSocialAdapter SocialAdapter;
    private List<SocialMediaModel> movieList = new ArrayList<SocialMediaModel>();
    RecyclerView sociallist;
    LinearLayout sponsor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event Details");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sociallist=findViewById(R.id.eventssociallistid);
        title=(TextView)findViewById(R.id.eventtitleid);
        description=(TextView)findViewById(R.id.eventmapsdescriptiontvid);
        when=(TextView)findViewById(R.id.eventwhentv);
        contactperson=(TextView)findViewById(R.id.eventContactnametv);
        contactmobile=(TextView)findViewById(R.id.eventContactmobiletv);
        contactemail=(TextView)findViewById(R.id.eventContactemailtv);
        organistaion=(TextView)findViewById(R.id.eventContactorganisationtv);
        location=(TextView)findViewById(R.id.eventContactlocationtv);
        imageView=(ImageView)findViewById(R.id.eventimageid);
        sponsor=(LinearLayout) findViewById(R.id.sponsor);


        eventtitle = getIntent().getStringExtra("EventTitle");
        eventimage = getIntent().getStringExtra("EventImage");
        eventid= getIntent().getStringExtra("Eventid");
        startdate = getIntent().getStringExtra("StartDate");
        enddate = getIntent().getStringExtra("EndDate");
        person = getIntent().getStringExtra("ContactPerson");
        mobile = getIntent().getStringExtra("ContactPhone");
        email = getIntent().getStringExtra("ContactEmail");
        loc = getIntent().getStringExtra("Location");
        organ = getIntent().getStringExtra("OrganisationName");
        descrip = getIntent().getStringExtra("Description");
        socialmeda = getIntent().getStringExtra("Socialmedia");
        sponsorlist = getIntent().getStringExtra("Sponsorlist");

        title.setText(eventtitle);
        when.setText(startdate +" -> "+ enddate);
        description.setText(descrip);
        contactperson.setText(person);
        contactmobile.setText(mobile);
        contactemail.setText(email);
        location.setText(loc);
        organistaion.setText(organ);
        if (!eventimage.equals("")){
            Picasso.with(this).load(eventimage).into(imageView);
        }

        sociallist.addItemDecoration(new DividerItemDecoration(EventDetails.this, LinearLayoutManager.HORIZONTAL));
        SocialAdapter = new EventsSocialAdapter(movieList, getApplicationContext());
//        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(EventDetails.this, R.dimen.columnspace);
//        sociallist.addItemDecoration(itemDecoration);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(EventDetails.this, LinearLayoutManager.HORIZONTAL, false);
        sociallist.setLayoutManager(verticalLayoutManager);
        sociallist.setAdapter(SocialAdapter);
        try {
            JSONArray jsonArray=new JSONArray(socialmeda);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SocialMediaModel data=new SocialMediaModel();
                data.setESMediaId(jsonObject.getString("ESMediaId"));
                data.setEventId(jsonObject.getString("EventId"));
                data.setSocialMedia(jsonObject.getString("SocialMedia"));
                data.setSocialMediaLogopath(jsonObject.getString("SocialMediaLogopath"));
                data.setSocialMediaLink(jsonObject.getString("SocialMediaLink"));
                data.setMediaTypeId(jsonObject.getString("MediaTypeId"));
                movieList.add(data);
            }
            SocialAdapter.notifyDataSetChanged();


        }catch (Exception e){
            Log.d("sociallist",e.toString());
        }
        try {
            JSONArray data=new JSONArray(sponsorlist);
            for(int i=0; i<= data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                String img = obj.getString("Logo");
                ImageView imgView = new ImageView(EventDetails.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 200);
                imgView.setPadding(5,5,5,5);
                imgView.setLayoutParams(lp);
                Picasso.with(getApplicationContext()).load(img).into(imgView);
                sponsor.addView(imgView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
