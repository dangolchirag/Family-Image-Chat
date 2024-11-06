package com.chat.familyimagechat.feature.presentation.models;

import java.time.ZonedDateTime;

public class ImageChatUI {
    private final String imagePath;
    private final ZonedDateTime dateTime;
    private final boolean isMe;
    private final MessageDelivery delivery;

    // Constructor
    public ImageChatUI(String imagePath, ZonedDateTime dateTime, boolean isMe, MessageDelivery delivery) {
        this.imagePath = imagePath;
        this.dateTime = dateTime;
        this.isMe = isMe;
        this.delivery = delivery;
    }

    // Constructor with default delivery value
    public ImageChatUI(String imagePath, ZonedDateTime dateTime, boolean isMe) {
        this(imagePath, dateTime, isMe, MessageDelivery.NOT_DELIVERED);
    }

    // Getters
    public String getImagePath() {
        return imagePath;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public boolean isMe() {
        return isMe;
    }

    public MessageDelivery getDelivery() {
        return delivery;
    }

    // toString method (if needed)
    @Override
    public String toString() {
        return "ImageChatUI{" +
                "imagePath='" + imagePath + '\'' +
                ", dateTime=" + dateTime +
                ", isMe=" + isMe +
                ", delivery=" + delivery +
                '}';
    }
}