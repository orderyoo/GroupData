package com.example.groupdata

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

class ServerConnection {

    @Throws(IOException::class)
    fun getContent(): String? {
        var reader: BufferedReader? = null
        var stream: InputStream? = null
        var connection: HttpURLConnection? = null

        return try {
            val url = URL(Dependencies.Const.SERVER_ADRESS)
            connection = url.openConnection() as HttpURLConnection

            // Установить метод запроса
            connection.requestMethod = "GET"

            // Установить время ожидания соединения (мс)
            connection.connectTimeout = 5000

            // Установить время ожидания чтения (мс)
            connection.readTimeout = 5000

            // Получение данных
            stream = connection.inputStream
            reader = BufferedReader(InputStreamReader(stream))
            val buf = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                buf.append(line).append("\n")
            }
            buf.toString()
        } finally {
            reader?.close()
            stream?.close()
            connection?.disconnect()
        }
    }
    private fun parse(content: String): MutableList<SchoolData>? {
        try {
            val gson = Gson()
            val listOfNotes: Type = object : TypeToken<MutableList<SchoolData>>() {}.type
            return gson.fromJson(content, listOfNotes)
        } catch (ex: Exception) {
            Log.d("TAG", "parse: Error: ${ex.message}")
        }
        return null
    }

}