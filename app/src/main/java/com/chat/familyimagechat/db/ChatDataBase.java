package com.chat.familyimagechat.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FamilyChatEntity.class}, version = 1)

public abstract class ChatDataBase extends RoomDatabase {

    public abstract ChatDao getChatDao();


}
