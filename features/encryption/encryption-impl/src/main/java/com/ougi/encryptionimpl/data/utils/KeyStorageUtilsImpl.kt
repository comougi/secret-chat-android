package com.ougi.encryptionimpl.data.utils

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.datastoreapi.data.read
import com.ougi.datastoreapi.data.write
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import com.ougi.encryptionapi.data.utils.KeyUtils
import kotlinx.coroutines.flow.first
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.X509EncodedKeySpec
import javax.inject.Inject

class KeyStorageUtilsImpl @Inject constructor(
    private val dataStoreClientApi: DataStoreClientApi,
    private val keyUtils: KeyUtils
) : KeyStorageUtils {

    override suspend fun savePassword(password: String) {
        dataStoreClientApi.write(PASS_ENCRYPTED, password)
    }

    override suspend fun readPassword(): String {
        val password = dataStoreClientApi.read<String, String>(PASS_ENCRYPTED).first()
        return requireNotNull(password) { "Password didn't set" }
    }

    override suspend fun saveDhKeyPair(public: String, private: String) {
        dataStoreClientApi.write(DH_PUBLIC_KEY_ENCRYPTED, public)
        dataStoreClientApi.write(DH_PRIVATE_KEY_ENCRYPTED, private)
    }

    override suspend fun saveRsaKeyPair(public: String, private: String) {
        dataStoreClientApi.write(RSA_PUBLIC_KEY_ENCRYPTED, public)
        dataStoreClientApi.write(RSA_PRIVATE_KEY_ENCRYPTED, private)
    }

    override suspend fun readDhKeyPair(): KeyPair =
        readKeyPair(DH_PUBLIC_KEY_ENCRYPTED, DH_PRIVATE_KEY_ENCRYPTED, "DH")
            ?: keyUtils.generateDHKeyPair().also { keyPair ->
                val publicKeyString = keyPair.public.encoded.decodeToString()
                val privateKeyString = keyPair.private.encoded.decodeToString()
                saveDhKeyPair(publicKeyString, privateKeyString)
            }


    override suspend fun readRsaKeyPair(): KeyPair =
        readKeyPair(RSA_PUBLIC_KEY_ENCRYPTED, RSA_PRIVATE_KEY_ENCRYPTED, "RSA")
            ?: keyUtils.generateRsaKeyPair().also { keyPair ->
                val publicKeyString = keyPair.public.encoded.decodeToString()
                val privateKeyString = keyPair.private.encoded.decodeToString()
                saveRsaKeyPair(publicKeyString, privateKeyString)
            }


    private suspend fun readKeyPair(
        publicDataStoreKey: Preferences.Key<String>,
        privateDataStoreKey: Preferences.Key<String>,
        keyFactoryAlgorithm: String
    ): KeyPair? {
        val publicKeyStr = dataStoreClientApi.read<String, String>(publicDataStoreKey).first()
        val privateKeyStr = dataStoreClientApi.read<String, String>(privateDataStoreKey).first()
        if (publicKeyStr == null || privateKeyStr == null)
            return null

        val kf = KeyFactory.getInstance(keyFactoryAlgorithm)
        val publicKeyBytes = publicKeyStr.encodeToByteArray()
        val privateKeyBytes = privateKeyStr.encodeToByteArray()
        val publicKey = kf.generatePublic(X509EncodedKeySpec(publicKeyBytes))
        val privateKey = kf.generatePrivate(X509EncodedKeySpec(privateKeyBytes))
        return KeyPair(publicKey, privateKey)
    }

    companion object {
        private const val PASS_NAME = "Password"
        val PASS_ENCRYPTED = stringPreferencesKey(PASS_NAME)
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