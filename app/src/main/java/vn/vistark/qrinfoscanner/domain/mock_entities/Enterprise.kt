package vn.vistark.qrinfoscanner.domain.mock_entities

import vn.vistark.qrinfoscanner.core.abstracts.Entity
import java.util.*

data class Enterprise(
    override var Id: Int = -1,
    var name: String = "",
    var identity: String = "",
    var hashPassword: String = "123456",
    var ownerName: String = "",
    var address: String = "",
    var website: String = "",
    override var Creationtime: Date = Date(),
    override var ModificationTime: Date = Date()
) : Entity()