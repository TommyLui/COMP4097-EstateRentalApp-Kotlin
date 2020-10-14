package edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.hkbu.comp.comp4097.estaterentalapp.*
import edu.hkbu.comp.comp4097.estaterentalapp.data.AppDatabase
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_houses_list, null, false) as
                RecyclerView
        val swipeLayout = SwipeRefreshLayout(requireContext())
        swipeLayout.addView(recyclerView)
        swipeLayout.setOnRefreshListener {
            if (context?.let { isOnline(it) }!!){
                swipeLayout.isRefreshing = true
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = AppDatabase.getInstance(requireContext(), true).housesDao()
                    Log.d("DB",  "DB checkpoint 7")
                    val houses = dao.findAllHouses()
//                Log.d("DB",  houses.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        recyclerView.adapter = HomeListRecyclerViewAdapter(houses)
                        Log.d("DB",  "DB checkpoint 8")
                    }
                }
                (activity as
                        AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                swipeLayout.isRefreshing = false
            }else{
                Toast.makeText(getActivity(), "Network fail!", Toast.LENGTH_SHORT).show()
                swipeLayout.isRefreshing = false
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getInstance(requireContext()).housesDao()
            Log.d("DB",  "DB checkpoint 7")
            val houses = dao.findAllHouses()
//            Log.d("DB",  "DB checkpoint 8")
//            Log.d("DB",  houses.toString())
            CoroutineScope(Dispatchers.Main).launch {
                recyclerView.adapter = HomeListRecyclerViewAdapter(houses)
                Log.d("DB",  "DB checkpoint 8")
            }
        }
        (activity as
                AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return swipeLayout
    }

//    private fun reloadData(recyclerView: RecyclerView) {
//        val HOUSES_URL = "https://morning-plains-00409.herokuapp.com/property/json"
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val json = Network.getTextFromNetwork(HOUSES_URL)
//                Log.d("Network",  "house checkpoint 4")
////                Log.d("Network",  json.toString())
//                val houses = Gson().fromJson<List<Houses>>(json,object :
//                    TypeToken<List<Houses>>() {}.type)
//                Log.d("Network",  "house checkpoint 5")
////                Log.d("Network",  houses.toString())
//                CoroutineScope(Dispatchers.Main).launch {
//                    recyclerView.adapter = HomeListRecyclerViewAdapter(houses)
//                }
//            } catch (e: Exception) {
//                Log.d("Network", "Error in loading data")
//                val houses =
//                        listOf(Houses("", "", "","Please check your network connection","Cannot fetch houses", "","","","","","",""))
//                CoroutineScope(Dispatchers.Main).launch {
//                    recyclerView.adapter = HomeListRecyclerViewAdapter(houses)
//                }
//            }
//        }
//    }

    companion object {
        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HomeFragment().apply {
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