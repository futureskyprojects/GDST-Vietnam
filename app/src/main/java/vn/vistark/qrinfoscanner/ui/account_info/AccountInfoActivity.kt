package vn.vistark.qrinfoscanner.ui.account_info

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account_info.*
import kotlinx.android.synthetic.main.component_bottom_nav.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import kotlinx.android.synthetic.main.component_top_search_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Authentication.Companion.logOut
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.helpers.VistarkContextWrapper
import vn.vistark.qrinfoscanner.core.helpers.ResourceSaver.Companion.SaveBitmap
import vn.vistark.qrinfoscanner.core.helpers.VistarkContextWrapper.Companion.forOnCreate
import vn.vistark.qrinfoscanner.core.overrides.VistarkActivity
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTProfileUpdateWithOutPassword
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTProfileUpdateWithPassword
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTShipmentCreateDTO
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.constants.Config.Companion.showLog
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTUserProfile
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTBottomBar
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTSmartBottomBar
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertShowImage
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.ui.home.HomeActivity
import vn.vistark.qrinfoscanner.ui.sign_in.SignInActivity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class AccountInfoActivity : VistarkActivity() {

    private val REQUEST_CODE: Int = 13323
    val crrProf = GDSTStorage.CurrentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        forOnCreate()

        initGDSTBottomBar(cbnBnvBottomNav, cbnBtnCenter, 2)
        btmNavLayout.initGDSTSmartBottomBar(aaiSvContent)

        masterLayout.setOnClickListener { HideKeyboard() }

        initData()

        initEvents()
    }

    private fun initData() {
        aaiEdtUsername.setText(GDSTStorage.CurrentUser.username)
        aaiEdtCompanyName.setText(GDSTStorage.CurrentCompany?.companyname ?: "")
        aaiEdtFullName.setText(crrProf.fullname)

        Glide.with(this)
            .load(crrProf.getImageAddress())
            .placeholder(R.drawable.holder)
            .into(aaiCivAvatar)
    }

    private fun initEvents() {
        aaiLnContainerRoot.setOnClickListener {
            HideKeyboard()
        }
        aaiBtnSave.clickAnimate {
            saveProfile()
        }
        aaiCivAvatar.setOnLongClickListener {
            showAlertShowImage(GDSTStorage.CurrentUser.getImageAddress())
            return@setOnLongClickListener true
        }
        aaiCivAvatar.clickAnimate {
            openGalleryForImage()
        }
        aaiBtnCancel.clickAnimate {
            onBackPressed()
        }

        aaiTvLogout.clickAnimate {
            showAlertConfirm(getString(R.string.btsmdxkplv), {
                logOut()
                GDSTStorage.CurrentUser = GDSTUserProfile()

                val intent = Intent(this, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            })
        }

        aaiIvLangVI_VN.clickAnimate {
            changeLanguage("vi")
        }

        aaiIvLangEN_US.clickAnimate {
            changeLanguage("en")
        }

        if (Config.LanguageCode == "vi")
            aaiIvLangVI_VN.visibility = View.GONE
        if (Config.LanguageCode == "en")
            aaiIvLangEN_US.visibility = View.GONE
    }

    fun changeLanguage(langCode: String = "vi") {
        Config.LanguageCode = langCode
        showAlertConfirm(getString(R.string.dtdnntc), {
            val intent = Intent(this, AccountInfoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        })
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(VistarkContextWrapper.wrap(newBase, Config.LanguageCode))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun saveProfile() {
        val fullName = aaiEdtFullName.text.toString()
        val password = aaiEdtPassword.text.toString()

        if (fullName.isBlank() || fullName.isEmpty()) {
            aaiEdtFullName.error = getString(R.string.vlnhvt)
        }

        if (password.isNotEmpty() && password.length < 8) {
            aaiEdtPassword.error = getString(R.string.mkpdttkt)
        }

        val loading = this.showLoadingAlert()
        loading.show()
        GlobalScope.launch {
            try {
                val response =
                    (if (password.isNotEmpty())
                        ApiService.mAPIServices.postGDSTUpdateProfile(
                            (GDSTProfileUpdateWithPassword(
                                password,
                                fullName,
                                GDSTStorage.CurrentUser.image
                            ))
                        )
                    else
                        ApiService.mAPIServices.postGDSTUpdateProfile(
                            GDSTProfileUpdateWithOutPassword(
                                fullName,
                                GDSTStorage.CurrentUser.image
                            )
                        ))
                        .await()

                runOnUiThread { loading.cancel() }

                if (response == null)
                    throw Exception("Không phân dải được KQ trả về")

                val temp = GDSTUserProfile(GDSTStorage.CurrentUser)
                temp.fullname = fullName
                GDSTStorage.CurrentUser = temp

                runOnUiThread {
                    showAlertConfirm(getString(R.string.cntttktc))
                }
            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm(getString(R.string.cntttkctc))
                }
                e.printStackTrace()
            } finally {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data?.data != null) {
                try {
                    val bmp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(
                                this.contentResolver,
                                data.data!!
                            )
                        )
                    } else {
                        MediaStore.Images.Media.getBitmap(this.contentResolver, data.data)
                    }
                    uploadBitmap(bmp)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, getString(R.string.ltqtpga), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun uploadBitmap(bmp: Bitmap) {

        showLog("W: ${bmp.width}x${bmp.height}")

        val loading = this.showLoadingAlert()
        loading.show()

        val resized = Bitmap.createScaledBitmap(
            bmp, 512,
            (bmp.height * (512F / bmp.width)).toInt(), true
        )

        val out = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 100, out)
        val decoded = BitmapFactory.decodeStream(ByteArrayInputStream(out.toByteArray()))

        SaveBitmap(
            "/avatar/${GDSTStorage.CurrentUser.username}_${System.currentTimeMillis() / 1000}.png",
            decoded
        ) { path ->
            runOnUiThread {
                // Upload lên server
                val file = File(path)
                val requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val body =
                    MultipartBody.Part.createFormData("image", file.name, requestFile)

                GlobalScope.launch {
                    try {
                        // Tải QR lên máy chủ
                        val uploadImagePath =
                            ApiService.mAPIServices.postGDSTUploadImage(body).await()
                                ?: throw Exception("Ko nhan dc du lieu")

                        // Tạo mới lô nglieu
                        val dto =
                            GDSTShipmentCreateDTO(uploadImagePath.result.path)

                        // Cập nhật lên server
                        val response =
                            ApiService.mAPIServices.postGDSTUpdateProfile(
                                GDSTProfileUpdateWithOutPassword(
                                    GDSTStorage.CurrentUser.fullname,
                                    dto.qrUrl
                                )
                            ).await()
                                ?: throw Exception("Không gửi dữ liệu lô nglieu len dc")

                        runOnUiThread { loading.cancel() }
                        runOnUiThread {
                            val temp = GDSTUserProfile(GDSTStorage.CurrentUser)
                            temp.image = dto.qrUrl
                            GDSTStorage.CurrentUser = temp

                            val intent =
                                Intent(this@AccountInfoActivity, AccountInfoActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread { loading.cancel() }
                        runOnUiThread {
                            showAlertConfirm(getString(R.string.cnaddktc))
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}