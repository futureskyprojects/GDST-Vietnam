package vn.vistark.qrinfoscanner.core.models.fao.response


class FAOs : ArrayList<FAO>() {
    fun nameFormCode(code: String): String {
        this.forEach {
            if (it.code == code)
                return it.name
        }
        return ""
    }
}