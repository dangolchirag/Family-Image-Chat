package com.chat.familyimagechat.feature.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chat.familyimagechat.R;
import com.chat.familyimagechat.databinding.ActivityMainBinding;
import com.chat.familyimagechat.db.ChatDataBase;
import com.chat.familyimagechat.db.DatabaseClient;
import com.chat.familyimagechat.db.FamilyChatEntity;
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;
import com.chat.familyimagechat.utils.Utils;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupSupportActionBar();
        setupRecyclerView();
//        ChatDataBase db = DatabaseClient.getInstance(this).getChatDatabase();
//        new Thread(() -> {
//            db.getChatDao().upsertChat(new FamilyChatEntity());
//        }).start();
    }

    private void setupRecyclerView() {
        List<ImageChatUI> chats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chats.add(
                    new ImageChatUI(
                            "https://picsum.photos/200/300",
                            Instant
                                    .ofEpochMilli(System.currentTimeMillis())
                                    .atZone(ZoneId.systemDefault()),
                            i % 2 == 0
                    )
            );
        }
        binding.imageChatList.setAdapter(new ImageChatAdaptor(chats));
        binding.imageChatList.setLayoutManager(new LinearLayoutManager(this));
        binding.imageChatList.setItemAnimator(new DefaultItemAnimator());
        binding.imageChatList.addItemDecoration(new ChatItemPaddingDecorator(Utils.dpToPx(8), chats));
    }

    private void setupSupportActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(
                getString(R.string.app_name)
        );
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
    }

}