package vn.vistark.qrinfoscanner.ui.shipment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.Shipment
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.interfaces.IClickable
import vn.vistark.qrinfoscanner.core.interfaces.IDeletable
import vn.vistark.qrinfoscanner.ui.material_batch.MaterialBatchActivity

class ShipmentAdapter(private val shipments: ArrayList<Shipment>) :
    RecyclerView.Adapter<ShipmentViewHolder>(), IClickable<Shipment>, IDeletable<Shipment> {

    override var onClick: ((Shipment) -> Unit)? = null
    override var onDelete: ((Shipment) -> Unit)? = null

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

        holder.ilsIvDeleteIcon.clickAnimate {
            onDelete?.invoke(shipment)
        }

        holder.ilsLnRoot.clickAnimate {
            onClick?.invoke(shipment)
        }
    }

}