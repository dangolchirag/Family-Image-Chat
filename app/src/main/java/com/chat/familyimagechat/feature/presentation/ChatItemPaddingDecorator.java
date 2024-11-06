package com.chat.familyimagechat.feature.presentation;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;
import com.chat.familyimagechat.utils.Utils;

import java.util.List;

public class ChatItemPaddingDecorator extends RecyclerView.ItemDecoration {

    private static final String TAG = "ChatItemPaddingDecorato";
    private final int padding;
    private final List<ImageChatUI> chats;

    public ChatItemPaddingDecorator(int padding, List<ImageChatUI> chats) {
        this.padding = padding;
        this.chats = chats;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        ImageChatUI chat = chats.get(parent.getChildAdapterPosition(view));

        int width = parent.getWidth();
        int horizontalPadding = width * 15 / 100;

        if (chat.isMe()) {
            outRect.left = horizontalPadding;
        } else {
            outRect.right = horizontalPadding;
        }


        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;

        if (position == 0) {
            outRect.top = padding + Utils.dpToPx(20);
        } else {
            outRect.top = padding;
        }
        if (position == itemCount - 1) {
            outRect.bottom = padding + Utils.dpToPx(100);
        } else {
            outRect.bottom = padding;
        }


    }
}
