package com.chat.familyimagechat.feature.presentation;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.chat.familyimagechat.db.ChatInsertWorker;
import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;
import com.chat.familyimagechat.feature.domain.models.ChatItem;
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class FamilyImageChatViewModel extends ViewModel {
    Uri uri = Uri.parse("android.resource://com.chat.familyimagechat/drawable/medium");
    private static final String TAG = "FamilyImageChatViewMode";
    private final LocalChatSourceRepository localChatSourceRepository;
    private final Context context;
    private final Executor executor = Executors.newSingleThreadExecutor();
    @Inject
    public FamilyImageChatViewModel(@ApplicationContext Context application, LocalChatSourceRepository localChatSourceRepository) {
        this.localChatSourceRepository = localChatSourceRepository;
        this.context = application.getApplicationContext();
    }

    public void upsertChat(ImageChatUI chat) {
//
        Data inputData = new Data.Builder()
                .putString("chat_id", "chat")
                .putString("chat_message", "chat.getMessage()")
                .build();

        // Create a OneTimeWorkRequest for the ChatInsertWorker
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ChatInsertWorker.class)
                .setInputData(inputData)
                .addTag("CUSTOMER_SUPPORT_IMAGE")
                .build();

        // Enqueue the work request
        WorkManager manager = WorkManager.getInstance(context);
        manager.enqueue(workRequest);
//
    }

    public void getAllChats() {
        executor.execute(() -> {
            Log.i(TAG, "run: ");
            List<ChatItem> items = localChatSourceRepository.getAllChats();
            for (ChatItem item : items) {
                Log.i(TAG, "run: "+item.getId());
            }
        });
    }
}
