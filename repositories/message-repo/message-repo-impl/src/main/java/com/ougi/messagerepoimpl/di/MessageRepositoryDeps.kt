package com.ougi.messagerepoimpl.di

import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.dbapi.data.DbClientApi
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils

interface MessageRepositoryDeps : BaseFeatureDeps {
    val dbClientApi: DbClientApi
    val encryptionClientApi: EncryptionClientApi
    val keyGenerationUtils: KeyGenerationUtils
    val encryptionUtils: EncryptionUtils
}