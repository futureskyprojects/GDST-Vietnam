package vn.vistark.qrinfoscanner.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.utils.AnimUtils.Companion.clickAnimate


class AlertConfirmUtils {
    companion object {
        @SuppressLint("InflateParams")
        fun AppCompatActivity.showAlertConfirm(
            msg: String,
            confirm: (() -> Unit),
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
                confirm.invoke()

            }
        }

        fun AppCompatActivity.showSelectStaticDataOptionAlert() {

            val v = LayoutInflater.from(this).inflate(R.layout.alert_select_static_data, null)

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
            }

            assdCvLicenseBtn.clickAnimate {
                mAlertDialog.dismiss()
            }
        }
    }
}