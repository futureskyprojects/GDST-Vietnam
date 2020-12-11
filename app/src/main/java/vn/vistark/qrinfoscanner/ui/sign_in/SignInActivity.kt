package vn.vistark.qrinfoscanner.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_account_info.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.masterLayout
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.Enterprise
import vn.vistark.qrinfoscanner.ui.home.HomeActivity
import vn.vistark.qrinfoscanner.ui.sign_up.SignUpActivity
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.core.models.enterprise.request.EnterpriseLogin
import vn.vistark.qrinfoscanner.databinding.ActivitySignInBinding
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.FloatQuickScanButtonHelper

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding

    companion object {
        var SIA: SignInActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        binding.enter = EnterpriseLogin(
            RuntimeStorage.CurrentEnterprise?.identity ?: ""
        )

        FloatQuickScanButtonHelper.initialize(asiIvQuickScanIcon, cfqsLnQuickScanBtn)

        btnSignIn.clickAnimate {
            val intent = Intent(this, HomeActivity::class.java)
            val enterpriseLogin = binding.enter ?: EnterpriseLogin()
            if (!validateData(enterpriseLogin))
                return@clickAnimate

            delayAction {
                if (!MockupData<Enterprise>().any { et ->
                        et.identity == enterpriseLogin.identity &&
                                et.hashPassword == enterpriseLogin.hashPassword
                    }) {
                    showAlertConfirm("Sai tài khoản hoặc mật khẩu")
                    return@delayAction
                } else {
                    RuntimeStorage.CurrentEnterprise = MockupData<Enterprise>().first {
                        it.identity == enterpriseLogin.identity
                    }
                    if (RuntimeStorage.CurrentEnterprise != null) {
                        startActivity(intent)
                        finish()
                    } else {
                        showAlertConfirm("Đã xảy ra lỗi khi đăng nhập")
                    }
                }
            }
        }

        tvSignUp.clickAnimate {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        asiEdtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                asiTvErrorMsg.text = ""
                asiTvErrorMsg.visibility = View.GONE
            }
        })

        asiEdtIdnetity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                asiTvErrorMsg.text = ""
                asiTvErrorMsg.visibility = View.GONE
            }
        })


        SIA = this

        masterLayout.setOnClickListener { HideKeyboard() }
    }

    private fun validateData(enterpriseLogin: EnterpriseLogin): Boolean {
        if (enterpriseLogin.identity.trim().isEmpty()) {
            asiTvErrorMsg.text = "Chưa nhập mã số thuế"
            asiTvErrorMsg.visibility = View.VISIBLE
            asiEdtIdnetity.error = "Chưa nhập mã số thuế"
            return false
        }
        if (enterpriseLogin.hashPassword.trim().isEmpty()) {
            asiTvErrorMsg.text = "Chưa nhập mật khẩu"
            asiTvErrorMsg.visibility = View.VISIBLE
            return false
        }
        return true
    }

    fun updateIdentityField(identity: String) {
        asiEdtIdnetity.setText(identity)
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