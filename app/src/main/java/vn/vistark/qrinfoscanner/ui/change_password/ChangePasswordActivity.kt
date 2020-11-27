package vn.vistark.qrinfoscanner.ui.change_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_account_info.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.utils.DimensionUtils

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var listener: ViewTreeObserver.OnGlobalLayoutListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

//        initCustomToolbar()
        initEvents()
    }

//    private fun initCustomToolbar() {
//        listener = ViewTreeObserver.OnGlobalLayoutListener {
//            val marginLayoutParams = rlCustomToolbar.layoutParams as LinearLayout.LayoutParams
//            marginLayoutParams.setMargins(
//                0,
//                DimensionUtils.statusBarHeight(this),
//                0,
//                0
//            )
//            rlCustomToolbar.layoutParams = marginLayoutParams
//            rlCustomToolbar.viewTreeObserver.removeOnGlobalLayoutListener(listener)
//        }
//        rlCustomToolbar.viewTreeObserver.addOnGlobalLayoutListener(listener)
//    }

    private fun initEvents() {
//        lnBackButton.setOnClickListener {
//            onBackPressed()
//        }
    }
}