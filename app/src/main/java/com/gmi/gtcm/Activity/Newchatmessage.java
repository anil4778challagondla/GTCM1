package com.gmi.gtcm.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.Adapter.NewchatAdapter;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.FriendsModel;
import com.gmi.gtcm.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Newchatmessage extends AppCompatActivity {

    NewchatAdapter adapter;
    RecyclerView Eventsrec;
    private Uri mCropImageUri;
    SharedPreferences sharedPreferences;
    EditText messageedt;
    String custid,merch,list,ismulvalue,imgString="";
    FloatingActionButton newchat;
    ImageButton gallery,sendmessage;
    ImageView selectimage;

    LinearLayoutManager mLayoutManager;
    public static ArrayList<String> selectedStrings;
    public static ArrayList<String> selectedcids;
    List<String> listss;
    private List<FriendsModel> Eventlist = new ArrayList<FriendsModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newchatmessage);
        Eventsrec = (RecyclerView) findViewById(R.id.newchatrecid);
        messageedt = (EditText) findViewById(R.id.text_send);
        sendmessage = (ImageButton) findViewById(R.id.btn_send);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        selectimage = (ImageView) findViewById(R.id.messageimageid);
        gallery = (ImageButton) findViewById(R.id.btn_galery);

        selectedStrings = new ArrayList<>();
        selectedcids = new ArrayList<>();
        adapter=new NewchatAdapter(this, Eventlist);
        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Eventsrec.setLayoutManager(mLayoutManager);
        Eventsrec.setAdapter(adapter);
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listss=adapter.getitems();
                list = Arrays.toString(listss.toArray()).replace("[", "").replace("]", "");

                if(listss.size()==0){
                    Toast.makeText(getApplicationContext(),"Select atleast one member to send message",Toast.LENGTH_LONG).show();
                }else {


                    sendmessage();
                }


            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });
        GETMEMBERS();
        setActionBarTitle();
    }
    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }
    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getApplicationContext(), data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(getApplicationContext(), imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                selectimage.setVisibility(View.VISIBLE);
                selectimage.setImageURI(result.getUri());
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(result.getUri());
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgString = encodeImage(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(getApplicationContext(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .setFixAspectRatio(true)
                .start(this);
    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,20,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
    private void setActionBarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Chat");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void GETMEMBERS(){
        Eventlist.clear();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:1000/api/Getsocial?cid=11&oid=1";
        Log.d("sss","url:"+url);
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("c", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                FriendsModel Members=new FriendsModel();
                                Members.setCustomerID(obj.getLong("CustomerId"));
                                Members.setOrgid(obj.getLong("orgid"));
                                Members.setFname(obj.getString("fname"));
                                Members.setLname(obj.getString("lname"));
                                Members.setMobile(obj.getString("Mobile"));
                                Members.setEmailID(obj.getString("EmailId"));
                                Members.setPassword(obj.getString("Password"));
                                Members.setNewPassword(obj.getString("NewPassword"));
                                Members.setProfession(obj.getString("Profession"));
                                Members.setCustomerImage(obj.getString("CustomerImage"));
                                Members.setMinistry(obj.getString("Ministry"));
                                Members.setCountryID(obj.getLong("CountryId"));
                                Members.setCountryName(obj.getString("CountryName"));
                                Members.setCityID(obj.getLong("CityId"));
                                Members.setIsCmtMember(obj.getLong("IsCmtMember"));
                                Members.setReferrerName(obj.getString("ReferrerName"));
                                Members.setSalvationInfo(obj.getString("SalvationInfo"));
                                Members.setCustomerImagePath(obj.getString("CustomerImagePath"));
                                Members.setTotalRecords(obj.getLong("TotalRecords"));
                                Members.setOrgLogoPath(obj.getString("OrgLogoPath"));
                                Members.setContactName1(obj.getString("ContactName1"));
                                Members.setContactName2(obj.getString("ContactName2"));
                                Members.setContactNumber1(obj.getString("ContactNumber1"));
                                Members.setContactNumber2(obj.getString("ContactNumber2"));
                                Members.setProfessionalContactNumber1(obj.getString("ProfessionalContactNumber1"));
                                Members.setMoodID(obj.getLong("MoodId"));
                                Members.setIsFriend(obj.getLong("IsFriend"));
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

    private void sendmessage(){

        int countval=listss.toArray().length;
        if(countval==1){
            ismulvalue="0";
        }else {
            ismulvalue="1";
        }
        final String messagevalue=messageedt.getText().toString().trim();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url="http://151.106.38.222:1000/api/InsertChatNewMessage";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("Pimage", imgString);
            jsonBody.put("imgextension", ".jpg");
            jsonBody.put("vidextension", "");
            jsonBody.put("Pvideo", "");
            jsonBody.put("orgid", "1");
            jsonBody.put("frmid", custid);
            jsonBody.put("toids", list);
            jsonBody.put("msg", messagevalue);
            jsonBody.put("ismultiple", ismulvalue);
            jsonBody.put("isgroup", "0");


            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Register_data","response"+response);


                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        selectedcids.clear();
                        messageedt.getText().clear();
                        listss.clear();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());


                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
