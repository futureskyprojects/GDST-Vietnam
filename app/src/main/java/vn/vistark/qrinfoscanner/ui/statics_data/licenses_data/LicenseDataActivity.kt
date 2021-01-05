package vn.vistark.qrinfoscanner.ui.statics_data.licenses_data

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_license_data.*
import kotlinx.android.synthetic.main.activity_license_data.masterLayout
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.mock_entities.CertificationAndLicense
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.helpers.MyContextWrapper
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupDelete
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.alert_helper.license.LicenseUpdateDialog.Companion.showAddCertAndLicenseDataAlert
import kotlin.collections.ArrayList

class LicenseDataActivity : AppCompatActivity() {
    val licenses = ArrayList<CertificationAndLicense>()
    lateinit var adapter: LicenseDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license_data)
        initEvents()

        initRecyclerView()
        initDataEvents()

        masterLayout.setOnClickListener { HideKeyboard() }
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, Config.LanguageCode))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    private fun initDataEvents() {
        adapter.onEdit = { cl ->
            showAlertConfirm(
                "Bạn có chắc muốn xóa dữ liệu giấy phép số [${cl.harvestCertification}] hay không?",
                {
                    delayAction {
                        if (MockupDelete(cl)) {
                            removeLicense(cl)
                            showAlertConfirm("Đã xóa thành công")
                        }
                    }
                }
            )
        }
    }

    private fun removeLicense(x: CertificationAndLicense) {
        val index = licenses.indexOfFirst { it.harvestCertification == x.harvestCertification }
        licenses.removeAt(index)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        aldRvLicenses.setHasFixedSize(true)
        aldRvLicenses.layoutManager = LinearLayoutManager(this)

        adapter = LicenseDataAdapter(licenses)
        aldRvLicenses.adapter = adapter

        initMockData()
    }

    private fun initEvents() {
        aldBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            showAddCertAndLicenseDataAlert({
                if (it != null) {
                    println("Đã nhận thêm mới dữ liệu giấy phép: ${Gson().toJson(it)}")
                    save(it)
                }
            })
        }
    }

    private fun save(certificationAndLicense: CertificationAndLicense) {
        delayAction {
            if (!CommonMockup.MockupCreate(certificationAndLicense) {
                    it.harvestCertification == certificationAndLicense.harvestCertification
                }) {
                showAlertConfirm("Dữ liệu tàu đã tồn tại")
            } else {
                licenses.add(0, certificationAndLicense)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initMockData() {
        delayAction {
            CommonMockup.MockupData<CertificationAndLicense>().forEach { cal ->
                licenses.add(0, cal)
                adapter.notifyDataSetChanged()
            }
        }
    }
}