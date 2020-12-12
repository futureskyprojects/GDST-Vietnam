package vn.vistark.qrinfoscanner.helpers.fao

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_models.fao.response.FAO

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