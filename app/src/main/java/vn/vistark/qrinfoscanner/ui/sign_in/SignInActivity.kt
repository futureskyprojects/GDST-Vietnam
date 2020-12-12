package vn.vistark.qrinfoscanner.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_account_info.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.masterLayout
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.api.AuthIntercepter
import vn.vistark.qrinfoscanner.core.extensions.Authentication.Companion.isAuthenticated
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.afterChanged
import vn.vistark.qrinfoscanner.domain.mock_entities.Enterprise
import vn.vistark.qrinfoscanner.ui.home.HomeActivity
import vn.vistark.qrinfoscanner.ui.sign_up.SignUpActivity
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTUserLoginDTO
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTUserProfile
import vn.vistark.qrinfoscanner.domain.mock_models.enterprise.request.EnterpriseLogin
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.FloatQuickScanButtonHelper
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import java.lang.Exception

class SignInActivity : AppCompatActivity() {

    companion object {
        var SIA: SignInActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        FloatQuickScanButtonHelper.initialize(asiIvQuickScanIcon, cfqsLnQuickScanBtn)

        btnSignIn.clickAnimate {
            if (!validateData())
                return@clickAnimate

            val intent = Intent(this, HomeActivity::class.java)

            val dto = GDSTUserLoginDTO()
            dto.username = asiEdtUsername.text.toString().trim()
            dto.password = asiEdtPassword.text.toString()

            val loading = this.showLoadingAlert()
            loading.show()

            GlobalScope.launch {
                try {
                    val response = ApiService.mAPIServices.postGDSTLogin(dto).await()
                    runOnUiThread { loading.cancel() }
                    if (response == null)
                        throw Exception("Không có KQ Trả về")

                    AuthIntercepter.CurrentToken = response.accessToken
                    AuthIntercepter.CurrentTokenType = response.tokenType

                    if (isAuthenticated()) {
                        GDSTStorage.CurrentUser = GDSTUserProfile(
                            -1,
                            dto.username,
                            -1,
                            dto.password,
                            ""
                        )
                        startActivity(intent)
                        finish()
                    } else {
                        throw Exception("Nhận được KQ đăng nhập OK nhưng vẫn k login zô đc")
                    }

                } catch (http: HttpException) {
                    runOnUiThread { loading.cancel() }
                    if (http.response().code() == 401) {
                        runOnUiThread {
                            showAlertConfirm("Tên tài khoản hoặc mật khẩu không đúng")
                        }
                    } else throw Exception("Mã HTTP RESPONSE không xác nhận được")
                } catch (e: Exception) {
                    runOnUiThread { loading.cancel() }
                    runOnUiThread {
                        showAlertConfirm("Đăng nhập không thành công, vui lòng thử lại")
                    }
                    e.printStackTrace()
                }
            }
        }

        tvSignUp.clickAnimate {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        asiEdtPassword.afterChanged {
            asiTvErrorMsg.text = ""
            asiTvErrorMsg.visibility = View.GONE
        }

        asiEdtUsername.afterChanged {
            asiTvErrorMsg.text = ""
            asiTvErrorMsg.visibility = View.GONE
        }


        SIA = this

        masterLayout.setOnClickListener { HideKeyboard() }
    }

    private fun validateData(): Boolean {
        if (asiEdtUsername.text.trim().isEmpty()) {
            asiTvErrorMsg.text = "Chưa nhập tên tài khoản"
            asiTvErrorMsg.visibility = View.VISIBLE
            asiEdtUsername.error = "Chưa nhập tên tài khoản"
            return false
        }
        if (asiEdtPassword.text?.trim()?.isEmpty() == true) {
            asiTvErrorMsg.text = "Chưa nhập mật khẩu"
            asiTvErrorMsg.visibility = View.VISIBLE
            return false
        }
        return true
    }

    fun updateIdentityField(identity: String) {
        asiEdtUsername.setText(identity)
    }

    override fun onBackPressed() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).apply {
            titleText = "Bạn thực sự muốn đóng ứng dụng?"
            contentText = "ĐÓNG ỨNG DỤNG"
            setConfirmButton("Đóng") {
                it.dismiss()
                finish()
            }
            setCancelButton("Không") {
                it.dismissWithAnimation()
            }
            show()
        }
    }
}