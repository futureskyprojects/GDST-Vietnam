package vn.vistark.qrinfoscanner.utils

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.ui.qr_scan.QrScanActivity
import vn.vistark.qrinfoscanner.utils.AnimUtils.Companion.scaleBounce

class FloatQuickScan {
    companion object {
        fun initialize(asiIvQuickScanIcon: ImageView, cfqsLnQuickScanBtn: View) {
            val context = asiIvQuickScanIcon.context
            Glide.with(context).asGif().load(R.raw.scan_qr_gif)
                .transform(CircleCrop()) // or bitmapTransform, whichever compiles
                .into(asiIvQuickScanIcon)
            cfqsLnQuickScanBtn.setOnClickListener {
                asiIvQuickScanIcon.scaleBounce {
                    val intent = Intent(context, QrScanActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}