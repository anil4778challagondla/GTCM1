package com.gmi.gtcm.Model;

public class Projectsimagelistmodel {
    private String ProjectimgId;
    private String ProjectId;
    private String Title;
    private String Image;

    public Projectsimagelistmodel() {

    }

    public String getProjectimgId() {
        return ProjectimgId;
    }

    public void setProjectimgId(String projectimgId) {
        ProjectimgId = projectimgId;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getImgpath() {
        return Imgpath;
    }

    public void setImgpath(String imgpath) {
        Imgpath = imgpath;
    }

    private String Imgpath;

    public Projectsimagelistmodel(String projectimgId, String projectId, String title, String image, String imgpath) {
        ProjectimgId = projectimgId;
        ProjectId = projectId;
        Title = title;
        Image = image;
        Imgpath = imgpath;
    }
}
