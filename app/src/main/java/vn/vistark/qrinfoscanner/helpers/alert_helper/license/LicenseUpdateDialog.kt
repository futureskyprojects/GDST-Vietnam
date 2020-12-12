package vn.vistark.qrinfoscanner.helpers.alert_helper.license

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.domain.mock_entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.domain.mock_models.organization.response.Organization
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showSelectLogicAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.helpers.organization.OrganizationBindHolder

class LicenseUpdateDialog {
    companion object {
        fun AppCompatActivity.showAddCertAndLicenseDataAlert(
            onCompleted: (CertificationAndLicense?) -> Unit,
            certificationAndLicense: CertificationAndLicense = CertificationAndLicense()
        ) {
            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_update_cert_license_data, null)

            v.setOnClickListener { HideKeyboard(v) }

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
                v.HideKeyboard()
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
                v.HideKeyboard()
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
                v.HideKeyboard()
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
                v.HideKeyboard()
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
                v.HideKeyboard()
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