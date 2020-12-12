package vn.vistark.qrinfoscanner.ui.traceable_object_information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.TraceableObjectInformation
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.interfaces.IClickable
import vn.vistark.qrinfoscanner.core.interfaces.IDeletable

class TOIAdapter(private val tois: ArrayList<TraceableObjectInformation>) :
    RecyclerView.Adapter<TOIViewHolder>(), IClickable<TraceableObjectInformation>,
    IDeletable<TraceableObjectInformation> {

    override var onClick: ((TraceableObjectInformation) -> Unit)? = null
    override var onDelete: ((TraceableObjectInformation) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TOIViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_traceable_object_information, parent, false)
        return TOIViewHolder(v)
    }

    override fun getItemCount(): Int {
        return tois.count()
    }

    override fun onBindViewHolder(holder: TOIViewHolder, position: Int) {
        val toi = tois[position]
        holder.bind(toi)

        holder.iltoiIvDeleteIcon.clickAnimate {
            onDelete?.invoke(toi)
        }
        holder.iltoiLnRoot.clickAnimate {
            onClick?.invoke(toi)
        }
    }

}