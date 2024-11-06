package com.chat.familyimagechat.feature.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.chat.familyimagechat.db.ChatDao;
import com.chat.familyimagechat.feature.domain.LocalChatSourceRepository;

import javax.inject.Inject;

public class LocalChatSourceRepositoryImpl implements LocalChatSourceRepository {
    private static final String TAG = "LocalChatSourceReposito";
    private final ChatDao dao;
    @Inject
    public LocalChatSourceRepositoryImpl(ChatDao dao){
        this.dao = dao;
    }


    @Override
    public void print(Context context) {
        Toast.makeText(context, "print", Toast.LENGTH_SHORT).show();
        //Log.i(TAG, "print: test");
    }
}
