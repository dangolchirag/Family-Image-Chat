package com.chat.familyimagechat.feature.domain;

import android.content.Context;

import androidx.room.Upsert;

import com.chat.familyimagechat.db.FamilyChatEntity;

import java.util.List;

public interface LocalChatSourceRepository {

    List<FamilyChatEntity> getAllChats();

    @Upsert
    void upsertChat(FamilyChatEntity chat);
}
