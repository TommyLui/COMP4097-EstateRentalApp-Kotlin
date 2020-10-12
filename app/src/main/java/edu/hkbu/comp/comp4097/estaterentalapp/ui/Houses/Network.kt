package edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

class Network {
    companion object {
        fun getTextFromNetwork(url: String) : String {
            val builder = StringBuilder()
            Log.d("Network", "${url} checkpoint 1")

            val connection =
                URL(url).openConnection() as HttpURLConnection
            connection.setRequestProperty("Accept", "application/json")
//            Log.d("Network", connection.responseCode.toString())
            Log.d("Network", "checkpoint 2")
            connection.connect()

            var data: Int = connection.inputStream.read()
            Log.d("Network", "checkpoint 3")
            while (data != -1) {
                builder.append(data.toChar())
                data = connection.inputStream.read()
            }
            return builder.toString()
        }
    }


}