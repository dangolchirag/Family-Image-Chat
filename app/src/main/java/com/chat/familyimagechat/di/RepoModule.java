package com.chat.familyimagechat.di;

import com.chat.familyimagechat.feature.data.LocalChatSourceRepositoryImpl;
import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepoModule {
    @Binds
    public abstract LocalChatSourceRepository bindRepository(LocalChatSourceRepositoryImpl localChatSourceRepository);
}