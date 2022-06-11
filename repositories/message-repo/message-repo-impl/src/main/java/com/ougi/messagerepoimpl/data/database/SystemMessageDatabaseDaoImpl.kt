package com.ougi.messagerepoimpl.data.database

import androidx.room.*
import com.ougi.messagerepoapi.data.database.SystemMessageDatabaseDao
import com.ougi.messagerepoapi.data.entities.SystemMessage

@Dao
interface SystemMessageDatabaseDaoImpl : SystemMessageDatabaseDao {

    @Query("SELECT * FROM system_messages")
    override suspend fun getAllSystemMessages(): List<SystemMessage>?

    @Query("SELECT * FROM system_messages WHERE id=:id")
    override suspend fun getMessageById(id: String): SystemMessage?

    @Update
    override suspend fun updateMessage(message: SystemMessage)

    @Insert
    override suspend fun insertMessage(message: SystemMessage)

    @Delete
    override suspend fun deleteMessage(message: SystemMessage)

    @Query("DELETE FROM system_messages WHERE id=:id")
    override suspend fun deleteMessageById(id: String)

}