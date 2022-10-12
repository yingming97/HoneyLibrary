package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.view.View
import android.widget.RelativeLayout
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity

class AddPhieuMuonActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var tool_bar: RelativeLayout

    override fun getLayout(): Int {
        return R.layout.acitvity_add_phieu_muon
    }

    override fun initView() {
        tool_bar = findViewById(R.id.tool_bar)

        ScreenUtils().setMarginStatusBar(this, tool_bar)
    }

    override fun initListener() {

    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {

    }

    override fun onClick(view: View?) {
        when (view) {

        }
    }
}