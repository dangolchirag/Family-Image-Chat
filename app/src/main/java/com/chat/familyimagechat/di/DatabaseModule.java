package com.chat.familyimagechat.di;

import com.chat.familyimagechat.db.ChatDao;
import com.chat.familyimagechat.db.ChatDataBase;
import dagger.Provides;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
import android.content.Context;
import androidx.room.Room;
import dagger.hilt.android.qualifiers.ApplicationContext;
@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    private static final String DATABASE_NAME = "chat_db";

    @Provides
    @Singleton
    public static ChatDataBase provideRoomDatabase(@ApplicationContext Context appContext) {
        return Room.databaseBuilder(appContext, ChatDataBase.class, DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    public ChatDao provideBrokerDao(ChatDataBase database) {
        return database.getChatDao();
    }
}