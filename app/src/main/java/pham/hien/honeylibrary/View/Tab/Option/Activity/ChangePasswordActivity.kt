package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.util.regex.Pattern

class ChangePasswordActivity : BaseActivity() {

    private lateinit var cvToolbar: CardView
    private lateinit var btnChangePass: Button
    private lateinit var edPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var edOlderPass: EditText

    override fun getLayout(): Int {
        return R.layout.activity_changepass
    }

    override fun initView() {
        btnChangePass = findViewById(R.id.btn_changepass)
        edPassword = findViewById(R.id.ed_password)
        edOlderPass = findViewById(R.id.ed_old_pass)
        edConfirmPassword = findViewById(R.id.ed_confirm_password)


    }

    override fun initListener() {
        btnChangePass.setOnClickListener(this)
    }

    override fun initViewModel() {
    }

    override fun initObserver() {
    }

    override fun initDataDefault() {
    }

    override fun onClick(view: View?) {
        when (view) {

            btnChangePass -> {
                isValidate(
                    edPassword.text.toString(),
                    edConfirmPassword.text.toString(),
                    edOlderPass.text.toString()
                ) {
                    if (it) {
                        val user = Firebase.auth.currentUser
                        user!!.updatePassword(edPassword.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    SharedPrefUtils.setPassword(this, edPassword.text.toString())
                                    Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                    }
                }
            }
        }
    }

    private fun isValidate(
        pass: String?,
        confirmPass: String?,
        oldPass: String?,
        callback: (Boolean) -> Unit
    ) {
        val passWordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"

        var title = ""
        var check = true

        if (oldPass.isNullOrEmpty()) {
            title += "\nMật khẩu cũ không được bỏ trống"
            check = false
        } else if (!Pattern.compile(passWordPattern).matcher(oldPass)
                .matches() && pass!!.length < 6
        ) {
            title += "\nMật khẩu cũ Không chứa ký tự đặc biệt và phải từ 6 ký tự trở lên"
            check = false
        } else {
            if (SharedPrefUtils.getPassword(this) != oldPass) {
                title += "\nMật khẩu cũ không hợp lệ"
                check = false
            }
        }

        if (pass.isNullOrEmpty()) {
            title += "\nMật khẩu mới không được bỏ trống"
            check = false
        } else if (!Pattern.compile(passWordPattern).matcher(pass).matches() && pass.length < 6) {
            title += "\nMật khẩu mới Không chứa ký tự đặc biệt và phải từ 6 ký tự trở lên"
            check = false
        }

        if (confirmPass.isNullOrEmpty() || confirmPass != pass) {
            title += "\nXác nhận mật khẩu mới không trùng khớp"
            check = false
        }

        if (!check) {
            callback(false)
            FailDialog(this, "Đổi mật khẩu thất bại", title).show()
        } else {
            callback(true)
        }
    }

}