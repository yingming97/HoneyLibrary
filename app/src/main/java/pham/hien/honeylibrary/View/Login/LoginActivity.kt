package pham.hien.honeylibrary.View.Login

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseActivity

class LoginActivity : BaseActivity() {

    private lateinit var btnLogin: Button
    private lateinit var edEmail: EditText
    private lateinit var edPass: EditText
    private lateinit var arrUser: List<UserModel>
    private lateinit var rltToolbar: RelativeLayout
    private lateinit var imbBack: ImageView

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        rltToolbar = findViewById(R.id.rlt_toolbar)
        btnLogin = findViewById(R.id.btn_login)
        edEmail = findViewById(R.id.ed_email)
        edPass = findViewById(R.id.ed_password)
        imbBack = findViewById(R.id.imb_back)
        ScreenUtils().setMarginStatusBar(this, rltToolbar)
    }

    override fun initListener() {
        btnLogin.setOnClickListener(this)
        imbBack.setOnClickListener(this)
    }

    override fun initViewModel() {
    }

    override fun initObserver() {
    }

    override fun initDataDefault() {
        arrUser = listOf()
        arrUser = UserDAO().getListUser()
    }

    override fun onClick(view: View?) {
        when (view) {
            btnLogin -> {
                KeyBoardUtils.hideKeyboard(this)
                var auth = Firebase.auth
                auth.signInWithEmailAndPassword(edEmail.text.toString(), edPass.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Log.d("TAG", "onClick: $user")
                            for (mUser in arrUser) {
                                if (mUser.email == user?.email) {
                                    SharedPrefUtils.setUserData(this, mUser)
                                    Log.d("TAG", "onClick:${mUser.toString()} ")
                                }
                            }
                            Toast.makeText(
                                baseContext, "Dang nhap thanh cong",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        } else {
                            Toast.makeText(
                                baseContext, "Sai tai khoan mat khau",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            imbBack -> {finish()}
        }
    }
}