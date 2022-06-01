package com.ougi.chatrepoimpl.data.database

import androidx.room.*
import com.ougi.chatrepoapi.data.database.ChatRepositoryDao
import com.ougi.chatrepoapi.data.entity.Chat
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRepositoryDatabaseDao : ChatRepositoryDao {

    @Query("SELECT * FROM chat")
    override suspend fun getAllChats(): List<Chat>

    @Query("SELECT * FROM chat")
    override fun getAllChatsFlow(): Flow<List<Chat>>

    @Query("SELECT * FROM chat WHERE id = :chatId")
    override suspend fun getChatById(chatId: String): Chat

    @Query("SELECT * FROM chat WHERE id = :chatId")
    override fun getChatByIdFlow(chatId: String): Flow<Chat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertChat(chat: Chat)

    @Query("DELETE FROM chat WHERE id = :chatId")
    override suspend fun deleteChatById(chatId: String)

    @Delete
    override suspend fun deleteChat(chat: Chat)
}