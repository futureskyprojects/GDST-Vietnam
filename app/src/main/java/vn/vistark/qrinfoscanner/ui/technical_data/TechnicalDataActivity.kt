package vn.vistark.qrinfoscanner.ui.technical_data

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_technical_data.*
import kotlinx.android.synthetic.main.activity_technical_data.masterLayout
import kotlinx.android.synthetic.main.component_float_add_btn.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.mock_entities.TechnicalData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup
import vn.vistark.qrinfoscanner.domain.entities.GDSTMaterialShip
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.technical_data.TechnicalDataUpdateDialog.Companion.showUpdateTechnicalDataAlert
import vn.vistark.qrinfoscanner.ui.traceable_object_information.TraceableObjectInformationActivity

class TechnicalDataActivity : AppCompatActivity() {
    private var materialShipId: Int = -1

    private val technicalDatas = ArrayList<TechnicalData>()
    private lateinit var adapter: TechnicalDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_technical_data)

        initTranshipmentData()

        initEvents()

        initRecyclerView()

        syncTechnicalData()

        initDataEvents()

        masterLayout.setOnClickListener { HideKeyboard() }
    }

    @SuppressLint("SetTextI18n")
    private fun initTranshipmentData() {
        materialShipId = intent.getIntExtra(GDSTMaterialShip::class.java.simpleName, -1)
        if (materialShipId <= 0) {
            Toast.makeText(this, "Không thể xác định tàu nguyên liệu được chọn", Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }
        atdTvLabel.text =
            "Thông tin kỹ thuật [#${materialShipId.toString().padStart(Config.padSize, '0')}]"
    }

    private fun initEvents() {
        atdBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            showUpdateTechnicalDataAlert({ techData ->
                if (techData != null) {
                    techData.materialShipId = materialShipId

                    val loading = this.showLoadingAlert()
                    loading.show()
                    GlobalScope.launch {
                        try {
                            val res =
                                ApiService.mAPIServices.postGDSTTechnicalData(techData).await()
                                    ?: throw  Exception("ko phan giai dc")
                            println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                            println(Gson().toJson(res))
                            println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                            runOnUiThread { loading.cancel() }
                            runOnUiThread {
                                syncTechnicalData()
//                                start(res.idShip)
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

    private fun syncTechnicalData() {
        if (technicalDatas.isEmpty()) {
            cfabIvIcon.performClick()
        }
//        delayAction {
//            CommonMockup.MockupData<TechnicalData>().forEach { vd ->
//                if (vd.materialShipId == materialShip.Id) {
//                    technicalDatas.add(0, vd)
//                    adapter.notifyDataSetChanged()
//                }
//            }
//        }
    }

    fun add(s: TechnicalData) {
        technicalDatas.add(0, s)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        atdRvVessels.setHasFixedSize(true)
        atdRvVessels.layoutManager = LinearLayoutManager(this)

        adapter = TechnicalDataAdapter(technicalDatas)
        atdRvVessels.adapter = adapter
    }

    private fun initDataEvents() {
        adapter.onDelete = {
            showAlertConfirm(
                "Bạn có chắc muốn xóa [#${
                it.Id.toString()
                    .padStart(Config.padSize, '0')
                }] hay không?",
                {
                    delayAction {
                        if (CommonMockup.MockupDelete(it)) {
                            removeTechnicalDataView(it)
                            showAlertConfirm("Đã xóa thành công")
                        }
                    }
                }
            )
        }

        adapter.onClick = { start(it) }
    }

    private fun removeTechnicalDataView(ship: TechnicalData) {
        val index = technicalDatas.indexOfFirst { it.Id == ship.Id }
        technicalDatas.removeAt(index)
        adapter.notifyDataSetChanged()
    }

    fun start(technicalData: TechnicalData) {
        val intent = Intent(this, TraceableObjectInformationActivity::class.java)
        intent.putExtra(TechnicalData::class.java.simpleName, technicalData.Id)
        startActivity(intent)
    }
}