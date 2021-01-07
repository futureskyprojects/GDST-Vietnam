package vn.vistark.qrinfoscanner.ui.traceable_object_information

import android.annotation.SuppressLint
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account_info.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.TraceableObjectInformation
import vn.vistark.qrinfoscanner.core.extensions.NumberExtension.Companion.round
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTInfomationFishUp

class TOIViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val iltoiLnRoot: LinearLayout = v.findViewById(R.id.iltoiLnRoot)
    val iltoiIvSpecialPhoto: ImageView = v.findViewById(R.id.iltoiIvSpecialPhoto)

    private val iltoiTvSpecialName: TextView = v.findViewById(R.id.iltoiTvSpecialName)
    val iltoiEdtSpecialWeight: EditText = v.findViewById(R.id.iltoiEdtSpecialWeight)
    val iltoiTvSpecialWeightLabel: TextView = v.findViewById(R.id.iltoiTvSpecialWeightLabel)

    @SuppressLint("SetTextI18n")
    fun bind(toi: GDSTInfomationFishUp) {
        iltoiLnRoot.setOnClickListener {
            iltoiLnRoot.HideKeyboard()
        }
        var quanti = ""

        if (toi.quantification.toInt().toFloat() == toi.quantification)
            quanti = toi.quantification.toInt().toString()
        else
            quanti = toi.quantification.toString()

        if (toi.quantification <= 0)
            quanti = ""
        iltoiEdtSpecialWeight.setText(quanti)
        val gottedSpecial = GDSTStorage.GDSTSpecies?.firstOrNull { x -> x.id == toi.spiceId }
        iltoiTvSpecialWeightLabel.text =
            iltoiTvSpecialWeightLabel.context.getString(R.string.sl) + " (" + toi.unit + ")"
        if (gottedSpecial != null) {
            Glide.with(iltoiIvSpecialPhoto.context)
                .load(gottedSpecial.TrueImage())
                .placeholder(R.drawable.holder)
                .into(iltoiIvSpecialPhoto)
            iltoiTvSpecialName.text = gottedSpecial.name
        } else {
            iltoiTvSpecialName.text =
                iltoiTvSpecialName.context.getString(R.string.kttlm) + " #${toi.spiceId}"
        }
        iltoiTvSpecialName.isSelected = true
    }
}