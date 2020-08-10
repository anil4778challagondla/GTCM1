package com.gmi.gtcm.Model;

public class NoticeModel {
    private String CountryId;
    private String CountryName;
    private String CountryCode;

    public NoticeModel() {

    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getCountryFalgimg() {
        return CountryFalgimg;
    }

    public void setCountryFalgimg(String countryFalgimg) {
        CountryFalgimg = countryFalgimg;
    }

    public String getCountryImagePath() {
        return CountryImagePath;
    }

    public void setCountryImagePath(String countryImagePath) {
        CountryImagePath = countryImagePath;
    }

    private String CountryFalgimg;
    private String CountryImagePath;

    public NoticeModel(String countryId, String countryName, String countryCode, String countryFalgimg, String countryImagePath) {
        CountryId = countryId;
        CountryName = countryName;
        CountryCode = countryCode;
        CountryFalgimg = countryFalgimg;
        CountryImagePath = countryImagePath;
    }



}
