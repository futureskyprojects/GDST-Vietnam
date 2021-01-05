package vn.vistark.qrinfoscanner.ui.qr_result_processing

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_result_processing.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.helpers.MyContextWrapper
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.ui.qr_scan.QrScanActivity


class QRResultProcessingActivity : AppCompatActivity() {
    var isEnterpiseScanner = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_processing)
        initArguments()
        initEvents()

        startQrScanner()
    }

    private fun initArguments() {
        isEnterpiseScanner =
            intent.getBooleanExtra(QRResultProcessingActivity::class.java.simpleName, false)
        if (isEnterpiseScanner) {
//            Toast.makeText(this, "Scan dưới danh nghĩa doanh nghiệp", Toast.LENGTH_SHORT).show()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, Config.LanguageCode))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    private fun initEvents() {
        btnScanAgain.setOnClickListener {
            startQrScanner()
        }
    }

    private fun startQrScanner() {
        IntentIntegrator(this).apply {
            captureActivity = QrScanActivity::class.java // activity custom để thực hiện scan.
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setPrompt("Di chuyển camera đến vùng chứa\nmã QR để quét")
            setCameraId(0)
            setBeepEnabled(false)
            setOrientationLocked(true)
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                finish()
            } else {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(result.contents)
                startActivity(i)
                finish()
            }
        }
    }
}