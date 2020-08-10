package com.gmi.gtcm.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Dailydevotion extends AppCompatActivity {
    ImageView left, right, calenders, dovationimage;
    TextView datetv, quotetv, titetv, bodytv, referencetv;
    String dateStr = "";
    String newDateStr;
    SharedPreferences sharedPreferences;
    String custid;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    DatePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailydevotion);

        // Declaring
        left = findViewById(R.id.imageView10);
        right = findViewById(R.id.imageView11);
        calenders = findViewById(R.id.calenderivid);
        dovationimage = findViewById(R.id.dailyimageviewid);
        datetv = findViewById(R.id.textView10);
        quotetv = findViewById(R.id.dovationquotetvid);
        titetv = findViewById(R.id.titletvid);
        bodytv = findViewById(R.id.desctvid);
        referencetv = findViewById(R.id.refencetitletvid);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid = sharedPreferences.getString(AppUrls.GROUPID, "");
        dovationimage.setVisibility(View.VISIBLE);

        // Gettting current date and converting to our format.
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("dd - MMM - yyyy");
        newDateStr = postFormater.format(c);
        datetv.setText(newDateStr);
        if (newDateStr != "") {
            gettingdata();
        }
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        calenders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                showDate(year, month, day);
                showDialog(999);
            }
        });
    }

    private static String formatDate(int day, int month, int year) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd - MMM - yyyy");

        return sdf.format(date);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2, arg3);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newDateStr = datetv.getText().toString().trim();
                            gettingdata();
                        }
                    }, 500);
                }
            };

    private void showDate(int year, int month, int day) {
        datetv.setText(formatDate(day, month, year));
    }


    private void gettingdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://151.106.38.222:92/api/GetDailyDevotion?Date=" + newDateStr + "&OrgId=1&GroupId=0";
        StringRequest movieReq = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("devotion", response.toString());
                        if (!response.equals("null")) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                quotetv.setText(obj.getString("Reference"));
                                titetv.setText(obj.getString("Title"));
                                bodytv.setText(obj.getString("Description"));
                                String imagepath = obj.getString("Imagepath");
                                if (imagepath.equals("")) {
                                    dovationimage.setVisibility(View.GONE);
                                } else {
                                    dovationimage.setVisibility(View.VISIBLE);
                                    if (!obj.getString("Imagepath").equals("")) {
                                        Picasso.with(getApplicationContext()).load(obj.getString("Imagepath")).into(dovationimage);

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(Dailydevotion.this, "No data Found", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
            }
        });
        requestQueue.add(movieReq);

    }
}
