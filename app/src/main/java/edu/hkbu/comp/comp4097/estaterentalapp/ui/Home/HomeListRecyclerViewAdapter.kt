package edu.hkbu.comp.comp4097.estaterentalapp.ui.Home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import edu.hkbu.comp.comp4097.estaterentalapp.data.Houses
import edu.hkbu.comp.comp4097.estaterentalapp.R

import edu.hkbu.comp.comp4097.estaterentalapp.ui.Home.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class HomeListRecyclerViewAdapter(
    private val values: List<Houses>
) : RecyclerView.Adapter<HomeListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_houses_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.titleTextView.text = item.property_title
        holder.priceTextView.text = item.rent
        holder.locationTextView.text = item.estate
        if (item.image_URL != "")
            Picasso.get().load(item.image_URL).into(holder.housesImageView)
        else
            holder.housesImageView.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_baseline_cloud_download_24))

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val housesImageView: ImageView = view.findViewById(R.id.housesImageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val locationTextView: TextView = view.findViewById(R.id.locationTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)

        init {
            view.setOnClickListener {

                it.findNavController().navigate(
                    R.id.action_HomeFragment_to_detailFragment,
                    bundleOf(Pair("housesName", titleTextView.text.toString()))
                )
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + titleTextView.text + "'"
        }
    }
}