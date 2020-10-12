package edu.hkbu.comp.comp4097.estaterentalapp

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses.HousesListRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object funClass {
    fun reloadData(recyclerView: RecyclerView) {
        val HOUSES_URL = "https://morning-plains-00409.herokuapp.com/"
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val json = Network.getTextFromNetwork(HOUSES_URL)
                val houses = Gson().fromJson<List<Houses>>(json, object :
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


}