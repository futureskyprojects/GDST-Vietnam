package vn.vistark.qrinfoscanner.ui.statics_data.vessel_data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_vessel_data.*
import kotlinx.android.synthetic.main.activity_vessel_data.masterLayout
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.VesselData
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.delayAction
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupCreate
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupData
import vn.vistark.qrinfoscanner.core.mockup.CommonMockup.Companion.MockupDelete
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.alert_helper.vessel.VesselUpdateDialog.Companion.showAddVessDataAlert
import kotlin.collections.ArrayList

class VesselDataActivity : AppCompatActivity() {
    val vesselDatas = ArrayList<VesselData>()
    lateinit var adapter: VesselDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vessel_data)
        initEvents()
        initRecyclerView()
        initDataEvents()

        masterLayout.setOnClickListener { HideKeyboard() }
    }

    private fun removeVesselData(x: VesselData) {
        val index = vesselDatas.indexOfFirst { it.vesselRegistration == x.vesselRegistration }
        vesselDatas.removeAt(index)
        adapter.notifyDataSetChanged()
    }

    private fun initDataEvents() {
        adapter.onDelete = {
            showAlertConfirm(
                "Bạn có chắc muốn xóa dữ liệu tàu mang số đăng ký [${it.vesselRegistration}] hay không?",
                {
                    delayAction {
                        if (MockupDelete(it)) {
                            removeVesselData(it)
                            showAlertConfirm("Đã xóa thành công")
                        }
                    }
                }
            )
        }
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
            showAddVessDataAlert({ vesselData ->
                if (vesselData != null) {
                    println("Đã nhận thêm mới dữ liệu tàu: ${Gson().toJson(vesselData)}")
                    save(vesselData)
                }
            })
        }
    }

    private fun save(vesselData: VesselData) {
        delayAction {
            if (!MockupCreate(vesselData) {
                    it.vesselRegistration == vesselData.vesselRegistration
                }) {
                showAlertConfirm("Dữ liệu tàu đã tồn tại")
            } else {
                vesselDatas.add(0, vesselData)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initMockData() {
        delayAction {
            MockupData<VesselData>().forEach { vd ->
                vesselDatas.add(0, vd)
                adapter.notifyDataSetChanged()
            }
        }
    }
}