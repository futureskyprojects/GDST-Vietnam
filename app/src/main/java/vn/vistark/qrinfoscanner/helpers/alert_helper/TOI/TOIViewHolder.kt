package vn.vistark.qrinfoscanner.helpers.alert_helper.technical_data

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.entities.MaterialShip
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupGet

class TOIViewHolder(v: View) {

    val autIvClose: ImageView = v.findViewById(R.id.autIvClose)
    val autTvErrorMsg: TextView = v.findViewById(R.id.autTvErrorMsg)
    val autTvDialogName: TextView = v.findViewById(R.id.autTvDialogName)

    val acvdBtnConfirm: TextView = v.findViewById(R.id.acvdBtnConfirm)
    // -------------------------------- //

    val autEdtLinkingKDE: EditText =
        v.findViewById(R.id.autEdtLinkingKDE)

    val autEdtWeight: EditText =
        v.findViewById(R.id.autEdtWeight)

    val autTvUnitOfMeasure: TextView =
        v.findViewById(R.id.autTvUnitOfMeasure)

    val autTvSpice: TextView =
        v.findViewById(R.id.autTvSpice)

    val autTvProductForm: TextView =
        v.findViewById(R.id.autTvProductForm)

    fun updateError(err: String = ""): Boolean {
        if (err.isNotEmpty()) {
            this.autTvErrorMsg.text = err
            this.autTvErrorMsg.visibility = View.VISIBLE
            return false
        } else {
            this.autTvErrorMsg.text = ""
            this.autTvErrorMsg.visibility = View.GONE
            return true
        }
    }


    fun loadExistData(materialShip: MaterialShip): Triple<VesselData?, CertificationAndLicense?, Int> {

        if (materialShip.Id > 0) {
            autTvDialogName.text = "Sửa dữ liệu tàu nguyên liệu"
            val vesselData = MockupGet<VesselData>(materialShip.VesselDataId)
            val certLicense =
                MockupGet<CertificationAndLicense>(materialShip.CertificationAndLicenseId)

            return Triple(vesselData, certLicense, -1)

        }
        autTvDialogName.text = "Tạo dữ liệu tàu nguyên liệu"
        return Triple(null, null, -1)
    }

    fun clearErrorOnTextChanger(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Clear Error state
                updateError()
            }
        })
    }

}