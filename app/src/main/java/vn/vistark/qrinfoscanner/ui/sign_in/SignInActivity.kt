package vn.vistark.qrinfoscanner.ui.sign_in

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_sign_in.*
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