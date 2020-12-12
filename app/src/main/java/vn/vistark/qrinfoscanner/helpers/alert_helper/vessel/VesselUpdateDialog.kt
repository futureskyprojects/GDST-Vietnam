package vn.vistark.qrinfoscanner.helpers.alert_helper.vessel

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.domain.mock_entities.VesselData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.domain.mock_models.country.response.Country
import vn.vistark.qrinfoscanner.domain.mock_models.fao.response.FAO
import vn.vistark.qrinfoscanner.domain.mock_models.organization.response.Organization
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.helpers.countries.CountryBindHolder
import vn.vistark.qrinfoscanner.helpers.fao.FaoBindHolder
import vn.vistark.qrinfoscanner.helpers.organization.OrganizationBindHolder

class VesselUpdateDialog {
    companion object {
        fun AppCompatActivity.showAddVessDataAlert(
            onCompleted: (VesselData?) -> Unit,
            vesselData: VesselData = VesselData()
        ) {

            val v = LayoutInflater.from(this).inflate(R.layout.alert_update_vessel_data, null)

            v.setOnClickListener { v.HideKeyboard() }

            // Khai báo viewholder
            val vh = VesselUpdateViewHolder(v)

            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Load dữ liệu nếu đã có
            val data = vh.loadExistData(vesselData)

            // Khai báo
            var country: Country? = data.first
            var organization: Organization? = data.second
            var fao: FAO? = data.third

            vh.clearErrorOnTextChanger(vh.acvdEdtVesselRegistration)
            vh.clearErrorOnTextChanger(vh.acvdEdtVesselOwnerName)


            // Sự kiện
            vh.acvdIvClose.clickAnimate {
                mAlertDialog.dismiss()
                onCompleted.invoke(null)
            }

            // Sự kiện chọn Flag
            vh.ilcaLnRoot.clickAnimate {
                v.HideKeyboard()
                // Clear Error state
                vh.updateError()

                showSelectBottomSheetAlert(
                    "Cờ tàu",
                    RuntimeStorage.Countries?.countries?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_country_alert,
                    { country, v ->
                        CountryBindHolder.bind(country, v)
                    }
                ) { c ->
                    if (c != null)
                        country = vh.updateCountry(c)
                }
            }


            // Sự kiện chọn Organization
            vh.ilcoaLnRoot.clickAnimate {
                v.HideKeyboard()
                // Clear Error state
                vh.updateError()

                showSelectBottomSheetAlert(
                    "VMS",
                    RuntimeStorage.Organizations?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_country_organization_alert,
                    { o, v ->
                        OrganizationBindHolder.bind(o, v)
                    }
                ) { o ->
                    if (o != null)
                        organization = vh.updateOrganization(o)
                }
            }

            // Sự kiện chọn FAO
            vh.ilfaLnRoot.clickAnimate {
                v.HideKeyboard()
                // Clear Error state
                vh.updateError()

                showSelectBottomSheetAlert(
                    "Toạ độ",
                    RuntimeStorage.FAOs?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_fao_alert,
                    { f, v ->
                        FaoBindHolder.bind(f, v)
                    }
                ) { f ->
                    if (f != null)
                        fao = vh.updateFao(f)
                }
            }

            // sự kiện xác nhận
            vh.acvdBtnCreateVesselData.clickAnimate {
                v.HideKeyboard()
                var isValidate = true
                vesselData.vesselRegistration = vh.acvdEdtVesselRegistration.text.toString()
                vesselData.vesselOwner = vh.acvdEdtVesselOwnerName.text.toString()

                if (vesselData.vesselRegistration.trim().isEmpty()) {
                    vh.acvdEdtVesselRegistration.error = "Vui lòng nhập số đăng ký"
                    isValidate = vh.updateError("Vui lòng nhập số đăng ký")
                }

                if (vesselData.vesselOwner.trim().isEmpty()) {
                    vh.acvdEdtVesselOwnerName.error = "Vui lòng nhập tên chủ tàu"
                    isValidate = vh.updateError("Vui lòng nhập tên chủ tàu")
                }

                if (country == null)
                    isValidate = vh.updateError("Vui lòng chọn cờ chủ tàu")

                if (organization == null)
                    isValidate = vh.updateError("Vui lòng chọn VMS")

                if (fao == null)
                    isValidate = vh.updateError("Vui lòng chọn Tọa độ")

                if (isValidate) {
                    vesselData.vesselName = vesselData.vesselRegistration
                    vesselData.uniqueVesselIdentification = vesselData.vesselRegistration
                    vesselData.publicVesselRegistryHyperlink = ""
                    vesselData.vesselFlag = country!!.alpha2
                    vesselData.availabilityOfCatchCoordinates = fao!!.code
                    vesselData.satelliteVesselTrackingAuthority = organization!!.name
                    onCompleted.invoke(vesselData)
                    mAlertDialog.dismiss()
                }
            }

        }
    }
}