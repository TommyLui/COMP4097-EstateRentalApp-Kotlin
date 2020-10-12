package edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.hkbu.comp.comp4097.estaterentalapp.MainActivity
import edu.hkbu.comp.comp4097.estaterentalapp.R
import edu.hkbu.comp.comp4097.estaterentalapp.funClass
import edu.hkbu.comp.comp4097.estaterentalapp.loginActivity
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Houses.dummy.DummyContent

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
            funClass.reloadData(recyclerView)
            swipeLayout.isRefreshing = false
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        funClass.reloadData(recyclerView)
        return swipeLayout
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