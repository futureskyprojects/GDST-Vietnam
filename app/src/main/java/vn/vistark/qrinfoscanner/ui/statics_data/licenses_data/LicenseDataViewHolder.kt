package vn.vistark.qrinfoscanner.ui.statics_data.licenses_data

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.CertificationAndLicense

class LicenseDataViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val ilcalLnRoot: LinearLayout = v.findViewById(R.id.ilcalLnRoot)
    private val ilcalTvHarvestCertification: TextView =
        v.findViewById(R.id.ilcalTvHarvestCertification)
    private val ilcalTvFishingAuthorization: TextView =
        v.findViewById(R.id.ilcalTvFishingAuthorization)
    private val ilcalTvTransshipmentAuthorization: TextView =
        v.findViewById(R.id.ilcalTvTransshipmentAuthorization)
    private val ilcalTvExistenceOfHumanWelfarePolicy: TextView =
        v.findViewById(R.id.ilcalTvExistenceOfHumanWelfarePolicy)
    private val ilcalTvHumanWelfarePolicyStandards: TextView =
        v.findViewById(R.id.ilcalTvHumanWelfarePolicyStandards)

    val ilcalIvDeleteIcon: ImageView = v.findViewById(R.id.ilcalIvDeleteIcon)

    fun bind(license: CertificationAndLicense) {
        setHarvestCertification(license)
        setFishingAuthorization(license)
        setTransshipmentAuthorization(license)
        setExistenceOfHumanWelfarePolicy(license)
        setHumanWelfarePolicyStandards(license)
    }

    @SuppressLint("SetTextI18n")
    private fun setHumanWelfarePolicyStandards(license: CertificationAndLicense) {
        ilcalTvHumanWelfarePolicyStandards.text =
            "Tiêu chuẩn Phúc lợi con người: " +
                    if (license.humanWelfarePolicyStandards) "Có" else "Không"
    }

    @SuppressLint("SetTextI18n")
    private fun setExistenceOfHumanWelfarePolicy(license: CertificationAndLicense) {
        ilcalTvExistenceOfHumanWelfarePolicy.text = "Chính sách Phúc lợi con người: " +
                if (license.existenceOfHumanWelfarePolicy) "Có" else "Không"
    }

    @SuppressLint("SetTextI18n")
    private fun setTransshipmentAuthorization(license: CertificationAndLicense) {
        ilcalTvTransshipmentAuthorization.text =
            "Cơ quan cấp phép trung chuyển: ${license.transshipmentAuthorization}"
    }

    @SuppressLint("SetTextI18n")
    private fun setFishingAuthorization(license: CertificationAndLicense) {
        ilcalTvFishingAuthorization.text =
            "Cơ quan cấp phép khai thác: ${license.fishingAuthorization}"
    }

    @SuppressLint("SetTextI18n")
    private fun setHarvestCertification(license: CertificationAndLicense) {
        ilcalTvHarvestCertification.text = "Giấy phép số: ${license.harvestCertification}"
    }

}