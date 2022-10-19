package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.widget.ImageView
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity

class ChiTietNhanVienActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_chi_tiet_nhan_vien
    }

    private lateinit var back: ImageView

    override fun initView() {
        back = findViewById(R.id.imb_backchitiet)
    }

    override fun initListener() {
        back.setOnClickListener(this)
        onBack()
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {

    }

    fun onBack() {
        back.setOnClickListener {
            onBackPressed()
        }
    }

}