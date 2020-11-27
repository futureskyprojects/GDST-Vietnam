package vn.vistark.qrinfoscanner.ui.material_batch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.RawMaterialBatch
import vn.vistark.qrinfoscanner.core.entities.Shipment

class MaterialBatchAdapter(private val materialBatchs: ArrayList<RawMaterialBatch>) :
    RecyclerView.Adapter<MaterialBatchHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialBatchHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_material_batch, parent, false)
        return MaterialBatchHolder(v)
    }

    override fun getItemCount(): Int {
        return materialBatchs.count()
    }

    override fun onBindViewHolder(holder: MaterialBatchHolder, position: Int) {
        val materialBatch = materialBatchs[position]
        holder.bind(materialBatch)
    }

}