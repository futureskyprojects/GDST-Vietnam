package vn.vistark.qrinfoscanner.ui.material_batch

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_material_batch.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.Config
import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage
import vn.vistark.qrinfoscanner.core.entities.RawMaterialBatch
import vn.vistark.qrinfoscanner.core.entities.Shipment
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.format
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupDelete
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupGet
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.ui.material_ship.MaterialShipActivity
import java.util.*
import kotlin.collections.ArrayList

class MaterialBatchActivity : AppCompatActivity() {
    private lateinit var shipment: Shipment

    private val materialBatchs = ArrayList<RawMaterialBatch>()
    private lateinit var adapter: MaterialBatchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_batch)

        initTranshipmentData()

        initEvents()

        initRecyclerView()

        initMockData()

        initDataEvents()
    }

    @SuppressLint("SetTextI18n")
    private fun initTranshipmentData() {
        val shipmentId = intent.getIntExtra(Shipment::class.java.simpleName, -1)
        shipment = MockupGet(shipmentId) ?: Shipment()
        if (shipment.Id <= 0) {
            Toast.makeText(this, "Không thể xác định lô hàng được chọn", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        ambTvTitle.text =
            "Lô nguyên liệu [#${shipment.Id.toString().padStart(Config.padSize, '0')}]"
    }

    private fun initEvents() {
        ambBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            val batch = RawMaterialBatch(
                CommonMockup.MockupMaxId<RawMaterialBatch>() + 1,
                shipment.Id,
                Date().format("HH:mm dd-MM-yyyy")
            )
            this.showAlertConfirm(
                "Bạn thực sự muốn tạo lô nguyên liệu mới số #${
                batch.Id.toString().padStart(
                    Config.padSize,
                    '0'
                )} vào lúc [${batch.Name}]",
                {
                    delayAction {
                        var msg = ""
                        if (CommonMockup.MockupCreate(batch, { false })) {
                            msg = "Đã tạo lô nguyên liệu mới thành công"
                            add(batch)
                        } else {
                            msg = "Tạo lô nguyên liệu mới không thành công"
                        }
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

                    }
                })
        }
    }

    private fun initMockData() {
        delayAction {
            MockupData<RawMaterialBatch>().forEach { vd ->
                if (vd.EnterpriseId == RuntimeStorage.CurrentEnterprise?.Id &&
                    vd.ShipmentId == shipment.Id
                ) {
                    materialBatchs.add(0, vd)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun add(s: RawMaterialBatch) {
        materialBatchs.add(0, s)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        asRvMaterialBatch.setHasFixedSize(true)
        asRvMaterialBatch.layoutManager = LinearLayoutManager(this)

        adapter = MaterialBatchAdapter(materialBatchs)
        asRvMaterialBatch.adapter = adapter
    }

    private fun initDataEvents() {
        adapter.onDelete = {
            showAlertConfirm(
                "Bạn có chắc muốn xóa dữ liệu lô nguyên liệu [#${it.Id.toString()
                    .padStart(Config.padSize, '0')}] hay không?",
                {
                    delayAction {
                        if (MockupDelete(it)) {
                            removeBatchView(it)
                            showAlertConfirm("Đã xóa thành công")
                        }
                    }
                }
            )
        }

        adapter.onClick = {
            val intent = Intent(this, MaterialShipActivity::class.java)
            intent.putExtra(RawMaterialBatch::class.java.simpleName, it.Id)
            startActivity(intent)
        }
    }

    private fun removeBatchView(batch: RawMaterialBatch) {
        val index = materialBatchs.indexOfFirst { it.Id == batch.Id }
        materialBatchs.removeAt(index)
        adapter.notifyDataSetChanged()
    }
}