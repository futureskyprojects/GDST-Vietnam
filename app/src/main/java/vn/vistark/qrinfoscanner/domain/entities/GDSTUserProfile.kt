package vn.vistark.qrinfoscanner.domain.entities

import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.api.IApiService
import vn.vistark.qrinfoscanner.domain.api.responses.account.AccountSuccessfulRespone
import vn.vistark.qrinfoscanner.domain.constants.Config

class GDSTUserProfile(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("username")
    var username: String = "",
    @SerializedName("company_id")
    var company_id: Int = 0,
    @SerializedName("password")
    var password: String = Config.defaultPassword,
    @SerializedName("fullname")
    var fullname: String = "",
    @SerializedName("image")
    var image: String = ""
) {
    fun getDisplayName(): String {
        return fullname.trim().split(" ").last()
    }

    fun getImageAddress(): String {
        val temp = image.replace("^/".toRegex(), "")
        return (IApiService.BASE_URL + temp)
    }

    constructor(res: AccountSuccessfulRespone) : this(
        res.id,
        res.username,
        res.companyId ?: 0,
        res.password,
        res.fullname ?: "",
        res.image ?: ""
    )

    constructor(profile: GDSTUserProfile) : this(
        profile.id,
        profile.username,
        profile.company_id,
        profile.password,
        profile.fullname,
        profile.image
    )

    fun updateFullName(fullname: String): GDSTUserProfile {
        this.fullname = fullname
        return this
    }

    fun updateImage(image: String): GDSTUserProfile {
        this.image = image
        return this
    }
}