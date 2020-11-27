package vn.vistark.qrinfoscanner.utils

import android.content.Context
import vn.vistark.qrinfoscanner.core.constants.AppPath
import java.io.File
import java.io.FileOutputStream

class ResourceUtils {
    companion object {
        fun save(
            context: Context,
            resourceId: Int,
            outFilename: String,
            callback: ((Boolean) -> Unit)
        ) {
            Thread {
                println("//====================== BẮT ĐẦU LƯU TÀI NGUYÊN ========================//")
                try {
                    val inp = context.resources.openRawResource(resourceId)

                    val localPath = AppPath.Base + outFilename

                    if (File(localPath).exists()) {
                        callback.invoke(true)
                    }

                    val out = FileOutputStream(localPath)
                    println("Đang ghi dữ liệu vào [$localPath]")
                    out.write(inp.readBytes())
                    println("Đã ghi thành công!")
                    callback.invoke(true)
                } catch (e: Exception) {
                    println("Ghi không thành công: $e")
                    callback.invoke(false)
                }
                println("//====================== KẾT THÚC LƯU TÀI NGUYÊN ========================//")
            }.start()
        }
    }
}