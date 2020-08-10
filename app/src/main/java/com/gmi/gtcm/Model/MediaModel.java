package com.gmi.gtcm.Model;

public class MediaModel {
    private String MediaId;
    private String OrgId;
    private String GroupId;
    private String Title;
    private String Description;
    private String Image;
    private String MediaImage;
    private String CreatedDate;

    public MediaModel() {

    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMediaImage() {
        return MediaImage;
    }

    public void setMediaImage(String mediaImage) {
        MediaImage = mediaImage;
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

    private String FormatedCreatedDate;
    private String ModifiedDate;
    private String FormatedModifiedDate;


    public MediaModel(String mediaId, String orgId, String groupId, String title, String description, String image, String mediaImage, String createdDate, String formatedCreatedDate, String modifiedDate, String formatedModifiedDate) {
        MediaId = mediaId;
        OrgId = orgId;
        GroupId = groupId;
        Title = title;
        Description = description;
        Image = image;
        MediaImage = mediaImage;
        CreatedDate = createdDate;
        FormatedCreatedDate = formatedCreatedDate;
        ModifiedDate = modifiedDate;
        FormatedModifiedDate = formatedModifiedDate;
    }


}
