package vn.vistark.qrinfoscanner.helpers.alert_helper.TOI

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.domain.mock_entities.TraceableObjectInformation
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.valueDialog
import vn.vistark.qrinfoscanner.helpers.alert_helper.technical_data.TOIViewHolder

class TOIUpdateDialog {
    companion object {
        fun AppCompatActivity.showUpdateTraceableObjectInformationAlert(
            onCompleted: (TraceableObjectInformation?) -> Unit,
            traceableObjectInformation: TraceableObjectInformation = TraceableObjectInformation()
        ) {
            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_update_toi, null)

            v.setOnClickListener { v.HideKeyboard() }

            val vh = TOIViewHolder(v)

//            val res = vh.loadExistData(materialShip)

            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Khai báo

            var fishData: BaseMap? = null
            var unitOfMeansure: BaseMap? = null
            var productForm: BaseMap? = null

            vh.clearErrorOnTextChanger(vh.autEdtLinkingKDE)
            vh.clearErrorOnTextChanger(vh.autEdtWeight)

            // Sự kiện
            vh.autIvClose.clickAnimate {
                mAlertDialog.dismiss()
                onCompleted.invoke(null)
            }

            vh.autTvUnitOfMeasure.valueDialog(
                RuntimeStorage.UnitOfMeansures?.toBaseMaps(),
                getString(R.string.dvt)
            ) {
                vh.updateError()
                traceableObjectInformation.unitOfMeasure = it?.name ?: return@valueDialog
                unitOfMeansure = it
            }

            vh.autTvSpice.valueDialog(
                RuntimeStorage.FishDatas?.toBaseMaps(),
                getString(R.string.loai)
            ) {
                vh.updateError()
                traceableObjectInformation.fishData =
                    RuntimeStorage.FishDatas?.fromBaseMap(it) ?: return@valueDialog
                fishData = it
            }

            vh.autTvProductForm.valueDialog(
                RuntimeStorage.ProductForms?.toBaseMaps(),
                getString(R.string.dsp)
            ) {
                vh.updateError()
                traceableObjectInformation.productForm = it?.name ?: return@valueDialog
                productForm = it
            }


            vh.acvdBtnConfirm.clickAnimate {
                traceableObjectInformation.linkingKDE = vh.autEdtLinkingKDE.text.toString()
                traceableObjectInformation.weightOrQuantity =
                    vh.autEdtWeight.text.toString().toDoubleOrNull() ?: -1.0

                var isValidate = true

                if (fishData == null)
                    isValidate = vh.updateError(getString(R.string.vlcl))

                if (unitOfMeansure == null)
                    isValidate = vh.updateError(getString(R.string.vlcdvt))

                if (productForm == null)
                    isValidate = vh.updateError(getString(R.string.vlcdsp))

                if (traceableObjectInformation.linkingKDE.isEmpty()) {
                    vh.autEdtLinkingKDE.error = getString(R.string.vlnkdelk)
                    isValidate = vh.updateError(getString(R.string.vlnkdelk))
                }

                if (traceableObjectInformation.weightOrQuantity < 0) {
                    vh.autEdtWeight.error = getString(R.string.vlnsl)
                    isValidate = vh.updateError(getString(R.string.vlnsl))
                }

                if (!isValidate)
                    return@clickAnimate

                onCompleted.invoke(traceableObjectInformation)
                mAlertDialog.dismiss()
            }

        }
    }
}