package vn.vistark.qrinfoscanner.helpers.alert_helper.license

import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.models.country.response.Country
import vn.vistark.qrinfoscanner.core.models.fao.response.FAO
import vn.vistark.qrinfoscanner.core.models.organization.response.Organization

class LicenseUpdateViewHolder(v: View) {
    val accaldIvClose: ImageView = v.findViewById(R.id.accaldIvClose)

    val accaldTvErrorMsg: TextView = v.findViewById(R.id.accaldTvErrorMsg)

    val accaldTvDialogName: TextView = v.findViewById(R.id.accaldTvDialogName)

    val accaldEdtHarvestCertification: EditText =
        v.findViewById(R.id.accaldEdtHarvestCertification)

    val accaldTvFishingAuthorization: TextView =
        v.findViewById(R.id.accaldTvFishingAuthorization)

    val accaldTvTransshipmentAuthorization: TextView =
        v.findViewById(R.id.accaldTvTransshipmentAuthorization)

    val accaldTvExistenceOfHumanWelfarePolicy: TextView =
        v.findViewById(R.id.accaldTvExistenceOfHumanWelfarePolicy)

    val accaldTvHumanWelfarePolicyStandards: TextView =
        v.findViewById(R.id.accaldTvHumanWelfarePolicyStandards)

    val accaldBtnCreateVesselData: Button =
        v.findViewById(R.id.accaldBtnCreateVesselData)

    fun updateError(err: String = ""): Boolean {
        if (err.isNotEmpty()) {
            this.accaldTvErrorMsg.text = err
            this.accaldTvErrorMsg.visibility = View.VISIBLE
            return false
        } else {
            this.accaldTvErrorMsg.text = ""
            this.accaldTvErrorMsg.visibility = View.GONE
            return true
        }
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

    fun updateOrg(o: Organization, v: TextView): Organization {
        v.text = o.name
        return o
    }

    fun updateLogic(logic: Boolean, v: TextView): Boolean {
        v.text = if (logic) "Có" else "Không"
        return logic
    }

    fun loadExistData(license: CertificationAndLicense): Triple<Organization?, Organization?, Int> {

        if (license.Id > 0) {
            accaldTvDialogName.text = "Chỉnh sửa giấy phép"

            val fishingAuthorization =
                RuntimeStorage.Organizations?.first { x -> x.name == license.fishingAuthorization }

            val transshipmentAuthorization =
                RuntimeStorage.Organizations?.first { x -> x.name == license.transshipmentAuthorization }

            if (fishingAuthorization != null) updateOrg(
                fishingAuthorization,
                accaldTvFishingAuthorization
            )

            if (transshipmentAuthorization != null) updateOrg(
                transshipmentAuthorization,
                accaldTvTransshipmentAuthorization
            )

            return Triple(fishingAuthorization, transshipmentAuthorization, -1)

        }
        accaldTvDialogName.text = "Thêm mới giấy phép"
        return Triple(null, null, -1)
    }

}