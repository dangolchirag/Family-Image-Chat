package com.chat.familyimagechat.db;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.chat.familyimagechat.feature.presentation.models.MessageDelivery;

import java.time.ZonedDateTime;

@Entity(
        tableName = "family_chat"
)
public class FamilyChatEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private final String imagePath;
    private final Long dateTime;
    private final boolean isMe;
    private final MessageDelivery delivery;

    public FamilyChatEntity(String imagePath, Long dateTime, boolean isMe, MessageDelivery delivery) {
        this.imagePath = imagePath;
        this.dateTime = dateTime;
        this.isMe = isMe;
        this.delivery = delivery;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public boolean isMe() {
        return isMe;
    }

    public MessageDelivery getDelivery() {
        return delivery;
    }
}
