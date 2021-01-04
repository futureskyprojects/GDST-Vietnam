package vn.vistark.qrinfoscanner.ui.shipment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.Shipment
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.interfaces.IClickable
import vn.vistark.qrinfoscanner.core.interfaces.IDeletable
import vn.vistark.qrinfoscanner.domain.entities.GDSTShipment

class ShipmentAdapter(private val shipments: ArrayList<GDSTShipment>) :
    RecyclerView.Adapter<ShipmentViewHolder>(), IClickable<GDSTShipment>, IDeletable<GDSTShipment> {

    override var onClick: ((GDSTShipment) -> Unit)? = null
    override var onEdit: ((GDSTShipment) -> Unit)? = null

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

        holder.ilsIvEditIcon.clickAnimate {
            onEdit?.invoke(shipment)
        }

        holder.ilsLnRoot.clickAnimate {
            onClick?.invoke(shipment)
        }
    }

}