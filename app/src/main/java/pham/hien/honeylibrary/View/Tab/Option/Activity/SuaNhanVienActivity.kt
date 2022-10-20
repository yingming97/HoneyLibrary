package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.widget.ImageView
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity

class SuaNhanVienActivity : BaseActivity() {
    private lateinit var backSuaNhanVien: ImageView
    override fun getLayout(): Int {
        return R.layout.activity_sua_nhan_vien
    }

    override fun initView() {
        backSuaNhanVien = findViewById(R.id.imb_backSuaNhanVien)
    }

    override fun initListener() {
        backSuaNhanVien.setOnClickListener(this)
        backSuaNV()
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {

    }

    private fun backSuaNV() {
        backSuaNhanVien.setOnClickListener {
            onBackPressed()
        }
    }

}