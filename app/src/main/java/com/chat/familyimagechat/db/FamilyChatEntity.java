package com.chat.familyimagechat.db;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "family_chat"
)
public class FamilyChatEntity {

    @PrimaryKey
    public int id;

    public FamilyChatEntity() {

    }

}
