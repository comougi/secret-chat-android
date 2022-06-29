package com.ougi.messagerepoimpl.data.database

import androidx.room.*
import com.ougi.messagerepoapi.data.database.PersonalMessageDatabaseDao
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalMessageDatabaseDaoImpl : PersonalMessageDatabaseDao {

    @Query("SELECT * FROM personal_messages WHERE id=:id")
    override suspend fun getMessageById(id: String): PersonalMessage?

    @Query("SELECT * FROM personal_messages WHERE id=:id")
    override fun getMessageByIdFlow(id: String): Flow<PersonalMessage>

    @Update
    override suspend fun updateMessage(message: PersonalMessage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertMessage(message: PersonalMessage)

    @Query("SELECT * FROM personal_messages WHERE chatId=:chatId ORDER BY date DESC")
    override fun getChatMessagesFlow(chatId: String): Flow<List<PersonalMessage>>

    @Delete
    override suspend fun deleteMessage(message: PersonalMessage)
}