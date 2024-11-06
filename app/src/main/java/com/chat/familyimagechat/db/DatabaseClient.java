package com.chat.familyimagechat.db;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static DatabaseClient instance;
    private final ChatDataBase chatDatabase;

    private DatabaseClient(Context context) {
        // Build the database instance
        chatDatabase = Room.databaseBuilder(
                context.getApplicationContext(),
                ChatDataBase.class,
                "chat_database"
        ).build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public ChatDataBase getChatDatabase() {
        return chatDatabase;
    }
}
