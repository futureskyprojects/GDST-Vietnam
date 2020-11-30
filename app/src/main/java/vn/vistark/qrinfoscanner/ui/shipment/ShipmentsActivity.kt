package vn.vistark.qrinfoscanner.ui.shipment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_shipments.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.Config.Companion.padSize
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.Shipment
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.format
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupCreate
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupMaxId
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.ui.material_batch.MaterialBatchActivity
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

        initMockData()

        initDataEvents()

    }

    private fun initDataEvents() {
        adapter.onDelete = {
            showAlertConfirm(
                "Bạn có chắc muốn xóa dữ liệu lô hàng [#${it.Id.toString()
                    .padStart(padSize, '0')}] hay không?",
                {
                    delayAction {
                        if (CommonMockup.MockupDelete(it)) {
                            removeShipmentView(it)
                            showAlertConfirm("Đã xóa thành công")
                        }
                    }
                }
            )
        }

        adapter.onClick = {
            gotoRawBatch(it)
        }
    }

    fun gotoRawBatch(shipment: Shipment) {
        val intent = Intent(this, MaterialBatchActivity::class.java)
        intent.putExtra(Shipment::class.java.simpleName, shipment.Id)
        startActivity(intent)
    }

    private fun removeShipmentView(shipment: Shipment) {
        val index = shipments.indexOfFirst { it.Id == shipment.Id }
        shipments.removeAt(index)
        adapter.notifyDataSetChanged()
    }

    private fun initEvents() {
        asBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            val shipment = Shipment(
                MockupMaxId<Shipment>() + 1,
                Date().format("HH:mm dd-MM-yyyy")
            )
            this.showAlertConfirm(
                "Bạn thực sự muốn tạo lô hàng mới số #${
                shipment.Id.toString().padStart(
                    padSize,
                    '0'
                )} vào lúc [${shipment.Name}]",
                {
                    delayAction {
                        var msg = ""
                        if (MockupCreate(shipment, { false })) {
                            msg = "Đã tạo lô hàng mới thành công"
                            add(shipment)
                            gotoRawBatch(shipment)
                        } else {
                            msg = "Tạo lô hàng mới không thành công"
                        }
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

                    }
                })
        }
    }

    private fun initMockData() {
        delayAction {
            MockupData<Shipment>().forEach { vd ->
                if (vd.EnterpriseId == RuntimeStorage.CurrentEnterprise?.Id) {
                    shipments.add(0, vd)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun add(s: Shipment) {
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