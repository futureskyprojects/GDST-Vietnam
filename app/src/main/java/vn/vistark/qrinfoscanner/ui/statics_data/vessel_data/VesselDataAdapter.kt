package vn.vistark.qrinfoscanner.ui.statics_data.vessel_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.Shipment
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.interfaces.IClickable
import vn.vistark.qrinfoscanner.core.interfaces.IDeletable

class VesselDataAdapter(private val vesselDatas: ArrayList<VesselData>) :
    RecyclerView.Adapter<VesselDataViewHolder>(), IClickable<VesselData>,
    IDeletable<VesselData> {

    override var onClick: ((VesselData) -> Unit)? = null
    override var onDelete: ((VesselData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VesselDataViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_vessel_data, parent, false)
        return VesselDataViewHolder(v)
    }

    override fun getItemCount(): Int {
        return vesselDatas.count()
    }

    override fun onBindViewHolder(holder: VesselDataViewHolder, position: Int) {
        val vesselData = vesselDatas[position]
        holder.bind(vesselData)

        holder.ilsLnRoot.clickAnimate {
            onClick?.invoke(vesselData)
        }
        holder.ilvdIvDeleteIcon.clickAnimate {
            onDelete?.invoke(vesselData)
        }
    }

}