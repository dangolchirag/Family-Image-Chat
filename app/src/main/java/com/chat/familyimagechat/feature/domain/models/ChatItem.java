package com.chat.familyimagechat.feature.domain.models;

import com.chat.familyimagechat.db.FamilyChatEntity;
import com.chat.familyimagechat.feature.presentation.models.MessageDelivery;
import com.google.gson.Gson;

import java.time.ZonedDateTime;

public class ChatItem {

    private final int id;
    private final String imagePath;
    private final long dateTime;
    private final boolean isMe;
    private final MessageDelivery delivery;

    // Constructor
    public ChatItem(int id,String imagePath, long dateTime, boolean isMe, MessageDelivery delivery) {
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

    public long getDateTime() {
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

    public FamilyChatEntity toEntity() {
        Gson gson = new Gson();
        return new FamilyChatEntity(id,gson.toJson(this));
    }
}
