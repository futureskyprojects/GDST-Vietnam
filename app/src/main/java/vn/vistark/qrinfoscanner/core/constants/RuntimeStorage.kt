package vn.vistark.qrinfoscanner.core.constants

import vn.vistark.qrinfoscanner.core.models.country.response.Countries
import vn.vistark.qrinfoscanner.core.models.fao.response.FAOs

class RuntimeStorage {
    companion object {
        var Countries: Countries? = null
        var FAOs: FAOs? = null
    }
}