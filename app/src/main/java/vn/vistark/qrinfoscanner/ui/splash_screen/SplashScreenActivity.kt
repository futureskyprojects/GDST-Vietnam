package vn.vistark.qrinfoscanner.ui.splash_screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.constants.AppPath
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.ui.sign_in.SignInActivity
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    val startTick = System.currentTimeMillis()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppPath.initialize(this)

        initStaticResources()
    }

    private fun initStaticResources() {
        GlobalScope.launch {
            try {
                // Lấy danh sách các quốc gia
                RuntimeStorage.Countries = ApiService.mAPIServices.getAllCountries().await()

                // Lấy danh sách FAOs
                RuntimeStorage.FAOs = ApiService.mAPIServices.getAllFAOs().await()

                // Khởi động màn hình tiếp theo
                startNextActivity()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun startNextActivity() {
        val delayTik = 800L
        val diffTik = System.currentTimeMillis() - startTick

        if (diffTik >= delayTik) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    startNextActivity()
                }
            }, delayTik - diffTik)
        }
    }

}