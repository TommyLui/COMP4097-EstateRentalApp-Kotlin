package edu.hkbu.comp.comp4097.estaterentalapp.ui.toRemove

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.hkbu.comp.comp4097.estaterentalapp.R
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Home.Network
import kotlinx.android.synthetic.main.fragment_logout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class logoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_logout)

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