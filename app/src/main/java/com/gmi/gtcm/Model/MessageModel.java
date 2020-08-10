package com.gmi.gtcm.Model;

public class MessageModel {
    public MessageModel(String GMSGId, String groupId, String orgId, String customerId, String message, String postedDate, String formatedPostedDate, String approvedDate, String fosmatedApprovedDate, String status, String senderName, String sender) {
        this.GMSGId = GMSGId;
        GroupId = groupId;
        OrgId = orgId;
        CustomerId = customerId;
        Message = message;
        PostedDate = postedDate;
        FormatedPostedDate = formatedPostedDate;
        ApprovedDate = approvedDate;
        FosmatedApprovedDate = fosmatedApprovedDate;
        Status = status;
        SenderName = senderName;
        Sender = sender;
    }

    public MessageModel() {

    }

    public String getGMSGId() {
        return GMSGId;
    }

    public void setGMSGId(String GMSGId) {
        this.GMSGId = GMSGId;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        PostedDate = postedDate;
    }

    public String getFormatedPostedDate() {
        return FormatedPostedDate;
    }

    public void setFormatedPostedDate(String formatedPostedDate) {
        FormatedPostedDate = formatedPostedDate;
    }

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public void setApprovedDate(String approvedDate) {
        ApprovedDate = approvedDate;
    }

    public String getFosmatedApprovedDate() {
        return FosmatedApprovedDate;
    }

    public void setFosmatedApprovedDate(String fosmatedApprovedDate) {
        FosmatedApprovedDate = fosmatedApprovedDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    private String GMSGId,GroupId,OrgId,CustomerId,Message,PostedDate,FormatedPostedDate,ApprovedDate,FosmatedApprovedDate,Status,SenderName,Sender;

    public MessageModel(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getMssgimage() {
        return mssgimage;
    }

    public void setMssgimage(String mssgimage) {
        this.mssgimage = mssgimage;
    }

    private String mssgimage;
}
