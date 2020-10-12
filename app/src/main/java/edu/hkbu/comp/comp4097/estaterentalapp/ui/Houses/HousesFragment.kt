package edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.hkbu.comp.comp4097.estaterentalapp.*
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class HousesFragment : Fragment() {

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
            swipeLayout.isRefreshing = true
            reloadData(recyclerView)
            swipeLayout.isRefreshing = false
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        reloadData(recyclerView)
        return swipeLayout
    }

    private fun reloadData(recyclerView: RecyclerView) {
        val HOUSES_URL = "https://morning-plains-00409.herokuapp.com/property/json"
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val json = Network.getTextFromNetwork(HOUSES_URL)
                val houses = Gson().fromJson<List<Houses>>(json,object :
                    TypeToken<List<Houses>>() {}.type)
                CoroutineScope(Dispatchers.Main).launch {
                    recyclerView.adapter = HousesListRecyclerViewAdapter(houses)
                }
            } catch (e: Exception) {
                Log.d("HousesFragment", "Error in loading data")
                val houses =
                        listOf(Houses("", "", "","Please check your network connection","Cannot fetch houses", "","","","","","",""))
                CoroutineScope(Dispatchers.Main).launch {
                    recyclerView.adapter = HousesListRecyclerViewAdapter(houses)
                }
            }
        }
    }

    companion object {
        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HousesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}