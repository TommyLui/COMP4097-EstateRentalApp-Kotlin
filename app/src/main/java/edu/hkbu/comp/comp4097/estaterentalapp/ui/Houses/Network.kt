package edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Network {
    companion object {
        fun getTextFromNetwork(url: String) : String {
            val builder = StringBuilder()
            Log.d("Network", "${url}, house checkpoint 1")
            val connection =
                URL(url).openConnection() as HttpURLConnection
//            connection.setRequestProperty("Accept", "application/json")
            Log.d("Network", "responseCode: " + connection.responseCode.toString())
            var data: Int = connection.inputStream.read()
            Log.d("Network",  "house checkpoint 2")
            while (data != -1) {
                builder.append(data.toChar())
                data = connection.inputStream.read()
            }
            Log.d("Network", builder.toString())
            return builder.toString()
        }

        fun userLogin(url: String, userName: String, password: String) :String {
            val builder = StringBuilder()

            try {
                Log.d("Network", "${url},login checkpoint 1")
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                val sendInfo = "username=Brittany&password=Hutt"

                connection.outputStream.apply {
                    write(sendInfo.toByteArray())
                    flush()
                }
                Log.d("Network", "responseCode: " + connection.responseCode.toString())
                var data: Int = connection.inputStream.read()
                Log.d("Network",  "login checkpoint 2")
                while (data != -1) {
                    builder.append(data.toChar())
                    data = connection.inputStream.read()
                }
            }catch (e: Exception){
                builder.append("error")
            }
            Log.d("Network",  builder.toString())
            Log.d("Network",  "login checkpoint 3")

            return builder.toString()
        }

        fun userLogout(url: String) : String {
            val builder = StringBuilder()
            try {
                Log.d("Network", "${url}, logout checkpoint 1")
                val connection =
                    URL(url).openConnection() as HttpURLConnection
//                connection.setRequestProperty("Accept", "application/json")
                Log.d("Network", "responseCode: " + connection.responseCode.toString())
                Log.d("Network",  "logout checkpoint 2")
                builder.append("logouted")
            }catch (e : Exception){
                Log.d("Network", "Exception: ${e}")
                builder.append("error")
            }
            Log.d("Network", builder.toString())

            return builder.toString()
        }

    }
}