package com.gmi.gtcm.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.R;

public class InfoActivity extends AppCompatActivity {
    ImageView board,vision,mission,statementfaith,ministry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        board=(ImageView)findViewById(R.id.boardoftrustivid);
        vision=(ImageView)findViewById(R.id.visionivid);
        mission=(ImageView)findViewById(R.id.missionivid);
        statementfaith=(ImageView)findViewById(R.id.statementivid);
        ministry=(ImageView)findViewById(R.id.ministryivid);
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToWebPage(6, "https://gtcm.org/board-of-trustees/");

            }
        });
        vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToWebPage(7, "https://gtcm.org/vision-2/");

            }
        });
        mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToWebPage(8, "https://gtcm.org/vision/");

            }
        });
        statementfaith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToWebPage(9, "https://gtcm.org/statement-of-faith/");

            }
        });
        ministry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToWebPage(10, "https://gtcm.org/ministry-focus/");

            }
        });
    }
    private void navigateToWebPage(int selectionType, String webLink) {
        Intent webViewIntent = new Intent(getApplicationContext(), Mywebview.class);
        webViewIntent.putExtra(AppUrls.INTENT_SELECTION_TYPE, selectionType);
        webViewIntent.putExtra(AppUrls.INTENT_WEBVIEW_LINK, webLink);
        startActivity(webViewIntent);
    }
}
