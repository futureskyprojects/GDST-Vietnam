package vn.vistark.qrinfoscanner.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.component_float_qr_scan_btn.*
import kotlinx.android.synthetic.main.home_menu_options.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.utils.AnimUtils.Companion.scaleBounce
import vn.vistark.qrinfoscanner.utils.FloatQuickScan

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        FloatQuickScan.initialize(asiIvQuickScanIcon, cfqsLnQuickScanBtn)
        loadImageGif()
        initEvents()
    }


    private fun initEvents() {
        hmoCvShipmentBtn.setOnClickListener {
            it.scaleBounce {}
        }
        hmoCvStaticDataBtn.setOnClickListener {
            it.scaleBounce {}
        }
        hmoCvGenerateQrBtn.setOnClickListener {
            it.scaleBounce {}
        }
        hmoCvScanQRBtn.setOnClickListener {
            it.scaleBounce {}
        }
    }

    private fun loadImageGif() {
        Glide.with(this).asGif().load(R.raw.qr_code_animation).into(ahcmpIvGenerateQR)
        Glide.with(this).asGif().load(R.raw.scan_qr_gif).into(ahcmpIvScanQR)
    }
}