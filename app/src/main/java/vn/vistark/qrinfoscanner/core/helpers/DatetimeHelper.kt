package vn.vistark.qrinfoscanner.core.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DatetimeHelper {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun Date.format(format: String = "dd-MM-yyyy"): String {
            val formatter = SimpleDateFormat(format)
            return formatter.format(this)
        }

        @SuppressLint("SimpleDateFormat")
        fun DateFrom(dateStr: String, format: String = "dd-MM-yyyy"): Date? {
            val parser = SimpleDateFormat(format)
            return parser.parse("dateStr")
        }
    }
}