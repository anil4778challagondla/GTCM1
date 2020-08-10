package com.gmi.gtcm.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.ProjectsAdapter;
import com.gmi.gtcm.Adapter.ProjectsimageGridAdapter;
import com.gmi.gtcm.CheckConnection;
import com.gmi.gtcm.GridSpacingItemDecoration;
import com.gmi.gtcm.Model.Projectmodel;
import com.gmi.gtcm.Model.Projectsimagelistmodel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectsDetails extends AppCompatActivity {
    String projectid,imagelist,project;
    ImageView projectimage;
    TextView projectname,projectcity,projectcountry,projectpostalcode;
    RecyclerView gridlist;
    private ProgressDialog pDialog;
    private ProjectsimageGridAdapter adapter;
    Dialog dialog;
    private List<Projectsimagelistmodel> movieList = new ArrayList<Projectsimagelistmodel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_details);

        projectimage=(ImageView)findViewById(R.id.icon_header_logo) ;
        projectname=(TextView)findViewById(R.id.partnertitletvid) ;
        projectcity=(TextView)findViewById(R.id.partnercitytvid) ;
        projectcountry=(TextView)findViewById(R.id.partnercountrytvid) ;
        projectpostalcode=(TextView)findViewById(R.id.partnerpincodetvid) ;
        gridlist=(RecyclerView) findViewById(R.id.projectsgridimages) ;
        projectid=getIntent().getStringExtra("projectid");
        adapter=new ProjectsimageGridAdapter(this, movieList);
        gridlist.setLayoutManager(new GridLayoutManager(this,2));
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(this, R.dimen.columnspace);
        gridlist.addItemDecoration(itemDecoration);
        gridlist.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Loading Project...");
        pDialog.show();
        connection();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/GetProjectImages?projectid="+projectid;
        StringRequest movieReq = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("testdat", response.toString());
                        hidePDialog();


                                try {
                                    JSONObject obj = new JSONObject(response);
                                    project=obj.getString("Project");
                                    imagelist=obj.getString("Images");

                                    JSONObject objs = new JSONObject(project);

                                    projectname.setText(objs.getString("OrphanageName"));
                                    projectcity.setText(objs.getString("City"));
                                    projectcountry.setText(objs.getString("CountryName"));
                                    projectpostalcode.setText(objs.getString("Postcode"));
                                    if (!objs.getString("ImagePath").equals("")) {
                                        Picasso.with(getApplicationContext()).load(objs.getString("ImagePath")).into(projectimage);
                                    }
                                    JSONArray array=new JSONArray(imagelist);
                                    for (int i = 0; i < array.length(); i++) {
                                        try {
                                            JSONObject imglist = array.getJSONObject(i);
                                            Projectsimagelistmodel movie = new Projectsimagelistmodel();
                                            movie.setImgpath(imglist.getString("Imgpath"));


                                            movieList.add(movie);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    adapter.notifyDataSetChanged();



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }




                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                hidePDialog();
            }
        });
        requestQueue.add(movieReq);
    }
    @SuppressLint("WrongConstant")
    public void connection(){
        if(CheckConnection.isInternetAvailable(ProjectsDetails.this)) //returns true if internet available
        {

        }
        else
        {
            Toast.makeText(ProjectsDetails.this,"No Internet Connection",1000).show();
        }
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Partners");

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

    @Override
    protected void onPause() {
        super.onPause();
        connection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        connection();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connection();
    }
}
