package vn.vistark.qrinfoscanner.helpers.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate

class VistarkAdapter<T>(
    val collection: Array<T>,
    val layoutId: Int,
    val bind: ((T, TView: View) -> View)
) : RecyclerView.Adapter<VistarkViewHolder>() {

    var onItemClicked: ((T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistarkViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return VistarkViewHolder(v)
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: VistarkViewHolder, position: Int) {
        val T = collection[position]
        val rootView = bind.invoke(T, holder.v)
        rootView.clickAnimate {
            onItemClicked?.invoke(T)
        }
    }
}