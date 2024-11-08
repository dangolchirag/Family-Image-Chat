package com.chat.familyimagechat.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;
import com.chat.familyimagechat.feature.domain.models.ChatItem;
import com.chat.familyimagechat.feature.presentation.models.MessageDelivery;

import java.time.Instant;
import java.time.ZoneId;

import javax.inject.Inject;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
//import androidx.hilt.work.HiltWorker;


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
//        Log.i(TAG, "ChatInsertWorker: "+localChatSourceRepository);
        this.localChatSourceRepository = localChatSourceRepository;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            // Get the chat data from the WorkManager input data
            String chatId = getInputData().getString("chat_id");
            String chatMessage = getInputData().getString("chat_message");



            // Validate the input data
            if (chatId == null || chatMessage == null) {
                return Result.failure();  // Return failure if data is invalid
            }

            // Create a ChatItem object and upsert it into the repository
            ChatItem chatItem = new ChatItem(1,"chatId1",System.currentTimeMillis(), true, MessageDelivery.DELIVERED);
            localChatSourceRepository.upsertChat(chatItem);
            Data data = new Data.Builder().putString("sadf","insert").build();
            return Result.success(data);  // Return success if everything is okay
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();  // Return failure if there is an exception
        }
    }

}