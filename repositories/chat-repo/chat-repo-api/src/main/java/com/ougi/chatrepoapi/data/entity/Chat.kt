package com.ougi.chatrepoapi.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ougi.userrepoapi.data.entities.User
import kotlinx.serialization.Serializable

@[Serializable Entity]
data class Chat(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String = id.filter { char -> char.isDigit() }.take(10),
    val users: List<User>
)