package com.gmi.gtcm.Model;

import java.time.OffsetDateTime;

public class ChatModel {
    private long chatID;
    private long customerID;
    private long isGroup;
    private long lastMessageID;
    private String messageText;
    private String image;
    private String imagePath;
    private String video;
    private String videoPath;
    private String firstName;
    private String lastName;
    private String name;
    private String pImage;
    private String profileImage;
    private OffsetDateTime lastMessageDate;
    private String lastMessageDatestring;
    private OffsetDateTime createdDate;
    private String createdDateString;

    public long getChatID() { return chatID; }
    public void setChatID(long value) { this.chatID = value; }

    public long getCustomerID() { return customerID; }
    public void setCustomerID(long value) { this.customerID = value; }

    public long getIsGroup() { return isGroup; }
    public void setIsGroup(long value) { this.isGroup = value; }

    public long getLastMessageID() { return lastMessageID; }
    public void setLastMessageID(long value) { this.lastMessageID = value; }

    public String getMessageText() { return messageText; }
    public void setMessageText(String value) { this.messageText = value; }

    public String getImage() { return image; }
    public void setImage(String value) { this.image = value; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String value) { this.imagePath = value; }

    public String getVideo() { return video; }
    public void setVideo(String value) { this.video = value; }

    public String getVideoPath() { return videoPath; }
    public void setVideoPath(String value) { this.videoPath = value; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { this.firstName = value; }

    public String getLastName() { return lastName; }
    public void setLastName(String value) { this.lastName = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getPImage() { return pImage; }
    public void setPImage(String value) { this.pImage = value; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String value) { this.profileImage = value; }

    public OffsetDateTime getLastMessageDate() { return lastMessageDate; }
    public void setLastMessageDate(OffsetDateTime value) { this.lastMessageDate = value; }

    public String getLastMessageDatestring() { return lastMessageDatestring; }
    public void setLastMessageDatestring(String value) { this.lastMessageDatestring = value; }

    public OffsetDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(OffsetDateTime value) { this.createdDate = value; }

    public String getCreatedDateString() { return createdDateString; }
    public void setCreatedDateString(String value) { this.createdDateString = value; }
}

