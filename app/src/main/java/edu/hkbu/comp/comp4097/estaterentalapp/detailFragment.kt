package edu.hkbu.comp.comp4097.estaterentalapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import edu.hkbu.comp.comp4097.estaterentalapp.data.AppDatabase
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Estate.EstateFragment
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Home.Network
import edu.hkbu.comp.comp4097.estaterentalapp.ui.HouseTitle.housesTitleRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [detailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class detailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(detailFragment.ARG_COLUMN_COUNT)
        }
    }

    lateinit var detailView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        detailView = inflater.inflate(R.layout.fragment_detail, container, false)

        return detailView
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        val sharedPreferences = this.requireActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val housesName = arguments?.getString("housesName")







        if(housesName != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val dao = context?.let { AppDatabase.getInstance(it).housesDao() }
                val houses: Houses? = dao?.findAllHousesByHousesTitle(housesName)
                CoroutineScope(Dispatchers.Main).launch {
                    if (houses != null) {
                        Picasso.get().load(houses.image_URL).into(detailView.imageView)
                        detailView.textView4.setText(houses.property_title)
                        detailView.textView5.setText("Estate: ${houses.estate}, Bedroom: ${houses.bedrooms}")
                        detailView.textView6.setText("Rent: $${houses.rent}, Tenants: ${houses.expected_tenants}, Area: ${houses.gross_area}")



//                        detailView.button.setOnClickListener {
//                            if(detailView.button.text == "Move-in"){
//                            Log.d("Button", "Move-in: clicked")
//                                val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
//                                if (builder != null) {
//                                    builder.setMessage("Are you sure to move in this rental?")
//                                        .setCancelable(false)
//                                        .setPositiveButton("Yes") { dialog, id ->
//                                            Log.d("Button", "Move-in: Yes clicked")
//                                            val addRental_URL =
//                                                "https://morning-plains-00409.herokuapp.com/user/rent/"
//                                            CoroutineScope(Dispatchers.IO).launch {
//                                                if (sharedPreferences.getString("loginState", "")
//                                                        .toString() == "login"
//                                                ) {
//                                                    var cookie =
//                                                        sharedPreferences.getString("cookie", "")
//                                                            .toString()
//                                                    Log.d(
//                                                        "Network",
//                                                        "Cookie load from preferences: ${cookie}"
//                                                    )
//                                                    val json = Network.addRental(
//                                                        addRental_URL,
//                                                        cookie,
//                                                        houses.id.toInt()
//                                                    )
//                                                    Log.d("Network", "json got: ${json.toString()}")
//                                                    if (json.toString() == "200") {
//                                                        Log.d("Network", "json to list")
//                                                        CoroutineScope(Dispatchers.Main).launch {
//                                                            Toast.makeText(
//                                                                activity,
//                                                                "Add record successful!",
//                                                                Toast.LENGTH_SHORT
//                                                            ).show()
//                                                        }
//                                                    } else if (json.toString() == "409") {
//                                                        CoroutineScope(Dispatchers.Main).launch {
//                                                            Toast.makeText(
//                                                                activity,
//                                                                "Record already added before!",
//                                                                Toast.LENGTH_SHORT
//                                                            ).show()
//                                                        }
//                                                    }
//                                                } else {
//                                                    CoroutineScope(Dispatchers.Main).launch {
//                                                        Toast.makeText(
//                                                            activity,
//                                                            "Not yet login!",
//                                                            Toast.LENGTH_SHORT
//                                                        ).show()
//                                                    }
//                                                }
//                                            }
//                                        }
//                                        .setNegativeButton("No") { dialog, id ->
//                                            Log.d("Button", "Move-in: No clicked")
//                                        }
//                                }
//                                val alert = builder?.create()
//                                if (alert != null) {
//                                    alert.show()
//                                }
//                            }else {
//                                Log.d("Button", "Move-out: clicked")
//                            }
//                        }

                        detailView.button2.setOnClickListener {
                            var housesLocation = houses.estate + ", Hong Kong"
                            it.findNavController().navigate(
                                R.id.action_detailFragment_to_mapsFragment,
                                bundleOf(Pair("housesLocation", housesLocation.toString()))
                            )
                        }
                    }
                }
            }
            (activity as
                    AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            detailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}