package vn.vistark.qrinfoscanner.helpers.alert_helper.technical_data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.TechnicalData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.models.fao.response.FAO
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showDatePicker
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.helpers.fao.FaoBindHolder
import java.util.*

class TechnicalDataUpdateDialog {
    companion object {
        fun AppCompatActivity.showUpdateTechnicalDataAlert(
            onCompleted: (TechnicalData?) -> Unit,
            technicalData: TechnicalData = TechnicalData()
        ) {
            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_update_technical_data, null)

            v.setOnClickListener { HideKeyboard(v) }

            val vh = TechnicalDataViewHolder(v)

//            val res = vh.loadExistData(materialShip)

            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Khai báo

            var eventDate: Date? = null
            var fao: FAO? = null

            vh.autdTvEventDate.showDatePicker({
                technicalData.eventDate = it
                eventDate = it
            })

            vh.clearErrorOnTextChanger(vh.autdEdtEventId)
            vh.clearErrorOnTextChanger(vh.autdEdtProductOwnerShip)
            vh.clearErrorOnTextChanger(vh.autdEdtInformationProvider)

            // Sự kiện
            vh.autdIvClose.clickAnimate {
                mAlertDialog.dismiss()
                onCompleted.invoke(null)
            }

            // Sự kiện chọn FAO
            vh.ilfaLnRoot.clickAnimate {
                v.HideKeyboard()
                // Clear Error state
                vh.updateError()

                showSelectBottomSheetAlert(
                    "Vị trí mẻ lưới",
                    RuntimeStorage.FAOs?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_fao_alert,
                    { f, v ->
                        FaoBindHolder.bind(f, v)
                    }
                ) { f ->
                    if (f != null) fao = vh.updateFao(f)
                }
            }

            vh.acvdBtnCreateVesselData.clickAnimate {
                v.HideKeyboard()
                technicalData.eventId = vh.autdEdtEventId.text.toString().toIntOrNull() ?: -1
                technicalData.productOwnerShip = vh.autdEdtProductOwnerShip.text.toString()
                technicalData.informationProvider = vh.autdEdtInformationProvider.text.toString()

                var isValidate = true

                if (eventDate == null)
                    isValidate = vh.updateError("Vui lòng chọn ngày giờ mẻ lưới")

                if (fao == null)
                    isValidate = vh.updateError("Vui lòng chọn vị trí mẻ lưới")

                if (technicalData.eventId <= 0) {
                    vh.autdEdtEventId.error = "Vui lòng nhập ID mẻ lưới"
                    isValidate = vh.updateError("Vui lòng nhập ID mẻ lưới")
                }

                if (technicalData.productOwnerShip.isEmpty()) {
                    vh.autdEdtProductOwnerShip.error = "Vui lòng nhập tên chủ sở hữu"
                    isValidate = vh.updateError("Vui lòng nhập tên chủ sở hữu")
                }

                if (technicalData.informationProvider.isEmpty()) {
                    vh.autdEdtInformationProvider.error =
                        "Vui lòng nhập tên người cung cấp thông tin"
                    isValidate = vh.updateError("Vui lòng nhập tên người cung cấp thông tin")
                }

                if (!isValidate)
                    return@clickAnimate

                onCompleted.invoke(technicalData)
                mAlertDialog.dismiss()
            }

        }
    }
}