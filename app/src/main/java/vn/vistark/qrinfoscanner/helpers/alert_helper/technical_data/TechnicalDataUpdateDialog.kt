package vn.vistark.qrinfoscanner.helpers.alert_helper.technical_data

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.domain.mock_entities.TechnicalData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTTechnicalDataDTO
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTLocation
import vn.vistark.qrinfoscanner.domain.entities.GDSTLocation.Companion.toBaseMap4
import vn.vistark.qrinfoscanner.domain.entities.GDSTProductForm
import vn.vistark.qrinfoscanner.domain.entities.GDSTProductForm.Companion.toBaseMap3
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showDatePicker
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.valueDialog
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.helpers.fao.FaoBindHolder
import java.util.*

class TechnicalDataUpdateDialog {
    companion object {
        private var eventDate: Date? = null
        private var materialBatchFao: BaseMap? = null
        private var productForm: BaseMap? = null
        private var transhipmentDate: Date? = null
        private var transhipmentFao: BaseMap? = null

        private var mTechnicalData: GDSTTechnicalDataDTO = GDSTTechnicalDataDTO()

        fun AppCompatActivity.showUpdateTechnicalDataAlert(
            onCompleted: (GDSTTechnicalDataDTO?) -> Unit,
            technicalData: GDSTTechnicalDataDTO = GDSTTechnicalDataDTO()
        ) {

            eventDate = null
            materialBatchFao = null
            productForm = null
            transhipmentDate = null
            transhipmentFao = null

            mTechnicalData = technicalData

            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_update_technical_data, null)
            v.setOnClickListener { HideKeyboard(v) }
            val vh = TechnicalDataViewHolder(v)
            val mAlertDialog = displayDialog(this, v)

            // Ngày, giờ mẻ lưới
            vh.autdTvEventDate.showDatePicker({
                mTechnicalData.eventDate = it.Format("yyyy-MM-dd")
                eventDate = it
            })

            // Ngày, giờ chuyển tải
            vh.autdTvTranshipmentDate.showDatePicker({
                mTechnicalData.dateTransshipment = it.Format("yyyy-MM-dd")
                transhipmentDate = it
            })

            vh.autdTvProductForm.valueDialog(
                GDSTStorage.GDSTProductForms.toBaseMap3(),
                "Product Forms"
            ) {
                vh.updateError()
                mTechnicalData.productFormId = it?.id ?: return@valueDialog
                productForm = it
            }



            vh.clearErrorOnTextChanger(vh.autdEdtEventId)
            vh.clearErrorOnTextChanger(vh.autdEdtKDELinking)

            // Sự kiện
            vh.autdIvClose.clickAnimate {
                mAlertDialog.dismiss()
                onCompleted.invoke(null)
            }

            // Sự kiện chọn FAO cho vị trí mẻ lưới
            vh.autdTvGeolocation.valueDialog(
                GDSTStorage.GDSTLocations.toBaseMap4(),
                "Event Read Point (Geolocation)"
            ) {
                vh.updateError()
                mTechnicalData.geolocationId = it?.id ?: return@valueDialog
                materialBatchFao = it
            }

            // Sự kiện chọn FAO cho vị trí chuyển tải
            vh.autdTvTranshipmentLocation.valueDialog(
                GDSTStorage.GDSTLocations.toBaseMap4(),
                "Transshipment Location"
            ) {
                vh.updateError()
                mTechnicalData.loactionTransshipmentId = it?.id ?: return@valueDialog
                transhipmentFao = it
            }

            vh.autdBtnConfirm.clickAnimate {
                v.HideKeyboard()
                mTechnicalData.eventId = vh.autdEdtEventId.text.toString().toIntOrNull() ?: -1
                mTechnicalData.KDE = vh.autdEdtKDELinking.text.toString()

                var isValidate = true

                if (eventDate == null)
                    isValidate = vh.updateError("Vui lòng chọn ngày giờ mẻ lưới")

                if (materialBatchFao == null)
                    isValidate = vh.updateError("Vui lòng chọn vị trí mẻ lưới")

                if (mTechnicalData.eventId <= 0) {
                    vh.autdEdtEventId.error = "Vui lòng nhập ID mẻ lưới"
                    isValidate = vh.updateError("Vui lòng nhập ID mẻ lưới")
                }

                if (mTechnicalData.KDE.isEmpty()) {
                    vh.autdEdtKDELinking.error = "Vui lòng nhập KDE liên kết"
                    isValidate = vh.updateError("Vui lòng nhập KDE liên kết")
                }

                if (!isValidate)
                    return@clickAnimate

                onCompleted.invoke(mTechnicalData)
                mAlertDialog.dismiss()
            }

        }

        private fun displayDialog(context: Context, v: View): AlertDialog {
            // Hiển thị
            val mBuilder = AlertDialog.Builder(context)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)
            return mAlertDialog
        }
    }
}