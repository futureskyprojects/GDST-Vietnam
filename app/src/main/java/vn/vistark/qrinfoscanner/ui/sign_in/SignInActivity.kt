package vn.vistark.qrinfoscanner.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.ui.home.HomeActivity
import vn.vistark.qrinfoscanner.ui.qr_scan.QrScanActivity
import vn.vistark.qrinfoscanner.ui.result_processing.ResultProcessingActivity
import vn.vistark.qrinfoscanner.ui.sign_up.SignUpActivity
import vn.vistark.qrinfoscanner.utils.AnimUtils.Companion.clickAnimate
import vn.vistark.qrinfoscanner.utils.FloatQuickScan

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        FloatQuickScan.initialize(asiIvQuickScanIcon, cfqsLnQuickScanBtn)

        btnSignIn.clickAnimate {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvSignUp.clickAnimate {
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