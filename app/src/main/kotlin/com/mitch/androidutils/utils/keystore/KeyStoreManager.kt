package com.mitch.androidutils.utils.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class KeyStoreManager(private val keyStoreAlias: String) {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val encryptCipher
        get() = Cipher.getInstance(Transformation).apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher {
        return Cipher.getInstance(Transformation).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(keyStoreAlias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(Algorithm).apply {
            init(
                KeyGenParameterSpec.Builder(
                    keyStoreAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setKeySize(KeySize * 8) // key size in bits
                    .setBlockModes(BlockMode)
                    .setEncryptionPaddings(Padding)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    fun encrypt(bytes: ByteArray, outputStream: OutputStream) {
        val cipher = encryptCipher
        val iv = cipher.iv
        outputStream.use {
            it.write(iv)
            // write the payload in chunks to make sure to support larger data amounts (this would otherwise fail silently and result in corrupted data being read back)
            val inputStream = ByteArrayInputStream(bytes)
            val buffer = ByteArray(ChunkSize)
            while (inputStream.available() > ChunkSize) {
                inputStream.read(buffer)
                val ciphertextChunk = cipher.update(buffer)
                it.write(ciphertextChunk)
            }
            // the last chunk must be written using doFinal() because this takes the padding into account
            val remainingBytes = inputStream.readBytes()
            val lastChunk = cipher.doFinal(remainingBytes)
            it.write(lastChunk)
        }
    }

    fun decrypt(inputStream: InputStream): ByteArray {
        return inputStream.use {
            val iv = ByteArray(KeySize)
            it.read(iv)
            val cipher = getDecryptCipherForIv(iv)
            val outputStream = ByteArrayOutputStream()

            // read the payload in chunks to make sure to support larger data amounts (this would otherwise fail silently and result in corrupted data being read back)
            val buffer = ByteArray(ChunkSize)
            while (inputStream.available() > ChunkSize) {
                inputStream.read(buffer)
                val ciphertextChunk = cipher.update(buffer)
                outputStream.write(ciphertextChunk)
            }
            // the last chunk must be read using doFinal() because this takes the padding into account
            val remainingBytes = inputStream.readBytes()
            val lastChunk = cipher.doFinal(remainingBytes)
            outputStream.write(lastChunk)

            outputStream.toByteArray()
        }
    }

    companion object {
        private const val ChunkSize = 1024 * 4 // bytes
        private const val KeySize = 16 // bytes
        private const val Algorithm = KeyProperties.KEY_ALGORITHM_AES
        private const val BlockMode = KeyProperties.BLOCK_MODE_CBC
        private const val Padding = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val Transformation = "$Algorithm/$BlockMode/$Padding"
    }
}
