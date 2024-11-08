package com.chat.familyimagechat.feature.presentation

import androidx.recyclerview.widget.DiffUtil
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI

private class DiffCallback : DiffUtil.ItemCallback<ImageChatUI>() {
    override fun areItemsTheSame(
        oldItem: ImageChatUI,
        newItem: ImageChatUI
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ImageChatUI,
        newItem: ImageChatUI
    ): Boolean {
        return oldItem.id == newItem.id && oldItem.imagePath == newItem.imagePath && oldItem.dateTime == newItem.dateTime
    }

}