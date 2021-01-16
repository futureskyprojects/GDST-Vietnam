package vn.vistark.qrinfoscanner.domain.constants

import android.util.Log
import vn.vistark.qrinfoscanner.core.constants.AppStorageManager

class Config {
    companion object {
        const val defaultPassword = "123456"
        const val qrPath = "QRcode/"
        const val padSize = 3
        const val maxSplashScreenWait: Long = 30000L
        const val isDebug = false

        var LanguageCode: String = "vi"
            get() = AppStorageManager.get("LANGUAGE_CODE") ?: field
            set(value) {
                AppStorageManager.update("LANGUAGE_CODE", value)
                field = value
            }

        fun showLog(message: String) {
            if (!isDebug)
                return
            Log.w("[VISTARK.ME]", message)
        }
    }
}