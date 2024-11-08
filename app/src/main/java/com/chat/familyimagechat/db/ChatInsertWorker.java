package com.chat.familyimagechat.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;
import com.chat.familyimagechat.feature.domain.models.ChatItem;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;


@HiltWorker
public class ChatInsertWorker extends Worker {
    private static final String TAG = "ChatInsertWorker";

    LocalChatSourceRepository localChatSourceRepository;

    @AssistedInject
    public ChatInsertWorker(@Assisted @NonNull Context context,
                            @Assisted @NonNull WorkerParameters workerParams,
                            LocalChatSourceRepository localChatSourceRepository
    ) {
        super(context, workerParams);
        this.localChatSourceRepository = localChatSourceRepository;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {

            int chatId = getInputData().getInt("_id", 0);
            String image = getInputData().getString("_image");
            boolean isMe = getInputData().getBoolean("_isMe", true);


            if (image == null) {
                return Result.failure();
            }

            ChatItem chatItem = new ChatItem(chatId, image, System.currentTimeMillis(), isMe);
            localChatSourceRepository.upsertChat(chatItem);

            return Result.success();
        } catch (Exception e) {

            return Result.failure();
        }
    }

}