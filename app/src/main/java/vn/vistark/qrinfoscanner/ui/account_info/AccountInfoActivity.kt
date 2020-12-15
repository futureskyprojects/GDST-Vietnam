package vn.vistark.qrinfoscanner.ui.account_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account_info.*
import kotlinx.android.synthetic.main.component_bottom_nav.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.domain.api.IApiService
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTBottomBar
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTSmartBottomBar

class AccountInfoActivity : AppCompatActivity() {

    val crrProf = GDSTStorage.CurrentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        initGDSTBottomBar(cbnBnvBottomNav, cbnBtnCenter, 2)
        btmNavLayout.initGDSTSmartBottomBar(aaiSvContent)

        masterLayout.setOnClickListener { HideKeyboard() }

        initData()

        initEvents()
    }

    private fun initData() {
        aaiEdtUsername.setText(GDSTStorage.CurrentUser.username)
        aaiEdtCompanyName.setText(GDSTStorage.CurrentCompany?.companyname ?: "")
        aaiEdtFullName.setText(crrProf.fullname)

        Glide.with(this)
            .load(
                (IApiService.BASE_URL + crrProf.image)
                    .replace("//", "/")
            )
            .placeholder(R.drawable.holder)
            .into(aaiCivAvatar)
    }

    private fun initEvents() {
        aaiBtnSave.clickAnimate {
            Toast.makeText(this, "Tính năng chưa được hỗ trợ", Toast.LENGTH_SHORT).show()
        }
        aaiCivAvatar.clickAnimate {
            Toast.makeText(
                this,
                "Tính năng chưa được hỗ trợ",
                Toast.LENGTH_SHORT
            ).show()
        }
        aaiBtnCancel.clickAnimate {
            onBackPressed()
        }
    }
}