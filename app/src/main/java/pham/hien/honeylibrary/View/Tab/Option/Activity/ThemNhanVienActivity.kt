package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import pham.hien.honeylibrary.FireBase.Auth.LoginRegisterAuth
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.SuccessDialog
import java.util.regex.Pattern

class ThemNhanVienActivity : BaseActivity() {
    private lateinit var hoTen: EditText
    private lateinit var email: EditText
    private lateinit var diaChi: EditText
    private lateinit var sdt: EditText
    private lateinit var thuThu: RadioButton
    private lateinit var quanLy: RadioButton
    private lateinit var them: TextView
    private lateinit var user: UserModel
    private lateinit var userModel: ArrayList<UserModel>
    private lateinit var arrUser: List<UserModel>


    override fun getLayout(): Int {
        return R.layout.activity_them_nhan_vien
    }

    override fun initView() {
        hoTen = findViewById(R.id.ed_HotenNhanvien)
        email = findViewById(R.id.ed_EmailNhanVien)
        diaChi = findViewById(R.id.ed_DiachiNhanVien)
        sdt = findViewById(R.id.ed_SdtNhanVien)
        thuThu = findViewById(R.id.rd_Thuthu)
        quanLy = findViewById(R.id.rdQuanLy)
        them = findViewById(R.id.tv_themNhanvien)
    }

    override fun initListener() {
        them.setOnClickListener {
            addNhanVien()
        }
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

    private fun addNhanVien() {
        var hoten = hoTen.text.toString()
        var mail = email.text.toString()
        var diachi = diaChi.text.toString()
        var sdt = sdt.text.toString()
        var thuthu: Int
        if (thuThu.isChecked) {
            thuthu = Constant.QUYEN.THU_THU
            Log.e("tuvm", "check$thuthu")
        } else {
            thuthu = Constant.QUYEN.ADMIN
        }
        checkForm(hoten, mail, diachi, sdt) { check, user, sdt ->
            if (check) {
                LoginRegisterAuth().registerNewAccount(this, user, sdt) { check, users ->
                    if (check) {
                        SuccessDialog(this, "Đăng ký thành công", "").show()
                        LoginRegisterAuth().loginAfterRegister(user, sdt, this)
                    }
                }
            }
            if (check) {
                val mUser = UserModel(user.userId, "", thuthu, hoten, mail, sdt, diachi)
                UserDAO().addUser(this, mUser)
                SuccessDialog(this, "Thêm Thành Công", "").show()
            }
        }
    }

    private fun checkForm(
        hoten: String,
        email: String,
        diachi: String,
        sdt: String,
        callback: (Boolean, UserModel, String) -> Unit
    ) {
        val phonePattern = "(84|0[3|5|7|8|9])+([0-9]{8,9})\\b"
        var title = ""
        var check = true
        if (hoten.isNullOrEmpty()) {
            title += "\nTên không được bỏ trống"
            check = false
        } else if (email.isNullOrEmpty()) {
            title += "\nEmail không được trống"
            check = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            title += "\nEmail sai định dạng"
            check = false
        } else if (diachi.isNullOrEmpty()) {
            title += "\nĐịa chỉ không được bỏ trống"
            check = false
        } else if (diachi.length!! < 6) {
            title += "\nĐịa chỉ không dưới 6 ký tự"
            check = false
        } else if (sdt.isNullOrEmpty()) {
            for (user in arrUser) {
                if (user.sdt == sdt) {
                    title += "\n SDT đã tồn tại"
                    check = false
                    break
                }
            }
            title += "\nSố điện thoại không được bỏ trống"
            check = false

        } else if (!Pattern.compile(phonePattern).matcher(sdt).matches()) {
            title += "\nSai định dạng số điện thoại"
            check = false
        }
        if (!check) {
            callback(false, UserModel(), sdt!!)
            FailDialog(this, "Thêm Thất Bại", title).show()
        } else {
            callback(true, UserModel(), sdt!!)
        }
    }
}