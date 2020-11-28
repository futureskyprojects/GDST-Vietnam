package vn.vistark.qrinfoscanner.core.extensions

import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.helpers.AlertHelper.Companion.showLoadingAlert
import java.util.*

class ViewExtension {
    companion object {
        fun View.clickAnimate(f: () -> Unit) {
            val anim = AnimationUtils.loadAnimation(this.context, R.anim.scale_bounce)
            this.setOnClickListener {
                this.startAnimation(anim)
                this.postDelayed(f, anim.duration / 2)
            }
        }

        fun AppCompatActivity.delayAction(f: () -> Unit) {
            val loading = this.showLoadingAlert()
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        loading.dismiss()
                        f.invoke()
                    }
                }
            }, 1000)
        }
    }
}