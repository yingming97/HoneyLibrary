package pham.hien.honeylibrary.View.Tab.Option.Activity.DocGia

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Base.BaseActivity


class ChiTietDocGiaActivity : BaseActivity() {
    private lateinit var imvEdit: ImageView
    private lateinit var imvAnh: ImageView
    private lateinit var imvBack: ImageView
    private lateinit var tvId: TextView
    private lateinit var tvHoTen: TextView
    private lateinit var tvDiaChi: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvSdt: TextView

    private lateinit var mUser: UserModel
    override fun getLayout(): Int {
        return R.layout.activity_chi_tiet_doc_gia
    }

    override fun initView() {
        imvEdit = findViewById(R.id.imv_edit_doc_gia)
        imvAnh = findViewById(R.id.imv_avatar_doc_gia)
        imvBack = findViewById(R.id.imv_back_doc_gia)

        tvId = findViewById(R.id.tv_id)
        tvHoTen = findViewById(R.id.tv_name)
        tvDiaChi = findViewById(R.id.tv_dia_chi)
        tvEmail = findViewById(R.id.tv_email)
        tvSdt = findViewById(R.id.tv_sdt)

    }

    override fun initListener() {
        imvBack.setOnClickListener(this)
        imvEdit.setOnClickListener(this)
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {
        mUser = intent.getSerializableExtra(Constant.USER.USER) as UserModel
        setData()
    }

    override fun onClick(view: View?) {
        when (view) {
            imvBack -> {
                onBackPressed()
            }
            imvEdit -> {
                DialogSuaDocGia(this, mUser) {
                    mUser = it
                    setData()
                    Toast.makeText(this, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show()
                }.show()
            }
        }
    }

    private fun setData() {
        Glide.with(this).load(mUser.avatar).placeholder(R.drawable.ic_avatar_default).into(imvAnh)
        tvId.text = mUser.userId.toString()
        tvHoTen.text = mUser.name
        tvSdt.text = mUser.sdt
        tvDiaChi.text = mUser.diaChi
        tvEmail.text = mUser.email
    }
}