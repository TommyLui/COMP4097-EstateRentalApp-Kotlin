package edu.hkbu.comp.comp4097.estaterentalapp.ui.Home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.lang.Exception
import java.net.HttpCookie
import java.net.HttpURLConnection
import java.net.URL

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

        fun userLogin(url: String, userName: String, password: String, sharedPreferences: SharedPreferences) :String {
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
                val cookie = HttpCookie.parse(
                    connection.getHeaderField("Set-Cookie")
                ).get(0).toString()

                if (sharedPreferences != null) {
                    sharedPreferences.edit()
                        .putString("cookie", cookie.toString())
                        .apply()
                }

                Log.d("Network",  "Cookei: ${sharedPreferences.getString("cookie", "")}")

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
                connection.requestMethod = "POST"
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

        fun getRental(url: String, cookie: String) : String {
            val builder = StringBuilder()
            Log.d("Network", "Function: getRental() executing...")
//            try {
                val connection =
                    URL(url).openConnection() as HttpURLConnection
                Log.d("Network", "Make connection to: ${url}")
                connection.setRequestProperty("Cookie", cookie.toString())
                Log.d("Network", "responseCode: ${connection.responseCode}")
                var data: Int = connection.inputStream.read()
                Log.d("Network", "Data inputting")
                while (data != -1) {
                    builder.append(data.toChar())
                    data = connection.inputStream.read()
                }
//            }catch (e : Exception){
//                Log.d("Network", "Exception: ${e}")
//                builder.append("error")
//            }
            Log.d("Network", "Data got: ${builder.toString()}")

            return builder.toString()
        }

        fun addRental(url: String, cookie: String, id: Int) : String {
            val builder = StringBuilder()
            Log.d("Network", "Function: addRental() executing...")
//            try {
            val connection =
                URL(url+id.toString()).openConnection() as HttpURLConnection
            Log.d("Network", "Make connection to: ${url+ id.toString()}")
            connection.setRequestProperty("Cookie", cookie.toString())
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            val sendInfo = "fk="+id

            connection.outputStream.apply {
                write(sendInfo.toByteArray())
                flush()
            }

            Log.d("Network", "responseCode: ${connection.responseCode}")
            builder.append(connection.responseCode)

            Log.d("Network", "Data got: ${builder.toString()}")

            return builder.toString()
        }

        fun dropRental(url: String, cookie: String, id: Int) : String {
            val builder = StringBuilder()
            Log.d("Network", "Function: dropRental() executing...")
//            try {
            val connection =
                URL(url+id.toString()).openConnection() as HttpURLConnection
            Log.d("Network", "Make connection to: ${url+ id.toString()}")
            connection.setRequestProperty("Cookie", cookie.toString())
            connection.requestMethod = "DELETE"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            val sendInfo = "fk="+id

            connection.outputStream.apply {
                write(sendInfo.toByteArray())
                flush()
            }

            Log.d("Network", "responseCode: ${connection.responseCode}")
            builder.append(connection.responseCode)

            Log.d("Network", "Data got: ${builder.toString()}")

            return builder.toString()
        }

    }
}