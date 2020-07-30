package vn.vistark.qrinfoscanner.components

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Display
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.R


class WhiteHeaderLogoComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    init {
        background = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(R.drawable.ic_header_image, null)
        } else {
            resources.getDrawable(R.drawable.ic_header_image)
        }

//        viewTreeObserver.addOnGlobalLayoutListener {
//            println("Chiều rộng là $width >>>>>>>>>>>>>>>>>>>")
//            layoutParams.height = (width * 0.4).toInt()
//        }
    }
}