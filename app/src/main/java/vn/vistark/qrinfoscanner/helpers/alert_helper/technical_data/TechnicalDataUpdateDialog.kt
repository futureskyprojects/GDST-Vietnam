package vn.vistark.qrinfoscanner.helpers.alert_helper.technical_data

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.extensions.StringExtension.Companion.ToYMDDate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTTechnicalDataDTO
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTLocation.Companion.toBaseMap4
import vn.vistark.qrinfoscanner.domain.entities.GDSTProductForm.Companion.toBaseMap3
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showDatePicker
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.valueDialog
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
            technicalData: GDSTTechnicalDataDTO = GDSTTechnicalDataDTO(),
            id: String = "",
            lastedEventIdInMaterialShip: Int = 0
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

            updateExistData(vh)
            vh.autdEdtEventId.setText(id)
            vh.autdEdtEventId.isEnabled = false
            if (id.isEmpty())
                vh.autdEdtEventId.setText((lastedEventIdInMaterialShip + 1).toString())

            vh.onCheckedChange {
                if (it) {
                    mTechnicalData.isTrasshipment = 1
                    mTechnicalData.dateTransshipment = ""
                    mTechnicalData.loactionTransshipmentId = 0
                    transhipmentDate = null
                    transhipmentFao = null
                } else {
                    mTechnicalData.isTrasshipment = 0
                }
            }

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
                getString(R.string.product_forms)
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
                getString(R.string.event_red_point)
            ) {
                vh.updateError()
                mTechnicalData.geolocationId = it?.id ?: return@valueDialog
                materialBatchFao = it
            }

            // Sự kiện chọn FAO cho vị trí chuyển tải
            vh.autdTvTranshipmentLocation.valueDialog(
                GDSTStorage.GDSTLocations.toBaseMap4(),
                getString(R.string.transhipment_location)
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
                    isValidate = vh.updateError(getString(R.string.vlcngml))

                if (materialBatchFao == null)
                    isValidate = vh.updateError(getString(R.string.vlcvtml))

                if (mTechnicalData.eventId <= 0) {
                    vh.autdEdtEventId.error = getString(R.string.vlnmml)
                    isValidate = vh.updateError(getString(R.string.vlnmml))
                }

//                if (mTechnicalData.KDE.isEmpty()) {
//                    vh.autdEdtKDELinking.error = getString(R.string.vlnkdelk)
//                    isValidate = vh.updateError(getString(R.string.vlnkdelk))
//                }

                if (!isValidate)
                    return@clickAnimate

                onCompleted.invoke(mTechnicalData)
                mAlertDialog.dismiss()
            }

        }

        private fun updateExistData(vh: TechnicalDataViewHolder) {

            if (mTechnicalData.isTrasshipment == 1) {
                vh.autdScIsTranshipment.isChecked = true
                vh.autdLnTranshipmentRoot.visibility = View.VISIBLE
            }

            // Gán dữ liệu đã có
            eventDate = mTechnicalData.eventDate.ToYMDDate()
            materialBatchFao =
                GDSTStorage.GDSTLocations?.firstOrNull { x -> x.id == mTechnicalData.geolocationId }
                    ?.toBaseMap()
            productForm =
                GDSTStorage.GDSTProductForms?.firstOrNull { x -> x.id == mTechnicalData.productFormId }
                    ?.toBaseMap()
            transhipmentDate = mTechnicalData.dateTransshipment.ToYMDDate()
            transhipmentFao =
                GDSTStorage.GDSTLocations?.firstOrNull { x -> x.id == mTechnicalData.loactionTransshipmentId }
                    ?.toBaseMap()

            // Xử lý view
            vh.autdTvEventDate.text = eventDate?.Format("yyyy-MM-dd")
            vh.autdTvTranshipmentDate.text = transhipmentDate?.Format("yyyy-MM-dd")
            vh.autdTvProductForm.text = productForm?.name
            vh.autdTvGeolocation.text = materialBatchFao?.name
            vh.autdTvTranshipmentLocation.text = transhipmentFao?.name
            vh.autdEdtKDELinking.setText(mTechnicalData.KDE)

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