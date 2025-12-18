package com.guzman.rene.cazarpatos

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedSharedPreferencesManager(context: Context) : FileHandler {

    // Crea o recupera la clave maestra, almacenada en el Keystore de Android
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "EncryptedUserPrefs", // Nombre del archivo
        masterKeyAlias,       // Clave maestra a usar
        context,              // Contexto
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveCredentials(email: String, pass: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.LOGIN_KEY, email)
        editor.putString(Constants.PASSWORD_KEY, pass)
        editor.apply()
    }

    override fun readCredentials(): Pair<String, String>? {
        val email = sharedPreferences.getString(Constants.LOGIN_KEY, null)
        val password = sharedPreferences.getString(Constants.PASSWORD_KEY, null)

        return if (email != null && password != null) {
            Pair(email, password)
        } else {
            null
        }
    }

    override fun clearCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove(Constants.LOGIN_KEY)
        editor.remove(Constants.PASSWORD_KEY)
        editor.apply()
    }
}
