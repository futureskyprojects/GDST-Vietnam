package vn.vistark.qrinfoscanner.ui.account_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account_info.*
import kotlinx.android.synthetic.main.component_bottom_nav.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import kotlinx.android.synthetic.main.component_top_search_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTProfileUpdateWithOutPassword
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTProfileUpdateWithPassword
import vn.vistark.qrinfoscanner.domain.api.IApiService
import vn.vistark.qrinfoscanner.domain.api.requests.technical_data.GetTechnicalDataBody
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTBottomBar
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTSmartBottomBar
import vn.vistark.qrinfoscanner.helpers.TopSearchBarHelper.Companion.initGDSTTopSearchBar
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert

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
            .load(crrProf.getImageAddress())
            .placeholder(R.drawable.holder)
            .into(aaiCivAvatar)
    }

    private fun initEvents() {
        aaiBtnSave.clickAnimate {
            saveProfile()
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

    private fun saveProfile() {
        val fullName = aaiEdtFullName.text.toString()
        val password = aaiEdtPassword.text.toString()

        if (fullName.isBlank() || fullName.isEmpty()) {
            aaiEdtFullName.error = "Vui lòng nhập họ và tên"
        }

        if (password.isNotEmpty() && password.length < 8) {
            aaiEdtPassword.error = "Mật khẩu phải đạt tối thiểu 8 ký tự"
        }

        val loading = this.showLoadingAlert()
        loading.show()
        GlobalScope.launch {
            try {
                val response =
                    (if (password.isNotEmpty())
                        ApiService.mAPIServices.postGDSTUpdateProfile(
                            (GDSTProfileUpdateWithPassword(
                                password,
                                fullName,
                                GDSTStorage.CurrentUser.image
                            ))
                        )
                    else
                        ApiService.mAPIServices.postGDSTUpdateProfile(
                            GDSTProfileUpdateWithOutPassword(
                                fullName,
                                GDSTStorage.CurrentUser.image
                            )
                        ))
                        .await()

                runOnUiThread { loading.cancel() }

                if (response == null)
                    throw Exception("Không phân dải được KQ trả về")

                GDSTStorage.CurrentUser.fullname = fullName

                if (password.isNotEmpty() && password.isNotBlank() && password.length >= 8) {
                    GDSTStorage.CurrentUser.password = password
                }

                runOnUiThread {
                    showAlertConfirm("Cập nhật thông tin tài khoản thành công")
                }
            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm("Cập nhật thông tin tài khoản chưa thành công")
                }
                e.printStackTrace()
            } finally {

            }
        }
    }
}