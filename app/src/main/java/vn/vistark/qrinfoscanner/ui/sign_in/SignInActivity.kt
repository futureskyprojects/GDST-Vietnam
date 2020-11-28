package vn.vistark.qrinfoscanner.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.ui.home.HomeActivity
import vn.vistark.qrinfoscanner.ui.sign_up.SignUpActivity
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.mockup.EnterpriseMockup
import vn.vistark.qrinfoscanner.core.models.enterprise.request.EnterpriseLogin
import vn.vistark.qrinfoscanner.databinding.ActivitySignInBinding
import vn.vistark.qrinfoscanner.helpers.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.FloatQuickScanButtonHelper

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding

    companion object {
        var SIA: SignInActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        binding.enter = EnterpriseLogin()

        FloatQuickScanButtonHelper.initialize(asiIvQuickScanIcon, cfqsLnQuickScanBtn)

        btnSignIn.clickAnimate {
            val intent = Intent(this, HomeActivity::class.java)
            val enterpriseLogin = binding.enter ?: EnterpriseLogin()
            if (!validateData(enterpriseLogin))
                return@clickAnimate
            val res = EnterpriseMockup.login(enterpriseLogin.identity, enterpriseLogin.hashPassword)

            delayAction {
                if (!res.isEmpty()) {
                    showAlertConfirm(res)
                    return@delayAction
                }
                startActivity(intent)
                finish()
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