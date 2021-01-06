package vn.vistark.qrinfoscanner.core.extensions

import android.icu.text.SimpleDateFormat
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.From
import vn.vistark.qrinfoscanner.domain.constants.Config.Companion.showLog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StringExtension {
    companion object {
        fun String.isSameWith(s: String): Boolean {
            return (contains(s) || s.contains(this) || s == this)
        }

        fun String.ToYMDDate(): Date? {
            if (this.isEmpty() || this.isBlank())
                return null
            val temp = this.split(" ").first().replace("/", "-").replace(".", "-").replace(" ", "")
            showLog(temp)
            val params = temp.split("-")
            try {
                if (params.size < 3)
                    throw Exception("Thiếu tham số")
                return Date().From(params[0].toInt(), params[1].toInt() - 1, params[2].toInt())
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }
}