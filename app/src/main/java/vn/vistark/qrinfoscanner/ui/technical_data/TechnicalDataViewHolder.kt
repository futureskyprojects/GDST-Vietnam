package vn.vistark.qrinfoscanner.ui.technical_data

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.TechnicalData
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format

class TechnicalDataViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val iltdLnRoot: LinearLayout = v.findViewById(R.id.iltdLnRoot)
    val iltdIvDeleteIcon: ImageView = v.findViewById(R.id.iltdIvDeleteIcon)

    private val iltdTvProductOwnership: TextView = v.findViewById(R.id.iltdTvProductOwnership)
    private val iltdTvEventDate: TextView = v.findViewById(R.id.iltdTvEventDate)
    private val iltdTvEventReadPoint: TextView = v.findViewById(R.id.iltdTvEventReadPoint)
    private val iltdTvInfomationProvider: TextView = v.findViewById(R.id.iltdTvInfomationProvider)
    private val iltdTvEventId: TextView = v.findViewById(R.id.iltdTvEventId)

    @SuppressLint("SetTextI18n")
    fun bind(technicalData: TechnicalData) {
        iltdTvEventId.text = "#${technicalData.eventId}"
        iltdTvProductOwnership.text = technicalData.productOwnerShip
        iltdTvEventDate.text = "Thời điểm mẻ lưới: " +
                technicalData.eventDate.Format("hh:mm dd-MM-yyyy") + " (${technicalData.timeZone})"
        iltdTvEventReadPoint.text = "Vị trí mẻ lưới: " + technicalData.eventReadPoint
        iltdTvInfomationProvider.text = "Thông tin từ: " + technicalData.informationProvider
    }


}