package vn.vistark.qrinfoscanner.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.component_bottom_nav.*
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import kotlinx.android.synthetic.main.home_menu_options.*
import kotlinx.android.synthetic.main.home_panel.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Authentication.Companion.isAuthenticated
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.helpers.VistarkContextWrapper
import vn.vistark.qrinfoscanner.core.overrides.VistarkActivity
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTUserProfile
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTBottomBar
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.ui.ship_collection.ShipCollectionActivity
import vn.vistark.qrinfoscanner.ui.shipment.ShipmentsActivity
import vn.vistark.qrinfoscanner.ui.sign_in.SignInActivity

class HomeActivity : VistarkActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initGDSTBottomBar(cbnBnvBottomNav, cbnBtnCenter)

        if (!isAuthenticated())
            gotoLogin()

        initEvents()

        loadUserProfile()
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(VistarkContextWrapper.wrap(newBase, Config.LanguageCode))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserProfile() {
        val loading = this.showLoadingAlert()
        loading.show()

        GlobalScope.launch {
            try {
                val profileResponse = ApiService.mAPIServices.getGDSTProfile().await()
                runOnUiThread { loading.cancel() }
                if (profileResponse == null)
                    throw Exception("Không có kết quả trả về")

                GDSTStorage.CurrentUser = GDSTUserProfile(profileResponse)

                GDSTStorage.CurrentCompany =
                    GDSTStorage.GDSTCompanies?.first { x -> x.id == profileResponse.companyId }

                if (GDSTStorage.CurrentCompany == null) {
                    runOnUiThread {
                        showAlertConfirm(getString(R.string.ktxnctbtt)) {
                            gotoLogin()
                        }
                    }
                }

            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm(getString(R.string.ktlhscb)) {
                        gotoLogin()
                    }
                }
                e.printStackTrace()
            } finally {
                runOnUiThread {
                    Glide.with(this@HomeActivity)
                        .load(GDSTStorage.CurrentUser.getImageAddress())
                        .placeholder(R.drawable.holder)
                        .into(haIvUserProfileImage)

                    htTvWelcomeText.text =
                        getString(R.string.hello) + " " + GDSTStorage.CurrentUser.getDisplayName()

                    haTvEnterpriseName.text =
                        GDSTStorage.CurrentCompany?.companyname ?: getString(R.string.cbcmntl)
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
        }
//        hmoCvGenerateQrBtn.clickAnimate {
//            Toast.makeText(this, "Xin lỗi, tính năng hiện đang được phát triển", Toast.LENGTH_SHORT)
//                .show()
//        }
//        hmoCvScanQRBtn.clickAnimate {
//            val intent = Intent(this, QRResultProcessingActivity::class.java)
//            intent.putExtra(QRResultProcessingActivity::class.java.simpleName, true)
//            startActivity(intent)
//        }
    }

//    private fun loadImageGif() {
//        Glide.with(this).asGif().load(R.raw.qr_code_animation).into(ahcmpIvGenerateQR)
//        Glide.with(this).asGif().load(R.raw.scan_qr_gif).into(ahcmpIvScanQR)
//    }
}