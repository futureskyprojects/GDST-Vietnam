package vn.vistark.qrinfoscanner.helpers.alert_helper

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
import vn.vistark.qrinfoscanner.helpers.organization.OrganizationBindHolder

class CreateOrUpdateCertAndLicenseBottomSheet {
    companion object {
        fun AppCompatActivity.showAddCertAndLicenseDataAlert(
            onCompleted: (CertificationAndLicense?) -> Unit,
            certificationAndLicense: CertificationAndLicense = CertificationAndLicense()
        ) {
            val v = LayoutInflater.from(this)
                .inflate(R.layout.alert_create_certification_and_license_data, null)
            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Khai báo

            var fishingAuthorization: Organization? = null
            var transshipmentAuthorization: Organization? = null
            var existenceOfHumanWelfarePolicy: Boolean? = null
            var humanWelfarePolicyStandards: Boolean? = null

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

            // Sự kiện
            accaldIvClose.clickAnimate {
                mAlertDialog.dismiss()
                onCompleted.invoke(null)
            }

            accaldEdtHarvestCertification.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    accaldTvErrorMsg.text = ""
                    accaldTvErrorMsg.visibility = View.GONE
                }
            })

            accaldTvFishingAuthorization.clickAnimate {
                // Clear Error state
                accaldTvErrorMsg.text = ""
                accaldTvErrorMsg.visibility = View.GONE

                showSelectBottomSheetAlert(
                    "Cơ quan cấp phép khai thác",
                    RuntimeStorage.Organizations?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_country_organization_alert,
                    { o, v ->
                        OrganizationBindHolder.bind(o, v)
                    }
                ) { o ->
                    if (o != null) {
                        fishingAuthorization = o
                        accaldTvFishingAuthorization.text = fishingAuthorization!!.name
                        certificationAndLicense.fishingAuthorization = fishingAuthorization!!.name
                    }
                }
            }

            accaldTvTransshipmentAuthorization.clickAnimate {
                // Clear Error state
                accaldTvErrorMsg.text = ""
                accaldTvErrorMsg.visibility = View.GONE

                showSelectBottomSheetAlert(
                    "Cơ quan cấp phép trung chuyển",
                    RuntimeStorage.Organizations?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_country_organization_alert,
                    { o, v ->
                        OrganizationBindHolder.bind(o, v)
                    }
                ) { o ->
                    if (o != null) {
                        transshipmentAuthorization = o
                        accaldTvTransshipmentAuthorization.text = transshipmentAuthorization!!.name
                        certificationAndLicense.transshipmentAuthorization =
                            transshipmentAuthorization!!.name
                    }
                }
            }

            accaldTvExistenceOfHumanWelfarePolicy.clickAnimate {
                // Clear Error state
                accaldTvErrorMsg.text = ""
                accaldTvErrorMsg.visibility = View.GONE

                showSelectLogicAlert {
                    if (it != null) {
                        existenceOfHumanWelfarePolicy = it
                        certificationAndLicense.existenceOfHumanWelfarePolicy = it
                        accaldTvExistenceOfHumanWelfarePolicy.text = if (it) "Có" else "Không"
                    }
                }
            }

            accaldTvHumanWelfarePolicyStandards.clickAnimate {
                // Clear Error state
                accaldTvErrorMsg.text = ""
                accaldTvErrorMsg.visibility = View.GONE

                showSelectLogicAlert {
                    if (it != null) {
                        humanWelfarePolicyStandards = it
                        certificationAndLicense.humanWelfarePolicyStandards = it
                        accaldTvHumanWelfarePolicyStandards.text = if (it) "Có" else "Không"
                    }
                }
            }

            accaldBtnCreateVesselData.clickAnimate {
                certificationAndLicense.harvestCertification =
                    accaldEdtHarvestCertification.text.toString()
                if (certificationAndLicense.harvestCertification.isEmpty()) {
                    accaldEdtHarvestCertification.error = "Chưa nhập giấy phép"
                    accaldTvErrorMsg.text = "Chưa nhập giấy phép"
                    accaldTvErrorMsg.visibility = View.VISIBLE
                }

                if (fishingAuthorization == null) {
                    accaldTvErrorMsg.text = "____________"
                    accaldTvErrorMsg.visibility = View.VISIBLE
                }

                if (transshipmentAuthorization == null) {
                    accaldTvErrorMsg.text = "____________"
                    accaldTvErrorMsg.visibility = View.VISIBLE
                }

                if (existenceOfHumanWelfarePolicy == null) {
                    accaldTvErrorMsg.text = "____________"
                    accaldTvErrorMsg.visibility = View.VISIBLE
                }

                if (humanWelfarePolicyStandards == null) {
                    accaldTvErrorMsg.text = "____________"
                    accaldTvErrorMsg.visibility = View.VISIBLE
                }
            }
            //
            if (certificationAndLicense.Id <= 0) {
                accaldTvDialogName.text = "Thêm mới giấy phép"
            } else {
                accaldTvDialogName.text = "Chỉnh sửa giấy phép"
            }

        }
    }
}