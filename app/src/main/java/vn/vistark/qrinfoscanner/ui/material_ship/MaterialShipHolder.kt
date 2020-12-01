package vn.vistark.qrinfoscanner.ui.material_ship

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.entities.MaterialShip
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupGet
import java.util.*

class MaterialShipHolder(v: View) : RecyclerView.ViewHolder(v) {
    val ilmsLnRoot: LinearLayout = v.findViewById(R.id.ilmsLnRoot)
    val ilmsdIvDeleteIcon: ImageView = v.findViewById(R.id.ilmsdIvDeleteIcon)

    private val ilmsIvVesselFlag: ImageView = v.findViewById(R.id.ilmsIvVesselFlag)
    private val ilmsTvVesselOwnerName: TextView = v.findViewById(R.id.ilmsTvVesselOwnerName)
    private val ilmsTvVesselRegistration: TextView = v.findViewById(R.id.ilmsTvVesselRegistration)
    private val ilmsTvVesselCoordinates: TextView = v.findViewById(R.id.ilmsTvVesselCoordinates)

    private val ilmsTvHarvestCertification: TextView =
        v.findViewById(R.id.ilmsTvHarvestCertification)

    private val ilmsTvFIP: TextView = v.findViewById(R.id.ilmsTvFIP)
    private val ilmsTvTripDate: TextView = v.findViewById(R.id.ilmsTvTripDate)
    private val ilmsTvGrearType: TextView = v.findViewById(R.id.ilmsTvGrearType)
    private val ilmsTvProductMethod: TextView = v.findViewById(R.id.ilmsTvProductMethod)
    private val ilmsTvLanding: TextView = v.findViewById(R.id.ilmsTvLanding)

    @SuppressLint("SetTextI18n")
    fun bind(materialShip: MaterialShip) {
        val vesselData: VesselData? = MockupGet(materialShip.VesselDataId)
        val certAndLicense: CertificationAndLicense? =
            MockupGet(materialShip.CertificationAndLicenseId)
        if (vesselData != null) {
            setFlags(vesselData)
            setOwnerName(vesselData)
            setRegistration(vesselData)
            setCoordinates(vesselData)
        }

        if (certAndLicense != null) {
            setHarvestCertification(certAndLicense)
        }

        ilmsTvFIP.text = "FIP: ${materialShip.FIP}"
        ilmsTvTripDate.text = "Ngày đi: ${materialShip.TripDate}"
        ilmsTvGrearType.text = "Ngư cụ: ${materialShip.GearType}"
        ilmsTvProductMethod.text = "Phương thức khai thác: ${materialShip.ProductMethod}"
        ilmsTvLanding.text =
            "Lên cá: ${materialShip.LandingLocation} (${materialShip.DatesOfLanding})"
    }

    private fun setFlags(vesselData: VesselData) {
        Glide.with(ilmsIvVesselFlag)
            .load("${RuntimeStorage.Countries?.reposeUrl}${vesselData.vesselFlag.toLowerCase(Locale.ROOT)}.png")
            .into(ilmsIvVesselFlag)
    }

    private fun setOwnerName(vesselData: VesselData) {
        ilmsTvVesselOwnerName.text = vesselData.vesselOwner
    }

    @SuppressLint("SetTextI18n")
    private fun setRegistration(vesselData: VesselData) {
        ilmsTvVesselRegistration.text = "Số đăng ký tàu:" + vesselData.vesselRegistration
    }

    @SuppressLint("SetTextI18n")
    private fun setCoordinates(vesselData: VesselData) {
        ilmsTvVesselCoordinates.text =
            "Tọa độ: FAO${vesselData.availabilityOfCatchCoordinates} (${RuntimeStorage.FAOs?.nameFormCode(
                vesselData.availabilityOfCatchCoordinates
            )})"
    }

    @SuppressLint("SetTextI18n")
    private fun setHarvestCertification(license: CertificationAndLicense) {
        ilmsTvHarvestCertification.text = "Giấy phép số: ${license.harvestCertification}"
    }
}