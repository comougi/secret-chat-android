package com.ougi.encryptionimpl.data

import android.util.Base64
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ougi.corecommon.Config
import com.ougi.encryptionapi.data.EncryptedDataStoreApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import kotlinx.coroutines.flow.first
import java.security.Key
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.SecretKey
import javax.inject.Inject

class KeyStorageApiImpl @Inject constructor(
    private val keyGenerationUtils: KeyGenerationUtils,
    private val encryptedDataStoreApi: EncryptedDataStoreApi,
) : KeyStorageApi {

    override suspend fun savePassword(password: String) {
        encryptedDataStoreApi.write(PASS_ENCRYPTED, password)
    }

    override suspend fun hasPassword(): Boolean {
        return encryptedDataStoreApi.read<String>(PASS_ENCRYPTED, false).first() != null
    }

    override suspend fun readPassword(): String? {
        return encryptedDataStoreApi.read<String>(PASS_ENCRYPTED).first()
    }

    override suspend fun saveDhKeyPair(public: String, private: String) {
        encryptedDataStoreApi.write(DH_PUBLIC_KEY_ENCRYPTED, public)
        encryptedDataStoreApi.write(DH_PRIVATE_KEY_ENCRYPTED, private)
    }

    override suspend fun saveRsaKeyPair(public: String, private: String) {
        encryptedDataStoreApi.write(RSA_PUBLIC_KEY_ENCRYPTED, public)
        encryptedDataStoreApi.write(RSA_PRIVATE_KEY_ENCRYPTED, private)
    }

    override suspend fun readDhKeyPair(): KeyPair =
        readKeyPair(DH_PUBLIC_KEY_ENCRYPTED, DH_PRIVATE_KEY_ENCRYPTED, "DH")
            ?: keyGenerationUtils.generateDHKeyPair().also { keyPair ->
                val public = encodeKeyToString(keyPair.public)
                val private = encodeKeyToString(keyPair.private)
                saveDhKeyPair(public, private)
            }


    override suspend fun readRsaKeyPair(): KeyPair =
        readKeyPair(RSA_PUBLIC_KEY_ENCRYPTED, RSA_PRIVATE_KEY_ENCRYPTED, "RSA")
            ?: keyGenerationUtils.generateRsaKeyPair().also { keyPair ->
                val public = encodeKeyToString(keyPair.public)
                val private = encodeKeyToString(keyPair.private)
                saveRsaKeyPair(public, private)
            }


    override suspend fun readDHAesKey(): SecretKey {
        val publicKeyStr = Config.SERVER_PUBLIC_KEY
        val publicKeyBytes = Base64.decode(publicKeyStr, Base64.NO_WRAP)
        val keyFactory = KeyFactory.getInstance("DH")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes))
        val privateKey = readDhKeyPair().private
        return keyGenerationUtils.generateDHAesSecretKey(publicKey, privateKey)
    }

    private suspend fun readKeyPair(
        publicDataStoreKey: Preferences.Key<String>,
        privateDataStoreKey: Preferences.Key<String>,
        keyFactoryAlgorithm: String
    ): KeyPair? {
        val publicKeyFromStore =
            encryptedDataStoreApi.read<String>(publicDataStoreKey).first()
        val privateKeyFromStore =
            encryptedDataStoreApi.read<String>(privateDataStoreKey).first()
        if (publicKeyFromStore == null || privateKeyFromStore == null)
            return null

        val publicKeyBytes = Base64.decode(publicKeyFromStore, Base64.NO_WRAP)
        val privateKeyBytes = Base64.decode(privateKeyFromStore, Base64.NO_WRAP)

        val kf = KeyFactory.getInstance(keyFactoryAlgorithm)

        val publicKey = kf.generatePublic(X509EncodedKeySpec(publicKeyBytes))
        val privateKey = kf.generatePrivate(PKCS8EncodedKeySpec(privateKeyBytes))
        return KeyPair(publicKey, privateKey)
    }

    private fun encodeKeyToString(key: Key): String {
        return Base64.encodeToString(key.encoded, Base64.NO_WRAP)
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