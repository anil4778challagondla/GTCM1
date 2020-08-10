package com.gmi.gtcm.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmi.gtcm.Adapter.EventsSocialAdapter;
import com.gmi.gtcm.Model.SocialMediaModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PartnerDetails extends AppCompatActivity {
    private TextView name, mobile, email, website, livestream, desc, city, country, postalcode, mission;
    private ImageView partnerimage;
    CardView livestreamcard;
    private EventsSocialAdapter SocialAdapter;
    private List<SocialMediaModel> movieList = new ArrayList<SocialMediaModel>();
    RecyclerView sociallist;
    private String strname, strmobile, stremail, strwebsite, strcity, strcountry, strpostalcode, strdesc, strmission, strimage, strlivelink, strsocialmedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_details);
        name = (TextView) findViewById(R.id.partnernametvid);
        email = (TextView) findViewById(R.id.partneremailidtvid);
        mobile = (TextView) findViewById(R.id.partnermobiletvid);
        website = (TextView) findViewById(R.id.partnerwebsitetvid);
        sociallist = findViewById(R.id.partnerssociallistid);
        city = (TextView) findViewById(R.id.partnercitytvid);
        country = (TextView) findViewById(R.id.partnercountrytvid);
        postalcode = (TextView) findViewById(R.id.partnerpostalcodetvid);
        desc = (TextView) findViewById(R.id.decptvid);
        mission = (TextView) findViewById(R.id.missiontvid);
        livestream = (TextView) findViewById(R.id.youtubelinktvid);
        partnerimage = (ImageView) findViewById(R.id.partnerimageviewid);
        livestreamcard = (CardView) findViewById(R.id.youtubecardid);
        strname = getIntent().getStringExtra("pname");
        strimage = getIntent().getStringExtra("image");
        strmobile = getIntent().getStringExtra("mobile");
        stremail = getIntent().getStringExtra("email");
        strwebsite = getIntent().getStringExtra("website");
        strcity = getIntent().getStringExtra("city");
        strcountry = getIntent().getStringExtra("country");
        strpostalcode = getIntent().getStringExtra("postalcode");
        strlivelink = getIntent().getStringExtra("livestream");
        strsocialmedia = getIntent().getStringExtra("sociallinks");
        strdesc = getIntent().getStringExtra("desc");
        strmission = getIntent().getStringExtra("mission");
        name.setText(strname);
        email.setText(stremail);
        mobile.setText(strmobile);
        website.setText(strwebsite);
        city.setText(strcity);
        country.setText(strcountry);
        postalcode.setText(strpostalcode);
        desc.setText(strdesc);
        mission.setText(strmission);
        if (!strimage.equals("")) {
            Picasso.with(getApplicationContext()).load(strimage).into(partnerimage);
        }
        if (strlivelink.equals("")) {
            livestreamcard.setVisibility(View.GONE);

        } else {
            livestreamcard.setVisibility(View.VISIBLE);

        }
        livestreamcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] array = strlivelink.split("=");
                String videocode = array[1];
                Log.d("videocode", "videocode : " + videocode);

                Intent intent = new Intent(getApplicationContext(), Livestreaming.class);
                intent.putExtra("videocode", videocode);

                startActivity(intent);
            }
        });
        sociallist.addItemDecoration(new DividerItemDecoration(PartnerDetails.this, LinearLayoutManager.HORIZONTAL));
        SocialAdapter = new EventsSocialAdapter(movieList, getApplicationContext());
//        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(EventDetails.this, R.dimen.columnspace);
//        sociallist.addItemDecoration(itemDecoration);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(PartnerDetails.this, LinearLayoutManager.HORIZONTAL, false);
        sociallist.setLayoutManager(verticalLayoutManager);
        sociallist.setAdapter(SocialAdapter);
        try {
            JSONArray jsonArray = new JSONArray(strsocialmedia);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SocialMediaModel data = new SocialMediaModel();
                data.setESMediaId(jsonObject.getString("PMediaId"));
                data.setEventId(jsonObject.getString("PartnerId"));
                data.setSocialMedia(jsonObject.getString("SocialMedia"));
                data.setSocialMediaLogopath(jsonObject.getString("MediaImage"));
                data.setSocialMediaLink(jsonObject.getString("SocialMediaLink"));
                data.setMediaTypeId(jsonObject.getString("MediaTypeId"));
                movieList.add(data);


            }
            SocialAdapter.notifyDataSetChanged();


        } catch (Exception e) {
            Log.d("sociallist", e.toString());
        }

    }
}
