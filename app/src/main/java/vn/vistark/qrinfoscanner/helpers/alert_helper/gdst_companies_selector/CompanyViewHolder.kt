package vn.vistark.qrinfoscanner.helpers.alert_helper.gdst_companies_selector

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.entities.GDSTCompany

class CompanyViewHolder {
    companion object {
        fun bind(company: GDSTCompany, v: View): View {
            val ilcRlRoot: RelativeLayout = v.findViewById(R.id.ilcRlRoot)
            val ilcIvCompanyLogo: ImageView = v.findViewById(R.id.ilcIvCompanyLogo)
            val ilcTvState: TextView = v.findViewById(R.id.ilcTvState)
            val ilcTvName: TextView = v.findViewById(R.id.ilcTvName)
            val ilcTvOwner: TextView = v.findViewById(R.id.ilcTvOwner)
            val ilcTvAddress: TextView = v.findViewById(R.id.ilcTvAddress)
            val ilcTvWebsite: TextView = v.findViewById(R.id.ilcTvWebsite)

            Glide.with(ilcIvCompanyLogo.context)
                .load(company.logo)
                .placeholder(R.drawable.no_image)
                .into(ilcIvCompanyLogo)

            ilcTvState.text = if (company.status == 1) "Hoạt động" else "Tạm ngưng"

            ilcTvName.text = company.companyname
            ilcTvName.isSelected = true

            ilcTvOwner.text = company.ownername
            ilcTvOwner.isSelected = true

            ilcTvAddress.text = company.address
            ilcTvAddress.isSelected = true

            ilcTvWebsite.text = company.website
            ilcTvWebsite.isSelected = true

            return ilcRlRoot
        }
    }

}