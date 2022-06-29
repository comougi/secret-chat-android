package com.ougi.messagerepoimpl.data.database

import androidx.room.TypeConverter
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import com.ougi.messagerepoapi.data.entities.SystemMessage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MessageRepositoryDatabaseTypeConverter {

    @TypeConverter
    fun convertPersonalMessageToJson(personalMessage: PersonalMessage): String {
        return Json.encodeToString(personalMessage)
    }

    @TypeConverter
    fun convertJsonToPersonalMessage(json: String): PersonalMessage {
        return Json.decodeFromString(json)
    }

    @TypeConverter
    fun convertSystemMessageToJson(systemMessage: SystemMessage): String {
        return Json.encodeToString(systemMessage)
    }

    @TypeConverter
    fun convertJsonToSystemMessage(json: String): SystemMessage {
        return Json.decodeFromString(json)
    }

    @TypeConverter
    fun convertSystemMessageDataToJson(data: SystemMessage.Data): String {
        return Json.encodeToString(data)
    }

    @TypeConverter
    fun convertJsonToSystemMessageData(json: String): SystemMessage.Data {
        return Json.decodeFromString(json)
    }
}