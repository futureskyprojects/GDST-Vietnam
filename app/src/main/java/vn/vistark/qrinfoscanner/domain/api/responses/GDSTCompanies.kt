package vn.vistark.qrinfoscanner.domain.api.responses

import vn.vistark.qrinfoscanner.domain.entities.GDSTCompany

class GDSTCompanies(
    var message: String = "",
    var companies: ArrayList<GDSTCompany> = ArrayList()
)