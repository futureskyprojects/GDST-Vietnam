package vn.vistark.qrinfoscanner.core.constants

import android.content.Context
import java.io.File

class AppPath {
    companion object {
        var Flags = ""
        var Base = ""

        fun initialize(context: Context) {
            println("//====================== BẮT ĐẦU KHỞI TẠO BỘ ĐƯỜNG DẪN CẦN THIẾT ========================//")
            Base = context.externalCacheDir!!.path + File.separator
            println("Đường dẫn cơ bản: $Base")
            Flags = context.externalCacheDir!!.path + File.separator + "flags" + File.separator
            println("Đường dẫn bộ cờ các nước: $Flags")
            println("//====================== KẾT THÚC KHỞI TẠO BỘ ĐƯỜNG DẪN CẦN THIẾT =======================//")
        }
    }
}