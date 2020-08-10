package com.gmi.gtcm.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.gmi.gtcm.Activity.Mywebview;
import com.gmi.gtcm.Activity.YoutubeActivity;
import com.gmi.gtcm.AppUrls;
import com.gmi.gtcm.Model.PhotosModel;
import com.gmi.gtcm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private Context mContext;
    private List<PhotosModel> mylist;
    Bitmap bmThumbnail;




    public VideoAdapter(Context context, List<PhotosModel> mylist) {
        this.mylist = mylist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.videocustomlist, viewGroup, false);

        return new VideoAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        String myUri = mylist.get(i).getImgpath();
        if (!mylist.get(i).getThumbnailpath().equals("")) {
            Picasso.with(mContext).load(mylist.get(i).getThumbnailpath()).into(myViewHolder.videos);
        }
        myViewHolder.title.setText(mylist.get(i).getTitle());

        myViewHolder.videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(mylist.get(i).getImgpath()));
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {

        return mylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ProgressDialog progressDialog;
        String videourl;

        ImageView videos;
        TextView title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            videos=(ImageView) itemView.findViewById(R.id.videoviewid);
            title=(TextView) itemView.findViewById(R.id.videotitleid);
        }
    }
    private void navigateToPage(String link, int type) {
        Intent webViewIntent = new Intent(mContext, Mywebview.class);
        webViewIntent.putExtra(AppUrls.INTENT_WEBVIEW_LINK, link);
        webViewIntent.putExtra(AppUrls.INTENT_SELECTION_TYPE, type);
        mContext.startActivity(webViewIntent);
    }

}
