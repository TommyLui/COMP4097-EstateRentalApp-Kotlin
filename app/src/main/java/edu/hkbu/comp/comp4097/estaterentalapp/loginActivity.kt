package edu.hkbu.comp.comp4097.estaterentalapp

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses.HousesListRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_login_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class loginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login_page)

        loginbtn.setOnClickListener {
            var loginetext = findViewById(R.id.usernameetext) as EditText
//            Toast.makeText(this, loginetext.text, Toast.LENGTH_SHORT).show()
            var passwordetext = findViewById(R.id.passwordetext) as EditText
//            Toast.makeText(this, passwordetext.text, Toast.LENGTH_SHORT).show()



        }
    }


}