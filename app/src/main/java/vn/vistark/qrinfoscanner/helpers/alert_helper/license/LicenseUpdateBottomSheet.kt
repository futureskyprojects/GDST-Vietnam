package vn.vistark.qrinfoscanner.helpers.alert_helper.license

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.models.organization.response.Organization
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showSelectLogicAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.vessel.VesselUpdateViewHolder
import vn.vistark.qrinfoscanner.helpers.organization.OrganizationBindHolder

class LicenseUpdateBottomSheet {
    companion object {
        fun AppCompatActivity.showAddCertAndLicenseDataAlert(
            onCompleted: (CertificationAndLicense?) -> Unit,
            certificationAndLicense: CertificationAndLicense = CertificationAndLicense()
        ) {
            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_create_certification_and_license_data, null)

            val vh = LicenseUpdateViewHolder(v)

            val res = vh.loadExistData(certificationAndLicense)

            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Khai báo

            var fishingAuthorization: Organization? = res.first
            var transshipmentAuthorization: Organization? = res.second
            var existenceOfHumanWelfarePolicy: Boolean? =
                certificationAndLicense.existenceOfHumanWelfarePolicy
            var humanWelfarePolicyStandards: Boolean? =
                certificationAndLicense.humanWelfarePolicyStandards


            // Sự kiện
            vh.accaldIvClose.clickAnimate {
                onCompleted.invoke(null)
                mAlertDialog.dismiss()
            }

            vh.clearErrorOnTextChanger(vh.accaldEdtHarvestCertification)

            vh.accaldTvFishingAuthorization.clickAnimate {
                // Clear Error state
                vh.updateError()

                showSelectBottomSheetAlert(
                    "Cơ quan cấp phép khai thác",
                    RuntimeStorage.Organizations?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_country_organization_alert,
                    { o, v ->
                        OrganizationBindHolder.bind(o, v)
                    }
                ) { o ->
                    if (o != null) {
                        fishingAuthorization = vh.updateOrg(o, vh.accaldTvFishingAuthorization)
                        certificationAndLicense.fishingAuthorization = fishingAuthorization!!.name
                    }
                }
            }

            vh.accaldTvTransshipmentAuthorization.clickAnimate {
                // Clear Error state
                vh.updateError()

                showSelectBottomSheetAlert(
                    "Cơ quan cấp phép trung chuyển",
                    RuntimeStorage.Organizations?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_country_organization_alert,
                    { o, v ->
                        OrganizationBindHolder.bind(o, v)
                    }
                ) { o ->
                    if (o != null) {
                        transshipmentAuthorization =
                            vh.updateOrg(o, vh.accaldTvTransshipmentAuthorization)
                        certificationAndLicense.transshipmentAuthorization =
                            transshipmentAuthorization!!.name
                    }
                }
            }

            vh.accaldTvExistenceOfHumanWelfarePolicy.clickAnimate {
                // Clear Error state
                vh.updateError()

                showSelectLogicAlert {
                    if (it != null) {
                        existenceOfHumanWelfarePolicy =
                            vh.updateLogic(it, vh.accaldTvExistenceOfHumanWelfarePolicy)
                        certificationAndLicense.existenceOfHumanWelfarePolicy = it
                    }
                }
            }

            vh.accaldTvHumanWelfarePolicyStandards.clickAnimate {
                // Clear Error state
                vh.updateError()

                showSelectLogicAlert {
                    if (it != null) {
                        humanWelfarePolicyStandards =
                            vh.updateLogic(it, vh.accaldTvHumanWelfarePolicyStandards)
                        certificationAndLicense.humanWelfarePolicyStandards = it
                    }
                }
            }

            vh.accaldBtnCreateVesselData.clickAnimate {
                var isValidate = true

                certificationAndLicense.harvestCertification =
                    vh.accaldEdtHarvestCertification.text.toString()
                if (certificationAndLicense.harvestCertification.isEmpty()) {
                    isValidate = vh.updateError("Chưa nhập giấy phép")
                    vh.accaldEdtHarvestCertification.error = "Chưa nhập giấy phép"
                }

                if (fishingAuthorization == null)
                    isValidate = vh.updateError("Vui lòng chọn cơ quan cấp phép khai thác")

                if (transshipmentAuthorization == null)
                    isValidate = vh.updateError("Vui lòng chọn ơ quan cấp phép trung chuyển")

                if (existenceOfHumanWelfarePolicy == null)
                    isValidate = vh.updateError("Có chính sách Phúc lợi con người không?")

                if (humanWelfarePolicyStandards == null)
                    isValidate = vh.updateError("Có Tiêu chuẩn Phúc lợi con người không?")

                if (isValidate) {
                    onCompleted.invoke(certificationAndLicense)
                    mAlertDialog.dismiss()
                }

            }

        }
    }
}