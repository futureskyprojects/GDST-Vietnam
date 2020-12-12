package vn.vistark.qrinfoscanner.helpers.alert_helper.technical_data

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.domain.mock_entities.MaterialShip
import vn.vistark.qrinfoscanner.domain.mock_entities.VesselData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupGet
import vn.vistark.qrinfoscanner.domain.mock_models.fao.response.FAO

class TechnicalDataViewHolder(v: View) {

    val autdIvClose: ImageView = v.findViewById(R.id.autdIvClose)
    val autdTvErrorMsg: TextView = v.findViewById(R.id.autdTvErrorMsg)
    val autdTvDialogName: TextView = v.findViewById(R.id.autdTvDialogName)

    val ilfaLnRoot: LinearLayout = v.findViewById(R.id.ilfaLnRoot)
    val ilfaTvCode: TextView = v.findViewById(R.id.ilfaTvCode)
    val ilfaTvName: TextView = v.findViewById(R.id.ilfaTvName)

    val acvdBtnCreateVesselData: TextView = v.findViewById(R.id.acvdBtnConfirm)
    // -------------------------------- //

    val autdEdtEventId: EditText =
        v.findViewById(R.id.autdEdtEventId)

    val autdTvEventDate: TextView =
        v.findViewById(R.id.autdTvEventDate)

    val autdEdtProductOwnerShip: EditText =
        v.findViewById(R.id.autdEdtProductOwnerShip)

    val autdEdtInformationProvider: EditText =
        v.findViewById(R.id.autdEdtInformationProvider)

    fun updateError(err: String = ""): Boolean {
        if (err.isNotEmpty()) {
            this.autdTvErrorMsg.text = err
            this.autdTvErrorMsg.visibility = View.VISIBLE
            return false
        } else {
            this.autdTvErrorMsg.text = ""
            this.autdTvErrorMsg.visibility = View.GONE
            return true
        }
    }


    fun loadExistData(materialShip: MaterialShip): Triple<VesselData?, CertificationAndLicense?, Int> {

        if (materialShip.Id > 0) {
            autdTvDialogName.text = "Sửa dữ liệu tàu nguyên liệu"
            val vesselData = MockupGet<VesselData>(materialShip.VesselDataId)
            val certLicense =
                MockupGet<CertificationAndLicense>(materialShip.CertificationAndLicenseId)

            return Triple(vesselData, certLicense, -1)

        }
        autdTvDialogName.text = "Tạo dữ liệu tàu nguyên liệu"
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

    fun updateFao(fao: FAO): FAO {
        this.ilfaTvCode.text = "FAO${fao.code}"
        this.ilfaTvName.text = fao.name
        return fao
    }

}