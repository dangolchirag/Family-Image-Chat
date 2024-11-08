package com.chat.familyimagechat.feature.presentation;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.chat.familyimagechat.db.ChatInsertWorker;
import com.chat.familyimagechat.db.FamilyChatEntity;
import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;
import com.chat.familyimagechat.feature.domain.models.ChatItem;
import com.chat.familyimagechat.feature.presentation.interfaces.OnChatsReceived;
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class FamilyImageChatViewModel extends ViewModel {
    List<ImageChatUI> chats = new ArrayList<>();
    Uri uri = Uri.parse("android.resource://com.chat.familyimagechat/drawable/medium");
    private static final String TAG = "FamilyImageChatViewMode";
    private final LocalChatSourceRepository localChatSourceRepository;
    private final Context context;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private OnChatsReceived onChatsReceived;
    @Inject
    public FamilyImageChatViewModel(@ApplicationContext Context application, LocalChatSourceRepository localChatSourceRepository) {
        this.localChatSourceRepository = localChatSourceRepository;
        this.context = application.getApplicationContext();
        getAllChats();
    }

    public void upsertChat(ImageChatUI chat) {
        chats.add(chat);
        insertIntoDB(chat);
    }

    private void insertIntoDB(ImageChatUI chat) {

        Data inputData = new Data.Builder()
                .putInt("_id",chat.getId())
                .putString("_image",chat.getImagePath())
                .putBoolean("_isMe",chat.isMe())
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
    private void getAllChats() {
        executor.execute(() -> {
            chats = localChatSourceRepository.getAllChats().stream().map(ChatItem::toUI).collect(Collectors.toList());
            onChatsReceived.onReceived();
            Log.i(TAG, "getAllChats: called");
            for (ImageChatUI item : chats) {
                Log.i(TAG, "run: "+item.getId());
            }
        });
    }

    public void setOnChatsReceived(OnChatsReceived onChatsReceived) {
        this.onChatsReceived = onChatsReceived;
    }
}
