package com.chat.familyimagechat.feature.domain.models;

import com.chat.familyimagechat.db.FamilyChatEntity;
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;
import com.google.gson.Gson;

import java.time.Instant;
import java.time.ZoneId;

public class ChatItem {

    private final int id;
    private final String imagePath;
    private final long dateTime;
    private final boolean isMe;

    // Constructor
    public ChatItem(int id,String imagePath, long dateTime, boolean isMe) {
        this.id = id;
        this.imagePath = imagePath;
        this.dateTime = dateTime;
        this.isMe = isMe;
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


    public int getId() {
        return id;
    }

    public FamilyChatEntity toEntity() {
        Gson gson = new Gson();
        return new FamilyChatEntity(id,gson.toJson(this));
    }
    public ImageChatUI toUI(){
        return new ImageChatUI(id,imagePath, Instant
                .ofEpochMilli(dateTime)
                .atZone(ZoneId.systemDefault()),isMe);
    }
}
