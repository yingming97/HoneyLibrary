package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import pham.hien.honeylibrary.Dialog.ProgressBarLoading
import pham.hien.honeylibrary.FireBase.Auth.CreateNewAccount
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
    private lateinit var mProgressBarLoading: ProgressBarLoading

    override fun getLayout(): Int {
        return R.layout.activity_them_nhan_vien_new
    }

    override fun initView() {
        backThemNhanVien = findViewById(R.id.imb_backchitiet)
        hoTen = findViewById(R.id.ed_name)
        email = findViewById(R.id.ed_email)
        diaChi = findViewById(R.id.ed_address)
        sdt = findViewById(R.id.ed_phone_number)
        thuThu = findViewById(R.id.rd_suaThuthu)
        quanLy = findViewById(R.id.rdQuanLy)
        them = findViewById(R.id.tv_them_nhan_vien)
    }

    override fun initListener() {
        backThemNhanVien.setOnClickListener(this)
        them.setOnClickListener(this)
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {
        mProgressBarLoading = ProgressBarLoading(this)
        UserDAO().getListUser {
            arrUser = it
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            them -> {
                mProgressBarLoading.showLoading()
                addNhanVien()
            }
            backThemNhanVien -> {
                finish()
            }
        }
    }

    private fun addNhanVien() {
        val hoten = hoTen.text.toString()
        val mail = email.text.toString()
        val diachi = diaChi.text.toString()
        val sdt = sdt.text.toString()
        val quyen: Int
        if (thuThu.isChecked) {
            quyen = Constant.QUYEN.THU_THU
            Log.e("tuvm", "check$quyen")
        } else {
            quyen = Constant.QUYEN.ADMIN
        }
        checkForm(hoten, mail, diachi, sdt, quyen) { check, user, sdt ->
            if (check) {
                CreateNewAccount().createNewUser(this, user) {
                    UserDAO().getListUser {
                        arrUser = it
                        hoTen.setText("")
                        email.setText("")
                        diaChi.setText("")
                        this.sdt.setText("")
                        thuThu.isChecked = true
                        mProgressBarLoading.hideLoading()
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
        callback: (Boolean, UserModel, String) -> Unit,
    ) {
        val phonePattern = "(84|0[3|5|7|8|9])+([0-9]{8,9})\\b"
        var title = ""
        var haveError = false
        if (hoten.isNullOrEmpty()) {
            title += "\nT??n kh??ng ????????c bo?? tr????ng"
            haveError = true
        } else if (email.isNullOrEmpty()) {
            title += "\nEmail kh??ng ????????c tr????ng"
            haveError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            title += "\nEmail sai ?????nh d???ng"
            haveError = true
        } else {
            for (user in arrUser) {
                if (user.email == email) {
                    title += "\n Email ??a?? t????n ta??i"
                    haveError = true
                    break
                }
            }
        }
        if (diachi.isNullOrEmpty()) {
            title += "\n??i??a chi?? kh??ng ????????c bo?? tr????ng"
            haveError = true
        } else if (diachi.length!! < 6) {
            title += "\n??i??a chi?? kh??ng d??????i 6 ky?? t????"
            haveError = true
        }
        if (sdt.isNullOrEmpty()) {
            title += "\nS???? ??i????n thoa??i kh??ng ????????c bo?? tr????ng"
            haveError = true

        } else if (!Pattern.compile(phonePattern).matcher(sdt).matches()) {
            title += "\nSai ??i??nh da??ng s???? ??i????n thoa??i"
            haveError = true
        } else{
            for (user in arrUser) {
                if (user.sdt == sdt) {
                    title += "\n S???? ??i????n thoa??i ??a?? t????n ta??i"
                    haveError = true
                    break
                }
            }
        }

        if (haveError) {
            callback(false, UserModel(), sdt)
            FailDialog(this, "Th??m Th???t B???i", title).show()
            mProgressBarLoading.hideLoading()
        } else {
            val userModel =
                UserModel(arrUser.last().userId + 1, "", quyen, hoten, email, sdt, diachi)
            callback(true, userModel, sdt)
        }
    }
}