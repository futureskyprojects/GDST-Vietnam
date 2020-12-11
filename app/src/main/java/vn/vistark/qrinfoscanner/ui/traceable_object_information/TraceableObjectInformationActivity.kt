package vn.vistark.qrinfoscanner.ui.traceable_object_information

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_traceable_object_information.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.constants.Config
import vn.vistark.qrinfoscanner.core.entities.TechnicalData
import vn.vistark.qrinfoscanner.core.entities.TraceableObjectInformation
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.TOI.TOIUpdateDialog.Companion.showUpdateTraceableObjectInformationAlert

class TraceableObjectInformationActivity : AppCompatActivity() {
    private lateinit var technicalData: TechnicalData

    private val tois = ArrayList<TraceableObjectInformation>()
    private lateinit var adapter: TOIAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traceable_object_information)

        initTranshipmentData()

        initEvents()

        initRecyclerView()

        initMockData()

        initDataEvents()

    }

    @SuppressLint("SetTextI18n")
    private fun initTranshipmentData() {
        val technicalDataId =
            intent.getIntExtra(TechnicalData::class.java.simpleName, -1)
        technicalData = CommonMockup.MockupGet(technicalDataId) ?: TechnicalData()

        if (technicalData.Id <= 0) {
            Toast.makeText(
                this,
                "Không thể xác định thông tin kỹ thuật của mẻ lưới được chọn",
                Toast.LENGTH_SHORT
            )
                .show()
            finish()
            return
        }
        atoiTvLabel.text =
            "Thông tin truy xuất [#${technicalData.Id.toString().padStart(Config.padSize, '0')}]"
    }

    private fun initEvents() {
        atoiBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            showUpdateTraceableObjectInformationAlert({ toiData ->
                if (toiData != null) {
                    toiData.technicalDataId = technicalData.Id
                    delayAction {
                        if (CommonMockup.MockupCreate(toiData, { false })) {
                            toiData.Id = CommonMockup.MockupMaxId<TechnicalData>()
                            add(toiData)
                            Toast.makeText(
                                this,
                                "Thêm thông tin truy xuất thành công",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            })
        }
    }

    private fun initMockData() {
        delayAction {
            CommonMockup.MockupData<TraceableObjectInformation>().forEach { vd ->
                if (vd.technicalDataId == technicalData.Id) add(vd)
            }
        }
    }

    fun add(s: TraceableObjectInformation) {
        tois.add(0, s)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        atoiRvVessels.setHasFixedSize(true)
        atoiRvVessels.layoutManager = LinearLayoutManager(this)

        adapter = TOIAdapter(tois)
        atoiRvVessels.adapter = adapter
    }

    private fun initDataEvents() {
        adapter.onDelete = {
            showAlertConfirm(
                "Bạn có chắc muốn xóa [#${it.Id.toString()
                    .padStart(Config.padSize, '0')}] hay không?",
                {
                    delayAction {
                        if (CommonMockup.MockupDelete(it)) {
                            removeTraceableObjectInformationView(it)
                            showAlertConfirm("Đã xóa thành công")
                        }
                    }
                }
            )
        }

        adapter.onClick = { }
    }

    private fun removeTraceableObjectInformationView(ship: TraceableObjectInformation) {
        val index = tois.indexOfFirst { it.Id == ship.Id }
        tois.removeAt(index)
        adapter.notifyDataSetChanged()
    }
}