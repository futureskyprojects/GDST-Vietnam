package vn.vistark.qrinfoscanner.domain.constants

import android.util.Log

class Config {
    companion object {
        const val defaultPassword = "123456"
        const val qrPath = "QRcode/"
        const val padSize = 3
        const val maxSplashScreenWait: Long = 30000L
        const val isDebug = true

        fun showLog(message: String) {
            Log.w("[VISTARK.ME]", message)
        }
    }
}