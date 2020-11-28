package vn.vistark.qrinfoscanner.core.extensions

import android.view.View
import android.view.animation.AnimationUtils
import vn.vistark.qrinfoscanner.R

class ViewExtension {
    companion object {
        fun View.clickAnimate(f: () -> Unit) {
            val anim = AnimationUtils.loadAnimation(this.context, R.anim.scale_bounce)
            this.setOnClickListener {
                this.startAnimation(anim)
                this.postDelayed(f, anim.duration / 2)
            }
        }
    }
}