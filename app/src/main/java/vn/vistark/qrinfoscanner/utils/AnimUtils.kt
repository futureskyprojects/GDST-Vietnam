package vn.vistark.qrinfoscanner.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import vn.vistark.qrinfoscanner.R

class AnimUtils {
    companion object {
        fun View.scaleBounce(f: () -> Unit) {
            val anim = AnimationUtils.loadAnimation(this.context, R.anim.scale_bounce)
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                }

                override fun onAnimationStart(p0: Animation?) {
                }
            })
            this.startAnimation(anim)
            this.postDelayed(f, anim.duration / 2)
        }
    }
}