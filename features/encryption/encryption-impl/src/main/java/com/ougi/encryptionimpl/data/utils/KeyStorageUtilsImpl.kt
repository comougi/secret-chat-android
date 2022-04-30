package com.ougi.encryptionimpl.data.utils

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.datastoreapi.data.read
import com.ougi.datastoreapi.data.write
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import kotlinx.coroutines.flow.first
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.X509EncodedKeySpec
import javax.inject.Inject

class KeyStorageUtilsImpl @Inject constructor(private val dataStoreClientApi: DataStoreClientApi) :
    KeyStorageUtils {

    override suspend fun saveDhKeyPair(public: String, private: String) {
        saveKey(DH_PUBLIC_KEY_ENCRYPTED, public)
        saveKey(DH_PRIVATE_KEY_ENCRYPTED, private)
    }

    override suspend fun saveRsaKeyPair(public: String, private: String) {
        saveKey(RSA_PUBLIC_KEY_ENCRYPTED, public)
        saveKey(RSA_PRIVATE_KEY_ENCRYPTED, private)
    }

    private suspend fun saveKey(dataStoreKey: Preferences.Key<String>, key: String) {
        dataStoreClientApi.write(dataStoreKey, key)
    }

    override suspend fun readDhKeyPair(): KeyPair =
        readKeyPair(DH_PUBLIC_KEY_ENCRYPTED, DH_PRIVATE_KEY_ENCRYPTED, "DH")


    override suspend fun readRsaKeyPair(): KeyPair =
        readKeyPair(RSA_PUBLIC_KEY_ENCRYPTED, RSA_PRIVATE_KEY_ENCRYPTED, "RSA")


    private suspend fun readKeyPair(
        publicDataStoreKey: Preferences.Key<String>,
        privateDataStoreKey: Preferences.Key<String>,
        keyFactoryAlgorithm: String
    ): KeyPair {
        val publicKeyStr = dataStoreClientApi.read<String, String>(publicDataStoreKey).first()
        val privateKeyStr = dataStoreClientApi.read<String, String>(privateDataStoreKey).first()
        if (publicKeyStr == null || privateKeyStr == null)
            throw NullPointerException("${keyFactoryAlgorithm}KeyPair did not saved yet")

        val kf = KeyFactory.getInstance(keyFactoryAlgorithm)
        val publicKeyBytes = publicKeyStr.encodeToByteArray()
        val privateKeyBytes = privateKeyStr.encodeToByteArray()
        val publicKey = kf.generatePublic(X509EncodedKeySpec(publicKeyBytes))
        val privateKey = kf.generatePrivate(X509EncodedKeySpec(privateKeyBytes))
        return KeyPair(publicKey, privateKey)
    }

    companion object {
        private const val RSA_PRIVATE_KEY_NAME = "PrivateRsaKey"
        val RSA_PRIVATE_KEY_ENCRYPTED = stringPreferencesKey(RSA_PRIVATE_KEY_NAME)
        private const val RSA_PUBLIC_KEY_NAME = "PublicRsaKey"
        val RSA_PUBLIC_KEY_ENCRYPTED = stringPreferencesKey(RSA_PUBLIC_KEY_NAME)
        private const val DH_PRIVATE_KEY_NAME = "PrivateDhKey"
        val DH_PRIVATE_KEY_ENCRYPTED = stringPreferencesKey(DH_PRIVATE_KEY_NAME)
        private const val DH_PUBLIC_KEY_NAME = "PublicDhKey"
        val DH_PUBLIC_KEY_ENCRYPTED = stringPreferencesKey(DH_PUBLIC_KEY_NAME)
    }
}