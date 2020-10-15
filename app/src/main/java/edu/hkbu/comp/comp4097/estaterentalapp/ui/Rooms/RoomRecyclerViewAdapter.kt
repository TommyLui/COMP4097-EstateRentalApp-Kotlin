package edu.hkbu.comp.comp4097.estaterentalapp.ui.Rooms

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import edu.hkbu.comp.comp4097.estaterentalapp.R
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses

import edu.hkbu.comp.comp4097.estaterentalapp.ui.Rooms.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RoomRecyclerViewAdapter(
    private val values: List<String>
) : RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_rooms_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.content)

        init {
            view.setOnClickListener {
//                Toast.makeText(view.context, "${contentView.text.toString()} is clicked",
//                    Toast.LENGTH_SHORT).show()

                var roomNum = 0
                if (contentView.text.toString() == "Bedrooms <= 2"){
                    roomNum = 2
                }else{
                    roomNum = 3
                }

                it.findNavController().navigate(
                    R.id.action_RoomsFragment_to_housesTitleFragment,
                    bundleOf(Pair("roomNum", roomNum.toString()))
                )

//                Toast.makeText(view.context, "${roomNum.toString()} is passed",
//                    Toast.LENGTH_SHORT).show()
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}