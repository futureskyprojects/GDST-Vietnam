package vn.vistark.qrinfoscanner.ui.statics_data.licenses_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.entities.VesselData

class LicenseDataAdapter(private val licenses: ArrayList<CertificationAndLicense>) :
    RecyclerView.Adapter<LicenseDataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseDataViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_certification_and_license, parent, false)
        return LicenseDataViewHolder(v)
    }

    override fun getItemCount(): Int {
        return licenses.count()
    }

    override fun onBindViewHolder(holder: LicenseDataViewHolder, position: Int) {
        val vesselData = licenses[position]
        holder.bind(vesselData)
    }

}