package com.wealthpark.exam.core.security

import com.wealthpark.exam.core.extensions.logError
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object Encryption {

    fun encrypt(dataToEncrypt: ByteArray, password: CharArray): HashMap<String, ByteArray> {
        val map = HashMap<String, ByteArray>()

        try {
            val random = SecureRandom()
            val salt = ByteArray(256)
            random.nextBytes(salt)

            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            val ivRandom = SecureRandom()
            val iv = ByteArray(16)
            ivRandom.nextBytes(iv)
            val ivSpec = IvParameterSpec(iv)

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(dataToEncrypt)

            map["salt"] = salt
            map["iv"] = iv
            map["encrypted"] = encrypted
        } catch (e: Exception) {
            logError(e)
        }

        return map

    }

    fun decrypt(map: HashMap<String, ByteArray>, password: CharArray): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            val salt = map["salt"]
            val iv = map["iv"]
            val encrypted = map["encrypted"]

            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            decrypted = cipher.doFinal(encrypted)
        } catch (e: Exception) {
            logError(e)
        }

        return decrypted
    }
}