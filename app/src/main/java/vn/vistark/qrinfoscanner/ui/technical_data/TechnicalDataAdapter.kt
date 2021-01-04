package vn.vistark.qrinfoscanner.ui.technical_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.interfaces.IClickable
import vn.vistark.qrinfoscanner.core.interfaces.IDeletable
import vn.vistark.qrinfoscanner.domain.entities.GDSTTechnicalData

class TechnicalDataAdapter(private val teachnicalDatas: ArrayList<GDSTTechnicalData>) :
    RecyclerView.Adapter<TechnicalDataViewHolder>(), IClickable<GDSTTechnicalData>,
    IDeletable<GDSTTechnicalData> {

    override var onClick: ((GDSTTechnicalData) -> Unit)? = null
    override var onEdit: ((GDSTTechnicalData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechnicalDataViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_technical_data, parent, false)
        return TechnicalDataViewHolder(v)
    }

    override fun getItemCount(): Int {
        return teachnicalDatas.count()
    }

    override fun onBindViewHolder(holder: TechnicalDataViewHolder, position: Int) {
        val teachnicalData = teachnicalDatas[position]
        holder.bind(teachnicalData)

        holder.iltdIvEditIcon.clickAnimate {
            onEdit?.invoke(teachnicalData)
        }
        holder.iltdLnRoot.clickAnimate {
            onClick?.invoke(teachnicalData)
        }
    }

}