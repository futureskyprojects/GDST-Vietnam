package vn.vistark.qrinfoscanner.utils

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class FileUtils {
    companion object {
        fun unzip(fz: String, targetDir: String, callback: ((Boolean) -> Unit)) {
            println("//====================== BẮT ĐẦU GIẢI NÉN ========================//")
            println("Tiến hành giải nén từ: [$fz] đến [$targetDir]")
            Thread {
                val zipFile = File(fz)
                if (!zipFile.exists())
                    throw Exception("File zip không tồn tại")
                val targetDirectory = File(targetDir)
                if (!targetDirectory.exists()) {
                    println(">> Thư mục đích không có, tiến hành tạo mới")
                    if (!targetDirectory.mkdirs()) {
                        throw Exception("Tạo thư mục đích không thành công!")
                    }
                } else {
                    println("Đã tồn tại dữ liệu trc đó")
                    callback.invoke(true)
                }
                val zis = ZipInputStream(
                    BufferedInputStream(FileInputStream(zipFile))
                )
                try {
                    var ze: ZipEntry?
                    var count: Int
                    val buffer = ByteArray(8192)
                    while (zis.nextEntry.also { ze = it } != null) {
                        if (ze == null)
                            break
                        val file = File(targetDirectory, ze!!.name)
                        val dir: File =
                            (if (ze!!.isDirectory) file else file.parentFile)
                                ?: throw Exception("Lỗi")
                        if (!dir.isDirectory && !dir.mkdirs()) throw FileNotFoundException(
                            "Failed to ensure directory: " +
                                    dir.absolutePath
                        )
                        if (ze!!.isDirectory) continue
                        val fout = FileOutputStream(file)
                        try {
                            while (zis.read(buffer).also { count = it } != -1) fout.write(
                                buffer,
                                0,
                                count
                            )
                        } catch (e: java.lang.Exception) {
                            println(e)
                            e.printStackTrace()
                            callback.invoke(true)
                        } finally {
                            fout.close()
                        }
                        /* if time should be restored as well
                long time = ze.getTime();
                if (time > 0)
                    file.setLastModified(time);
                */
                    }

                    callback.invoke(true)
                } catch (e: Exception) {
                    println(e)
                    e.printStackTrace()
                    callback.invoke(false)
                } finally {
                    zis.close()
                    println("//====================== KẾT THÚC GIẢI NÉN ========================//")
                }
            }.start()
        }
    }
}