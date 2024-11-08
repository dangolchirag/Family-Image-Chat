package com.chat.familyimagechat.feature.presentation;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chat.familyimagechat.R;
import com.chat.familyimagechat.databinding.ChatItemMeBinding;
import com.chat.familyimagechat.databinding.ChatItemNotMeBinding;
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;
import com.chat.familyimagechat.utils.Utils;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImageChatAdaptor extends ListAdapter<ImageChatUI,RecyclerView.ViewHolder> {



    public ImageChatAdaptor() {
        super(new DiffCallback());

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean isMe = getItem(viewType).isMe();
        return isMe ?
                new ImageChatMeVH(ChatItemMeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false))
                : new ImageChatVH(ChatItemNotMeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ImageChatUI chatUI = getItem(position);
        if (holder instanceof ImageChatVH) {
            ((ImageChatVH) holder).bind(chatUI);
        } else if (holder instanceof ImageChatMeVH) {
            ((ImageChatMeVH) holder).bind(chatUI);
        }
    }
    ImageChatUI getChat(int position) {
        return getItem(position);
    }


    public static class ImageChatVH extends RecyclerView.ViewHolder {

        private final ChatItemNotMeBinding binding;

        public ImageChatVH(ChatItemNotMeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ImageChatUI chat) {
            Glide.with(itemView.getContext())
                    .load(Uri.parse(chat.getImagePath()))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpToPx(8))))
                    .into(binding.chatImage);
            binding.dateTime.setText(String.valueOf(DateTimeFormatter
                    .ofPattern("mm:ss a")
                    .format(chat.getDateTime())));
        }
    }

    public static class ImageChatMeVH extends RecyclerView.ViewHolder {

        private static final String TAG = "ImageChatMeVH";
        private final ChatItemMeBinding binding;

        public ImageChatMeVH(ChatItemMeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ImageChatUI chat) {
            Glide.with(itemView.getContext())
                    .load(Uri.parse(chat.getImagePath()))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(Utils.dpToPx(8))))
                    .into(binding.chatImage);
            binding.dateTime.setText(String.valueOf(DateTimeFormatter
                    .ofPattern("h:m a")
                    .format(chat.getDateTime())));
        }
    }
}


