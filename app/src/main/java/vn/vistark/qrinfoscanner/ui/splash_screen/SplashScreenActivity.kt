package vn.vistark.qrinfoscanner.ui.splash_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.constants.AppPath
import vn.vistark.qrinfoscanner.core.constants.AppStorageManager
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.extensions.Authentication.Companion.isAuthenticated
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.mockup.core.MockData
import vn.vistark.qrinfoscanner.ui.home.HomeActivity
import vn.vistark.qrinfoscanner.ui.sign_in.SignInActivity
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private val startTick = System.currentTimeMillis()

    var timerLongResponse: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppStorageManager.initialize(this)

        // Dành cho demo
        MockData.initialize(this)

        AppPath.initialize(this)

        initLongResponse()

        RuntimeStorage.initStaticResources {
            // Tắt timer
            timerLongResponse?.cancel()
            timerLongResponse = null

            // Khởi động màn hình tiếp theo
            startNextActivity()
        }
    }

    private fun initLongResponse() {
        timerLongResponse = Timer()
        timerLongResponse?.schedule(object : TimerTask() {
            override fun run() {
                assTvLongResponseMessage.post {
                    assTvLongResponseMessage.visibility = View.VISIBLE
                }
            }
        }, 10000)
    }

    fun startNextActivity() {
        val delayTik = 800L
        val diffTik = System.currentTimeMillis() - startTick

        if (diffTik >= delayTik) {
            val intent = Intent(
                this,
                if (!isAuthenticated())
                    SignInActivity::class.java
                else
                    HomeActivity::class.java
            )
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