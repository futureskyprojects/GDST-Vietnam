package vn.vistark.qrinfoscanner.core.constants

import vn.vistark.qrinfoscanner.core.entities.Enterprise
import vn.vistark.qrinfoscanner.core.models.country.response.Countries
import vn.vistark.qrinfoscanner.core.models.fao.response.FAOs
import vn.vistark.qrinfoscanner.core.models.organization.response.Organizations
import vn.vistark.qrinfoscanner.core.models.port.response.SeaPorts

class RuntimeStorage {
    companion object {
        var Countries: Countries? = null
        var FAOs: FAOs? = null
        var Organizations: Organizations? = null
        var SeaPorts: SeaPorts? = null

        var CurrentEnterprise: Enterprise? = null
            get() = AppStorageManager.getObject<Enterprise>(Enterprise::class.java.simpleName + "Account")
            set(value) {
                AppStorageManager.update(Enterprise::class.java.simpleName + "Account", value)
                field = value
            }
    }
}