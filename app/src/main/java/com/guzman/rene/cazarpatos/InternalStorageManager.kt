package com.guzman.rene.cazarpatos

import android.content.Context
import java.io.File
import java.io.IOException

class InternalStorageManager(private val context: Context) : FileHandler {

    private val fileName = "credentials.txt"

    override fun saveCredentials(email: String, pass: String) {
        try {
            val file = File(context.filesDir, fileName)
            file.writeText("$email\n$pass")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun readCredentials(): Pair<String, String>? {
        try {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) return null

            val lines = file.readLines()
            return if (lines.size >= 2) {
                Pair(lines[0], lines[1])
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    override fun clearCredentials() {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            file.delete()
        }
    }
}
