package vn.vistark.qrinfoscanner.helpers.fao

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.models.country.response.Country
import vn.vistark.qrinfoscanner.core.models.fao.response.FAO
import java.util.*

class FaoBindHolder {
    companion object {
        @SuppressLint("SetTextI18n")
        fun bind(fao: FAO, v: View): View {
            val ilfaLnRoot: LinearLayout = v.findViewById(R.id.ilfaLnRoot)
            val ilfaTvCode: TextView = v.findViewById(R.id.ilfaTvCode)
            val ilfaTvName: TextView = v.findViewById(R.id.ilfaTvName)

            ilfaTvCode.text = "FAO${fao.code}"
            ilfaTvName.text = fao.name

            return ilfaLnRoot
        }
    }
}