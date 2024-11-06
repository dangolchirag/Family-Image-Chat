package com.chat.familyimagechat.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM family_chat")
    List<FamilyChatEntity> getAllChats();

    @Upsert
    void upsertChat(FamilyChatEntity chat);
}
