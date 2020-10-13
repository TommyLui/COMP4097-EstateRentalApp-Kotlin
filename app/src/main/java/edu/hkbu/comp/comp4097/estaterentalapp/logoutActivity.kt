package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.hkbu.comp.comp4097.estaterentalapp.data.AccountInfo
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses.Network
import kotlinx.android.synthetic.main.layout_login_page.*
import kotlinx.android.synthetic.main.layout_logout_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class logoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_logout_page)

        logoutbtn.setOnClickListener {
            logout()
        }
    }

    fun logout(){
        val LOGOUT_URL = "https://morning-plains-00409.herokuapp.com/user/logout"
        CoroutineScope(Dispatchers.IO).launch {
                val json = Network.userLogout(LOGOUT_URL)
                Log.d("Network", "logout checkpoint 4")
//                Log.d("Network", json.toString())
                Log.d("Network", "logout checkpoint 5")
                var sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
                sharedPreferences.edit()
                    .putString("loginState", "logout")
                    .apply()
            finish()
        }
    }

}