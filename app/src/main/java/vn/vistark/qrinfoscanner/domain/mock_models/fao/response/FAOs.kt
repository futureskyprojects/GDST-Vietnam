package vn.vistark.qrinfoscanner.domain.mock_models.fao.response


class FAOs : ArrayList<FAO>() {
    fun nameFormCode(code: String): String {
        this.forEach {
            if (it.code == code)
                return it.name
        }
        return ""
    }
}