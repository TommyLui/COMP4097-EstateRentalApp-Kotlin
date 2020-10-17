package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import edu.hkbu.comp.comp4097.estaterentalapp.data.AccountInfo
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Home.Network
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cookie


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [userFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class userFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var userView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userView = inflater.inflate(R.layout.fragment_user, container, false)

        return userView
    }

    override fun onResume(){
        super.onResume()
        val sharedPreferences = this.requireActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        var loginState = sharedPreferences.getString("loginState", "")
        var userIcon = sharedPreferences.getString("userIcon", "")
        var username = sharedPreferences.getString("username", "")

        userView.loginPageBtn.setOnClickListener {
            if (loginState == "login") {
                it.findNavController().navigate(
                    R.id.action_userFragment_to_logoutFragment)
            }else{
                it.findNavController().navigate(
                    R.id.action_userFragment_to_loginFragment)
            }
        }

        userView.rentalsBtn.setOnClickListener {
            val getRental_URL = "https://morning-plains-00409.herokuapp.com/user/myRentals"
            CoroutineScope(Dispatchers.IO).launch {
                if (sharedPreferences.getString("loginState","").toString() == "login") {
                var cookie = sharedPreferences.getString("cookie","").toString()
                Log.d("Network", "Cookie load from preferences: ${cookie}")
                val json = Network.getRental(getRental_URL, cookie)
                Log.d("Network", "json got: ${json.toString()}")
                    if (json.toString().length > 3) {
                        sharedPreferences.edit()
                            .putString("rentals", json.toString())
                            .apply()
                        Log.d("Network", "SharedPreferences set")

                        if (sharedPreferences != null) {
                            sharedPreferences.edit()
                                .putString("fromFragment", "userFragment")
                                .apply()
                        }

                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(activity, "Get record successful!", Toast.LENGTH_SHORT).show()
                        it.findNavController().navigate(
                            R.id.action_userFragment_to_housesTitleFragment,
                            bundleOf(Pair("rentalRecords", "displayRecord"))
                        )
                    }
                    }else if (json.toString().length < 3){
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(activity, "No recorded record!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(activity, "Please login first!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        userView.debugbtn.setOnClickListener {
            Toast.makeText(getActivity(), loginState.toString(), Toast.LENGTH_SHORT).show()
        }

        Log.d("Network", "State: ${loginState}")

        if (loginState == "logout"){
            userView.userIcon.setImageResource(R.mipmap.man_foreground);
            userView.userName.setText("User Name")
        }
        else if (loginState == "login"){
            Picasso.get().load(userIcon).into(userView.userIcon)
            userView.userName.setText(username)
        }
    }


}