package com.gmi.gtcm.Model;

public class EventsModel {
    private String EventId;
    private String OrgId;
    private String OrgName;
    private String GroupId;
    private String GroupName;
    private String Title;
    private String Description;
    private String Image;
    private String EventImagePath;
    private String StartDate;
    private String StartDateString;
    private String EndDate;
    private String EndDateString;
    private String ContactPerson;
    private String ContactPhone;
    private String ContactEmail;
    private String Location;
    private String Latitude;
    private String Longitude;
    private String EventType;
    private String IsBookingNeeded;
    private String TicketPrice;
    private String TicketsAvailable;
    private String TicketsPerPerson;
    private String TicketsBooked;

    public String getSponsorlist() {
        return Sponsorlist;
    }

    public void setSponsorlist(String sponsorlist) {
        Sponsorlist = sponsorlist;
    }

    private String Sponsorlist;

    public EventsModel(String socialMedia) {
        SocialMedia = socialMedia;
    }

    public String getSocialMedia() {
        return SocialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        SocialMedia = socialMedia;
    }

    private String SocialMedia;

    public EventsModel() {

    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
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

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
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

    public String getEventImagePath() {
        return EventImagePath;
    }

    public void setEventImagePath(String eventImagePath) {
        EventImagePath = eventImagePath;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartDateString() {
        return StartDateString;
    }

    public void setStartDateString(String startDateString) {
        StartDateString = startDateString;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndDateString() {
        return EndDateString;
    }

    public void setEndDateString(String endDateString) {
        EndDateString = endDateString;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String contactPhone) {
        ContactPhone = contactPhone;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public String getIsBookingNeeded() {
        return IsBookingNeeded;
    }

    public void setIsBookingNeeded(String isBookingNeeded) {
        IsBookingNeeded = isBookingNeeded;
    }

    public String getTicketPrice() {
        return TicketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        TicketPrice = ticketPrice;
    }

    public String getTicketsAvailable() {
        return TicketsAvailable;
    }

    public void setTicketsAvailable(String ticketsAvailable) {
        TicketsAvailable = ticketsAvailable;
    }

    public String getTicketsPerPerson() {
        return TicketsPerPerson;
    }

    public void setTicketsPerPerson(String ticketsPerPerson) {
        TicketsPerPerson = ticketsPerPerson;
    }

    public String getTicketsBooked() {
        return TicketsBooked;
    }

    public void setTicketsBooked(String ticketsBooked) {
        TicketsBooked = ticketsBooked;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedDateString() {
        return CreatedDateString;
    }

    public void setCreatedDateString(String createdDateString) {
        CreatedDateString = createdDateString;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getModifiedDateString() {
        return ModifiedDateString;
    }

    public void setModifiedDateString(String modifiedDateString) {
        ModifiedDateString = modifiedDateString;
    }

    public String getTotalRecords() {
        return TotalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        TotalRecords = totalRecords;
    }

    private String CreatedDate;
    private String CreatedDateString;
    private String ModifiedDate;
    private String ModifiedDateString;
    private String TotalRecords;

    public EventsModel(String eventId, String orgId, String orgName, String groupId, String groupName, String title, String description, String image, String eventImagePath, String startDate, String startDateString, String endDate, String endDateString, String contactPerson, String contactPhone, String contactEmail, String location, String latitude, String longitude, String eventType, String isBookingNeeded, String ticketPrice, String ticketsAvailable, String ticketsPerPerson, String ticketsBooked, String createdDate, String createdDateString, String modifiedDate, String modifiedDateString, String totalRecords) {
        EventId = eventId;
        OrgId = orgId;
        OrgName = orgName;
        GroupId = groupId;
        GroupName = groupName;
        Title = title;
        Description = description;
        Image = image;
        EventImagePath = eventImagePath;
        StartDate = startDate;
        StartDateString = startDateString;
        EndDate = endDate;
        EndDateString = endDateString;
        ContactPerson = contactPerson;
        ContactPhone = contactPhone;
        ContactEmail = contactEmail;
        Location = location;
        Latitude = latitude;
        Longitude = longitude;
        EventType = eventType;
        IsBookingNeeded = isBookingNeeded;
        TicketPrice = ticketPrice;
        TicketsAvailable = ticketsAvailable;
        TicketsPerPerson = ticketsPerPerson;
        TicketsBooked = ticketsBooked;
        CreatedDate = createdDate;
        CreatedDateString = createdDateString;
        ModifiedDate = modifiedDate;
        ModifiedDateString = modifiedDateString;
        TotalRecords = totalRecords;
    }


}
