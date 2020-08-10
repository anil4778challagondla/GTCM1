package com.gmi.gtcm.Model;

public class Partnersmodel {
    private String PartnerId;
    private String PartnerName;
    private String OrgId;
    private String OrgName;
    private String Logo;
    private String LogoPath;
    private String Images;
    private String EmailId;
    private String Phone;
    private String CountryId;
    private String CityId;
    private String CityName;

    public Partnersmodel(String mission) {
        this.mission = mission;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    private String mission;
    private String Description;


    public Partnersmodel(String cityName, String countryName, String postCode) {
        CityName = cityName;
        CountryName = countryName;
        PostCode = postCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    private String CountryName;
    private String PostCode;

    public Partnersmodel() {

    }

    public String getPartnerId() {
        return PartnerId;
    }

    public void setPartnerId(String partnerId) {
        PartnerId = partnerId;
    }

    public String getPartnerName() {
        return PartnerName;
    }

    public void setPartnerName(String partnerName) {
        PartnerName = partnerName;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getLogoPath() {
        return LogoPath;
    }

    public void setLogoPath(String logoPath) {
        LogoPath = logoPath;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getTotalRecords() {
        return TotalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        TotalRecords = totalRecords;
    }

    private String Website;

    private String CreatedDate;
    private String FormatedCreatedDate;
    private String ModifiedDate;
    private String FormatedModifiedDate;
    private String TotalRecords;
    private String SocilaMediaLinks;

    public String getSocilaMediaLinks() {
        return SocilaMediaLinks;
    }

    public void setSocilaMediaLinks(String socilaMediaLinks) {
        SocilaMediaLinks = socilaMediaLinks;
    }

    public String getLiveStreaming() {
        return LiveStreaming;
    }

    public void setLiveStreaming(String liveStreaming) {
        LiveStreaming = liveStreaming;
    }

    private String LiveStreaming;

    public Partnersmodel(String socilaMediaLinks, String liveStreaming) {
        SocilaMediaLinks = socilaMediaLinks;
        LiveStreaming = liveStreaming;
    }

    public Partnersmodel(String partnerId, String partnerName, String orgId, String orgName, String logo, String logoPath, String images, String emailId, String phone, String countryId, String cityId, String website, String description, String createdDate, String formatedCreatedDate, String modifiedDate, String formatedModifiedDate, String totalRecords) {
        PartnerId = partnerId;
        PartnerName = partnerName;
        OrgId = orgId;
        OrgName = orgName;
        Logo = logo;
        LogoPath = logoPath;
        Images = images;
        EmailId = emailId;
        Phone = phone;
        CountryId = countryId;
        CityId = cityId;
        Website = website;
        Description = description;
        CreatedDate = createdDate;
        FormatedCreatedDate = formatedCreatedDate;
        ModifiedDate = modifiedDate;
        FormatedModifiedDate = formatedModifiedDate;
        TotalRecords = totalRecords;
    }



}
