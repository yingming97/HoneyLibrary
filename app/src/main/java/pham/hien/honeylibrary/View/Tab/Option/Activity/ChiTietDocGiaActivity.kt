package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity


class ChiTietDocGiaActivity : BaseActivity() {
    private lateinit var imvSua : ImageView
    private lateinit var imvAnh : ImageView
    private lateinit var imvBack : ImageView
    private lateinit var tvId: TextView
    private lateinit var tvHoTen : TextView
    private lateinit var tvDiaChi : TextView
    private lateinit var tvEmail : TextView
    private lateinit var tvSdt : TextView

    private lateinit var  mUser : UserModel
    override fun getLayout(): Int {
        return R.layout.activity_chi_tiet_doc_gia
    }

    override fun initView() {
        imvSua = findViewById(R.id.imv_suaDocGia)
        imvAnh = findViewById(R.id.imv_anhDocGia)
        imvBack = findViewById(R.id.imb_back_chitiet)

        tvId = findViewById(R.id.tv_idDocGia)
        tvHoTen = findViewById(R.id.tv_hoTenDocGia)
        tvDiaChi = findViewById(R.id.tv_diaChiDocGia)
        tvEmail = findViewById(R.id.tv_emailDocGia)
        tvSdt = findViewById(R.id.tv_sdtDocGia)


        var user: UserModel = intent.extras?.get("user") as UserModel
        tvId.text = "ID: "+user.userId.toString()
        tvHoTen.text = "Họ Tên: "+user.name
        tvSdt.text ="Số Điện Thoại: " +user.sdt
        tvDiaChi.text ="Địa Chỉ: " +user.diaChi
        tvEmail.text ="Email: "+user.email

    }

    override fun initListener() {
        imvBack.setOnClickListener(this)
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {

    }

    override fun onClick(view: View?) {
        when (view) {

            imvBack ->{
                onBackPressed()
            }
        }
    }
}