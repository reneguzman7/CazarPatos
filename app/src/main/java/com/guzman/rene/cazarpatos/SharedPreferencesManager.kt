package com.guzman.rene.cazarpatos

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) : FileHandler {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

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
