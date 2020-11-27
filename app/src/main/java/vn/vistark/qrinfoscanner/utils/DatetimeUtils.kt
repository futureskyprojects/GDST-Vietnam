package vn.vistark.qrinfoscanner.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class DatetimeUtils {
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