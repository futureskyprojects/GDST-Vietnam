package vn.vistark.qrinfoscanner.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import kotlinx.android.synthetic.main.home_menu_options.*
import kotlinx.android.synthetic.main.home_panel.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.api.AuthIntercepter
import vn.vistark.qrinfoscanner.core.extensions.Authentication.Companion.isAuthenticated
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.ui.account_info.AccountInfoActivity
import vn.vistark.qrinfoscanner.ui.qr_scan.QrScanActivity
import vn.vistark.qrinfoscanner.ui.shipment.ShipmentsActivity
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showSelectStaticDataOptionAlert
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTUserProfile
import vn.vistark.qrinfoscanner.helpers.FloatQuickScanButtonHelper
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.ui.qr_result_processing.QRResultProcessingActivity
import vn.vistark.qrinfoscanner.ui.ship_collection.ShipCollectionActivity
import vn.vistark.qrinfoscanner.ui.sign_in.SignInActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        FloatQuickScanButtonHelper.initialize(asiIvQuickScanIcon, cfqsLnQuickScanBtn)

        if (!isAuthenticated())
            gotoLogin()

        loadImageGif()
        initEvents()

        loadUserProfile()
    }

    private fun loadUserProfile() {
        val loading = this.showLoadingAlert()
        loading.show()

        GlobalScope.launch {
            try {
                val profileResponse = ApiService.mAPIServices.getGDSTProfile().await()
                runOnUiThread { loading.cancel() }
                if (profileResponse == null)
                    throw Exception("Không có kết quả trả về")

                GDSTStorage.CurrentUser.company_id = profileResponse.companyId ?: 0
                GDSTStorage.CurrentUser.fullname = profileResponse.fullname ?: ""
                GDSTStorage.CurrentUser.id = profileResponse.id
                GDSTStorage.CurrentUser.image = profileResponse.image ?: ""
                GDSTStorage.CurrentUser.username = profileResponse.username

                GDSTStorage.CurrentCompany =
                    GDSTStorage.GDSTCompanies?.first { x -> x.id == profileResponse.companyId }

                if (GDSTStorage.CurrentCompany == null) {
                    runOnUiThread {
                        showAlertConfirm("Không thể xác nhận công ty bạn trực thuộc") {
                            gotoLogin()
                        }
                    }
                }

            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm("Không thể lấy hồ sơ của bạn") {
                        gotoLogin()
                    }
                }
                e.printStackTrace()
            } finally {
                runOnUiThread {
                    Glide.with(this@HomeActivity)
                        .load(GDSTStorage.CurrentUser.image)
                        .placeholder(R.drawable.holder)
                        .into(haIvUserProfileImage)

                    htTvWelcomeText.text =
                        "Xin chào, ${GDSTStorage.CurrentUser?.getDisplayName()}"

                    haTvEnterpriseName.text =
                        GDSTStorage.CurrentCompany?.companyname ?: "Chúc bạn có một ngày tốt lành"
                }
            }
        }
    }

    private fun gotoLogin() {
        val intent = Intent(this@HomeActivity, SignInActivity::class.java)
        startActivity(intent)
    }


    private fun initEvents() {
        hmoCvShipmentBtn.clickAnimate {
            val intent = Intent(this, ShipmentsActivity::class.java)
            startActivity(intent)
        }
        hmoCvStaticDataBtn.clickAnimate {
            val intent = Intent(this, ShipCollectionActivity::class.java)
            startActivity(intent)
//            this.showSelectStaticDataOptionAlert()
        }
        hmoCvGenerateQrBtn.clickAnimate {}
        hmoCvScanQRBtn.clickAnimate {
            val intent = Intent(this, QRResultProcessingActivity::class.java)
            intent.putExtra(QRResultProcessingActivity::class.java.simpleName, true)
            startActivity(intent)
        }
        ahcmpTvEditProfile.clickAnimate {
            val intent = Intent(this, AccountInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadImageGif() {
        Glide.with(this).asGif().load(R.raw.qr_code_animation).into(ahcmpIvGenerateQR)
        Glide.with(this).asGif().load(R.raw.scan_qr_gif).into(ahcmpIvScanQR)
    }
}