package vn.vistark.qrinfoscanner.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.AppPath
import vn.vistark.qrinfoscanner.ui.sign_in.SignInActivity
import vn.vistark.qrinfoscanner.utils.FileUtils
import vn.vistark.qrinfoscanner.utils.ResourceUtils
import java.io.File
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
        initFlags {
            startNextActivity()
        }
    }

    fun startNextActivity() {
        val delayTik = 1000L
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

    private fun initFlags(callback: ((Boolean) -> Unit)) {
        val zipFlags = "flags.zip"
        val zipFlagsFullPath = "${AppPath.Base}${zipFlags}"
        if (File(AppPath.Flags).exists()) {
            callback.invoke(true)
            return
        }
        ResourceUtils.save(this, R.raw.flags, zipFlags) { saveZipSuccess ->
            if (saveZipSuccess) {
                FileUtils.unzip(zipFlagsFullPath, AppPath.Flags) { unzipSuccess ->
                    if (unzipSuccess) {
                        File(zipFlagsFullPath).delete()
                        runOnUiThread {
                            callback.invoke(true)
                        }
                    } else {
                        println("//================== LỖI KHI GIẢI NÉN FILE ZIP ===================//")
                    }
                }
            } else {
                println("//================== LỖI KHI LƯU FILE ZIP ===================//")
            }
        }
    }
}