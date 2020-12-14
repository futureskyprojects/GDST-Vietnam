package vn.vistark.qrinfoscanner.ui.account_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_account_info.*
import kotlinx.android.synthetic.main.component_bottom_nav.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTBottomBar
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTSmartBottomBar

class AccountInfoActivity : AppCompatActivity() {
    lateinit var listener: ViewTreeObserver.OnGlobalLayoutListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        initGDSTBottomBar(cbnBnvBottomNav, cbnBtnCenter, 2)
        btmNavLayout.initGDSTSmartBottomBar(aaiSvContent)

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