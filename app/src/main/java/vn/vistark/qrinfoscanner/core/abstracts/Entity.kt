package vn.vistark.qrinfoscanner.core.abstracts

import vn.vistark.qrinfoscanner.core.interfaces.ICreationTime
import vn.vistark.qrinfoscanner.core.interfaces.IModificationTime

abstract class Entity : BaseEntity(), ICreationTime, IModificationTime