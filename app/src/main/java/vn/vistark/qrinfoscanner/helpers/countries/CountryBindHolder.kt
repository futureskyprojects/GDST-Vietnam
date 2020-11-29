package vn.vistark.qrinfoscanner.helpers.countries

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
import java.util.*

class CountryBindHolder {
    companion object {
        @SuppressLint("SetTextI18n")
        fun bind(country: Country, v: View): View {
            val ilcaLnRoot: LinearLayout = v.findViewById(R.id.ilcaLnRoot)
            val ilcaIvFlag: ImageView = v.findViewById(R.id.ilcaIvFlag)
            val ilcaTvCountryName: TextView = v.findViewById(R.id.ilcaTvCountryName)

            ilcaTvCountryName.text = "${country.name} (${country.alpha2})"
            Glide.with(ilcaIvFlag)
                .load("${RuntimeStorage.Countries?.reposeUrl}${country.alpha2.toLowerCase(Locale.ROOT)}.png")
                .into(ilcaIvFlag)

            return ilcaLnRoot
        }
    }
}