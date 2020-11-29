package vn.vistark.qrinfoscanner.ui.statics_data.vessel_data

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import java.util.*

class VesselDataViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val ilsLnRoot: LinearLayout = v.findViewById(R.id.ilvLnRoot)
    private val ilvIvVesselFlag: ImageView = v.findViewById(R.id.ilvIvVesselFlag)
    private val ilvdIvDeleteIcon: ImageView = v.findViewById(R.id.ilvdIvDeleteIcon)
    private val ilvTvVesselOwnerName: TextView = v.findViewById(R.id.ilvTvVesselOwnerName)
    private val ilvTvVesselRegistration: TextView = v.findViewById(R.id.ilvTvVesselRegistration)
    private val ilvTvVesselCoordinates: TextView = v.findViewById(R.id.ilvTvVesselCoordinates)
    private val ilvTvVesselVMS: TextView = v.findViewById(R.id.ilvTvVesselVMS)

    fun bind(vesselData: VesselData) {
        setOwnerName(vesselData)
        setRegistration(vesselData)
        setCoordinates(vesselData)
        setVMS(vesselData)
        setFlags(vesselData)

        ilsLnRoot.clickAnimate {

        }
        ilvdIvDeleteIcon.clickAnimate {

        }
    }

    private fun setFlags(vesselData: VesselData) {
        Glide.with(ilvIvVesselFlag)
            .load("${RuntimeStorage.Countries?.reposeUrl}${vesselData.vesselFlag.toLowerCase(Locale.ROOT)}.png")
            .into(ilvIvVesselFlag)
    }

    private fun setOwnerName(vesselData: VesselData) {
        ilvTvVesselOwnerName.text = vesselData.vesselOwner
    }

    @SuppressLint("SetTextI18n")
    private fun setRegistration(vesselData: VesselData) {
        ilvTvVesselRegistration.text = "Số đăng ký tàu:" + vesselData.vesselRegistration
    }

    @SuppressLint("SetTextI18n")
    private fun setCoordinates(vesselData: VesselData) {
        ilvTvVesselCoordinates.text =
            "Tọa độ: FAO${vesselData.availabilityOfCatchCoordinates} (${RuntimeStorage.FAOs?.nameFormCode(
                vesselData.availabilityOfCatchCoordinates
            )})"
    }

    private fun setVMS(vesselData: VesselData) {
        ilvTvVesselVMS.text = "VMS: " + vesselData.satelliteVesselTrackingAuthority
    }

}