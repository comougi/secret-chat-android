package com.ougi.chatrepoimpl.data.database

import androidx.room.*
import com.ougi.chatrepoapi.data.database.ChatDatabaseDao
import com.ougi.chatrepoapi.data.entity.Chat
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDatabaseDaoImpl : ChatDatabaseDao {

    @Query("SELECT * FROM chat ORDER BY id DESC")
    override suspend fun getAllChats(): List<Chat>

    @Query("SELECT * FROM chat ORDER BY id DESC")
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