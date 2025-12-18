package com.guzman.rene.cazarpatos

interface FileHandler {
    fun saveCredentials(email: String, pass: String)
    fun readCredentials(): Pair<String, String>?
    fun clearCredentials()
}
