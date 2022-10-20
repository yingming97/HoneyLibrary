package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.content.Intent
import android.media.Image
import android.widget.ImageView
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity

class ChiTietNhanVienActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_chi_tiet_nhan_vien
    }

    private lateinit var back: ImageView
    private lateinit var updateNhanVien:ImageView

    override fun initView() {
        back = findViewById(R.id.imb_backchitiet)
        updateNhanVien = findViewById(R.id.imageSuaNhanVien)
    }

    override fun initListener() {
        back.setOnClickListener(this)
        updateNhanVien.setOnClickListener(this)
        onBack()
        nextUpdate()
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
    fun nextUpdate(){
        updateNhanVien.setOnClickListener{
            startActivity(Intent(this,SuaNhanVienActivity::class.java))
            finish()
        }
    }

}