package com.gmi.gtcm.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Imagepostmessage extends AppCompatActivity {
    private Uri mCropImageUri;
    private ImageView ceimagevie;
    String imgString,merch,cusugid,lat="",lng="";
    ProgressDialog pDialog;
    String custid,cmty,file_extn;

    Button createevent;
    String Groupname="",groupid="",messagetext;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepostmessage);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        cusugid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        cmty=sharedPreferences.getString(AppUrls.CMTMEMBER,"");
        ceimagevie=(ImageView)findViewById(R.id.createevent_setimageview);
        createevent=(Button) findViewById(R.id.messagesendbtnid);
        groupid=getIntent().getStringExtra("grpid");
        custid=getIntent().getStringExtra("cid");
        messagetext=getIntent().getStringExtra("msg");


        createevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmethod();
            }
        });

        ceimagevie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });
    }
    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }
    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getApplicationContext(), data);
            String filePath = getPath(imageUri);
            file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
            Log.d("extns","extns : "+file_extn);
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
                ceimagevie.setImageURI(result.getUri());
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
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
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
    public void sendmethod(){

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url="http://151.106.38.222:92/api/sendgroupnewmessage";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("orgid","1");
            jsonBody.put("grpid",groupid);
            jsonBody.put("cid",custid);
            jsonBody.put("msg",messagetext);
            jsonBody.put("Pimage",imgString);
            jsonBody.put("Pvideo","");
            jsonBody.put("imgextension","."+file_extn);
            jsonBody.put("vidextension","");
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("offers","response"+response);
                    try {
                        JSONObject data=new JSONObject(response);
                        String StatusMessage= data.getString("StatusMessage");
                        String StatusCode= data.getString("StatusCode");
                        if(StatusMessage.contains("Posted Successfully")){
                            Toast.makeText(getApplicationContext(),StatusMessage,Toast.LENGTH_LONG).show();
                            if(cmty.equals("0")){
                                startActivity(new Intent(getApplicationContext(),MessageBoard.class));
                                finish();
                            }else {
                                startActivity(new Intent(getApplicationContext(),Messageboard2.class));
                                finish();

                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Try again please",Toast.LENGTH_LONG).show();

                        }











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
