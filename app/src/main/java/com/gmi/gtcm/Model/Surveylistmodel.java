package com.gmi.gtcm.Model;

public class Surveylistmodel {
    private String SurveyId;
    private String SurveyName;
    private String CreatedDatestring;
    private String SurveyCode;
    private String TotalRecords;
    private String Surveyimage;

    public Surveylistmodel() {

    }

    public String getSurveyId() {
        return SurveyId;
    }

    public void setSurveyId(String surveyId) {
        SurveyId = surveyId;
    }

    public String getSurveyName() {
        return SurveyName;
    }

    public void setSurveyName(String surveyName) {
        SurveyName = surveyName;
    }

    public String getCreatedDatestring() {
        return CreatedDatestring;
    }

    public void setCreatedDatestring(String createdDatestring) {
        CreatedDatestring = createdDatestring;
    }

    public String getSurveyCode() {
        return SurveyCode;
    }

    public void setSurveyCode(String surveyCode) {
        SurveyCode = surveyCode;
    }

    public String getTotalRecords() {
        return TotalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        TotalRecords = totalRecords;
    }

    public String getSurveyimage() {
        return Surveyimage;
    }

    public void setSurveyimage(String surveyimage) {
        Surveyimage = surveyimage;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getSmsCode() {
        return SmsCode;
    }

    public void setSmsCode(String smsCode) {
        SmsCode = smsCode;
    }

    public String getStartDatestring() {
        return StartDatestring;
    }

    public void setStartDatestring(String startDatestring) {
        StartDatestring = startDatestring;
    }

    public String getIsReferFriend() {
        return IsReferFriend;
    }

    public void setIsReferFriend(String isReferFriend) {
        IsReferFriend = isReferFriend;
    }

    public String getEndDatestring() {
        return EndDatestring;
    }

    public void setEndDatestring(String endDatestring) {
        EndDatestring = endDatestring;
    }

    private String AppId;
    private String ClientId;
    private String BusinessId;
    private String QRCode;
    private String SmsCode;
    private String StartDatestring;
    private String IsReferFriend;

    public Surveylistmodel(String surveyId, String surveyName, String createdDatestring, String surveyCode, String totalRecords, String surveyimage, String appId, String clientId, String businessId, String QRCode, String smsCode, String startDatestring, String isReferFriend, String endDatestring) {
        SurveyId = surveyId;
        SurveyName = surveyName;
        CreatedDatestring = createdDatestring;
        SurveyCode = surveyCode;
        TotalRecords = totalRecords;
        Surveyimage = surveyimage;
        AppId = appId;
        ClientId = clientId;
        BusinessId = businessId;
        this.QRCode = QRCode;
        SmsCode = smsCode;
        StartDatestring = startDatestring;
        IsReferFriend = isReferFriend;
        EndDatestring = endDatestring;
    }

    private String EndDatestring;
}
