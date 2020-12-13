package vn.vistark.qrinfoscanner.ui.ship_collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.entities.GDSTShip

class ShipCollectionAdapter(val ships: ArrayList<GDSTShip>) :
    RecyclerView.Adapter<ShipCollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipCollectionViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_static_ship, parent, false)
        return ShipCollectionViewHolder(v)
    }

    override fun onBindViewHolder(holder: ShipCollectionViewHolder, position: Int) {
        val ship = ships[position]
        holder.bind(ship)
    }

    override fun getItemCount(): Int {
        return ships.size
    }
}