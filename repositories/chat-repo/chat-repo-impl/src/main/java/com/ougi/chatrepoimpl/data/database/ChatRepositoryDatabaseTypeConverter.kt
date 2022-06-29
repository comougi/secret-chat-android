package com.ougi.chatrepoimpl.data.database

import androidx.room.TypeConverter
import com.ougi.userrepoapi.data.entities.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChatRepositoryDatabaseTypeConverter {

    @TypeConverter
    fun convertUserListToJson(userList: List<User>): String {
        return Json.encodeToString(userList)
    }

    @TypeConverter
    fun convertJsonToUserList(json: String): List<User> {
        return Json.decodeFromString(json)
    }

}