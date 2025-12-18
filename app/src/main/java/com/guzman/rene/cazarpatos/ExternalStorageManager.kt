package com.guzman.rene.cazarpatos

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException

class ExternalStorageManager(private val context: Context) : FileHandler {

    private val fileName = "credentials.txt"

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    override fun saveCredentials(email: String, pass: String) {
        if (!isExternalStorageWritable()) {
            // Handle error: external storage not available
            return
        }
        try {
            val file = File(context.getExternalFilesDir(null), fileName)
            file.writeText("$email\n$pass")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun readCredentials(): Pair<String, String>? {
        if (!isExternalStorageReadable()) {
            // Handle error: external storage not available
            return null
        }
        try {
            val file = File(context.getExternalFilesDir(null), fileName)
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
        if (!isExternalStorageWritable()) {
            // Handle error: external storage not available
            return
        }
        val file = File(context.getExternalFilesDir(null), fileName)
        if (file.exists()) {
            file.delete()
        }
    }
}
