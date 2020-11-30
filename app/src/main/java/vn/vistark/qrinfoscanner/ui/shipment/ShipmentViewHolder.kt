package vn.vistark.qrinfoscanner.ui.shipment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.Config
import vn.vistark.qrinfoscanner.core.entities.Shipment
import vn.vistark.qrinfoscanner.ui.material_batch.MaterialBatchActivity
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate

class ShipmentViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val ilsLnRoot: LinearLayout = v.findViewById(R.id.ilsLnRoot)
    private val ilsTvShipmentId: TextView = v.findViewById(R.id.ilsTvShipmentId)
    private val ilsTvShipmentName: TextView = v.findViewById(R.id.ilsTvShipmentName)
    val ilsIvDeleteIcon: ImageView = v.findViewById(R.id.ilsIvDeleteIcon)
    private val ilsTvMaterialBathCount: TextView = v.findViewById(R.id.ilsTvMaterialBathCount)
    private val ilsTvTotalWeightCount: TextView = v.findViewById(R.id.ilsTvTotalWeightCount)
    private val ilsTotalVesselCount: TextView = v.findViewById(R.id.ilsTotalVesselCount)
    private val ilsTvTotalSpiceCount: TextView = v.findViewById(R.id.ilsTvTotalSpiceCount)

    fun bind(shipment: Shipment) {
        setShipmentId(shipment.Id)
        setShipmentName(shipment.Name)
        setMaterialBathCount(0)
        setTotalWeightCount(0F)
        setTotalVesselCount(0)
        setTotalSpiceCount(0)
    }

    @SuppressLint("SetTextI18n")
    private fun setShipmentId(id: Int) {
        ilsTvShipmentId.text = "#" + "$id".padStart(Config.padSize, '0')
    }

    @SuppressLint("SetTextI18n")
    private fun setShipmentName(name: String) {
        ilsTvShipmentName.text = "Lô hàng $name"
    }

    @SuppressLint("SetTextI18n")
    private fun setMaterialBathCount(count: Int) {
        ilsTvMaterialBathCount.text = "Lô nguyên liệu: $count"
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalWeightCount(float: Float) {
        ilsTvTotalWeightCount.text = "Kh.Lượng: $float (Tấn)"
    }

    private fun setTotalVesselCount(count: Int) {
        ilsTotalVesselCount.text = "Tàu liên kết: $count"
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalSpiceCount(count: Int) {
        ilsTvTotalSpiceCount.text = "Tổng loài: $count"
    }
}