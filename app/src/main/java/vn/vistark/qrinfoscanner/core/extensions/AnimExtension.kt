package vn.vistark.qrinfoscanner.core.extensions

import android.view.animation.Animation

class AnimExtension {
    companion object {
        fun Animation.doOnEnd(onEnd: () -> Unit) {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    onEnd.invoke()
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }
            })
        }
    }
}