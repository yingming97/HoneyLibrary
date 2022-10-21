package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity

class ChiTietNhanVienActivity : BaseActivity() {
    private lateinit var userModels: UserModel
    private lateinit var back: ImageView
    private lateinit var updateNhanVien: ImageView
    private lateinit var idNhanvien: TextView
    private lateinit var anhNhanvien: ImageView
    private lateinit var tenNhanVien: TextView
    private lateinit var diachiNhanvien: TextView
    private lateinit var sdtNhanvien: TextView
    private lateinit var emailNhanvien: TextView
    override fun getLayout(): Int {
        return R.layout.activity_chi_tiet_nhan_vien
    }

    override fun initView() {
        back = findViewById(R.id.imb_backchitiet)
        updateNhanVien = findViewById(R.id.imageSuaNhanVien)
        idNhanvien = findViewById(R.id.tv_idNV)
        anhNhanvien = findViewById(R.id.image_anhNhanvien)
        tenNhanVien = findViewById(R.id.tv_hoTen)
        diachiNhanvien = findViewById(R.id.tv_diaChiNV)
        sdtNhanvien = findViewById(R.id.tv_sdtNV)
        emailNhanvien = findViewById(R.id.tv_emailNV)
    }

    override fun initListener() {
        back.setOnClickListener(this)
        updateNhanVien.setOnClickListener(this)
        onBack()
        nextUpdate()
        setData()
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

    fun nextUpdate() {
        updateNhanVien.setOnClickListener {
            intent = Intent(this, SuaNhanVienActivity::class.java)
            intent.putExtra("suaNV", userModels)
            startActivity(intent)
            finish()
        }
    }

    private fun setData() {
        userModels = intent.getSerializableExtra("chitiet") as UserModel
        Glide.with(this).load(userModels.avatar).placeholder(R.drawable.ic_avatar_default)
            .into(anhNhanvien)
        idNhanvien.setText("ID: " + userModels.userId.toString())
        tenNhanVien.setText("Họ Tên: " + userModels.name)
        diachiNhanvien.setText("Địa Chỉ: " + userModels.diaChi)
        sdtNhanvien.setText("Số Điện Thoại: " + userModels.sdt)
        emailNhanvien.setText("Email: " + userModels.email)
    }

}