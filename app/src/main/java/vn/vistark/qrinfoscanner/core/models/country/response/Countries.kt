package vn.vistark.qrinfoscanner.core.models.country.response

data class Countries(
    var countries: List<Country> = emptyList(),
    var reposeUrl: String = ""
)