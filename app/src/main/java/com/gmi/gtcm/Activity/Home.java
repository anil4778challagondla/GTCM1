package com.gmi.gtcm.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;
import com.gmi.gtcm.VoiceQuizActivity;
import com.gmi.gtcm.db.DatabaseHelper;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView events,media,notices,partners,messageboard,profilebtn,dailydevotion;
    SharedPreferences sharedPreferences;
    String custid,cmty;
    DatabaseHelper databaseHelper;
    private static final int PERMISSION_REQUEST_CODE = 200;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        custid=sharedPreferences.getString(AppUrls.CUSTOMERID,"");
        cmty=sharedPreferences.getString(AppUrls.CMTMEMBER,"");
        databaseHelper = new DatabaseHelper(this);
        events=(ImageView)findViewById(R.id.imageView5);
        media=(ImageView)findViewById(R.id.mediaivid);
        notices=(ImageView)findViewById(R.id.noticesivid);
        partners=(ImageView)findViewById(R.id.imageView7);
        messageboard=(ImageView)findViewById(R.id.imageView6);
        profilebtn=(ImageView)findViewById(R.id.profilebtnid);
        dailydevotion=(ImageView)findViewById(R.id.dailydevationivid);
        if (!checkPermission()) {
            requestPermission();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Events.class);
                intent.putExtra("groupid","0");
               startActivity(intent);
            }
        });
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Medialist.class));
            }
        });
        notices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notices.class));
            }
        });
        partners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Partners.class));
            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Myprofile.class));
            }
        });
        dailydevotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Dailydevotion.class));
            }
        });
        messageboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Messagescreen.class));

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int FINELOCATION = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int COARSELOCATION = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int CAMERA = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA);
        int READSTORAGE = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int WRITESTORAGE = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && FINELOCATION == PackageManager.PERMISSION_GRANTED && COARSELOCATION == PackageManager.PERMISSION_GRANTED && READSTORAGE == PackageManager.PERMISSION_GRANTED && WRITESTORAGE == PackageManager.PERMISSION_GRANTED && CAMERA == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE, SEND_SMS, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean smsAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean fineAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean coarseAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted && smsAccepted && fineAccepted && coarseAccepted && storageAccepted && cameraAccepted) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow the all permissions before Checking",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_PHONE_STATE, SEND_SMS, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, android.Manifest.permission.CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.homeid) {
            startActivity(new Intent(getApplicationContext(),Home.class));
        } else if (id == R.id.donateid) {
            startActivity(new Intent(getApplicationContext(),TextQuiz.class));
        } else if (id == R.id.notificationsid) {
            Intent intent=new Intent(getApplicationContext(), VoiceQuizActivity.class);
            startActivity(intent);
        } else if (id == R.id.promotionsid) {
            Intent intent=new Intent(getApplicationContext(), Promotions.class);
            startActivity(intent);
        } else if (id == R.id.surveyid) {
            Intent intent=new Intent(getApplicationContext(), Survey.class);
            startActivity(intent);
        } else if (id == R.id.myprofileid) {
            startActivity(new Intent(getApplicationContext(),Myprofile.class));
        } else if (id == R.id.smartquizid) {
            startActivity(new Intent(getApplicationContext(),SmartQuiz.class));
        } else if (id == R.id.reedemid) {
            startActivity(new Intent(getApplicationContext(),Reedem.class));
        } else if (id == R.id.gameid) {
            startActivity(new Intent(getApplicationContext(),Gameslist.class));

        }else if (id == R.id.infoid) {
            startActivity(new Intent(getApplicationContext(),InfoActivity.class));

        }else if (id == R.id.Donateid) {
            navigateToWebPage(11, "https://hillsongchannel.com/donate/");

        }else if (id == R.id.projectsid) {
            startActivity(new Intent(getApplicationContext(),Projects.class));

        } else if (id == R.id.logoutid) {
//            sharedPreferences.edit().remove(AppUrls.CUSTOMERID).apply();
//            startActivity(new Intent(Home.this, Login.class));
//            finish();
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void navigateToWebPage(int selectionType, String webLink) {
        Intent webViewIntent = new Intent(getApplicationContext(), Mywebview.class);
        webViewIntent.putExtra(AppUrls.INTENT_SELECTION_TYPE, selectionType);
        webViewIntent.putExtra(AppUrls.INTENT_WEBVIEW_LINK, webLink);
        startActivity(webViewIntent);
    }
    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage("Do you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        databaseHelper.removeAll();
                        sharedPreferences.edit().remove(AppUrls.CUSTOMERID).apply();
                        startActivity(new Intent(Home.this, Login.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
