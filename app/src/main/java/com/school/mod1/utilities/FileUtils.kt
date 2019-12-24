package com.school.mod1.utilities

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FileUtils(private val appContext: Context) {

    fun readTextFromRaw(@RawRes resId: Int): String {
        val stringBuilder = StringBuilder()
        try {
            var bufferedReader: BufferedReader? = null
            try {
                var line: String?
                val inputStream = appContext.resources.openRawResource(resId)
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                do {
                    line = bufferedReader.readLine()
                    if (line != null) {
                        stringBuilder.append(line)
                        stringBuilder.append("\r\n")
                    }
                } while (line != null)
            } finally {
                bufferedReader?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}