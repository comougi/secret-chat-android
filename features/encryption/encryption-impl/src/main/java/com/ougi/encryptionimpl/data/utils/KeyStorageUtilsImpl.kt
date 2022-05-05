package com.ougi.encryptionimpl.data.utils

import android.util.Base64
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.datastoreapi.data.read
import com.ougi.datastoreapi.data.write
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import kotlinx.coroutines.flow.first
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.X509EncodedKeySpec
import javax.crypto.SecretKey
import javax.inject.Inject

class KeyStorageUtilsImpl @Inject constructor(
    private val dataStoreClientApi: DataStoreClientApi,
    private val keyGenerationUtils: KeyGenerationUtils,
    private val encryptionUtils: EncryptionUtils
) : KeyStorageUtils {

    private val secretKey by lazy { keyGenerationUtils.secretKey!! }

    override suspend fun savePassword(password: String, secretKey: SecretKey) {
        val passwordEncrypted = encryptionUtils.encryptViaSecretKey(password, secretKey)
        val passwordToStore = passwordEncrypted.first +
                SEPARATOR +
                Base64.encodeToString(passwordEncrypted.second, Base64.DEFAULT)
        dataStoreClientApi.write(PASS_ENCRYPTED, passwordToStore)
    }

    override suspend fun hasPassword(): Boolean {
        return dataStoreClientApi.read<String, String>(PASS_ENCRYPTED).first() != null
    }

    override suspend fun readPassword(secretKey: SecretKey): String? {
        val passwordFromStore = dataStoreClientApi.read<String, String>(PASS_ENCRYPTED).first()
            ?: return null
        val passwordSplitted = passwordFromStore.split(SEPARATOR)
        val passwordStr = passwordSplitted[0]
        val passwordIv = Base64.decode(passwordSplitted[1], Base64.DEFAULT)
        return encryptionUtils.decryptViaSecretKey(passwordStr, secretKey, passwordIv)
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
            ?: keyGenerationUtils.generateDHKeyPair().also { keyPair ->
                val (public, private) = encryptKeyPair(keyPair)
                saveDhKeyPair(public, private)
            }


    override suspend fun readRsaKeyPair(): KeyPair =
        readKeyPair(RSA_PUBLIC_KEY_ENCRYPTED, RSA_PRIVATE_KEY_ENCRYPTED, "RSA")
            ?: keyGenerationUtils.generateRsaKeyPair().also { keyPair ->
                val (public, private) = encryptKeyPair(keyPair)
                saveRsaKeyPair(public, private)
            }


    private suspend fun readKeyPair(
        publicDataStoreKey: Preferences.Key<String>,
        privateDataStoreKey: Preferences.Key<String>,
        keyFactoryAlgorithm: String
    ): KeyPair? {
        val publicKeyFromStore =
            dataStoreClientApi.read<String, String>(publicDataStoreKey).first()
        val privateKeyFromStore =
            dataStoreClientApi.read<String, String>(privateDataStoreKey).first()
        if (publicKeyFromStore == null || privateKeyFromStore == null)
            return null

        val publicKeySplitted = publicKeyFromStore.split(SEPARATOR)
        val publicKeyStr = publicKeySplitted[0]
        val publicKeyIv = Base64.decode(publicKeySplitted[1], Base64.DEFAULT)

        val privateKeySplitted = privateKeyFromStore.split(SEPARATOR)
        val privateKeyStr = privateKeySplitted[0]
        val privateKeyIv = Base64.decode(privateKeySplitted[1], Base64.DEFAULT)

        val kf = KeyFactory.getInstance(keyFactoryAlgorithm)

        val publicKeyBytes = Base64.decode(
            encryptionUtils.decryptViaSecretKey(publicKeyStr, secretKey, publicKeyIv),
            Base64.DEFAULT
        )

        val privateKeyBytes = Base64.decode(
            encryptionUtils.decryptViaSecretKey(privateKeyStr, secretKey, privateKeyIv),
            Base64.DEFAULT
        )

        val publicKey = kf.generatePublic(X509EncodedKeySpec(publicKeyBytes))
        val privateKey = kf.generatePrivate(X509EncodedKeySpec(privateKeyBytes))
        return KeyPair(publicKey, privateKey)
    }

    private fun encryptKeyPair(keyPair: KeyPair): Pair<String, String> {
        Base64.encodeToString(keyPair.public.encoded, Base64.DEFAULT)

        val publicKeyString = Base64.encodeToString(keyPair.public.encoded, Base64.DEFAULT)
        val privateKeyString = Base64.encodeToString(keyPair.private.encoded, Base64.DEFAULT)

        val publicKeyStringEncrypted =
            encryptionUtils.encryptViaSecretKey(publicKeyString, secretKey)

        val privateKeyStringEncrypted =
            encryptionUtils.encryptViaSecretKey(privateKeyString, secretKey)

        val public = publicKeyStringEncrypted.first +
                SEPARATOR +
                Base64.encodeToString(publicKeyStringEncrypted.second, Base64.DEFAULT)

        val private = privateKeyStringEncrypted.first +
                SEPARATOR +
                Base64.encodeToString(privateKeyStringEncrypted.second, Base64.DEFAULT)
        return public to private
    }

    companion object {

        private const val SEPARATOR = " !.!.! "

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