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
import java.util.regex.Pattern


class AddDocGiaActivity : BaseActivity() {

    private lateinit var edNameDocGia: EditText
    private lateinit var edEmail: EditText
    private lateinit var edDiaChi: EditText
    private lateinit var edSdt: EditText
    private lateinit var tvAddDocGia: TextView

    private lateinit var mDocGia: UserModel
    private var mListUser = ArrayList<UserModel>()
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
            mListUser = it
        }
    }

    override fun initDataDefault() {
        mDocGiaViewModel.getListUser()
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
        val phonePattern = "(84|0[3|5|7|8|9])+([0-9]{8,9})\\b"
        var check = true
        if (edNameDocGia.text.toString().isEmpty()) {
            strError += "Bạn chưa nhập tên.\n"
            check = false
        }
        if (edEmail.text.toString().isEmpty()) {
            strError += "Bạn chưa nhập email.\n"
            check = false
        } else {
            for (user in mListUser) {
                if (edEmail.text.toString() == user.email) {
                    strError += "Email đã tồn tại.\n"
                    check = false
                    break
                }
            }
        }
        if (edSdt.text.toString().isEmpty()) {
            strError += "Bạn chưa nhập số điện thoại.\n"
            check = false

        } else if (!Pattern.compile(phonePattern).matcher(edSdt.text.toString()).matches()) {
            strError += "Sai định dạng số điện thoại.\n"
            check = true
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
            mDocGia.userId = mListUser.last().userId + 1
            mDocGia.type = 0
            mDocGia.name = edNameDocGia.text.toString()
            mDocGia.email = edEmail.text.toString()
            mDocGia.sdt = edSdt.text.toString()
            mDocGia.diaChi = edDiaChi.text.toString()
            CreateNewAccount().createNewDocGia(this, mDocGia) {
                edNameDocGia.setText("")
                edEmail.setText("")
                edSdt.setText("")
                edDiaChi.setText("")

            }
        } else {
            FailDialog(this, "Lỗi", strError).show()

        }
    }
}
