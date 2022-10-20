package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import pham.hien.honeylibrary.FireBase.Auth.CreateNewAccount
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.ViewModel.DocGiaViewModel


class AddDocGiaActivity : BaseActivity() {
    private var mIdUser : Int = 0
    private lateinit var edNameDocGia: EditText
    private lateinit var edEmail: EditText
    private lateinit var edDiaChi: EditText
    private lateinit var edSdt: EditText
    private lateinit var toolBar: RelativeLayout
    private lateinit var tvAddDocGia: TextView
    private lateinit var mDocGiaViewModel: DocGiaViewModel

    private lateinit var mDocGia: UserModel

    override fun getLayout(): Int {
        return R.layout.activity_add_doc_gia
    }

    override fun initView() {

        toolBar = findViewById(R.id.tool_bar)
        edNameDocGia = findViewById(R.id.ed_name_doc_gia)
        edEmail = findViewById(R.id.ed_email_doc_gia)
        edDiaChi = findViewById(R.id.ed_dia_chi_doc_gia)
        edSdt = findViewById(R.id.ed_sdt_doc_gia)

        tvAddDocGia = findViewById(R.id.tv_add_doc_gia)


        ScreenUtils().setMarginStatusBar(this, toolBar)
    }

    override fun initListener() {
        tvAddDocGia.setOnClickListener(this)
        val intent = intent
        mIdUser = intent.getIntExtra("idUser",0)

    }

    override fun initViewModel() {
        mDocGiaViewModel = ViewModelProvider(this)[DocGiaViewModel::class.java]
    }

    override fun initObserver() {

    }

    override fun initDataDefault() {
        mDocGiaViewModel.getListDocGia()//Load list DocGia
    }

    override fun onClick(view: View?) {
        when (view) {
            tvAddDocGia -> {
                mDocGia.userId = mIdUser
                mDocGia.name = edNameDocGia.text.toString()
                mDocGia.email = edEmail.text.toString()
                mDocGia.sdt = edSdt.text.toString()
                mDocGia.diaChi = edDiaChi.text.toString()
                CreateNewAccount().createNewUser(this, mDocGia)
               //
            }
            }

        }


}
