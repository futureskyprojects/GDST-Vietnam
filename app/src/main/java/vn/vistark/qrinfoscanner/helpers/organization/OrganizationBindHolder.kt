package vn.vistark.qrinfoscanner.helpers.organization

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
import vn.vistark.qrinfoscanner.core.models.organization.response.Organization
import java.util.*

class OrganizationBindHolder {
    companion object {
        @SuppressLint("SetTextI18n")
        fun bind(organization: Organization, v: View): View {
            val ilcoaLnRoot: LinearLayout = v.findViewById(R.id.ilcoaLnRoot)
            val ilcoaIv: ImageView = v.findViewById(R.id.ilcoaIv)
            val ilcoaTv: TextView = v.findViewById(R.id.ilcoaTv)

            ilcoaTv.text = organization.name

            return ilcoaLnRoot
        }
    }
}