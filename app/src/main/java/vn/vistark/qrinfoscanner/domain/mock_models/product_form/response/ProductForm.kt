package vn.vistark.qrinfoscanner.domain.mock_models.product_form.response


import com.google.gson.annotations.SerializedName

data class ProductForm(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("code")
    var code: String = ""
)