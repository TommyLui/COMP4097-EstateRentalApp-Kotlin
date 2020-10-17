package edu.hkbu.comp.comp4097.estaterentalapp

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.hkbu.comp.comp4097.estaterentalapp.data.AccountInfo
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Home.Network
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [loginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class loginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(detailFragment.ARG_COLUMN_COUNT)
        }

    }

    lateinit var loginView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginView = inflater.inflate(R.layout.fragment_login, container, false)

            loginView.loginbtn.setOnClickListener {
                val progressDialog = ProgressDialog(context)
                progressDialog.setMessage("loading, please wait!")
                progressDialog.show()

                if (context?.let { it1 -> isOnline(it1) }!!){
                var loginetext = loginView.usernameetext.text.toString()
                var passwordetext = loginView.passwordetext.text.toString()

                var sharedPreferences =
                    this.activity?.getSharedPreferences("loginInfo", Context.MODE_PRIVATE)

                if (sharedPreferences != null) {
                    sharedPreferences.edit()
                        .putString("account", loginetext.toString())
                        .putString("password", passwordetext.toString())
                        .apply()
                }

                val LOGIN_URL = "https://morning-plains-00409.herokuapp.com/user/login"
                CoroutineScope(Dispatchers.IO).launch {
                    val json = sharedPreferences?.let { it1 ->
                        Network.userLogin(
                            LOGIN_URL, loginetext, passwordetext,
                            it1
                        )
                    }
                    if (json.toString() != "error") {
//                    Log.d("Network", "login checkpoint 4")
//                Log.d("Network", json.toString())
                        val accountInfo = Gson().fromJson<AccountInfo>(json, object :
                            TypeToken<AccountInfo>() {}.type)
//                    Log.d("Network", "login checkpoint 5")
//                Log.d("Network", accountInfo.toString())
                        if (sharedPreferences != null) {
                            sharedPreferences.edit()
                                .putString("loginState", "login")
                                .putString("username", accountInfo.username.toString())
                                .putString("userIcon", accountInfo.avatar.toString())
                                .apply()
                        }
                        progressDialog.dismiss()
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show()
                            it.findNavController().navigate(
                                R.id.action_loginFragment_to_userFragment
                            )
                        }
                    } else {
                        progressDialog.dismiss()
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(activity, "Wrong account or password!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                    progressDialog.dismiss()
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(activity, "Network fail!", Toast.LENGTH_SHORT).show()
                    }
        }
        }

        return loginView
    }

    companion object {
        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            loginFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}