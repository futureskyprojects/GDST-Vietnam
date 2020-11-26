package vn.vistark.qrinfoscanner.ui.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.ui.qr_scan.QrScanActivity
import vn.vistark.qrinfoscanner.ui.result_processing.ResultProcessingActivity
import vn.vistark.qrinfoscanner.ui.sign_up.SignUpActivity

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnSignIn.setOnClickListener {
            val intent = Intent(this, ResultProcessingActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        initQuickScanIcon()
    }

    private fun initQuickScanIcon() {
        Glide.with(this).asGif().load(R.raw.qr_code_animation).into(asiIvQuickScanIcon)
        asiIvQuickScanIcon.setOnClickListener {
            val intent = Intent(this, QrScanActivity::class.java)
            startActivity(intent)
        }
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