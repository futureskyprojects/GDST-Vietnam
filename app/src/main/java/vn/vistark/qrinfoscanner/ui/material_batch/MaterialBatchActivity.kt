package vn.vistark.qrinfoscanner.ui.material_batch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_material_batch.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.entities.RawMaterialBatch
import vn.vistark.qrinfoscanner.helpers.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.format
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import java.util.*
import kotlin.collections.ArrayList

class MaterialBatchActivity : AppCompatActivity() {
    private val materialBatchs = ArrayList<RawMaterialBatch>()
    private lateinit var adapter: MaterialBatchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_batch)
        initEvents()

        initRecyclerView()

        loadMockRawMaterialBatchs()
    }

    private fun initEvents() {
        ambBackButton.clickAnimate {
            onBackPressed()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            this.showAlertConfirm(
                "Bạn thực sự muốn tạo lô nguyên liệu mới thứ #9892 vào ngày ${Date().format()}",
                {

                })
        }
    }

    private fun loadMockRawMaterialBatchs() {
        for (i in 0..30) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, -i)
            val d = calendar.time
            val s = RawMaterialBatch(i, -1, d.format(), d, d)
            addNewMaterialItem(s)
        }
    }

    fun addNewMaterialItem(s: RawMaterialBatch) {
        materialBatchs.add(0, s)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        asRvMaterialBatch.setHasFixedSize(true)
        asRvMaterialBatch.layoutManager = LinearLayoutManager(this)

        adapter = MaterialBatchAdapter(materialBatchs)
        asRvMaterialBatch.adapter = adapter
    }
}