package vn.vistark.qrinfoscanner.helpers.alert_helper

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.ui.statics_data.licenses_data.LicenseDataActivity
import vn.vistark.qrinfoscanner.ui.statics_data.vessel_data.VesselDataActivity
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.slideDown
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.slideUp
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.From
import vn.vistark.qrinfoscanner.core.models.BaseMap
import vn.vistark.qrinfoscanner.core.models.country.response.Country
import vn.vistark.qrinfoscanner.core.models.fao.response.FAO
import vn.vistark.qrinfoscanner.core.models.organization.response.Organization
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.helpers.common.VistarkAdapter
import vn.vistark.qrinfoscanner.helpers.countries.CountryBindHolder
import vn.vistark.qrinfoscanner.helpers.fao.FaoBindHolder
import vn.vistark.qrinfoscanner.helpers.organization.OrganizationBindHolder
import java.util.*


class AlertHelper {
    companion object {
        fun TextView.showDatePicker(onResult: ((Date) -> Unit), date: Date? = null) {
            this.clickAnimate {
                val calendar = Calendar.getInstance()
                calendar.time = date ?: Date()
                val x = DatePickerDialog(
                    this.context,
                    DatePickerDialog.OnDateSetListener { v, year, month, dayOfMonth ->
                        val dateRes = Date().From(year, month, dayOfMonth)
                        this.text = dateRes.Format()
                        onResult.invoke(dateRes)
                    },
                    calendar.get(Calendar.YEAR)
                    , calendar.get(Calendar.MONTH)
                    , calendar.get(Calendar.DAY_OF_MONTH)
                )
                x.datePicker.init(
                    calendar.get(Calendar.YEAR)
                    , calendar.get(Calendar.MONTH)
                    , calendar.get(Calendar.DAY_OF_MONTH)
                ) { v, year, month, dayOfMonth ->
                    val dateRes = Date().From(year, month, dayOfMonth)
                    this.text = dateRes.Format()
                    onResult.invoke(dateRes)
                    x.cancel()
                }
                x.show()
            }
        }

        fun TextView.valueDialog(
            data: Array<BaseMap>?,
            dialogName: String,
            onResult: ((BaseMap?) -> Unit)
        ) {
            this.clickAnimate {
                (this.context as AppCompatActivity).showSelectBottomSheetAlert(
                    dialogName,
                    data ?: emptyArray(),
                    R.layout.item_layout_componet_text_only,
                    { t, v ->
                        val vz = v.findViewById<TextView>(R.id.ilctoTvContent)
                        vz.isSelected = true
                        vz.text = t.name
                        return@showSelectBottomSheetAlert vz
                    }
                ) { t ->
                    if (t != null) {
                        this.text = t.name
                        this.isSelected = true
                    }
                    onResult.invoke(t)
                }
            }
        }

        @SuppressLint("InflateParams")
        fun AppCompatActivity.showAlertConfirm(
            msg: String,
            confirm: (() -> Unit)? = null,
            cancel: (() -> Unit)? = null
        ) {

            val v = LayoutInflater.from(this).inflate(R.layout.alert_confirm, null)

            val acIvClose: ImageView = v.findViewById(R.id.acIvClose)
            val acTvContent: TextView = v.findViewById(R.id.acTvContent)
            val acBtnConfirm: Button = v.findViewById(R.id.acBtnConfirm)
            val acBtnCancel: Button = v.findViewById(R.id.acBtnCancel)


            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            acTvContent.text = msg

            acIvClose.clickAnimate {
                mAlertDialog.dismiss()
            }

            acBtnCancel.clickAnimate {
                mAlertDialog.dismiss()
                cancel?.invoke()
            }

            acBtnConfirm.clickAnimate {
                mAlertDialog.dismiss()
                confirm?.invoke()
            }

            if (confirm == null && cancel == null) {
                acBtnCancel.visibility = View.GONE
                acBtnConfirm.text = "Đóng"
            }
        }

        fun AppCompatActivity.showSelectLogicAlert(onCompleted: (Boolean?) -> Unit) {
            val v =
                LayoutInflater.from(this).inflate(R.layout.alert_select_logic_bottom_sheet, null)

            val context = v.context

            val aslbsRlMainLayout: RelativeLayout = v.findViewById(R.id.aslbsRlMainLayout)
            val asbsIvCloseBtn: ImageView = v.findViewById(R.id.asbsIvCloseBtn)
            val aslbsCvYes: CardView = v.findViewById(R.id.aslbsCvYes)
            val aslbsNo: CardView = v.findViewById(R.id.aslbsNo)


            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(true)

            aslbsRlMainLayout.slideUp()

            asbsIvCloseBtn.clickAnimate {
                aslbsRlMainLayout.slideDown {
                    onCompleted.invoke(null)
                    mAlertDialog.dismiss()
                }
            }

            aslbsCvYes.clickAnimate {
                aslbsRlMainLayout.slideDown {
                    onCompleted.invoke(true)
                    mAlertDialog.dismiss()
                }
            }

            aslbsNo.clickAnimate {
                aslbsRlMainLayout.slideDown {
                    onCompleted.invoke(false)
                    mAlertDialog.dismiss()
                }
            }

        }

        fun AppCompatActivity.showSelectStaticDataOptionAlert() {

            val v = LayoutInflater.from(this).inflate(R.layout.alert_select_static_data, null)

            val context = v.context

            val assdIvClose: ImageView = v.findViewById(R.id.assdIvClose)
            val assdCvVesselDataBtn: CardView = v.findViewById(R.id.assdCvVesselDataBtn)
            val assdCvLicenseBtn: CardView = v.findViewById(R.id.assdCvLicenseBtn)


            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(true)

            assdIvClose.clickAnimate {
                mAlertDialog.dismiss()
            }

            assdCvVesselDataBtn.clickAnimate {
                mAlertDialog.dismiss()
                val intent = Intent(context, VesselDataActivity::class.java)
                startActivity(intent)
            }

            assdCvLicenseBtn.clickAnimate {
                mAlertDialog.dismiss()
                val intent = Intent(context, LicenseDataActivity::class.java)
                startActivity(intent)
            }
        }

        fun AppCompatActivity.showLoadingAlert(): AlertDialog {

            val v = LayoutInflater.from(this).inflate(R.layout.alert_fish_loading, null)

            val context = v.context

            val aflIvLoadingGif: ImageView = v.findViewById(R.id.aflIvLoadingGif)


            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            Glide.with(context).asGif().load(R.raw.fish_loading)
                .transform(CircleCrop()) // or bitmapTransform, whichever compiles
                .into(aflIvLoadingGif)
            return mAlertDialog
        }

    }
}