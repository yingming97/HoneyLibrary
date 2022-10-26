package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.annotation.SuppressLint
import android.view.View
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseActivity

class ProfileActivity : BaseActivity() {

    private lateinit var imb_backchitiet: ImageView
    private lateinit var imv_avatar: ImageView
    private lateinit var imv_sua_nhan_vien: ImageView
    private lateinit var tv_name: TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_id: TextView
    private lateinit var tv_dia_chi: TextView
    private lateinit var tv_sdt: TextView

    private lateinit var userModels: UserModel

    override fun getLayout(): Int {
        return R.layout.activity_profile
    }

    override fun initView() {
        imb_backchitiet = findViewById(R.id.imb_backchitiet)
        imv_avatar = findViewById(R.id.imv_avatar)
        imv_sua_nhan_vien = findViewById(R.id.imv_sua_nhan_vien)
        tv_name = findViewById(R.id.tv_name)
        tv_email = findViewById(R.id.tv_email)
        tv_id = findViewById(R.id.tv_id)
        tv_dia_chi = findViewById(R.id.tv_dia_chi)
        tv_sdt = findViewById(R.id.tv_sdt)
    }

    override fun initListener() {
        imb_backchitiet.setOnClickListener(this)
        imv_sua_nhan_vien.setOnClickListener(this)
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {
        userModels = SharedPrefUtils.getUserData(this)!!
        setData()
    }

    override fun onClick(view: View?) {
        when (view) {
            imv_sua_nhan_vien -> {
                val intent = Intent(this, ProfileUpdateActivity::class.java)
                startActivity(intent)
            }
            imb_backchitiet -> {
                onBackPressed()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        Glide.with(this).load(userModels.avatar).placeholder(R.drawable.ic_avatar_default)
            .into(imv_avatar)
        tv_name.text = userModels.name
        tv_email.text = userModels.email
        tv_id.text = userModels.userId.toString()
        tv_dia_chi.text = userModels.diaChi
        tv_sdt.text = userModels.sdt

        tv_name = findViewById(R.id.tv_name)
        tv_email = findViewById(R.id.tv_email)
        tv_id = findViewById(R.id.tv_id)
        tv_dia_chi = findViewById(R.id.tv_dia_chi)
        tv_sdt = findViewById(R.id.tv_sdt)
    }

    override fun onResume() {
        super.onResume()
        userModels = SharedPrefUtils.getUserData(this)!!
        setData()
    }
}
