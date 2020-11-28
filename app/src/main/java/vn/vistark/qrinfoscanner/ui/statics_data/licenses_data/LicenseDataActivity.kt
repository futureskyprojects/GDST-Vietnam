package vn.vistark.qrinfoscanner.ui.statics_data.licenses_data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_license_data.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.helpers.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.format
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import java.util.*
import kotlin.collections.ArrayList

class LicenseDataActivity : AppCompatActivity() {
    val licenses = ArrayList<CertificationAndLicense>()
    lateinit var adapter: LicenseDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license_data)
        initEvents()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        aldRvLicenses.setHasFixedSize(true)
        aldRvLicenses.layoutManager = LinearLayoutManager(this)

        adapter = LicenseDataAdapter(licenses)
        aldRvLicenses.adapter = adapter

        iniMockData()
    }

    private fun iniMockData() {
        for (i in 0..100) {
            val cal = CertificationAndLicense(
                i + 1,
                "Chi cục Khánh Hoà",
                "21341425425",
                "",
                "Chi cục Khánh Hoà",
                "",
                true,
                true
            )

            licenses.add(cal)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initEvents() {
        aldBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            this.showAlertConfirm(
                "Bạn thực sự muốn tạo lô hàng mới thứ #9892 vào ngày ${Date().format()}",
                {

                })
        }
    }
}