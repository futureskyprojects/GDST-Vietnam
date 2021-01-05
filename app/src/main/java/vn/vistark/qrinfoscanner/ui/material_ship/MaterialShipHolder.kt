package vn.vistark.qrinfoscanner.ui.material_ship

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTMaterialShip
import java.util.*

class MaterialShipHolder(v: View) : RecyclerView.ViewHolder(v) {
    val ilmsLnRoot: LinearLayout = v.findViewById(R.id.ilmsLnRoot)
    val ilmsdIvEditIcon: ImageView = v.findViewById(R.id.ilmsdIvEditIcon)

    private val ilmsTvMaterialShipId: TextView = v.findViewById(R.id.ilmsTvMaterialShipId)
    private val ilmsTvMaterialShipname: TextView = v.findViewById(R.id.ilmsTvMaterialShipname)

    private val ilmsTvFIP: TextView = v.findViewById(R.id.ilmsTvFIP)
    private val ilmsTvTripDate: TextView = v.findViewById(R.id.ilmsTvTripDate)
    private val ilmsTvGrearType: TextView = v.findViewById(R.id.ilmsTvGrearType)
    private val ilmsTvProductMethod: TextView = v.findViewById(R.id.ilmsTvProductMethod)
    private val ilmsTvLanding: TextView = v.findViewById(R.id.ilmsTvLanding)

    @SuppressLint("SetTextI18n")
    fun bind(materialShip: GDSTMaterialShip) {
        ilmsTvMaterialShipname.text =
            ilmsTvMaterialShipname.context.getString(R.string.tnl) + " #${materialShip.id}"
        ilmsTvMaterialShipId.text = "#${materialShip.id}"

        val fip =
            GDSTStorage.GDSTFipCodes?.firstOrNull { x -> x.id == materialShip.fipcodeId }?.title
                ?: ""
        ilmsTvFIP.text = "FIP: $fip"

        ilmsTvTripDate.text = ilmsTvTripDate.context.getString(R.string.nd) + materialShip.dateGo

        val gearType =
            GDSTStorage.GDSTGearTypes?.firstOrNull { x -> x.id == materialShip.gearId }?.title ?: ""

        ilmsTvGrearType.text = ilmsTvGrearType.context.getString(R.string.nc) + gearType

        val method =
            GDSTStorage.GDSTProductForms?.firstOrNull { x -> x.id == materialShip.prodctMethod }?.title
                ?: ""

        ilmsTvProductMethod.text = ilmsTvProductMethod.context.getString(R.string.ptkt) + method

        val location =
            GDSTStorage.GDSTLocations?.firstOrNull { x -> x.id == materialShip.upFishing }?.title
                ?: ""

        ilmsTvLanding.text =
            ilmsTvLanding.context.getString(R.string.lc) + location + " (" + materialShip.dateUpFishing + ")"
    }
}