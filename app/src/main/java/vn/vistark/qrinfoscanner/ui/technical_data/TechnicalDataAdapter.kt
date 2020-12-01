package vn.vistark.qrinfoscanner.ui.technical_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.TechnicalData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.interfaces.IClickable
import vn.vistark.qrinfoscanner.core.interfaces.IDeletable

class TechnicalDataAdapter(private val teachnicalDatas: ArrayList<TechnicalData>) :
    RecyclerView.Adapter<TechnicalDataViewHolder>(), IClickable<TechnicalData>,
    IDeletable<TechnicalData> {

    override var onClick: ((TechnicalData) -> Unit)? = null
    override var onDelete: ((TechnicalData) -> Unit)? = null

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

        holder.iltdIvDeleteIcon.clickAnimate {
            onDelete?.invoke(teachnicalData)
        }
        holder.iltdLnRoot.clickAnimate {
            onClick?.invoke(teachnicalData)
        }
    }

}