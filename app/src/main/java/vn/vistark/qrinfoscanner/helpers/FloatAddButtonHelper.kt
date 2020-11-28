package vn.vistark.qrinfoscanner.helpers

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate

class FloatAddButtonHelper {
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