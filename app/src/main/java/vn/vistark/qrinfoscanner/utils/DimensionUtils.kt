package vn.vistark.qrinfoscanner.utils

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DimensionUtils {
    companion object {
        fun dpToPx(context: Context, dp: Float): Float {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        fun pxToDp(context: Context, px: Float): Float {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            return (px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        fun statusBarHeight(context: Context): Int {
            val rectangle = Rect()
            val window: Window = (context as AppCompatActivity).window
            window.decorView.getWindowVisibleDisplayFrame(rectangle)
            return rectangle.top
        }
    }
}