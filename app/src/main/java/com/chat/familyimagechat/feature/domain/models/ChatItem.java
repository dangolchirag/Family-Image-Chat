package com.chat.familyimagechat.feature.domain.models;

import com.chat.familyimagechat.db.FamilyChatEntity;
import com.chat.familyimagechat.feature.presentation.models.MessageDelivery;

import java.time.ZonedDateTime;

public class ChatItem {

    private final String imagePath;
    private final ZonedDateTime dateTime;
    private final boolean isMe;
    private final MessageDelivery delivery;

    // Constructor
    public ChatItem(String imagePath, ZonedDateTime dateTime, boolean isMe, MessageDelivery delivery) {
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


    public FamilyChatEntity toEntity() {
        return new FamilyChatEntity(
                imagePath,
                dateTime.toInstant().toEpochMilli(),
                isMe,
                delivery);
    }
}
