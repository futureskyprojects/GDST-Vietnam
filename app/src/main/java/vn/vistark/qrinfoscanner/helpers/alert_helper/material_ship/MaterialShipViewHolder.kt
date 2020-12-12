package vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.domain.mock_entities.MaterialShip
import vn.vistark.qrinfoscanner.domain.mock_entities.VesselData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupGet
import vn.vistark.qrinfoscanner.ui.statics_data.licenses_data.LicenseDataViewHolder
import vn.vistark.qrinfoscanner.ui.statics_data.vessel_data.VesselDataViewHolder
import java.util.*
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert

class MaterialShipViewHolder(v: View) {

    val aumvldIvClose: ImageView = v.findViewById(R.id.aumvldIvClose)
    val aumvldTvErrorMsg: TextView = v.findViewById(R.id.aumvldTvErrorMsg)
    val aumvldTvDialogName: TextView = v.findViewById(R.id.aumvldTvDialogName)

    val vesselDataViewHolder = VesselDataViewHolder(v)
    val materialShipDataViewHolder = LicenseDataViewHolder(v)

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


    fun loadExistData(materialShip: MaterialShip): Triple<VesselData?, CertificationAndLicense?, Int> {

        if (materialShip.Id > 0) {
            aumvldTvDialogName.text = "Sửa dữ liệu tàu nguyên liệu"
            val vesselData = MockupGet<VesselData>(materialShip.VesselDataId)
            val certLicense =
                MockupGet<CertificationAndLicense>(materialShip.CertificationAndLicenseId)

            return Triple(vesselData, certLicense, -1)

        }
        aumvldTvDialogName.text = "Tạo dữ liệu tàu nguyên liệu"
        return Triple(null, null, -1)
    }

    companion object {
        fun VesselDataViewHolder.select(
            vesselDatas: Array<VesselData>?,
            onResult: ((VesselData?) -> Unit),
            dialogName: String = "Chọn thông tin tàu"
        ) {
            val context = this.ilsLnRoot.context
            context.showSelectBottomSheetAlert(
                dialogName,
                vesselDatas ?: emptyArray(),
                R.layout.item_layout_vessel_data,
                { t, v ->
                    val vh = VesselDataViewHolder(v)
                    vh.bind(t)
                    vh.ilvdIvDeleteIcon.visibility = View.INVISIBLE
                    return@showSelectBottomSheetAlert vh.ilsLnRoot
                }
            ) { t ->
                if (t != null) {
                    this.bind(t)
                    this.ilvdIvDeleteIcon.visibility = View.INVISIBLE
                }
                onResult.invoke(t)
            }
        }

        fun LicenseDataViewHolder.select(
            licenseData: Array<CertificationAndLicense>?,
            onResult: ((CertificationAndLicense?) -> Unit),
            dialogName: String = "Chọn thông tin giấy phép"
        ) {
            val context = this.ilcalLnRoot.context
            context.showSelectBottomSheetAlert(
                dialogName,
                licenseData ?: emptyArray(),
                R.layout.item_layout_certification_and_license,
                { t, v ->
                    val vh = LicenseDataViewHolder(v)
                    vh.bind(t)
                    vh.ilcalIvDeleteIcon.visibility = View.INVISIBLE
                    return@showSelectBottomSheetAlert vh.ilcalLnRoot
                }
            ) { t ->
                if (t != null) {
                    this.bind(t)
                    this.ilcalIvDeleteIcon.visibility = View.INVISIBLE
                }
                onResult.invoke(t)
            }
        }
    }

}