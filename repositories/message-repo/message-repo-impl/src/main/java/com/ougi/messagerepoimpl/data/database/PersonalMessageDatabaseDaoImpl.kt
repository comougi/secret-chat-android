package com.ougi.messagerepoimpl.data.database

import androidx.room.*
import com.ougi.messagerepoapi.data.database.PersonalMessageDatabaseDao
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalMessageDatabaseDaoImpl : PersonalMessageDatabaseDao {

    @Query("SELECT * FROM personal_messages WHERE id=:id")
    override suspend fun getMessageById(id: String): PersonalMessage?

    @Update
    override suspend fun updateMessage(message: PersonalMessage)

    @Insert
    override suspend fun insertMessage(message: PersonalMessage)

    @Query("SELECT * FROM personal_messages WHERE chatId=:chatId")
    override fun getChatMessagesFlow(chatId: String): Flow<List<PersonalMessage>>

    @Delete
    override suspend fun deleteMessage(message: PersonalMessage)
}