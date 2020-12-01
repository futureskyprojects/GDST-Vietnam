package vn.vistark.qrinfoscanner.ui.traceable_object_information

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.TraceableObjectInformation
import vn.vistark.qrinfoscanner.core.extensions.NumberExtension.Companion.round
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format

class TOIViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val iltoiLnRoot: LinearLayout = v.findViewById(R.id.iltoiLnRoot)
    val iltoiIvDeleteIcon: ImageView = v.findViewById(R.id.iltoiIvDeleteIcon)

    private val iltoiTvId: TextView = v.findViewById(R.id.iltoiTvId)
    private val iltoiTvSpiceName: TextView = v.findViewById(R.id.iltoiTvSpiceName)
    private val iltoiTvProductForm: TextView = v.findViewById(R.id.iltoiTvProductForm)
    private val iltoiTvLinkingKDE: TextView = v.findViewById(R.id.iltoiTvLinkingKDE)
    private val iltoiTvWeight: TextView = v.findViewById(R.id.iltoiTvWeight)

    @SuppressLint("SetTextI18n")
    fun bind(toi: TraceableObjectInformation) {
        iltoiTvId.text = "#${toi.Id}"
        iltoiTvSpiceName.text = toi.fishData.viName + "(${toi.fishData.globalName})"
        iltoiTvSpiceName.isSelected = true
        iltoiTvProductForm.text = "Dạng sản phẩm: ${toi.productForm}"
        iltoiTvLinkingKDE.text = "KDE Liên kết: ${toi.linkingKDE}"
        iltoiTvWeight.text = "Khối lượng: ${toi.weightOrQuantity.round(2)} (${toi.unitOfMeasure})"
    }
}