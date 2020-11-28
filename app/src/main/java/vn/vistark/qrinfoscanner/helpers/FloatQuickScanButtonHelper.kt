package vn.vistark.qrinfoscanner.helpers

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.ui.qr_scan.QrScanActivity
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate

class FloatQuickScanButtonHelper {
    companion object {
        fun initialize(asiIvQuickScanIcon: ImageView, cfqsLnQuickScanBtn: View) {
            val context = asiIvQuickScanIcon.context
            Glide.with(context).asGif().load(R.raw.scan_qr_gif)
                .transform(CircleCrop()) // or bitmapTransform, whichever compiles
                .into(asiIvQuickScanIcon)
            asiIvQuickScanIcon.clickAnimate {
                val intent = Intent(context, QrScanActivity::class.java)
                context.startActivity(intent)
            }
            cfqsLnQuickScanBtn.setOnClickListener {
                asiIvQuickScanIcon.performClick()
            }
        }
    }
}