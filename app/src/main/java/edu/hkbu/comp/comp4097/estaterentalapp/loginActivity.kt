package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import edu.hkbu.comp.comp4097.estaterentalapp.data.AccountInfo
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses.HousesListRecyclerViewAdapter
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses.Network
import kotlinx.android.synthetic.main.layout_login_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class loginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login_page)

        loginbtn.setOnClickListener {
            var loginetext = findViewById(R.id.usernameetext) as EditText
//            Toast.makeText(this, loginetext.text, Toast.LENGTH_SHORT).show()
            var passwordetext = findViewById(R.id.passwordetext) as EditText
//            Toast.makeText(this, passwordetext.text, Toast.LENGTH_SHORT).show()

            var sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)

            sharedPreferences.edit()
                .putString("account", loginetext.text.toString())
                .putString("password", passwordetext.text.toString())
                .apply()

            login(loginetext.text.toString(), passwordetext.text.toString())
        }
    }

    fun login(userName: String, password: String){
        val LOGIN_URL = "https://morning-plains-00409.herokuapp.com/user/login"
        CoroutineScope(Dispatchers.IO).launch {
                val json = Network.userLogin(LOGIN_URL, userName, password)
                if (json.toString() != "error") {
                    Log.d("Network", "login checkpoint 4")
//                Log.d("Network", json.toString())
                    val accountInfo = Gson().fromJson<AccountInfo>(json, object :
                        TypeToken<AccountInfo>() {}.type)
                    Log.d("Network", "login checkpoint 5")
//                Log.d("Network", accountInfo.toString())
                    var sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
                    sharedPreferences.edit()
                        .putString("loginState", "login")
                        .putString("username", accountInfo.username.toString())
                        .putString("userIcon", accountInfo.avatar.toString())
                        .apply()
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@loginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }else{
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@loginActivity, "Login fail!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}