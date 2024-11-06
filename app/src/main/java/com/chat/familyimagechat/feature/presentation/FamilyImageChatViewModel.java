package com.chat.familyimagechat.feature.presentation;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class FamilyImageChatViewModel extends ViewModel {
    private final LocalChatSourceRepository localChatSourceRepository;
    private final Context context;

    @Inject
    public FamilyImageChatViewModel(@ApplicationContext Context application, LocalChatSourceRepository localChatSourceRepository) {
        this.localChatSourceRepository = localChatSourceRepository;
        this.context = application.getApplicationContext();
    }

    public void print() {
        localChatSourceRepository.print(context);
    }
}
