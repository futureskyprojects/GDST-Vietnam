package vn.vistark.qrinfoscanner.core.helpers

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ResourceSaver {
    companion object {
        fun Context.SaveBitmap(destFileName: String, bmp: Bitmap, onComplete: ((String) -> Unit)) {
            Thread {
                var dest = externalCacheDir?.path ?: ""
                if (dest.isEmpty()) {
                    onComplete.invoke("")
                    return@Thread
                }
                dest += File.separator + destFileName
                dest = dest.replace("//", "/")

                val parentPath = File(dest).parent ?: ""

                val f = File(parentPath)

                if (!f.exists()) f.mkdirs()

                try {
                    FileOutputStream(dest).use { out ->
                        bmp.compress(
                            Bitmap.CompressFormat.PNG,
                            100,
                            out
                        ) // bmp is your Bitmap instance
                    }
                    onComplete.invoke(dest)
                } catch (e: IOException) {
                    e.printStackTrace()
                    onComplete.invoke("")
                }
            }.start()
        }
    }
}