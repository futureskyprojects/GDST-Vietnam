package vn.vistark.qrinfoscanner.core.constants

import vn.vistark.qrinfoscanner.core.entities.Enterprise
import vn.vistark.qrinfoscanner.core.models.country.response.Countries
import vn.vistark.qrinfoscanner.core.models.fao.response.FAOs

class RuntimeStorage {
    companion object {
        var Countries: Countries? = null
        var FAOs: FAOs? = null

        var CurrentEnterprise: Enterprise? = null
            get() = AppStorageManager.getObject(Enterprise::class.java.simpleName + "Account")
            set(value) {
                AppStorageManager.update(Enterprise::class.java.simpleName + "Account", value)
                field = value
            }
    }
}