package vn.vistark.qrinfoscanner.ui.material_ship

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.MaterialShip
import vn.vistark.qrinfoscanner.core.entities.RawMaterialBatch
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate

class MaterialShipHolder(v: View) : RecyclerView.ViewHolder(v) {
    val ilmsLnRoot: LinearLayout = v.findViewById(R.id.ilmsLnRoot)
    val ilmsdIvDeleteIcon: ImageView = v.findViewById(R.id.ilmsdIvDeleteIcon)
    val ilmsIvVesselFlag: ImageView = v.findViewById(R.id.ilmsIvVesselFlag)
    val ilmsTvVesselOwnerName: TextView = v.findViewById(R.id.ilmsTvVesselOwnerName)
    val ilmsTvVesselRegistration: TextView = v.findViewById(R.id.ilmsTvVesselRegistration)
    val ilmsTvVesselCoordinates: TextView = v.findViewById(R.id.ilmsTvVesselCoordinates)
    val ilmsTvHarvestCertification: TextView = v.findViewById(R.id.ilmsTvHarvestCertification)
    val ilmsTvFIP: TextView = v.findViewById(R.id.ilmsTvFIP)
    val ilmsTvTripCount: TextView = v.findViewById(R.id.ilmsTvTripCount)
    val ilmsTvGrearType: TextView = v.findViewById(R.id.ilmsTvGrearType)
    val ilmsTvProductMethod: TextView = v.findViewById(R.id.ilmsTvProductMethod)
    val ilmsTvVesselVMS: TextView = v.findViewById(R.id.ilmsTvVesselVMS)

    fun bind(materialShip: MaterialShip) {
    }

}