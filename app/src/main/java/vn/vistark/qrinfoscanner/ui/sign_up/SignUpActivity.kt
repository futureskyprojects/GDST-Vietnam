package vn.vistark.qrinfoscanner.ui.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.masterLayout
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.Enterprise
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupCreate
import vn.vistark.qrinfoscanner.databinding.ActivitySignUpBinding
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.ui.sign_in.SignInActivity

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_sign_up
        )
        binding.enter = Enterprise()
        initEvents()

        masterLayout.setOnClickListener { HideKeyboard() }
    }

    private fun initEvents() {
        asuBtnSignUp.clickAnimate {
            val enterprise = binding.enter ?: Enterprise()
            if (validateData(enterprise)) {
                // Dành cho mockup
                delayAction {
//                    enterprise.hashPassword = "12345678"
                    val isSuccess = MockupCreate(enterprise) {
                        it.identity == enterprise.identity
                    }
                    if (isSuccess) {
                        SignInActivity.SIA?.updateIdentityField(enterprise.identity)
                        Toast.makeText(
                            this,
                            "Tạo tài khoản thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        showAlertConfirm("Tài khoản đã tồn tại")
                    }
                }
            }
        }
        tvSignInBtn.clickAnimate {
            finish()
        }
    }

    private fun validateData(enterprise: Enterprise): Boolean {
        var res = true
        if (enterprise.name.trim().isEmpty()) {
            asuEdtName.error = "Chưa nhập tên doanh nghiệp"
            res = false
        }
        if (enterprise.ownerName.trim().isEmpty()) {
            asuEdtOwnerName.error = "Chưa nhập tên chủ doanh nghiệp"
            res = false
        }
        if (enterprise.identity.trim().isEmpty()) {
            asuEdtIdentity.error = "Chưa nhập mã số thuế"
            res = false
        }
        if (enterprise.address.trim().isEmpty()) {
            asuEdtAddress.error = "Chưa nhập địa chỉ"
            res = false
        }
        if (enterprise.website.trim().isEmpty()) {
            asuEdtWebsite.error = "Chưa nhập website"
            res = false
        }
        return res
    }
}