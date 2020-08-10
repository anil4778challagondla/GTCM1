package com.gmi.gtcm.Model;

public class SocialMediaModel {
    private String ESMediaId;
    private String EventId;
    private String MediaTypeId;
    private String SocialMedia;

    public SocialMediaModel() {

    }

    public String getESMediaId() {
        return ESMediaId;
    }

    public void setESMediaId(String ESMediaId) {
        this.ESMediaId = ESMediaId;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getMediaTypeId() {
        return MediaTypeId;
    }

    public void setMediaTypeId(String mediaTypeId) {
        MediaTypeId = mediaTypeId;
    }

    public String getSocialMedia() {
        return SocialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        SocialMedia = socialMedia;
    }

    public String getSocialMediaLogo() {
        return SocialMediaLogo;
    }

    public void setSocialMediaLogo(String socialMediaLogo) {
        SocialMediaLogo = socialMediaLogo;
    }

    public String getSocialMediaLogopath() {
        return SocialMediaLogopath;
    }

    public void setSocialMediaLogopath(String socialMediaLogopath) {
        SocialMediaLogopath = socialMediaLogopath;
    }

    public String getSocialMediaLink() {
        return SocialMediaLink;
    }

    public void setSocialMediaLink(String socialMediaLink) {
        SocialMediaLink = socialMediaLink;
    }

    private String SocialMediaLogo;
    private String SocialMediaLogopath;
    private String SocialMediaLink;

    public SocialMediaModel(String ESMediaId, String eventId, String mediaTypeId, String socialMedia, String socialMediaLogo, String socialMediaLogopath, String socialMediaLink) {
        this.ESMediaId = ESMediaId;
        EventId = eventId;
        MediaTypeId = mediaTypeId;
        SocialMedia = socialMedia;
        SocialMediaLogo = socialMediaLogo;
        SocialMediaLogopath = socialMediaLogopath;
        SocialMediaLink = socialMediaLink;
    }

}
