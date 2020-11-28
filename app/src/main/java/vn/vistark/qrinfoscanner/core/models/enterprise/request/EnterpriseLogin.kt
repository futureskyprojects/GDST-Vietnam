package vn.vistark.qrinfoscanner.core.models.enterprise.request

data class EnterpriseLogin(
    var identity: String = "",
    var hashPassword: String = ""
)