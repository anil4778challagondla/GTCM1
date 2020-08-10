package com.gmi.gtcm.Model;

import java.time.OffsetDateTime;

public class MessageModel1 {
    private long senderID;
    private String senderName;
    private String senderImage;
    private String senderImagePath;
    private long receiverID;
    private String receiverName;
    private String receiverImage;
    private String receiverImagePath;
    private long chatID;
    private long messageID;
    private String messageText;
    private String image;
    private String imagePath;
    private String video;
    private String videoPath;
    private long isSender;
    private OffsetDateTime createdDate;
    private String createdDateString;

    public long getSenderID() { return senderID; }
    public void setSenderID(long value) { this.senderID = value; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String value) { this.senderName = value; }

    public String getSenderImage() { return senderImage; }
    public void setSenderImage(String value) { this.senderImage = value; }

    public String getSenderImagePath() { return senderImagePath; }
    public void setSenderImagePath(String value) { this.senderImagePath = value; }

    public long getReceiverID() { return receiverID; }
    public void setReceiverID(long value) { this.receiverID = value; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String value) { this.receiverName = value; }

    public String getReceiverImage() { return receiverImage; }
    public void setReceiverImage(String value) { this.receiverImage = value; }

    public String getReceiverImagePath() { return receiverImagePath; }
    public void setReceiverImagePath(String value) { this.receiverImagePath = value; }

    public long getChatID() { return chatID; }
    public void setChatID(long value) { this.chatID = value; }

    public long getMessageID() { return messageID; }
    public void setMessageID(long value) { this.messageID = value; }

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

    public long getIsSender() { return isSender; }
    public void setIsSender(long value) { this.isSender = value; }

    public OffsetDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(OffsetDateTime value) { this.createdDate = value; }

    public String getCreatedDateString() { return createdDateString; }
    public void setCreatedDateString(String value) { this.createdDateString = value; }
}
