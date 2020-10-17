package edu.hkbu.comp.comp4097.estaterentalapp

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import edu.hkbu.comp.comp4097.estaterentalapp.data.AppDatabase
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import edu.hkbu.comp.comp4097.estaterentalapp.data.myRentalHouses
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Home.HomeListRecyclerViewAdapter
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
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("loading, please wait!")
            progressDialog.show()

            val getRental_URL = "https://morning-plains-00409.herokuapp.com/user/myRentals"
            CoroutineScope(Dispatchers.IO).launch {
                val dao = AppDatabase.getInstance(requireContext()).myRentalDao()

                if (sharedPreferences.getString("loginState","").toString() == "login") {
                    if (context?.let { it1 -> isOnline(it1) } == true){
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

                        Log.d("DB", "old record got: ${ dao.findAllRecord().toString()}")

                        dao.findAllRecord().forEach{dao.delete(it)}
                        Log.d("DB", "rental record deleted!")

                        var myRentalHouses : myRentalHouses = myRentalHouses(json.toString())

                        dao.insert(myRentalHouses)
                        Log.d("DB", "new record insert: ${ dao.findAllRecord().toString()}")

                        progressDialog.dismiss()
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(activity, "Online record read!", Toast.LENGTH_SHORT).show()

                        it.findNavController().navigate(
                            R.id.action_userFragment_to_housesTitleFragment,
                            bundleOf(Pair("rentalRecords", "displayRecord"))
                        )
                    }
                    }else if (json.toString().length < 3){
                        Log.d("DB", "old record got: ${ dao.findAllRecord().toString()}")
                        dao.findAllRecord().forEach{dao.delete(it)}
                        Log.d("DB", "rental record deleted!")
                        progressDialog.dismiss()
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(activity, "Online record empty!", Toast.LENGTH_SHORT).show()
                        }
                    }}else{
                        CoroutineScope(Dispatchers.IO).launch {
                            var myRentalHouses = listOf<myRentalHouses>()
                            myRentalHouses = dao.findAllRecord()
                            Log.d("DB", "old record length: ${myRentalHouses.toString().length}")
                            if (myRentalHouses.toString().length > 3){
                                var json = myRentalHouses[0].json.toString()
                                Log.d("DB", "old record got: ${json}")
                                sharedPreferences.edit()
                                    .putString("rentals", json)
                                    .apply()
                                Log.d("DB", "SharedPreferences set")
                                if (sharedPreferences != null) {
                                    sharedPreferences.edit()
                                        .putString("fromFragment", "userFragment")
                                        .apply()
                                }
                                CoroutineScope(Dispatchers.Main).launch {
                                    progressDialog.dismiss()
                                    Toast.makeText(activity, "local record read!", Toast.LENGTH_SHORT).show()
                                    it.findNavController().navigate(
                                        R.id.action_userFragment_to_housesTitleFragment,
                                        bundleOf(Pair("rentalRecords", "displayRecord")))
                                }
                            }else{
                                CoroutineScope(Dispatchers.Main).launch {
                                    progressDialog.dismiss()
                                    Toast.makeText(activity, "local record empty", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        var myRentalHouses = listOf<myRentalHouses>()
                        myRentalHouses = dao.findAllRecord()
                        Log.d("DB", "old record length: ${myRentalHouses.toString().length}")
                        if (myRentalHouses.toString().length > 3){
                        var json = myRentalHouses[0].json.toString()
                        Log.d("DB", "old record got: ${json}")
                        sharedPreferences.edit()
                            .putString("rentals", json)
                            .apply()
                        Log.d("DB", "SharedPreferences set")
                        if (sharedPreferences != null) {
                            sharedPreferences.edit()
                                .putString("fromFragment", "userFragment")
                                .apply()
                        }
                            CoroutineScope(Dispatchers.Main).launch {
                                progressDialog.dismiss()
                                Toast.makeText(activity, "local record read!", Toast.LENGTH_SHORT).show()
                                it.findNavController().navigate(
                                    R.id.action_userFragment_to_housesTitleFragment,
                                    bundleOf(Pair("rentalRecords", "displayRecord")))
                            }
                        }else{
                            CoroutineScope(Dispatchers.Main).launch {
                                progressDialog.dismiss()
                                Toast.makeText(activity, "local record empty", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
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