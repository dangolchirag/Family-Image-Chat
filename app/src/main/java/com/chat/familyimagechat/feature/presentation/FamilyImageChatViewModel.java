package com.chat.familyimagechat.feature.presentation;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.chat.familyimagechat.db.ChatInsertWorker;
import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;
import com.chat.familyimagechat.feature.domain.models.ChatItem;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class FamilyImageChatViewModel extends ViewModel {
    private static final String TAG = "FamilyImageChatViewMode";
    private final LocalChatSourceRepository localChatSourceRepository;
    private final Context context;

    @Inject
    public FamilyImageChatViewModel(@ApplicationContext Context application, LocalChatSourceRepository localChatSourceRepository) {
        this.localChatSourceRepository = localChatSourceRepository;
        this.context = application.getApplicationContext();
    }

    public void print() {
//        new Thread(() -> {
//            localChatSourceRepository.upsertChat(new FamilyChatEntity(",",0L,true, MessageDelivery.DELIVERED));
//        }).start();

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
//        LiveData<List<WorkInfo>> workInfos = manager.getWorkInfosByTagLiveData("CUSTOMER_SUPPORT_IMAGE");
//
//        Log.i(TAG, "print: "+workInfos.getValue());
//        WorkInfo workInfo = workInfos.getValue().get(0);
//        Log.i(TAG, "print: "+workInfo.getState());
//        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
//            Data outputData = workInfo.getOutputData();
//            Log.i(TAG, "print: "+outputData);
//            String result = outputData.getString("asdf");
//            Log.i(TAG, "print: "+result);
//        }
    }

    public List<ChatItem> getAllChats() {
        return localChatSourceRepository.getAllChats();
    }
}
