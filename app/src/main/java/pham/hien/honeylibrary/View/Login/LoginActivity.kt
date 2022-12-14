package pham.hien.honeylibrary.View.Login

import android.util.Log
import android.util.Patterns
import android.view.View
import pham.hien.honeylibrary.Animation.AlphaAnimation
import android.widget.*
import pham.hien.honeylibrary.FireBase.Auth.LoginRegisterAuth
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.View.Tab.Option.CustomView.ForgetPasswordView
import pham.hien.honeylibrary.View.Tab.Option.CustomView.RegisterView
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.util.regex.Pattern

class LoginActivity : BaseActivity() {

    private lateinit var btnLogin: Button
    private lateinit var edEmail: EditText
    private lateinit var edPass: EditText
    private var arrUser = ArrayList<UserModel>()
    private lateinit var tvRegister: TextView
    private lateinit var registerView: RegisterView
    private lateinit var tvResetPass: TextView
    private lateinit var layoutForgetPasswordView: ForgetPasswordView




    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        btnLogin = findViewById(R.id.btn_login)
        edEmail = findViewById(R.id.ed_email)
        edPass = findViewById(R.id.ed_password)
        tvRegister = findViewById(R.id.tv_register)
        registerView = findViewById(R.id.view_register)
        tvResetPass = findViewById(R.id.tv_reset_pass)
        layoutForgetPasswordView = findViewById(R.id.layout_forget_password)
        registerView.openForTheFirstTime(this)
        layoutForgetPasswordView.openForTheFirstTime(this)

    }

    override fun initListener() {
        btnLogin.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
        tvResetPass.setOnClickListener(this)
    }

    override fun initViewModel() {
    }

    override fun initObserver() {
        registerView.initObserver(this)
    }

    override fun initDataDefault() {
//        UserDAO().getListUser {
//            arrUser = it
//        }
    }

    override fun onClick(view: View?) {
        when (view) {
            btnLogin -> {
                UserDAO().getListUser {listUser ->
                    isValidate(edEmail.text.toString(), edPass.text.toString()) {
                        if (it) {
                            LoginRegisterAuth().accountLogin(
                                this,
                                this,
                                edEmail.text.toString(),
                                edPass.text.toString(),
                                listUser
                            ) { its ->
                                if (its) {
                                    SharedPrefUtils.setPassword(this, edPass.text.toString())
                                    KeyBoardUtils.hideKeyboard(this)
                                    finish()
                                }
                            }
                        }
                    }
                }
            }

            tvRegister -> {
                AlphaAnimation().visibleAnimation(registerView, 300)
            }
            tvResetPass -> {
                AlphaAnimation().visibleAnimation(layoutForgetPasswordView, 300)

            }
        }
    }

    private fun isValidate(email: String?, pass: String?, callback: (Boolean) -> Unit) {
        val passWordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        var check = false
        var content = ""
        if (email.isNullOrEmpty()) {
            check = false
            content += "Email kh??ng ????????c bo?? tr????ng"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            check = false
            content += "\nEmail kh??ng ??u??ng ??i??nh da??ng"
        } else if (pass.isNullOrEmpty()) {
            check = false
            content += "\nM????t kh????u kh??ng ????????c bo?? tr????ng"
        } else if (!Pattern.compile(passWordPattern).matcher(pass).matches() && pass.length < 6) {
            check = false
            content += "\nM????t kh????u Kh??ng ch????a ky?? t???? ??????c bi????t va?? pha??i t???? 6 ky?? t???? tr???? l??n"
        } else {
            check = true
        }

        if(check){
            callback(true)
        }else{
            callback(false)
            FailDialog(this, "????ng nh????p th????t ba??i", content).show()
        }
    }

    override fun onBackPressed() {
        if (registerView.visibility == View.VISIBLE) {
            registerView.closeView()
        }else if(layoutForgetPasswordView.visibility == View.VISIBLE){
            layoutForgetPasswordView.closeView()
        }
        else {
            super.onBackPressed()
        }
    }
}