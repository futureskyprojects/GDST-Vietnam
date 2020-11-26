package vn.vistark.qrinfoscanner.core.entities

import vn.vistark.qrinfoscanner.core.abstracts.Entity
import java.util.*

class Enterprise(
    override var Id: Int,
    var name: String,
    var identity: String,
    var hashPassword: String,
    var ownerName: String,
    var address: String,
    var website: String,
    override var Creationtime: Date,
    override var ModificationTime: Date
) : Entity()