package com.chat.familyimagechat.feature.domain;

import android.content.Context;

import androidx.room.Upsert;

import com.chat.familyimagechat.db.FamilyChatEntity;
import com.chat.familyimagechat.feature.domain.models.ChatItem;

import java.util.List;

public interface LocalChatSourceRepository {

    List<ChatItem> getAllChats();

    @Upsert
    void upsertChat(ChatItem chat);
}
