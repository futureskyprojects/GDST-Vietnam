package vn.vistark.qrinfoscanner.utils

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.ui.qr_scan.QrScanActivity
import vn.vistark.qrinfoscanner.utils.AnimUtils.Companion.clickAnimate

class FloatAdd {
    companion object {
        fun initialize(cfabIvIcon: ImageView, cfabLnAddBtn: View, f: (() -> Unit)) {
            val context = cfabIvIcon.context
            Glide.with(context).asGif().load(R.raw.add_animate)
                .transform(CircleCrop()) // or bitmapTransform, whichever compiles
                .into(cfabIvIcon)
            cfabIvIcon.clickAnimate {
                f.invoke()
            }
            cfabLnAddBtn.setOnClickListener {
                cfabIvIcon.performClick()
            }
        }
    }
}