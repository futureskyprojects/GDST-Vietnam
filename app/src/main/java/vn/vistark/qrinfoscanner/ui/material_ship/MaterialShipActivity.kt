package vn.vistark.qrinfoscanner.ui.material_ship

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_material_batch.*
import kotlinx.android.synthetic.main.activity_material_ship.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.Config
import vn.vistark.qrinfoscanner.core.entities.MaterialShip
import vn.vistark.qrinfoscanner.core.entities.RawMaterialBatch
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import kotlin.collections.ArrayList

class MaterialShipActivity : AppCompatActivity() {
    private lateinit var rawMaterialBatch: RawMaterialBatch

    private val materialShips = ArrayList<MaterialShip>()
    private lateinit var adapter: MaterialShipAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_ship)

        initTranshipmentData()

        initEvents()

        initRecyclerView()

        initMockData()

        initDataEvents()
    }

    @SuppressLint("SetTextI18n")
    private fun initTranshipmentData() {
        val rawMaterialBatchId = intent.getIntExtra(RawMaterialBatch::class.java.simpleName, -1)
        rawMaterialBatch = CommonMockup.MockupGet(rawMaterialBatchId) ?: RawMaterialBatch()
        if (rawMaterialBatch.Id <= 0) {
            Toast.makeText(this, "Không thể xác định lô nguyên liệu được chọn", Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }
        amsTvLabel.text =
            "Tàu nguyên liệu [#${rawMaterialBatch.Id.toString().padStart(Config.padSize, '0')}]"
    }

    private fun initEvents() {
        amsBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {

        }
    }

    private fun initMockData() {
        delayAction {
            MockupData<MaterialShip>().forEach { vd ->
                if (vd.RawMaterialBatchId == rawMaterialBatch.Id) {
                    materialShips.add(0, vd)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun add(s: MaterialShip) {
        materialShips.add(0, s)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        amsRvShipments.setHasFixedSize(true)
        amsRvShipments.layoutManager = LinearLayoutManager(this)

        adapter = MaterialShipAdapter(materialShips)
        amsRvShipments.adapter = adapter
    }

    private fun initDataEvents() {
        adapter.onDelete = {
            showAlertConfirm(
                "Bạn có chắc muốn xóa tàu nguyên liệu [#${it.Id.toString()
                    .padStart(Config.padSize, '0')}] hay không?",
                {
                    delayAction {
                        if (CommonMockup.MockupDelete(it)) {
                            removeMaterialShipView(it)
                            showAlertConfirm("Đã xóa thành công")
                        }
                    }
                }
            )
        }

        adapter.onClick = {
//            val intent = Intent(this, MaterialBatchActivity::class.java)
//            intent.putExtra(Shipment::class.java.simpleName, it.Id)
//            startActivity(intent)
        }
    }

    private fun removeMaterialShipView(ship: MaterialShip) {
        val index = materialShips.indexOfFirst { it.Id == ship.Id }
        materialShips.removeAt(index)
        adapter.notifyDataSetChanged()
    }
}