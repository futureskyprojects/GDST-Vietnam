package vn.vistark.qrinfoscanner.core.helpers

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class QRGenerator {
    companion object {
        fun ImageView.ShowQR(content: String) {
            val bmp = QRBitmap(content) ?: return
            setImageBitmap(bmp)
        }

        fun QRBitmap(content: String): Bitmap? {
            val writer = QRCodeWriter()
            try {
                val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
                return bmp
            } catch (e: WriterException) {
                e.printStackTrace()
                return null
            }
        }
    }
}