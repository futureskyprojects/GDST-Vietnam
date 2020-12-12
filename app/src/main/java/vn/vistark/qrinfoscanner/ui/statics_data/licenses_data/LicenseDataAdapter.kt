package vn.vistark.qrinfoscanner.ui.statics_data.licenses_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.interfaces.IClickable
import vn.vistark.qrinfoscanner.core.interfaces.IDeletable

class LicenseDataAdapter(private val licenses: ArrayList<CertificationAndLicense>) :
    RecyclerView.Adapter<LicenseDataViewHolder>(), IClickable<CertificationAndLicense>,
    IDeletable<CertificationAndLicense> {

    override var onClick: ((CertificationAndLicense) -> Unit)? = null
    override var onDelete: ((CertificationAndLicense) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseDataViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_certification_and_license, parent, false)
        return LicenseDataViewHolder(v)
    }

    override fun getItemCount(): Int {
        return licenses.count()
    }

    override fun onBindViewHolder(holder: LicenseDataViewHolder, position: Int) {
        val license = licenses[position]
        holder.bind(license)
        holder.ilcalLnRoot.clickAnimate {
            onClick?.invoke(license)
        }

        holder.ilcalIvDeleteIcon.clickAnimate {
            onDelete?.invoke(license)
        }
    }

}