package vn.vistark.qrinfoscanner.ui.ship_collection

import android.R.attr.path
import android.annotation.SuppressLint
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.svg_handler.SvgSoftwareLayerSetter
import vn.vistark.qrinfoscanner.domain.api.IApiService
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTShip


class ShipCollectionViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val ilssLnRoot: LinearLayout = v.findViewById(R.id.ilssLnRoot)
    private val ilssIvVesselFlag: ImageView = v.findViewById(R.id.ilssIvVesselFlag)
    private val ilssTvVesselName: TextView = v.findViewById(R.id.ilssTvVesselName)
    private val ilssdIvIsTranshipmentIcon: ImageView =
        v.findViewById(R.id.ilssdIvIsTranshipmentIcon)

    private val ilssTvVesselRegistration: TextView = v.findViewById(R.id.ilssTvVesselRegistration)
    private val ilssTvVesselPublicRegistry: TextView =
        v.findViewById(R.id.ilssTvVesselPublicRegistry)
    private val ilssTvSatelliteTracking: TextView = v.findViewById(R.id.ilssTvSatelliteTracking)

    private val ilssTvFishingAuthorization: TextView =
        v.findViewById(R.id.ilssTvFishingAuthorization)
    private val ilssTvHarvestCertification: TextView =
        v.findViewById(R.id.ilssTvHarvestCertification)
    private val ilssTvTranshipmentAuthorization: TextView =
        v.findViewById(R.id.ilssTvTranshipmentAuthorization)
    private val ilssTvHumanWelfarePolicy: TextView = v.findViewById(R.id.ilssTvHumanWelfarePolicy)
    private val ilssTvWelfarePolicyStandards: TextView =
        v.findViewById(R.id.ilssTvWelfarePolicyStandards)

    @SuppressLint("SetTextI18n")
    fun bind(ship: GDSTShip) {
        val flagPath = GDSTStorage.GDSTCountries?.first { x -> x.id == ship.vesselFlag }?.flag ?: ""
        val imagePath = "${IApiService.BASE_URL}$flagPath".replace("//", "/")

        if (imagePath.endsWith("svg")) {
            val requestBuilder: RequestBuilder<PictureDrawable> =
                Glide.with(ilssIvVesselFlag.context)
                    .`as`(PictureDrawable::class.java)
                    .transition(withCrossFade())
                    .listener(SvgSoftwareLayerSetter())
            val uri: Uri = Uri.parse(imagePath)
            requestBuilder.load(uri).into(ilssIvVesselFlag)
        } else {
            Glide.with(ilssIvVesselFlag.context).load(imagePath).into(ilssIvVesselFlag)
        }

        ilssTvVesselName.text = ship.vesselName
        ilssdIvIsTranshipmentIcon.setImageResource(if (ship.isTransshipment > 0) R.drawable._2side_active else R.drawable._2side_off)

        ilssTvVesselRegistration.text = "Số đăng ký tàu: " + ship.vesselID
        ilssTvVesselPublicRegistry.text = "PVRH: " + ship.vesselPublicRegistry
        ilssTvSatelliteTracking.text = "VMS: " + ship.satelliteTracking

        ilssTvFishingAuthorization.text = "Cấp phép kh.thác: " + ship.fishingAuthorization
        ilssTvHarvestCertification.text = "GP Kh.thác:" + ship.harvestCertification
        ilssTvTranshipmentAuthorization.text =
            "Cấp phép Tr.chuyển: " + ship.transshipmentAuthorization
        ilssTvHumanWelfarePolicy.text =
            "Chính sách phúc lợi con người: " + if (ship.humanWelfarePolicy > 0) "Có" else "Không"
        ilssTvWelfarePolicyStandards.text =
            "Tiêu chuẩn phúc lợi con người: " + if (ship.welfarePolicyStandards > 0) "Có" else "Không"
    }
}