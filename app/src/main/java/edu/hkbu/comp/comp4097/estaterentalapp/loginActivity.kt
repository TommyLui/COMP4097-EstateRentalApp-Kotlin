package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.layout_login_page.*


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
                .putString("loginState", "logout")
//                .putString("loginState", "login")
                .apply()

            Toast.makeText(this, (sharedPreferences.getString("account", "")), Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, (sharedPreferences.getString("password", "")), Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, (sharedPreferences.getString("loginState", "")), Toast.LENGTH_SHORT).show()

//            Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
//            if (currentFragment instanceof "NAME OF YOUR FRAGMENT CLASS") {
//                FragmentTransaction fragTransaction =   (getActivity()).getFragmentManager().beginTransaction();
//                fragTransaction.detach(currentFragment);
//                fragTransaction.attach(currentFragment);
//                fragTransaction.commit();}
//              }

        }
    }




}