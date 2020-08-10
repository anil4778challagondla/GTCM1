package com.gmi.gtcm.Model;

import android.net.Uri;

public class PhotosModel {
    private String MediaimgId;
    private String MediaId;
    private String Type;
    private String path;
    private String Imgpath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnailpath(String thumbnailpath) {
        Thumbnailpath = thumbnailpath;
    }

    private String title;

    public PhotosModel(String thumbnailpath) {
        Thumbnailpath = thumbnailpath;
    }

    public String getThumbnailpath() {
        return Thumbnailpath;
    }


    public PhotosModel(String title, String thumbnailpath) {
        this.title = title;
        Thumbnailpath = thumbnailpath;
    }

    private String Thumbnailpath;

    public PhotosModel(Uri imgpaths) {
        Imgpaths = imgpaths;
    }

    public Uri getImgpaths() {
        return Imgpaths;
    }

    public void setImgpaths(Uri imgpaths) {
        Imgpaths = imgpaths;
    }

    private Uri Imgpaths;

    public PhotosModel() {

    }

    public String getMediaimgId() {
        return MediaimgId;
    }

    public void setMediaimgId(String mediaimgId) {
        MediaimgId = mediaimgId;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImgpath() {
        return Imgpath;
    }

    public void setImgpath(String imgpath) {
        Imgpath = imgpath;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getFormatedCreatedDate() {
        return FormatedCreatedDate;
    }

    public void setFormatedCreatedDate(String formatedCreatedDate) {
        FormatedCreatedDate = formatedCreatedDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getFormatedModifiedDate() {
        return FormatedModifiedDate;
    }

    public void setFormatedModifiedDate(String formatedModifiedDate) {
        FormatedModifiedDate = formatedModifiedDate;
    }

    private String CreatedDate;
    private String FormatedCreatedDate;
    private String ModifiedDate;
    private String FormatedModifiedDate;


    public PhotosModel(String mediaimgId, String mediaId, String type, String path, String imgpath, String createdDate, String formatedCreatedDate, String modifiedDate, String formatedModifiedDate) {
        MediaimgId = mediaimgId;
        MediaId = mediaId;
        Type = type;
        this.path = path;
        Imgpath = imgpath;
        CreatedDate = createdDate;
        FormatedCreatedDate = formatedCreatedDate;
        ModifiedDate = modifiedDate;
        FormatedModifiedDate = formatedModifiedDate;
    }


}
