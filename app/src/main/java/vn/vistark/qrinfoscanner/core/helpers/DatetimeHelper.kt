package vn.vistark.qrinfoscanner.core.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DatetimeHelper {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun Date.Format(format: String = "dd-MM-yyyy"): String {
            val formatter = SimpleDateFormat(format)
            return formatter.format(this)
        }

        fun Date.From(year: Int, month: Int, dayOfMonth: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            return calendar.time
        }
    }
}