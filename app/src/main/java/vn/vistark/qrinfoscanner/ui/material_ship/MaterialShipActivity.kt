package vn.vistark.qrinfoscanner.ui.material_ship

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_material_ship.*
import kotlinx.android.synthetic.main.activity_material_ship.masterLayout
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.mock_entities.MaterialShip
import vn.vistark.qrinfoscanner.domain.mock_entities.RawMaterialBatch
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupCreate
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupMaxId
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship.MaterialShipUpdateDialog.Companion.showUpdateMaterialShipAlert
import vn.vistark.qrinfoscanner.ui.technical_data.TechnicalDataActivity
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

        masterLayout.setOnClickListener { HideKeyboard() }
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
            showUpdateMaterialShipAlert({ mts ->
                if (mts != null) {
                    mts.RawMaterialBatchId = rawMaterialBatch.Id
                    delayAction {
                        if (MockupCreate(mts, { false })) {
                            mts.Id = MockupMaxId<MaterialShip>()
                            add(mts)
                            Toast.makeText(
                                this,
                                "Thêm tàu nguyên liệu thành công",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            start(mts)
                        }
                    }
                }
            })
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

        adapter.onClick = { start(it) }
    }

    private fun removeMaterialShipView(ship: MaterialShip) {
        val index = materialShips.indexOfFirst { it.Id == ship.Id }
        materialShips.removeAt(index)
        adapter.notifyDataSetChanged()
    }

    fun start(materialShip: MaterialShip) {
        val intent = Intent(this, TechnicalDataActivity::class.java)
        intent.putExtra(MaterialShip::class.java.simpleName, materialShip.Id)
        startActivity(intent)
    }
}