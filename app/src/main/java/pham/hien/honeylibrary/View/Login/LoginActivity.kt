package pham.hien.honeylibrary.View.Login

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
import pham.hien.honeylibrary.View.Tab.Option.CustomView.RegisterView
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.util.regex.Pattern

class LoginActivity : BaseActivity() {

    private lateinit var btnLogin: Button
    private lateinit var edEmail: EditText
    private lateinit var edPass: EditText
    private lateinit var arrUser: List<UserModel>
    private lateinit var rltToolbar: RelativeLayout
    private lateinit var imbBack: ImageView
    private lateinit var tvRegister: TextView
    private lateinit var registerView: RegisterView

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        rltToolbar = findViewById(R.id.rlt_toolbar)
        btnLogin = findViewById(R.id.btn_login)
        edEmail = findViewById(R.id.ed_email)
        edPass = findViewById(R.id.ed_password)
        imbBack = findViewById(R.id.imb_back)
        tvRegister = findViewById(R.id.tv_register)
        registerView = findViewById(R.id.view_register)
        ScreenUtils().setMarginStatusBar(this, rltToolbar)

        registerView.openForTheFirstTime(this)
    }

    override fun initListener() {
        btnLogin.setOnClickListener(this)
        imbBack.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
    }

    override fun initViewModel() {
    }

    override fun initObserver() {
        registerView.initObserver(this)
    }

    override fun initDataDefault() {
        arrUser = listOf()
        arrUser = UserDAO().getListUser()
    }

    override fun onClick(view: View?) {
        when (view) {
            btnLogin -> {
                isValidate(edEmail.text.toString(), edPass.text.toString()) {
                    if (it) {
                        LoginRegisterAuth().accountLogin(
                            this,
                            this,
                            edEmail.text.toString(),
                            edPass.text.toString(),
                            arrUser
                        ) { its ->
                            if (its) {
                                SharedPrefUtils.setPassword(this, edPass.text.toString())
                                KeyBoardUtils.hideKeyboard(this)
//                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                    }
                }
            }
            imbBack -> {
                finish()
            }
            tvRegister -> {
                AlphaAnimation().visibleAnimation(registerView, 300)
            }
        }
    }

    private fun isValidate(email: String?, pass: String?, callback: (Boolean) -> Unit) {
        val passWordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        if (email.isNullOrEmpty()) {
            callback(false)
            FailDialog(this, "Sai Email", "Email không được bỏ trống").show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FailDialog(this, "Sai Email", "Email không đúng định dạng").show()
        } else if (pass.isNullOrEmpty()) {
            FailDialog(this, "Sai mật khẩu", "Mật khẩu không được bỏ trống").show()
            callback(false)
        } else if (!Pattern.compile(passWordPattern).matcher(pass).matches() && pass.length < 6) {
            FailDialog(
                this,
                "Sai mật khẩu",
                "Mật khẩu không đúng định dạng:\nKhông chứa ký tự đặc biệt và phải từ 6 ký tự trở lên"
            ).show()
            callback(false)

        } else {
            callback(true)
        }
    }

    override fun onBackPressed() {
        if(registerView.visibility == View.VISIBLE){
           registerView.closeView()
        }else{
            super.onBackPressed()
        }
    }
}