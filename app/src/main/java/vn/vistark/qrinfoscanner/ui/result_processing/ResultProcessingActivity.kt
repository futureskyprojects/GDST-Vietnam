package vn.vistark.qrinfoscanner.ui.result_processing

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_result_processing.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.ui.qr_scan.QrScanActivity


class ResultProcessingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_processing)

        initEvents()

        startQrScanner()
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
                Toast.makeText(this, "Đã đóng", Toast.LENGTH_LONG).show()
            } else {
                tvResultShow.text = result.contents
            }
        }
    }
}