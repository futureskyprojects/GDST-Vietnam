package vn.vistark.qrinfoscanner.ui.technical_data

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.AppStorageManager
import vn.vistark.qrinfoscanner.domain.mock_entities.TechnicalData
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTTechnicalDataDTO
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTTechnicalData

class TechnicalDataViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val iltdLnRoot: LinearLayout = v.findViewById(R.id.iltdLnRoot)
    val iltdIvDeleteIcon: ImageView = v.findViewById(R.id.iltdIvDeleteIcon)

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
        iltdTvEventId.text = "#${technicalData.eventId}"
        iltdTvEventDateAndGeo.text =
            "${technicalData.eventDate} (${GDSTStorage.GDSTLocations?.firstOrNull { x -> x.id == technicalData.geolocationId } ?: "<Rỗng>"})"
        iltdTvLinkingKDE.text = "KDE Liên kết: ${technicalData.KDE}"
        iltdTvProductForm.text =
            "Dạng sản phẩm: ${GDSTStorage.GDSTProductForms?.firstOrNull { x -> x.id == technicalData.productFormId } ?: "<Rỗng>"}"
        iltdTvTotalSpices.text = "Tổng số loài: 0"
        iltdTvTotalSpiceQuantify.text = "Tổng khối lượng: 0 (KG)"

        if (technicalData.isTrasshipment == 1) {
            iltdLnTranshipmentRoot.visibility = View.VISIBLE
            iltdTvTranshipmentDate.text = "Ngày giờ chuyển tải: ${technicalData.dateTransshipment}"
            iltdTvTranshipmentLocation.text =
                "Vị trí chuyển tải: ${GDSTStorage.GDSTLocations?.firstOrNull { x -> x.id == technicalData.loactionTransshipmentId } ?: "<Rỗng>"}"
        }
    }


}