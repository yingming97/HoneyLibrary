package pham.hien.honeylibrary.View.Tab.Option.Activity.DocGia

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import pham.hien.honeylibrary.FireBase.Auth.CreateNewAccount
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.ViewModel.DocGiaViewModel
import pham.yingming.honeylibrary.Dialog.FailDialog


class AddDocGiaActivity : BaseActivity() {

    private lateinit var edNameDocGia: EditText
    private lateinit var edEmail: EditText
    private lateinit var edDiaChi: EditText
    private lateinit var edSdt: EditText
    private lateinit var tvAddDocGia: TextView

    private lateinit var mDocGia: UserModel
    private var mListDocGia = ArrayList<UserModel>()
    private lateinit var mDocGiaViewModel: DocGiaViewModel

    override fun getLayout(): Int {
        return R.layout.activity_add_doc_gia
    }

    override fun initView() {

        edNameDocGia = findViewById(R.id.ed_name_doc_gia)
        edEmail = findViewById(R.id.ed_email_doc_gia)
        edDiaChi = findViewById(R.id.ed_dia_chi_doc_gia)
        edSdt = findViewById(R.id.ed_sdt_doc_gia)

        tvAddDocGia = findViewById(R.id.tv_add_doc_gia)

    }

    override fun initListener() {
        tvAddDocGia.setOnClickListener(this)

    }

    override fun initViewModel() {
        mDocGiaViewModel = ViewModelProvider(this)[DocGiaViewModel::class.java]
    }

    override fun initObserver() {
        mDocGiaViewModel.mListDocGiaLiveData.observe(this) {
            mListDocGia = it
        }
    }

    override fun initDataDefault() {
        mDocGiaViewModel.getListDocGia()
    }

    override fun onClick(view: View?) {
        when (view) {
            tvAddDocGia -> {
                checkValidate()
            }
        }
    }

    private fun checkValidate() {
        var strError = ""
        var check = true
        if (edNameDocGia.text.toString().isEmpty()) {
            strError += "Bạn chưa nhập tên.\n"
            check = false
        }
        if (edEmail.text.toString().isEmpty()) {
            strError += "Bạn chưa nhập email.\n"
            check = false
        }
        if (edSdt.text.toString().isEmpty()) {
            strError += "Bạn chưa nhập số điện thoại.\n"
            check = false

        }
        if (edDiaChi.text.toString().isEmpty()) {
            strError += "Bạn chưa nhập địa chỉ.\n"
            check = false

        }
        if (strError.isNotEmpty()) {
            check = false

        }

        if (check) {
            mDocGia = UserModel()
            mDocGia.userId = mListDocGia.last().userId + 1
            mDocGia.type = 0
            mDocGia.name = edNameDocGia.text.toString()
            mDocGia.email = edEmail.text.toString()
            mDocGia.sdt = edSdt.text.toString()
            mDocGia.diaChi = edDiaChi.text.toString()
            CreateNewAccount().createNewDocGia(this, mDocGia) {}
        } else {
            FailDialog(this, "Lỗi", strError).show()

        }
    }
}
