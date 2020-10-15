package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Home.Network
import kotlinx.android.synthetic.main.fragment_logout.*
import kotlinx.android.synthetic.main.fragment_logout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [logoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class logoutFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var logoutView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        logoutView = inflater.inflate(R.layout.fragment_logout, container, false)

        logoutView.logoutbtn.setOnClickListener {
            val LOGOUT_URL = "https://morning-plains-00409.herokuapp.com/user/logout"
            CoroutineScope(Dispatchers.IO).launch {
                val json = Network.userLogout(LOGOUT_URL)
                Log.d("Network", "logout checkpoint 4")
//                Log.d("Network", json.toString())
                Log.d("Network", "logout checkpoint 5")
                var sharedPreferences = activity?.getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
                if (sharedPreferences != null) {
                    sharedPreferences.edit()
                        .putString("loginState", "logout")
                        .apply()
                }
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(activity, "Logout successful!", Toast.LENGTH_SHORT).show()
                    it.findNavController().navigate(
                        R.id.action_logoutFragment_to_userFragment)
                }
            }
        }

        return logoutView
    }
}