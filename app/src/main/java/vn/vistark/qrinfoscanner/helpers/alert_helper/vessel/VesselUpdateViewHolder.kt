package vn.vistark.qrinfoscanner.helpers.alert_helper.vessel

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.models.country.response.Country
import vn.vistark.qrinfoscanner.core.models.fao.response.FAO
import vn.vistark.qrinfoscanner.core.models.organization.response.Organization
import java.util.*

class VesselUpdateViewHolder(v: View) {
    val acvdIvClose: ImageView = v.findViewById(R.id.acvdIvClose)

    val acvdTvErrorMsg: TextView = v.findViewById(R.id.acvdTvErrorMsg)

    val acvdTvDialogName: TextView = v.findViewById(R.id.acvdTvDialogName)
    val acvdEdtVesselRegistration: EditText = v.findViewById(R.id.acvdEdtVesselRegistration)
    val acvdEdtVesselOwnerName: EditText = v.findViewById(R.id.acvdEdtVesselOwnerName)
    val ilcaLnRoot: LinearLayout = v.findViewById(R.id.ilcaLnRoot)
    val ilcaIvFlag: ImageView = v.findViewById(R.id.ilcaIvFlag)
    val ilcaTvCountryName: TextView = v.findViewById(R.id.ilcaTvCountryName)

    val ilcoaLnRoot: LinearLayout = v.findViewById(R.id.ilcoaLnRoot)
    val ilcoaIv: ImageView = v.findViewById(R.id.ilcoaIv)
    val ilcoaTv: TextView = v.findViewById(R.id.ilcoaTv)

    val ilfaLnRoot: LinearLayout = v.findViewById(R.id.ilfaLnRoot)
    val ilfaTvCode: TextView = v.findViewById(R.id.ilfaTvCode)
    val ilfaTvName: TextView = v.findViewById(R.id.ilfaTvName)

    val acvdBtnCreateVesselData: Button = v.findViewById(R.id.acvdBtnConfirm)

    fun updateError(err: String = ""): Boolean {
        if (err.isNotEmpty()) {
            this.acvdTvErrorMsg.text = err
            this.acvdTvErrorMsg.visibility = View.VISIBLE
            return false
        } else {
            this.acvdTvErrorMsg.text = ""
            this.acvdTvErrorMsg.visibility = View.GONE
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

    @SuppressLint("SetTextI18n")
    fun updateCountry(country: Country): Country {
        Glide.with(this.ilcaIvFlag)
            .load(
                "${RuntimeStorage.Countries?.reposeUrl}${country.alpha2.toLowerCase(
                    Locale.ROOT
                )}.png"
            )
            .into(this.ilcaIvFlag)

        this.ilcaTvCountryName.text = "${country.name} (${country.alpha2})"
        return country
    }

    fun updateOrganization(organization: Organization): Organization {
        this.ilcoaTv.text = organization.name
        return organization
    }

    fun updateFao(fao: FAO): FAO {
        this.ilfaTvCode.text = "FAO${fao.code}"
        this.ilfaTvName.text = fao.name
        return fao
    }

    fun loadExistData(vesselData: VesselData): Triple<Country?, Organization?, FAO?> {

        if (vesselData.Id > 0) {
            acvdTvDialogName.text = "Cập nhật dữ liệu tàu"
            val country =
                RuntimeStorage.Countries?.countries?.first { x -> x.alpha2 == vesselData.vesselFlag }
            val fao = RuntimeStorage.FAOs?.first { x ->
                x.code == vesselData.availabilityOfCatchCoordinates.replace(
                    "FAO",
                    ""
                )
            }
            val organization =
                RuntimeStorage.Organizations?.first { x -> x.name == vesselData.satelliteVesselTrackingAuthority }

            if (country != null) this.updateCountry(country)

            if (organization != null) this.updateOrganization(organization)

            if (fao != null) this.updateFao(fao)

            return Triple(country, organization, fao)

        }
        acvdTvDialogName.text = "Tạo dữ liệu tàu mới"
        return Triple(null, null, null)
    }
}