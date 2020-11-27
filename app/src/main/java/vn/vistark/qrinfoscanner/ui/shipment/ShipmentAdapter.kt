package vn.vistark.qrinfoscanner.ui.shipment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.Shipment

class ShipmentAdapter(val shipments: ArrayList<Shipment>) :
    RecyclerView.Adapter<ShipmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipmentViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_shipment, parent, false)
        return ShipmentViewHolder(v)
    }

    override fun getItemCount(): Int {
        return shipments.count()
    }

    override fun onBindViewHolder(holder: ShipmentViewHolder, position: Int) {
        val shipment = shipments[position]
        holder.bind(shipment)
    }

}