package vn.vistark.qrinfoscanner.ui.traceable_object_information

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_traceable_object_information.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.mock_entities.TechnicalData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.domain.entities.GDSTInfomationFishUp

class TraceableObjectInformationActivity : AppCompatActivity() {
    private var specials: ArrayList<GDSTInfomationFishUp> = ArrayList()

    private lateinit var adapter: TOIAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traceable_object_information)

        initTranshipmentData()

        initEvents()

        initRecyclerView()

        initDataEvents()

    }

    @SuppressLint("SetTextI18n")
    private fun initTranshipmentData() {
        val specialData =
            intent.getStringExtra(GDSTInfomationFishUp::class.java.simpleName) ?: ""

        val technicalDataId =
            intent.getIntExtra(TechnicalData::class.java.simpleName, -1)

        if (specialData.isEmpty() || technicalDataId <= 0) {
            Toast.makeText(
                this,
                "Không thể xác định thông tin kỹ thuật của mẻ lưới được chọn",
                Toast.LENGTH_SHORT
            )
                .show()
            finish()
            return
        }

        try {
            val tempSpecials = ArrayList(
                Gson().fromJson(
                    specialData,
                    Array<GDSTInfomationFishUp>::class.java
                ).toList()
            )
            specials.addAll(tempSpecials)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        atoiTvLabel.text =
            "Thông tin sản lượng [#${technicalDataId.toString().padStart(Config.padSize, '0')}]"
    }

    private fun initEvents() {
        atoiBackButton.clickAnimate {
            onBackPressed()
        }

    }

    private fun initRecyclerView() {
        atoiRvVessels.setHasFixedSize(true)
        atoiRvVessels.layoutManager = LinearLayoutManager(this)

        adapter = TOIAdapter(specials)
        atoiRvVessels.adapter = adapter
    }

    private fun initDataEvents() {
        adapter.onClick = { }
    }


}