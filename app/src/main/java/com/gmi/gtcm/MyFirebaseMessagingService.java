package com.gmi.gtcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.gmi.gtcm.Activity.Home;
import com.gmi.gtcm.Activity.Livestreaming;
import com.gmi.gtcm.Activity.MainActivity;
import com.gmi.gtcm.Activity.Mywebview;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String mTitle,navurl;
    SharedPreferences sharedPreferences;
    String userid;
    private String mBody;


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppUrls.TOKENID,s);
        editor.commit();
        Log.d("token","token :"+s);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> params = remoteMessage.getData();
        Log.d("data",""+remoteMessage.getData());
        if (params != null) {
            JSONObject jsonObject = new JSONObject(params);
            mTitle = jsonObject.optString("title");
            mBody = jsonObject.optString("body");
            navurl = jsonObject.optString("navurl");
            //Calling method to generate notification
            Shownotification(jsonObject, mTitle, mBody,navurl);

        }
    }
    private void Shownotification(JSONObject jsonObject, String mTitle, String mBody,String navurl){
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="GTCM";
        Intent intent;
        String link = jsonObject.optString("link");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        userid = sharedPreferences.getString(AppUrls.CUSTOMERID, "");

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Group");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        if (link.length()!=0 && userid!="") {
            intent = new Intent(this, Mywebview.class);
            intent.putExtra(AppUrls.INTENT_WEBVIEW_LINK, link);
        } else if (navurl!=""){
            String[] array=navurl.split("=");
            String videocode=array[1];
            Log.d("videocode","videocode : "+videocode);

            intent = new Intent(this, Livestreaming.class);
            intent.putExtra("videocode",videocode);

        }else {
            intent = new Intent(this, MainActivity.class);

        }
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                // add all of SecondActvity's parents to the stack,
                // followed by SecondActvity itself
                .addNextIntentWithParentStack(intent)
                .addParentStack(Home.class)
                .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifiactionbulider=new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notifiactionbulider.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(mTitle)
                .setContentText(link)
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mBody))
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(),notifiactionbulider.build());
    }
}
