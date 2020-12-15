package vn.vistark.qrinfoscanner.ui.material_ship

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_material_ship.*
import kotlinx.android.synthetic.main.activity_material_ship.btmNavLayout
import kotlinx.android.synthetic.main.activity_material_ship.masterLayout
import kotlinx.android.synthetic.main.component_bottom_nav.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import kotlinx.android.synthetic.main.component_top_search_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.mock_entities.MaterialShip
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupCreate
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupMaxId
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTMaterialBacthCreateDTO
import vn.vistark.qrinfoscanner.domain.entities.GDSTMaterialBacth
import vn.vistark.qrinfoscanner.domain.entities.GDSTMaterialShip
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTBottomBar
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTSmartBottomBar
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.TopSearchBarHelper.Companion.initGDSTSmartTopSearchBar
import vn.vistark.qrinfoscanner.helpers.TopSearchBarHelper.Companion.initGDSTTopSearchBar
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship.MaterialShipUpdateDialog
import vn.vistark.qrinfoscanner.helpers.alert_helper.material_ship.MaterialShipUpdateDialog.Companion.showUpdateMaterialShipAlert
import vn.vistark.qrinfoscanner.ui.technical_data.TechnicalDataActivity
import kotlin.collections.ArrayList

class MaterialShipActivity : AppCompatActivity() {
    private var materialBatchId: Int = -1

    private val materialShips = ArrayList<GDSTMaterialShip>()
    private lateinit var adapter: MaterialShipAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_ship)
        initGDSTBottomBar(cbnBnvBottomNav, cbnBtnCenter, -1)
        btmNavLayout.initGDSTSmartBottomBar(amsRvShipments)
        topSearchBar.initGDSTSmartTopSearchBar(amsRvShipments)

        initTranshipmentData()

        initEvents()

        initRecyclerView()

        syncMaterialShip()

        initDataEvents()

        MaterialShipUpdateDialog.shipArr = emptyArray()

        masterLayout.setOnClickListener { HideKeyboard() }
    }

    @SuppressLint("SetTextI18n")
    private fun initTranshipmentData() {
        materialBatchId = intent.getIntExtra(GDSTMaterialBacth::class.java.simpleName, -1)
        if (materialBatchId <= 0) {
            Toast.makeText(this, "Không thể xác định lô nguyên liệu được chọn", Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }
        amsTvLabel.text =
            "Tàu nguyên liệu [#${materialBatchId.toString().padStart(Config.padSize, '0')}]"
    }

    private fun initEvents() {
        amsBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            showUpdateMaterialShipAlert({ mts ->
                if (mts != null) {
                    mts.materialId = materialBatchId

                    val loading = this.showLoadingAlert()
                    loading.show()
                    GlobalScope.launch {
                        try {
                            val res = ApiService.mAPIServices.postGDSTMaterialShip(mts).await()
                                ?: throw  Exception("ko phan giai dc")
                            runOnUiThread { loading.cancel() }
                            runOnUiThread {
                                syncMaterialShip()
                                start(res.idShip)
                            }

                        } catch (e: Exception) {
                            runOnUiThread { loading.cancel() }
                            e.printStackTrace()
                            runOnUiThread {
                                showAlertConfirm("Tạo lô nguyên liệu không thành công (Error: 1)")
                            }
                        }
                    }
                }
            })
        }
    }

    private fun syncMaterialShip() {
        materialShips.clear()
        val loading = this.showLoadingAlert()
        loading.show()
        GlobalScope.launch {
            try {
                val response = ApiService.mAPIServices.getGDSTMaterialShip().await()
                runOnUiThread { loading.cancel() }
                if (response == null)
                    throw Exception("Không phân dải được KQ trả về")

                runOnUiThread {
//                    updateCount(response.size)
                    response.forEach { mtb ->
                        if (mtb.materialId == materialBatchId)
                            add(mtb)
                    }

                    if (materialShips.isEmpty()) {
                        cfabIvIcon.performClick()
                    }
                    aaEdtSearchBar.initGDSTTopSearchBar(materialShips) {
                        materialShips.clear()
                        materialShips.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm("Không lấy được tập dữ liệu có sẵn")
                }
                e.printStackTrace()
            } finally {

            }
        }
    }

    fun add(s: GDSTMaterialShip) {
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
                "Bạn có chắc muốn xóa tàu nguyên liệu [#${
                    it.id.toString()
                        .padStart(Config.padSize, '0')
                }] hay không?",
                {
                    Toast.makeText(this, "Tác vụ này không được cho phép", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        }

        adapter.onClick = { start(it.id) }
    }

    private fun removeMaterialShipView(ship: GDSTMaterialShip) {
        val index = materialShips.indexOfFirst { it.id == ship.id }
        materialShips.removeAt(index)
        adapter.notifyDataSetChanged()
    }

    fun start(materialShipId: Int) {
        val intent = Intent(this, TechnicalDataActivity::class.java)
        intent.putExtra(MaterialShip::class.java.simpleName, materialShipId)
//        startActivity(intent)
        Toast.makeText(this, "Tác vụ hiện đang được tiếp tục phát triển", Toast.LENGTH_SHORT).show()
    }
}