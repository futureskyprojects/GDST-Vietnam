package vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.entities.GDSTShip
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.ui.ship_collection.ShipCollectionViewHolder

class MaterialShipViewHolder(v: View) {

    val aumvldIvClose: ImageView = v.findViewById(R.id.aumvldIvClose)
    val aumvldTvErrorMsg: TextView = v.findViewById(R.id.aumvldTvErrorMsg)
    val aumvldTvDialogName: TextView = v.findViewById(R.id.aumvldTvDialogName)

    // -------------------------------- //

    val aumvldTvFIP: TextView =
        v.findViewById(R.id.aumvldTvFIP)

    val aumvldTvTripDate: TextView =
        v.findViewById(R.id.aumvldTvTripDate)

    val aumvldTvGearType: TextView =
        v.findViewById(R.id.aumvldTvGearType)

    val aumvldTvProductionMethod: TextView =
        v.findViewById(R.id.aumvldTvProductionMethod)

    val aumvldTvLandingLocation: TextView =
        v.findViewById(R.id.aumvldTvLandingLocation)

    val aumvldTvDatesOfLanding: TextView =
        v.findViewById(R.id.aumvldTvDatesOfLanding)

    val aumvldBtnCreateMaterialShip: Button =
        v.findViewById(R.id.aumvldBtnCreateMaterialShip)

    fun updateError(err: String = ""): Boolean {
        if (err.isNotEmpty()) {
            this.aumvldTvErrorMsg.text = err
            this.aumvldTvErrorMsg.visibility = View.VISIBLE
            return false
        } else {
            this.aumvldTvErrorMsg.text = ""
            this.aumvldTvErrorMsg.visibility = View.GONE
            return true
        }
    }


//    fun loadExistData(materialShip: GDSTMaterialShip): Triple<VesselData?, CertificationAndLicense?, Int> {
//
//        if (materialShip.Id > 0) {
//            aumvldTvDialogName.text = "Sửa dữ liệu tàu nguyên liệu"
//            val vesselData = MockupGet<VesselData>(materialShip.VesselDataId)
//            val certLicense =
//                MockupGet<CertificationAndLicense>(materialShip.CertificationAndLicenseId)
//
//            return Triple(vesselData, certLicense, -1)
//
//        }
//        aumvldTvDialogName.text = "Tạo dữ liệu tàu nguyên liệu"
//        return Triple(null, null, -1)
//    }

    companion object {
        fun ShipCollectionViewHolder.select(
            vesselDatas: Array<GDSTShip>?,
            onResult: ((GDSTShip?) -> Unit),
            dialogName: String = "Chọn thông tin tàu"
        ) {
            val context = this.ilssLnRoot.context
            context.showSelectBottomSheetAlert(
                dialogName,
                vesselDatas ?: emptyArray(),
                R.layout.item_layout_static_ship,
                { t, v ->
                    val vh = ShipCollectionViewHolder(v)
                    vh.bind(t)
                    return@showSelectBottomSheetAlert vh.ilssLnRoot
                }
            ) { t ->
                if (t != null) {
                    this.bind(t)
                }
                onResult.invoke(t)
            }
        }
    }

}