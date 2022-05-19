package com.ougi.encryptionimpl.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ougi.corecommon.Config
import com.ougi.encryptionapi.data.EncryptedDataStoreApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import javax.crypto.SecretKey
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Config.DATASTORE_NAME)

class EncryptedDataStoreApiImpl @Inject constructor(
    override val encryptionUtils: EncryptionUtils,
    private val keyGenerationUtils: KeyGenerationUtils,
    context: Context
) : EncryptedDataStoreApi() {
    override val secretKey: SecretKey
        get() = keyGenerationUtils.secretKey!!
    override val dataStore: DataStore<Preferences> = context.dataStore
}
