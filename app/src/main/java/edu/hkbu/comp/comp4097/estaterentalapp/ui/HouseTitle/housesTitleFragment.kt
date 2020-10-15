package edu.hkbu.comp.comp4097.estaterentalapp.ui.HouseTitle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.hkbu.comp.comp4097.estaterentalapp.R
import edu.hkbu.comp.comp4097.estaterentalapp.data.AppDatabase
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import edu.hkbu.comp.comp4097.estaterentalapp.ui.HouseTitle.dummy.DummyContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class housesTitleFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_houses_title_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val estateName = arguments?.getString("estateName")
                val roomNum = arguments?.getString("roomNum")
                Log.d ("DB","HouseTitle value executing:${estateName} && ${roomNum}")

                if ( estateName != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getInstance(context).housesDao()
                        val houses = dao.findAllHousesByEstate(estateName)
                        CoroutineScope(Dispatchers.Main).launch {
                            adapter = housesTitleRecyclerViewAdapter(houses)
                        }
                    }
                    (activity as
                            AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }

                if(roomNum != null){
                    Log.d("DB", "Loaded by bedrooms number!")
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getInstance(context).housesDao()
                        var houses : List<Houses>
                        if (roomNum == "2") {
                            houses = dao.findAllHousesByRoomsSmallerThan(2)
                        }else{
                            houses = dao.findAllHousesByRoomsLargerThan(3)
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            adapter = housesTitleRecyclerViewAdapter(houses)
                        }
                    }
                    (activity as
                            AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            housesTitleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}