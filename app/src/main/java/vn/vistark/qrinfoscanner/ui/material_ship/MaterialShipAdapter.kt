package vn.vistark.qrinfoscanner.ui.material_ship

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.MaterialShip
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.interfaces.IClickable
import vn.vistark.qrinfoscanner.core.interfaces.IDeletable

class MaterialShipAdapter(private val materialships: ArrayList<MaterialShip>) :
    RecyclerView.Adapter<MaterialShipHolder>(), IClickable<MaterialShip>,
    IDeletable<MaterialShip> {

    override var onClick: ((MaterialShip) -> Unit)? = null
    override var onDelete: ((MaterialShip) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialShipHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_material_ship, parent, false)
        return MaterialShipHolder(v)
    }

    override fun getItemCount(): Int {
        return materialships.count()
    }

    override fun onBindViewHolder(holder: MaterialShipHolder, position: Int) {
        val materialBatch = materialships[position]
        holder.bind(materialBatch)

        holder.ilmsdIvDeleteIcon.clickAnimate {
            onDelete?.invoke(materialBatch)
        }
        holder.ilmsLnRoot.clickAnimate {
            onClick?.invoke(materialBatch)
        }
    }

}