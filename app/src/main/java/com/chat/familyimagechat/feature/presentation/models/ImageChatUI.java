package com.chat.familyimagechat.feature.presentation.models;

import java.time.ZonedDateTime;

public class ImageChatUI {
    private final int id;
    private final String imagePath;
    private final ZonedDateTime dateTime;
    private final boolean isMe;
    private final MessageDelivery delivery;

    // Constructor
    public ImageChatUI(int id,String imagePath, ZonedDateTime dateTime, boolean isMe, MessageDelivery delivery) {
        this.id = id;
        this.imagePath = imagePath;
        this.dateTime = dateTime;
        this.isMe = isMe;
        this.delivery = delivery;
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

    public int getId() {
        return id;
    }
}