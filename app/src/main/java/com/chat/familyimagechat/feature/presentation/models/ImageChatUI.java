package com.chat.familyimagechat.feature.presentation.models;

import java.time.ZonedDateTime;

public class ImageChatUI {
    private final int id;
    private final String imagePath;
    private final ZonedDateTime dateTime;
    private final boolean isMe;

    public ImageChatUI(int id, String imagePath, ZonedDateTime dateTime, boolean isMe) {
        this.id = id;
        this.imagePath = imagePath;
        this.dateTime = dateTime;
        this.isMe = isMe;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public boolean isMe() {
        return isMe;
    }

    public int getId() {
        return id;
    }
}