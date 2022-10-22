package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.FireBase.Auth.CreateNewAccount
import pham.hien.honeylibrary.FireBase.Auth.LoginRegisterAuth
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.util.regex.Pattern

class ThemNhanVienActivity : BaseActivity() {
    private lateinit var backThemNhanVien: ImageView

    private val TAG = "YingMing"

    private lateinit var hoTen: EditText
    private lateinit var email: EditText
    private lateinit var diaChi: EditText
    private lateinit var sdt: EditText
    private lateinit var thuThu: RadioButton
    private lateinit var quanLy: RadioButton
    private lateinit var them: TextView
    private lateinit var arrUser: List<UserModel>
    private lateinit var mListUser: ArrayList<UserModel>


    override fun getLayout(): Int {
        return R.layout.activity_them_nhan_vien
    }

    override fun initView() {
        backThemNhanVien = findViewById(R.id.imb_backThemNhanVien)
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
        backThemNhanVien.setOnClickListener(this)
        backThemNV()
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {
        UserDAO().getListUser {
            arrUser = it
            val user = UserModel()
        }
    }

    private fun addNhanVien() {
        val hoten = hoTen.text.toString()
        val mail = email.text.toString()
        var diachi = diaChi.text.toString()
        var sdt = sdt.text.toString()
        var quyen: Int
        if (thuThu.isChecked) {
            quyen = Constant.QUYEN.THU_THU
            Log.e("tuvm", "check$quyen")
        } else {
            quyen = Constant.QUYEN.ADMIN
        }
        checkForm(hoten, mail, diachi, sdt, quyen) { check, user, sdt ->
            if (check) {
                LoginRegisterAuth().registerNewAccount(this, user!!, sdt) { checks, users ->
                    if (checks) {
                        UserDAO().addUser(this, users)
                    }
                }
            }
        }
    }

    private fun checkForm(
        hoten: String,
        email: String,
        diachi: String,
        sdt: String,
        quyen: Int,
        callback: (Boolean, UserModel?, String) -> Unit,
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
            callback(false, null, sdt)
            FailDialog(this, "Thêm Thất Bại", title).show()
        } else {
            val userModel =
                UserModel(arrUser[arrUser.size - 1].userId, "", quyen, hoten, email, sdt, diachi)
            callback(true, userModel, sdt)
        }
    }

    private fun backThemNV() {
        backThemNhanVien.setOnClickListener {
            onBackPressed()
        }
    }
}