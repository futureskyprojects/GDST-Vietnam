package vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.StringExtension.Companion.ToYMDDate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTMaterialShipCreateDTO
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.*
import vn.vistark.qrinfoscanner.domain.entities.GDSTFipCode.Companion.toBaseMap1
import vn.vistark.qrinfoscanner.domain.entities.GDSTGearType.Companion.toBaseMap2
import vn.vistark.qrinfoscanner.domain.entities.GDSTLocation.Companion.toBaseMap4
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showDatePicker
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.valueDialog
import vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship.MaterialShipViewHolder.Companion.select
import vn.vistark.qrinfoscanner.ui.ship_collection.ShipCollectionViewHolder
import java.util.*

class MaterialShipUpdateDialog {
    companion object {
        // Khai báo

        var ship: GDSTShip? = null

        var fip: BaseMap? = null
        var tripDate: Date? = null
        var gearType: BaseMap? = null
        var productMethod: BaseMap? = null
        var loactioin: BaseMap? = null
        var ladingDate: Date? = null

        var crrShip: GDSTMaterialShipCreateDTO = GDSTMaterialShipCreateDTO()

        fun AppCompatActivity.showUpdateMaterialShipAlert(
            onCompleted: (GDSTMaterialShipCreateDTO?) -> Unit,
            materialShip: GDSTMaterialShipCreateDTO = GDSTMaterialShipCreateDTO()
        ) {
            crrShip = materialShip

            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_update_material_ship, null)

            v.setOnClickListener { HideKeyboard(v) }

            val vh = MaterialShipViewHolder(v)

            val shipVH = ShipCollectionViewHolder(v)

            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Khai báo

            ship = null

            fip = null
            tripDate = null
            gearType = null
            productMethod = null
            loactioin = null
            ladingDate = null

            loadExistData(vh, shipVH)

            shipVH.ilssLnRoot.clickAnimate {
                v.HideKeyboard()
                runOnUiThread {
                    shipVH.select(GDSTStorage.GDSTShips?.toTypedArray() ?: emptyArray(), {
                        ship = it ?: return@select
                        materialShip.shipId = it.id
                    })
                }

            }

            // Sự kiện
            vh.aumvldIvClose.clickAnimate {
                onCompleted.invoke(null)
                mAlertDialog.dismiss()
            }

            vh.aumvldTvFIP.valueDialog(
                GDSTStorage.GDSTFipCodes.toBaseMap1(),
                getString(R.string.fips)
            ) {
                vh.updateError()
                fip = it
                materialShip.fipcodeId = it?.id ?: return@valueDialog
            }

            vh.aumvldTvTripDate.showDatePicker({
                materialShip.dateGo = it.Format("yyyy-MM-dd")
                tripDate = it
            })

            vh.aumvldTvDatesOfLanding.showDatePicker({
                materialShip.dateUpFishing = it.Format("yyyy-MM-dd")
                ladingDate = it
            })

            vh.aumvldTvGearType.valueDialog(
                GDSTStorage.GDSTGearTypes.toBaseMap2(),
                getString(R.string.gear_type)
            ) {
                vh.updateError()
                materialShip.gearId = it?.id ?: return@valueDialog
                gearType = it
            }

            vh.aumvldTvProductionMethod.valueDialog(
                GDSTStorage.GDSTGearTypes.toBaseMap2(),
                getString(R.string.ptkt_clear)
            ) {
                vh.updateError()
                materialShip.prodctMethod = it?.id ?: return@valueDialog
                productMethod = it
            }

            vh.aumvldTvLandingLocation.valueDialog(
                GDSTStorage.GDSTLocations.toBaseMap4(),
                getString(R.string.landing_locations)
            ) {
                vh.updateError()
                materialShip.upFishing = it?.id ?: return@valueDialog
                loactioin = it
            }

            vh.aumvldBtnCreateMaterialShip.clickAnimate {
                v.HideKeyboard()
                var isValidate = true

                if (ship == null)
                    isValidate = vh.updateError(getString(R.string.vlcttt))

                if (fip == null)
                    isValidate = vh.updateError(getString(R.string.vlcfip))

                if (tripDate == null)
                    isValidate = vh.updateError(getString(R.string.vlcnd))

                if (gearType == null)
                    isValidate = vh.updateError(getString(R.string.vlcnc))

                if (productMethod == null)
                    isValidate = vh.updateError(getString(R.string.vlcptkt))

                if (loactioin == null)
                    isValidate = vh.updateError(getString(R.string.vlcvtlc))

                if (ladingDate == null)
                    isValidate = vh.updateError(getString(R.string.vlcnlc))

                if (!isValidate)
                    return@clickAnimate

                onCompleted.invoke(materialShip)
                mAlertDialog.dismiss()
            }

        }

        private fun loadExistData(vh: MaterialShipViewHolder, shipVH: ShipCollectionViewHolder) {
            ship = GDSTStorage.GDSTShips?.firstOrNull { crrShip.shipId == it.id }
            fip = GDSTStorage.GDSTFipCodes?.firstOrNull { it.id == crrShip.fipcodeId }?.toBaseMap()
            tripDate = crrShip.dateGo.ToYMDDate()
            gearType =
                GDSTStorage.GDSTGearTypes?.firstOrNull { it.id == crrShip.gearId }?.toBaseMap()
            productMethod = GDSTStorage.GDSTGearTypes?.firstOrNull { it.id == crrShip.prodctMethod }
                ?.toBaseMap()
            loactioin =
                GDSTStorage.GDSTLocations?.firstOrNull { it.id == crrShip.upFishing }?.toBaseMap()
            ladingDate = crrShip.dateUpFishing.ToYMDDate()

            // Cập nhật lên view
            if (ship != null)
                shipVH.bind(ship!!)

            vh.aumvldTvFIP.text = fip?.name
            vh.aumvldTvTripDate.text = tripDate?.Format()
            vh.aumvldTvDatesOfLanding.text = ladingDate?.Format()
            vh.aumvldTvGearType.text = gearType?.name
            vh.aumvldTvProductionMethod.text = productMethod?.name
            vh.aumvldTvLandingLocation.text = loactioin?.name
        }
    }
}