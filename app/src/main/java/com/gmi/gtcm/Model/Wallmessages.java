package com.gmi.gtcm.Model;

import java.time.OffsetDateTime;

public class Wallmessages {
    private long gmsgID;
    private long orgID;
    private long customerID;
    private String message;
    private String customerImage;
    private String customerImagePath;
    private OffsetDateTime postedDate;
    private String formatedPostedDate;
    private OffsetDateTime approvedDate;
    private String fosmatedApprovedDate;
    private long status;
    private String senderName;
    private long sender;
    private String postImage;
    private String imagepath;
    private String postVideo;
    private String videopath;

    public long getGmsgID() { return gmsgID; }
    public void setGmsgID(long value) { this.gmsgID = value; }

    public long getOrgID() { return orgID; }
    public void setOrgID(long value) { this.orgID = value; }

    public long getCustomerID() { return customerID; }
    public void setCustomerID(long value) { this.customerID = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public String getCustomerImage() { return customerImage; }
    public void setCustomerImage(String value) { this.customerImage = value; }

    public String getCustomerImagePath() { return customerImagePath; }
    public void setCustomerImagePath(String value) { this.customerImagePath = value; }

    public OffsetDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(OffsetDateTime value) { this.postedDate = value; }

    public String getFormatedPostedDate() { return formatedPostedDate; }
    public void setFormatedPostedDate(String value) { this.formatedPostedDate = value; }

    public OffsetDateTime getApprovedDate() { return approvedDate; }
    public void setApprovedDate(OffsetDateTime value) { this.approvedDate = value; }

    public String getFosmatedApprovedDate() { return fosmatedApprovedDate; }
    public void setFosmatedApprovedDate(String value) { this.fosmatedApprovedDate = value; }

    public long getStatus() { return status; }
    public void setStatus(long value) { this.status = value; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String value) { this.senderName = value; }

    public long getSender() { return sender; }
    public void setSender(long value) { this.sender = value; }

    public String getPostImage() { return postImage; }
    public void setPostImage(String value) { this.postImage = value; }

    public String getImagepath() { return imagepath; }
    public void setImagepath(String value) { this.imagepath = value; }

    public String getPostVideo() { return postVideo; }
    public void setPostVideo(String value) { this.postVideo = value; }

    public String getVideopath() { return videopath; }
    public void setVideopath(String value) { this.videopath = value; }
}

