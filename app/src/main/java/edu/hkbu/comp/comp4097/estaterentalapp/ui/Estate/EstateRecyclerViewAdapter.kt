package edu.hkbu.comp.comp4097.estaterentalapp.ui.Estate

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import edu.hkbu.comp.comp4097.estaterentalapp.R
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses

import edu.hkbu.comp.comp4097.estaterentalapp.ui.Estate.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class EstateRecyclerViewAdapter(
    private val values: List<String>
) : RecyclerView.Adapter<EstateRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_estate_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.content)
        val sharedPreferences = view.getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)

        init {
            view.setOnClickListener {
//                Toast.makeText(view.context, "${contentView.text.toString()} is clicked",
//                    Toast.LENGTH_SHORT).show()
                if (sharedPreferences != null) {
                    sharedPreferences.edit()
                        .putString("fromFragment", "estateFragment")
                        .apply()
                }

                it.findNavController().navigate(
                    R.id.action_EstateFragment_to_housesTitleFragment,
                    bundleOf(Pair("estateName", contentView.text.toString()))
                )
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}