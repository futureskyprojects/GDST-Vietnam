package vn.vistark.qrinfoscanner.helpers.alert_helper

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.models.country.response.Country
import vn.vistark.qrinfoscanner.core.models.fao.response.FAO
import vn.vistark.qrinfoscanner.core.models.organization.response.Organization
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.helpers.countries.CountryBindHolder
import vn.vistark.qrinfoscanner.helpers.fao.FaoBindHolder
import vn.vistark.qrinfoscanner.helpers.organization.OrganizationBindHolder
import java.util.*

class CreateOrUpdateVesselDataBottomSheet {
    companion object {
        fun AppCompatActivity.showAddVessDataAlert(
            onCompleted: (VesselData?) -> Unit,
            vesselData: VesselData = VesselData()
        ) {

            val v = LayoutInflater.from(this).inflate(R.layout.alert_create_vessel_data, null)

            // Hiển thị
            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            // Khai báo

            var country: Country? = null
            var fao: FAO? = null
            var organization: Organization? = null

            val acvdIvClose: ImageView = v.findViewById(R.id.acvdIvClose)

            val acvdTvErrorMsg: TextView = v.findViewById(R.id.acvdTvErrorMsg)

            val acvdTvDialogName: TextView = v.findViewById(R.id.acvdTvDialogName)

            val acvdEdtVesselRegistration: EditText = v.findViewById(R.id.acvdEdtVesselRegistration)
            acvdEdtVesselRegistration.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // Clear Error state
                    acvdTvErrorMsg.text = ""
                    acvdTvErrorMsg.visibility = View.GONE
                }
            })
            val acvdEdtVesselOwnerName: EditText = v.findViewById(R.id.acvdEdtVesselOwnerName)
            acvdEdtVesselOwnerName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // Clear Error state
                    acvdTvErrorMsg.text = ""
                    acvdTvErrorMsg.visibility = View.GONE
                }
            })

            val ilcaLnRoot: LinearLayout = v.findViewById(R.id.ilcaLnRoot)
            val ilcaIvFlag: ImageView = v.findViewById(R.id.ilcaIvFlag)
            val ilcaTvCountryName: TextView = v.findViewById(R.id.ilcaTvCountryName)

            val ilcoaLnRoot: LinearLayout = v.findViewById(R.id.ilcoaLnRoot)
            val ilcoaIv: ImageView = v.findViewById(R.id.ilcoaIv)
            val ilcoaTv: TextView = v.findViewById(R.id.ilcoaTv)

            val ilfaLnRoot: LinearLayout = v.findViewById(R.id.ilfaLnRoot)
            val ilfaTvCode: TextView = v.findViewById(R.id.ilfaTvCode)
            val ilfaTvName: TextView = v.findViewById(R.id.ilfaTvName)

            val acvdBtnCreateVesselData: Button = v.findViewById(R.id.acvdBtnCreateVesselData)

            // Sự kiện
            acvdIvClose.clickAnimate {
                mAlertDialog.dismiss()
                onCompleted.invoke(null)
            }

            // Đổi nhãn của dialog
            if (vesselData.Id <= 0) {
                acvdTvDialogName.text = "Tạo dữ liệu tàu mới"
            } else {
                acvdTvDialogName.text = "Cập nhật dữ liệu tàu"
            }

            // Sự kiện chọn Flag
            ilcaLnRoot.clickAnimate {
                // Clear Error state
                acvdTvErrorMsg.text = ""
                acvdTvErrorMsg.visibility = View.GONE

                showSelectBottomSheetAlert(
                    "Cờ tàu",
                    RuntimeStorage.Countries?.countries?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_country_alert,
                    { country, v ->
                        CountryBindHolder.bind(country, v)
                    }
                ) { c ->
                    if (c != null) {
                        country = c
                        Glide.with(ilcaIvFlag)
                            .load(
                                "${RuntimeStorage.Countries?.reposeUrl}${country!!.alpha2.toLowerCase(
                                    Locale.ROOT
                                )}.png"
                            )
                            .into(ilcaIvFlag)

                        ilcaTvCountryName.text = "${country!!.name} (${country!!.alpha2})"
                    }
                }
            }

            // Sự kiện chọn Organization
            ilcoaLnRoot.clickAnimate {
                // Clear Error state
                acvdTvErrorMsg.text = ""
                acvdTvErrorMsg.visibility = View.GONE

                showSelectBottomSheetAlert(
                    "VMS",
                    RuntimeStorage.Organizations?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_country_organization_alert,
                    { o, v ->
                        OrganizationBindHolder.bind(o, v)
                    }
                ) { o ->
                    if (o != null) {
                        organization = o
                        ilcoaTv.text = organization!!.name
                    }
                }
            }

            // Sự kiện chọn FAO
            ilfaLnRoot.clickAnimate {
                // Clear Error state
                acvdTvErrorMsg.text = ""
                acvdTvErrorMsg.visibility = View.GONE

                showSelectBottomSheetAlert(
                    "Toạ độ",
                    RuntimeStorage.FAOs?.toTypedArray() ?: emptyArray(),
                    R.layout.item_layout_fao_alert,
                    { f, v ->
                        FaoBindHolder.bind(f, v)
                    }
                ) { f ->
                    if (f != null) {
                        fao = f
                        ilfaTvCode.text = "FAO${fao!!.code}"
                        ilfaTvName.text = fao!!.name
                    }
                }
            }

            // sự kiện xác nhận
            acvdBtnCreateVesselData.clickAnimate {
                var isValidate = true
                vesselData.vesselRegistration = acvdEdtVesselRegistration.text.toString()
                vesselData.vesselOwner = acvdEdtVesselOwnerName.text.toString()

                if (vesselData.vesselRegistration.trim().isEmpty()) {
                    acvdEdtVesselRegistration.error = "Vui lòng nhập số đăng ký"
                    acvdTvErrorMsg.text = "Vui lòng nhập số đăng ký"
                    acvdTvErrorMsg.visibility = View.VISIBLE
                    isValidate = false
                }
                if (vesselData.vesselOwner.trim().isEmpty()) {
                    acvdEdtVesselOwnerName.error = "Vui lòng nhập tên chủ tàu"
                    acvdTvErrorMsg.text = "Vui lòng nhập tên chủ tàu"
                    acvdTvErrorMsg.visibility = View.VISIBLE
                    isValidate = false
                }
                if (country == null) {
                    acvdTvErrorMsg.text = "Vui lòng chọn cờ chủ tàu"
                    acvdTvErrorMsg.visibility = View.VISIBLE
                    isValidate = false
                }

                if (organization == null) {
                    acvdTvErrorMsg.text = "Vui lòng chọn VMS"
                    acvdTvErrorMsg.visibility = View.VISIBLE
                    isValidate = false
                }

                if (fao == null) {
                    acvdTvErrorMsg.text = "Vui lòng chọn Tọa độ"
                    acvdTvErrorMsg.visibility = View.VISIBLE
                    isValidate = false
                }

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


            //region Tải dữ liệu nếu có
            if (vesselData.Id > 0) {
                country =
                    RuntimeStorage.Countries?.countries?.first { x -> x.alpha2 == vesselData.vesselFlag }
                fao = RuntimeStorage.FAOs?.first { x ->
                    x.code == vesselData.availabilityOfCatchCoordinates.replace(
                        "FAO",
                        ""
                    )
                }
                organization =
                    RuntimeStorage.Organizations?.first { x -> x.name == vesselData.satelliteVesselTrackingAuthority }

                //region Cập nhật view của Flag
                if (country != null) {
                    Glide.with(ilcaIvFlag)
                        .load(
                            "${RuntimeStorage.Countries?.reposeUrl}${country!!.alpha2.toLowerCase(
                                Locale.ROOT
                            )}.png"
                        )
                        .into(ilcaIvFlag)

                    ilcaTvCountryName.text = "${country!!.name} (${country!!.alpha2})"
                }
                //endregion

                //region Cập nhật giao diện của tổ chức
                if (organization != null) {
//                    ilcoaIv
                    ilcoaTv.text = organization!!.name
                }
                //endregion

                //region Cập nhật giao diện của FAO
                if (fao != null) {
                    ilfaTvCode.text = "FAO${fao!!.code}"
                    ilfaTvName.text = fao!!.name
                }
                //endregion

            }

            //endregion


        }
    }
}