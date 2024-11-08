package com.chat.familyimagechat.feature.data;

import com.chat.familyimagechat.db.ChatDao;
import com.chat.familyimagechat.db.FamilyChatEntity;
import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;
import com.chat.familyimagechat.feature.domain.models.ChatItem;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class LocalChatSourceRepositoryImpl implements LocalChatSourceRepository {
    private static final String TAG = "LocalChatSourceReposito";
    private final ChatDao dao;

    @Inject
    public LocalChatSourceRepositoryImpl(ChatDao dao) {
        this.dao = dao;
    }


    @Override
    public List<ChatItem> getAllChats() {
        return dao.getAllChats().stream().map(FamilyChatEntity::toChatItem).collect(Collectors.toList());
    }

    @Override
    public void upsertChat(ChatItem chat) {
        dao.upsertChat(chat.toEntity());
    }
}
