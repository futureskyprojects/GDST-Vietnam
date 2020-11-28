package vn.vistark.qrinfoscanner.helpers

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.ui.statics_data.licenses_data.LicenseDataActivity
import vn.vistark.qrinfoscanner.ui.statics_data.vessel_data.VesselDataActivity
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate


class AlertHelper {
    companion object {
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