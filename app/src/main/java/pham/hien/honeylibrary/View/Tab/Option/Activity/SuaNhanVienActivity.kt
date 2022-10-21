package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.SuccessDialog
import java.util.regex.Pattern

class SuaNhanVienActivity : BaseActivity() {
    private lateinit var backThemNhanVien: ImageView
    private lateinit var suahoTen: EditText
    private lateinit var suaemail: EditText
    private lateinit var suadiaChi: EditText
    private lateinit var suasdt: EditText
    private lateinit var suathuThu: RadioButton
    private lateinit var suaquanLy: RadioButton
    private lateinit var sua: TextView
    private lateinit var users: UserModel
    private lateinit var userModel: ArrayList<UserModel>
    private lateinit var arrUser: List<UserModel>

    private lateinit var backSuaNhanVien: ImageView
    override fun getLayout(): Int {
        return R.layout.activity_sua_nhan_vien
    }

    override fun initView() {
        suahoTen = findViewById(R.id.ed_suaHotenNhanvien)
        suaemail = findViewById(R.id.ed_suaEmailNhanVien)
        suadiaChi = findViewById(R.id.ed_suaDiachiNhanVien)
        suasdt = findViewById(R.id.ed_suaSdtNhanVien)
        suathuThu = findViewById(R.id.rd_suaThuthu)
        suaquanLy = findViewById(R.id.rdQuanLy)
        sua = findViewById(R.id.tv_suaNhanvien)
        backSuaNhanVien = findViewById(R.id.imb_backSuaNhanVien)
    }

    override fun initListener() {
        backSuaNhanVien.setOnClickListener(this)
        backSuaNV()
        sua.setOnClickListener {
            suaNhanVien()
        }
        setData()
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {
        UserDAO().getListUser {
            arrUser = it
        }
    }

    private fun backSuaNV() {
        backSuaNhanVien.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setData() {
        users = intent.getSerializableExtra("suaNV") as UserModel
        suahoTen.setText(users.name)
        suasdt.setText(users.sdt)
        suadiaChi.setText(users.diaChi)
        suaemail.setText(users.email)
        if (users.type == Constant.QUYEN.THU_THU) {
            suathuThu.isChecked
        } else {
            suaquanLy.isChecked
        }
    }

    private fun suaNhanVien() {
        var hoten = suahoTen.text.toString()
        var mail = suaemail.text.toString()
        var diachi = suadiaChi.text.toString()
        var sdt = suasdt.text.toString()
        var thuthu: Int
        if (suathuThu.isChecked) {
            thuthu = Constant.QUYEN.THU_THU
            Log.e("tuvm", "check$thuthu")
        } else {
            thuthu = Constant.QUYEN.ADMIN
        }
        checkForm(hoten, mail, diachi, sdt, thuthu) { check, user ->
            if (check) {
                UserDAO().updateNhanVien(this, user!!)
            }
        }
    }

    private fun checkForm(
        hoten: String,
        email: String,
        diachi: String,
        sdt: String,
        quyen: Int,
        callback: (Boolean, UserModel?) -> Unit
    ) {
        val phonePattern = "(84|0[3|5|7|8|9])+([0-9]{8,9})\\b"
        var title = ""
        var haveError = false
        if (hoten.isNullOrEmpty()) {
            title += "\nTên không được bỏ trống"
            haveError = true
        } else if (email.isNullOrEmpty()) {
            title += "\nEmail không được trống"
            haveError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            title += "\nEmail sai định dạng"
            haveError = true
        } else if (diachi.isNullOrEmpty()) {
            title += "\nĐịa chỉ không được bỏ trống"
            haveError = true
        } else if (diachi.length!! < 6) {
            title += "\nĐịa chỉ không dưới 6 ký tự"
            haveError = true
        } else if (sdt.isNullOrEmpty()) {
            for (user in arrUser) {
                if (user.sdt == sdt) {
                    title += "\n SDT đã tồn tại"
                    haveError = true
                    break
                }
            }
            title += "\nSố điện thoại không được bỏ trống"
            haveError = true

        } else if (!Pattern.compile(phonePattern).matcher(sdt).matches()) {
            title += "\nSai định dạng số điện thoại"
            haveError = true
        }


        if (haveError) {
            callback(false, null)
            FailDialog(this, "Thêm Thất Bại", title).show()
        } else {
            users.name = hoten
            users.sdt = sdt
            users.email = email
            users.diaChi = diachi
            users.type = quyen
            callback(true, users)
        }
    }
}