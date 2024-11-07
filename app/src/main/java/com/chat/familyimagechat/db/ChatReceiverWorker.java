package com.chat.familyimagechat.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;
import com.chat.familyimagechat.feature.domain.models.ChatItem;
import com.chat.familyimagechat.feature.presentation.models.MessageDelivery;

import java.time.Instant;
import java.time.ZoneId;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

public class ChatReceiverWorker extends Worker {
    private static final String TAG = "ChatInsertWorker";

    LocalChatSourceRepository localChatSourceRepository;

    @AssistedInject
    public ChatReceiverWorker(@Assisted @NonNull Context context,
                              @Assisted @NonNull WorkerParameters workerParams,
                              LocalChatSourceRepository localChatSourceRepository
    ) {
        super(context, workerParams);
//        Log.i(TAG, "ChatInsertWorker: "+localChatSourceRepository);
        this.localChatSourceRepository = localChatSourceRepository;
    }

    @NonNull
    @Override
    public Result doWork() {
        ChatItem chatItem = new ChatItem("chatId",  Instant
                .ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.systemDefault()), true, MessageDelivery.DELIVERED);
        Data data = new Data.Builder().putString("sadf","").build();
        return Result.success(data);
    }
}
