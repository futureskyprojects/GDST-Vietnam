package vn.vistark.qrinfoscanner.ui.shipment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_shipments.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.Shipment
import vn.vistark.qrinfoscanner.core.helpers.AlertConfirmHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.format
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import java.util.*
import kotlin.collections.ArrayList

class ShipmentsActivity : AppCompatActivity() {
    private val shipments = ArrayList<Shipment>()
    private lateinit var adapter: ShipmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipments)

        initEvents()

        initRecyclerView()

        loadMockShiments()

    }

    private fun initEvents() {
        asBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            this.showAlertConfirm(
                "Bạn thực sự muốn tạo lô hàng mới thứ #9892 vào ngày ${Date().format()}",
                {

                })
        }
    }

    private fun loadMockShiments() {
        for (i in 0..100) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, -i)
            val d = calendar.time
            val s = Shipment(i, d.format(), -1, d, d)
            addNewShipmentItem(s)
        }
    }

    fun addNewShipmentItem(s: Shipment) {
        shipments.add(0, s)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        asRvShipments.setHasFixedSize(true)
        asRvShipments.layoutManager = LinearLayoutManager(this)

        adapter = ShipmentAdapter(shipments)
        asRvShipments.adapter = adapter
    }
}