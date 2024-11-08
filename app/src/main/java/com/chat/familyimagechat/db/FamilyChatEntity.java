package com.chat.familyimagechat.db;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.chat.familyimagechat.feature.domain.models.ChatItem;
import com.google.gson.Gson;

@Entity(
        tableName = "family_chat"
)
public class FamilyChatEntity {

    @PrimaryKey
    public int id;

    public String json;

    public FamilyChatEntity(int id, String json) {
        this.id = id;
        this.json = json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public ChatItem toChatItem(){
        Gson gson = new Gson();
        return gson.fromJson(json, ChatItem.class);
    }
}
