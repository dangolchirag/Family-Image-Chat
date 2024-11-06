package com.chat.familyimagechat.feature.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chat.familyimagechat.R;
import com.chat.familyimagechat.databinding.ChatItemMeBinding;
import com.chat.familyimagechat.databinding.ChatItemNotMeBinding;
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;
import com.chat.familyimagechat.utils.Utils;

import java.util.List;

public class ImageChatAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ImageChatUI> chats;

    public ImageChatAdaptor(List<ImageChatUI> chats) {
        this.chats = chats;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean isMe = chats.get(viewType).isMe();
        return isMe ?
                new ImageChatMeVH(ChatItemMeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false))
                : new ImageChatVH(ChatItemNotMeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageChatVH) {
            ((ImageChatVH) holder).bind();
        } else if (holder instanceof ImageChatMeVH) {
            ((ImageChatMeVH) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ImageChatVH extends RecyclerView.ViewHolder {

        private final ChatItemNotMeBinding binding;

        public ImageChatVH(ChatItemNotMeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
            Glide.with(itemView.getContext())
                    .load(R.drawable.medium)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpToPx(16))))
                    .into(binding.chatImage);
        }
    }

    public static class ImageChatMeVH extends RecyclerView.ViewHolder {

        private final ChatItemMeBinding binding;

        public ImageChatMeVH(ChatItemMeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
            Glide.with(itemView.getContext())
                    .load(R.drawable.medium)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpToPx(16))))
                    .into(binding.chatImage);
        }
    }
}


