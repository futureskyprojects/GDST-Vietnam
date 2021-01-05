package vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTMaterialShipCreateDTO
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.*
import vn.vistark.qrinfoscanner.domain.entities.GDSTFipCode.Companion.toBaseMap1
import vn.vistark.qrinfoscanner.domain.entities.GDSTGearType.Companion.toBaseMap2
import vn.vistark.qrinfoscanner.domain.entities.GDSTLocation.Companion.toBaseMap4
import vn.vistark.qrinfoscanner.domain.entities.GDSTProductForm.Companion.toBaseMap3
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
        var shipArr: Array<GDSTShip> = emptyArray()
        fun AppCompatActivity.showUpdateMaterialShipAlert(
            onCompleted: (GDSTMaterialShipCreateDTO?) -> Unit,
            materialShip: GDSTMaterialShipCreateDTO = GDSTMaterialShipCreateDTO()
        ) {
            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_update_material_ship, null)

            v.setOnClickListener { HideKeyboard(v) }

            val vh = MaterialShipViewHolder(v)

//            val res = vh.loadExistData(materialShip)

            val shipVH = ShipCollectionViewHolder(v)

            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Khai báo

            var ship: GDSTShip? = null

            var fip: BaseMap? = null
            var tripDate: Date? = null
            var gearType: BaseMap? = null
            var productForm: BaseMap? = null
            var loactioin: BaseMap? = null
            var ladingDate: Date? = null

            shipVH.ilssLnRoot.clickAnimate {
                v.HideKeyboard()

                if (shipArr.isEmpty()) {
                    val loading = this.showLoadingAlert()
                    loading.show()
                    GlobalScope.launch {
                        try {
                            val response = ApiService.mAPIServices.getGDSTShip().await()
                            runOnUiThread { loading.cancel() }
                            if (response == null)
                                throw Exception(getString(R.string.kpgdkqtv))

                            runOnUiThread {
                                shipArr = response.toTypedArray()
                                shipVH.select(shipArr, {
                                    ship = it ?: return@select
                                    materialShip.shipId = it.id
                                })
                            }
                        } catch (e: Exception) {
                            runOnUiThread { loading.cancel() }
                            runOnUiThread {
                                showAlertConfirm(getString(R.string.kldtdlcs))
                            }
                            e.printStackTrace()
                        }
                    }
                } else {
                    runOnUiThread {
                        shipVH.select(shipArr, {
                            ship = it ?: return@select
                            materialShip.shipId = it.id
                        })
                    }
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
                GDSTStorage.GDSTProductForms.toBaseMap3(),
                getString(R.string.product_forms)
            ) {
                vh.updateError()
                materialShip.prodctMethod = it?.id ?: return@valueDialog
                productForm = it
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

                if (productForm == null)
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
    }
}