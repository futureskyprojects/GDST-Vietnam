package vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.entities.MaterialShip
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showDatePicker
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.valueDialog
import vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship.MaterialShipViewHolder.Companion.select
import vn.vistark.qrinfoscanner.ui.statics_data.licenses_data.LicenseDataViewHolder
import vn.vistark.qrinfoscanner.ui.statics_data.vessel_data.VesselDataViewHolder

class MaterialShipUpdateDialog {
    companion object {
        fun AppCompatActivity.showUpdateMaterialShipAlert(
            onCompleted: (MaterialShip?) -> Unit,
            materialShip: MaterialShip = MaterialShip()
        ) {
            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_update_material_ship, null)

            val vh = MaterialShipViewHolder(v)

            val res = vh.loadExistData(materialShip)

            val vesselDataViewHolder = VesselDataViewHolder(v)
            val licenseDataViewHolder = LicenseDataViewHolder(v)

            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Khai báo

            var vesselData: VesselData? = res.first
            var certificationAndLicense: CertificationAndLicense? = res.second

            vesselDataViewHolder.ilsLnRoot.clickAnimate {
                vesselDataViewHolder.select(MockupData(), {
                    vesselData = it ?: return@select
                })
            }

            licenseDataViewHolder.ilcalLnRoot.clickAnimate {
                licenseDataViewHolder.select(MockupData(), {
                    certificationAndLicense = it ?: return@select
                })
            }

            // Sự kiện
            vh.aumvldIvClose.clickAnimate {
                onCompleted.invoke(null)
                mAlertDialog.dismiss()
            }

            vh.aumvldTvFIP.valueDialog(
                RuntimeStorage.FIPs,
                "FIPs"
            ) {
                vh.updateError()
                materialShip.FIP = it?.name ?: return@valueDialog
            }

            vh.aumvldTvTripDate.showDatePicker({ materialShip.TripDate = it })

            vh.aumvldTvDatesOfLanding.showDatePicker({ materialShip.TripDate = it })

            vh.aumvldTvGearType.valueDialog(
                RuntimeStorage.GearTypes,
                "Gear type"
            ) {
                vh.updateError()
                materialShip.GearType = it?.name ?: return@valueDialog
            }

            vh.aumvldTvProductionMethod.valueDialog(
                RuntimeStorage.ProductMethods,
                "Product Methods"
            ) {
                vh.updateError()
                materialShip.ProductMethod = it?.name ?: return@valueDialog
            }

            vh.aumvldTvLandingLocation.valueDialog(
                RuntimeStorage.SeaPorts?.toBaseMaps(),
                "Landing Location"
            ) {
                vh.updateError()
                materialShip.ProductMethod = it?.name ?: return@valueDialog
            }

            vh.aumvldBtnCreateMaterialShip.clickAnimate {
                var isValidate = true

            }

        }
    }
}