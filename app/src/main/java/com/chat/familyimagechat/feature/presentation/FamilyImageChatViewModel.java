package com.chat.familyimagechat.feature.presentation;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.chat.familyimagechat.db.ChatInsertWorker;
import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;
import com.chat.familyimagechat.feature.domain.models.ChatItem;
import com.chat.familyimagechat.feature.presentation.interfaces.OnAutoResponseReceived;
import com.chat.familyimagechat.feature.presentation.interfaces.OnChatsReceived;
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    static private final List<String> autoResponseList = new ArrayList<>();
    static {
        autoResponseList.add("android.resource://com.chat.familyimagechat/drawable/namaste");
        autoResponseList.add("android.resource://com.chat.familyimagechat/drawable/surprise");
        autoResponseList.add("android.resource://com.chat.familyimagechat/drawable/angry");
        autoResponseList.add("android.resource://com.chat.familyimagechat/drawable/good_job");
        autoResponseList.add("android.resource://com.chat.familyimagechat/drawable/good_bye");
    }
    private static final String TAG = "FamilyImageChatViewMode";
    private final LocalChatSourceRepository localChatSourceRepository;
    private final Context context;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private OnChatsReceived onChatsReceived;
    private OnAutoResponseReceived onAutoResponseReceived;
    @Inject
    public FamilyImageChatViewModel(@ApplicationContext Context application, LocalChatSourceRepository localChatSourceRepository) {
        this.localChatSourceRepository = localChatSourceRepository;
        this.context = application.getApplicationContext();
        getAllChats();
    }

    public void upsertChat(ImageChatUI chat) {
        chats.add(chat);
        insertIntoDB(chat);
        autoResponseChat();
    }

    private void autoResponseChat() {
        ImageChatUI autoChat = new ImageChatUI(chats.size() , pickRandomAutoResponse(), Instant
                .ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.systemDefault()), false);
        chats.add(autoChat);
        insertIntoDB(autoChat);
        new Handler().postDelayed(() -> {
            onAutoResponseReceived.onReceived();
        }, 1000);
    }

    private void insertIntoDB(ImageChatUI chat) {

        Data inputData = new Data.Builder()
                .putInt("_id", chat.getId())
                .putString("_image", chat.getImagePath())
                .putBoolean("_isMe", chat.isMe())
                .build();

        // Create a OneTimeWorkRequest for the ChatInsertWorker
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ChatInsertWorker.class)
                .setInputData(inputData)
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
        });
    }

    public String pickRandomAutoResponse() {
        Random random = new Random();
        int randomIndex = random.nextInt(autoResponseList.size());
        return autoResponseList.get(randomIndex);
    }
    public void setOnChatsReceived(OnChatsReceived onChatsReceived) {
        this.onChatsReceived = onChatsReceived;
    }

    public void setOnAutoResponseReceived(OnAutoResponseReceived onAutoResponseReceived) {
        this.onAutoResponseReceived = onAutoResponseReceived;
    }
}
