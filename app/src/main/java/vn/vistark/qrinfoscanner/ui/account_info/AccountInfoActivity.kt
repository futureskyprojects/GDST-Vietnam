package vn.vistark.qrinfoscanner.ui.account_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_account_info.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard

class AccountInfoActivity : AppCompatActivity() {
    lateinit var listener: ViewTreeObserver.OnGlobalLayoutListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)
//        initCustomToolbar()
        masterLayout.setOnClickListener { HideKeyboard() }
        initEvents()
    }

//    private fun initCustomToolbar() {
//        listener = ViewTreeObserver.OnGlobalLayoutListener {
//            val marginLayoutParams = rlCustomToolbar.layoutParams as LinearLayout.LayoutParams
//            marginLayoutParams.setMargins(
//                0,
//                DimensionUtils.statusBarHeight(this@AccountInfoActivity),
//                0,
//                0
//            )
//            rlCustomToolbar.layoutParams = marginLayoutParams
//            rlCustomToolbar.viewTreeObserver.removeOnGlobalLayoutListener(listener)
//        }
//        rlCustomToolbar.viewTreeObserver.addOnGlobalLayoutListener(listener)
//    }

    private fun initEvents() {
        aaiBtnSave.clickAnimate {}
        aaiCivAvatar.clickAnimate { }
        aaiBtnCancel.clickAnimate {
            onBackPressed()
        }
//        lnBackButton.setOnClickListener {
//            onBackPressed()
//        }
    }
}