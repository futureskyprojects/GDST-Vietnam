package vn.vistark.qrinfoscanner.ui.statics_data.vessel_data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_vessel_data.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.core.helpers.AlertConfirmHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.format
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import java.util.*
import kotlin.collections.ArrayList

class VesselDataActivity : AppCompatActivity() {
    val vesselDatas = ArrayList<VesselData>()
    lateinit var adapter: VesselDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vessel_data)
        initEvents()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        avdRvVessels.setHasFixedSize(true)
        avdRvVessels.layoutManager = LinearLayoutManager(this)

        adapter = VesselDataAdapter(vesselDatas)

        avdRvVessels.adapter = adapter

        initMockData()
    }

    private fun initEvents() {
        avdBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            this.showAlertConfirm(
                "Bạn thực sự muốn tạo lô hàng mới thứ #9892 vào ngày ${Date().format()}",
                {

                })
        }
    }

    private fun initMockData() {
        for (i in 0..100) {
            val vd = VesselData(
                i + 1,
                -1,
                "",
                "Nguyễn Trọng Nghĩa",
                "${i}12${i}43${i}4${i}TS",
                "",
                "",
                "VN",
                "61",
                "Chi cục Khánh Hòa"
            )
            vesselDatas.add(vd)
            adapter.notifyDataSetChanged()
        }
    }
}