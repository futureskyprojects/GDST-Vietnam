package vn.vistark.qrinfoscanner.ui.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.masterLayout
import kotlinx.android.synthetic.main.item_layout_company.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTUserRegisterDTO
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTCompany
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.SelectBottomSheet.Companion.showSelectBottomSheetAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.gdst_companies_selector.CompanyViewHolder
import vn.vistark.qrinfoscanner.ui.sign_in.SignInActivity

class SignUpActivity : AppCompatActivity() {

    var selectedCompany: GDSTCompany? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initEvents()
    }


    private fun initEvents() {
        masterLayout.setOnClickListener { HideKeyboard() }

        ilcRlRoot.clickAnimate {
            asuTvNoCompanySelected.visibility = View.GONE
            HideKeyboard()
            showSelectBottomSheetAlert(
                "Chọn công ty trực thuộc",
                GDSTStorage.GDSTCompanies?.toTypedArray() ?: emptyArray(),
                R.layout.item_layout_company,
                { o, v ->
                    CompanyViewHolder.bind(o, v)
                }
            ) { o ->
                if (o != null) {
                    selectedCompany = o
                    CompanyBind()
                }
            }
        }

        asuBtnSignUp.clickAnimate {
            val dto = GDSTUserRegisterDTO()

            dto.company_id = selectedCompany?.id ?: 0
            dto.fullname = asuEdtFullName.text.toString()
            dto.username = asuEdtUsername.text.toString()
            dto.password = asuEdtPassword.text.toString()

            if (validateData()) {
                val loading = this.showLoadingAlert()
                loading.show()
                // Dành cho mockup
                GlobalScope.launch {
                    try {
                        val response = ApiService.mAPIServices.postGDSTRegister(dto).await()
                        runOnUiThread { loading.cancel() }
                        if (response == null)
                            throw Exception("Không phân dải được KQ trả về")
                        else {
                            runOnUiThread {
                                SignInActivity.SIA?.updateIdentityField(dto.username)
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "Đăng ký thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        }
                    } catch (httpException: HttpException) {
                        runOnUiThread { loading.cancel() }
                        if (httpException.response().code() == 419) {
                            asuEdtUsername.post {
                                asuEdtUsername.error = "Tên tài khoản đã tồn tại"
                            }
                        } else throw Exception("Mã HTTP RESPONSE không xác nhận được")
                    } catch (e: Exception) {
                        runOnUiThread { loading.cancel() }
                        runOnUiThread {
                            showAlertConfirm("Đăng ký không thành công, vui lòng thử lại")
                        }
                        e.printStackTrace()
                    }
                }
            }
        }
        tvSignInBtn.clickAnimate {
            finish()
        }
    }

    private fun validateData(): Boolean {
        var res = true
        if (asuEdtFullName.text.trim().isEmpty()) {
            asuEdtFullName.error = "Chưa nhập họ và tên"
            res = false
        }
        if (asuEdtUsername.text.trim().isEmpty()) {
            asuEdtUsername.error = "Chưa nhập tên tài khoản"
            res = false
        }
        if (selectedCompany == null) {
            asuTvNoCompanySelected.visibility = View.VISIBLE
            res = false
        }
        return res
    }

    fun CompanyBind() {
        Glide.with(ilcIvCompanyLogo.context)
            .load(selectedCompany?.logo)
            .placeholder(R.drawable.no_image)
            .into(ilcIvCompanyLogo)

        ilcTvState.text = if (selectedCompany!!.status == 1) "Hoạt động" else "Tạm ngưng"

        ilcTvName.text = selectedCompany!!.companyname
        ilcTvName.isSelected = true

        ilcTvOwner.text = selectedCompany!!.ownername
        ilcTvOwner.isSelected = true

        ilcTvAddress.text = selectedCompany!!.address
        ilcTvAddress.isSelected = true

        ilcTvWebsite.text = selectedCompany!!.website
        ilcTvWebsite.isSelected = true
    }
}