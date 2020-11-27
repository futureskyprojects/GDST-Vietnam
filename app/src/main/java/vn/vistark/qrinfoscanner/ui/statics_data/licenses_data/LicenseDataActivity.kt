package vn.vistark.qrinfoscanner.ui.statics_data.licenses_data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_license_data.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.utils.AlertConfirmUtils.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.utils.AnimUtils.Companion.clickAnimate
import vn.vistark.qrinfoscanner.utils.DatetimeUtils.Companion.format
import vn.vistark.qrinfoscanner.utils.FloatAdd
import java.util.*

class LicenseDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license_data)
        initEvents()
    }

    private fun initEvents() {
        aldBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAdd.initialize(cfabIvIcon, cfabLnAddBtn) {
            this.showAlertConfirm(
                "Bạn thực sự muốn tạo lô hàng mới thứ #9892 vào ngày ${Date().format()}",
                {

                })
        }
    }
}