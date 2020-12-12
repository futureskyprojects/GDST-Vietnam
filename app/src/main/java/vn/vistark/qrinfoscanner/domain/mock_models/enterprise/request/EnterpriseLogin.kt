package vn.vistark.qrinfoscanner.domain.mock_models.enterprise.request

data class EnterpriseLogin(
    var identity: String = "",
    var hashPassword: String = ""
)