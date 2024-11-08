package com.chat.familyimagechat.feature.domain;

import androidx.room.Upsert;

import com.chat.familyimagechat.feature.domain.models.ChatItem;

import java.util.List;

public interface LocalChatSourceRepository {

    List<ChatItem> getAllChats();

    @Upsert
    void upsertChat(ChatItem chat);
}
