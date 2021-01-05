package vn.vistark.qrinfoscanner.ui.technical_data

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTTechnicalData

class TechnicalDataViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val iltdLnRoot: LinearLayout = v.findViewById(R.id.iltdLnRoot)
    val iltdIvEditIcon: ImageView = v.findViewById(R.id.iltdIvEditIcon)

    val iltdLnTranshipmentRoot: LinearLayout = v.findViewById(R.id.iltdLnTranshipmentRoot)

    val iltdTvEventId: TextView = v.findViewById(R.id.iltdTvEventId)
    val iltdTvEventDateAndGeo: TextView = v.findViewById(R.id.iltdTvEventDateAndGeo)
    val iltdTvLinkingKDE: TextView = v.findViewById(R.id.iltdTvLinkingKDE)
    val iltdTvProductForm: TextView = v.findViewById(R.id.iltdTvProductForm)
    val iltdTvTotalSpices: TextView = v.findViewById(R.id.iltdTvTotalSpices)
    val iltdTvTotalSpiceQuantify: TextView = v.findViewById(R.id.iltdTvTotalSpiceQuantify)
    val iltdTvTranshipmentDate: TextView = v.findViewById(R.id.iltdTvTranshipmentDate)
    val iltdTvTranshipmentLocation: TextView = v.findViewById(R.id.iltdTvTranshipmentLocation)

    @SuppressLint("SetTextI18n")
    fun bind(technicalData: GDSTTechnicalData) {
        iltdTvEventId.text = "#${technicalData.id}"
        iltdTvEventDateAndGeo.text =
            "${
                technicalData.eventDate.split(" ").first()
            } (${
                GDSTStorage.GDSTLocations?.firstOrNull { x -> x.id == technicalData.geolocationId }?.title ?: iltdTvEventDateAndGeo.context.getString(
                    R.string.empty
                )
            })"
        iltdTvLinkingKDE.text =
            iltdTvLinkingKDE.context.getString(R.string.kdelk) + technicalData.KDE
        iltdTvProductForm.text =
            iltdTvProductForm.context.getString(R.string.dsp) + ": " + (GDSTStorage.GDSTProductForms?.firstOrNull { x -> x.id == technicalData.productFormId }?.title
                ?: iltdTvEventDateAndGeo.context.getString(
                    R.string.empty
                ))
        iltdTvTotalSpices.text =
            iltdTvTotalSpices.context.getString(R.string.tsl) + technicalData.informationFishingUpObject.count { x -> x.quantification > 0 }
        iltdTvTotalSpiceQuantify.text =
            iltdTvTotalSpiceQuantify.context.getString(R.string.tkl) + technicalData.informationFishingUpObject.map { it.quantification }
                .sum() + " (" + iltdTvTotalSpiceQuantify.context.getString(R.string.kg) + ")"

        if (technicalData.isTrasshipment == 1) {
            iltdLnTranshipmentRoot.visibility = View.VISIBLE
            iltdTvTranshipmentDate.text =
                iltdTvTranshipmentDate.context.getString(R.string.ngct) + technicalData.dateTransshipment
            iltdTvTranshipmentLocation.text =
                iltdTvTranshipmentLocation.context.getString(R.string.vtct) + (GDSTStorage.GDSTLocations?.firstOrNull { x -> x.id == technicalData.loactionTransshipmentId }?.title
                    ?: iltdTvEventDateAndGeo.context.getString(
                        R.string.empty
                    ))
        }
    }


}