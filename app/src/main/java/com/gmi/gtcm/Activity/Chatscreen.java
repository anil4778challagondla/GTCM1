package com.gmi.gtcm.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.gmi.gtcm.Adapter.MessageAdapter1;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.MessageModel;
import com.gmi.gtcm.Model.MessageModel1;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chatscreen extends AppCompatActivity {
    String custid,fndid,chatid,name,image,imgString="";

    SharedPreferences sharedPreferences;
    private MessageAdapter1 adapter;
    RecyclerView Eventsrec;
    ImageView profileimage,selectimage;
    private Uri mCropImageUri;
    TextView profilename;
    ImageButton gallery,send;
    EditText messageedt;
    LinearLayoutManager mLayoutManager;
    private List<MessageModel1> Eventlist = new ArrayList<MessageModel1>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatscreen);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        Eventsrec = (RecyclerView) findViewById(R.id.messagerecid);
        gallery = (ImageButton) findViewById(R.id.btn_galery);
        send = (ImageButton) findViewById(R.id.btn_send);
        profileimage = (ImageView) findViewById(R.id.profileimagechat);
        selectimage = (ImageView) findViewById(R.id.messageimageid);
        messageedt = (EditText) findViewById(R.id.text_send);
        profilename = (TextView) findViewById(R.id.usernamechat);
        fndid=getIntent().getStringExtra("customerid");
        chatid=getIntent().getStringExtra("chatid");
        name=getIntent().getStringExtra("name");
        image=getIntent().getStringExtra("image");
        Picasso.with(this).load(image).placeholder(R.drawable.user).into(profileimage);
        profilename.setText(name);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        GETMESSAGES();
        Eventsrec.setHasFixedSize(true);
        adapter=new MessageAdapter1(this, Eventlist);
        mLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setStackFromEnd(true);
        Eventsrec.setLayoutManager(mLayoutManager);
        Eventsrec.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendmessage();

            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });
        final Handler handler = new Handler();
        final int delay = 30000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                GETMESSAGES();
                handler.postDelayed(this, delay);
            }
        }, delay);

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

    public void GETMESSAGES(){

        Eventlist.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:1000/api/GetChatMessageByChatId?cid=11&chatid="+chatid+"&pgindex=1&pgsize=-1";
        Log.d("sss","url:"+url);
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("c", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                MessageModel1 Members=new MessageModel1();
                                Members.setIsSender(obj.getLong("SenderId"));
                                Members.setSenderName(obj.getString("SenderName"));
                                Members.setSenderImagePath(obj.getString("SenderImagePath"));
                                Members.setReceiverID(obj.getLong("ReceiverId"));
                                Members.setReceiverName(obj.getString("ReceiverName"));
                                Members.setReceiverImagePath(obj.getString("ReceiverImagePath"));
                                Members.setChatID(obj.getLong("ChatId"));
                                Members.setMessageID(obj.getLong("MessageId"));
                                Members.setMessageText(obj.getString("MessageText"));
                                Members.setImagePath(obj.getString("ImagePath"));
                                Members.setImage(obj.getString("Image"));
                                Members.setVideo(obj.getString("Video"));
                                Members.setVideoPath(obj.getString("VideoPath"));
                                Members.setIsSender(obj.getLong("IsSender"));
                                Members.setCreatedDateString(obj.getString("CreatedDateString"));


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


        final String messagevalue=messageedt.getText().toString().trim();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url="http://151.106.38.222:1000/api/ChatReplyMessage";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("Pimage", imgString);
            jsonBody.put("imgextension", ".jpg");
            jsonBody.put("vidextension", "");
            jsonBody.put("Pvideo", "");
            jsonBody.put("orgid", "1");
            jsonBody.put("frmid", custid);
            jsonBody.put("toid", fndid);
            jsonBody.put("msg", messagevalue);
            jsonBody.put("chatid", chatid);



            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Register_data","response"+response);



                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        Eventlist.clear();
                        imgString="";
                        selectimage.setVisibility(View.GONE);
                        messageedt.setText("");
                        GETMESSAGES();

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
