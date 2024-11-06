package com.chat.familyimagechat.db;

import androidx.room.TypeConverter;

import com.chat.familyimagechat.feature.presentation.models.MessageDelivery;

public class MessageDeliveryConverter {

    @TypeConverter
    public static String fromMessageDelivery(MessageDelivery messageDelivery) {
        return messageDelivery == null ? null : messageDelivery.name();
    }

    @TypeConverter
    public static MessageDelivery toMessageDelivery(String messageDeliveryString) {
        return messageDeliveryString == null ? null : MessageDelivery.valueOf(messageDeliveryString);
    }
}