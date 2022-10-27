package pham.hien.honeylibrary.View.Tab.Option.CustomView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import pham.hien.honeylibrary.Animation.AlphaAnimation
import pham.hien.honeylibrary.FireBase.Auth.LoginRegisterAuth
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.SuccessDialog
import java.util.regex.Pattern

class RegisterView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false
    private lateinit var btnRegister: Button
    private lateinit var edEmail: EditText
    private lateinit var edName: EditText
    private lateinit var edAddress: EditText
    private lateinit var edPhoneNumber: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var arrUser: List<UserModel>
    private lateinit var mActivity: Activity

    constructor(context: Context?) : super(context) {
        if (context != null) {
            mContext = context
            initView(context, null)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        if (context != null) {
            mContext = context
            initView(context, attrs)
        }
    }

    override fun initView(context: Context?, attrs: AttributeSet?) {
        super.initView(context, attrs)
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView: View = inflater.inflate(R.layout.view_register, this)
        btnRegister = rootView.findViewById(R.id.btn_register)
        edEmail = rootView.findViewById(R.id.ed_email)
        edName = rootView.findViewById(R.id.ed_name)
        edAddress = rootView.findViewById(R.id.ed_address)
        edPhoneNumber = rootView.findViewById(R.id.ed_phone_number)
        edPassword = rootView.findViewById(R.id.ed_password)
        edConfirmPassword = rootView.findViewById(R.id.ed_confirm_password)


        btnRegister.setOnClickListener(this)
    }


    override fun initViewModel(viewModel: ViewModel?) {
    }


    @SuppressLint("SetTextI18n")
    override fun initObserver(owner: LifecycleOwner?) {
    }

    @SuppressLint("SetTextI18n")
    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        UserDAO().getListUser{
            arrUser = it
        }
    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = true
            mActivity = activity!!
            initDataDefault(activity)
        }
    }

    override fun onClick(view: View) {
        when (view) {
            btnRegister -> {
                isValidate(
                    edEmail.text.toString(),
                    edName.text.toString(),
                    edAddress.text.toString(),
                    edPhoneNumber.text.toString(),
                    edPassword.text.toString(),
                    edConfirmPassword.text.toString()
                ) { check, user, pass ->
                    if (check) {
                        LoginRegisterAuth().registerNewAccount(
                            mActivity,
                            user,
                            pass
                        ) { checks, mUser ->
                            if (checks) {
                                Toast.makeText(context,"Đăng ký thành công", Toast.LENGTH_SHORT).show()
                                KeyBoardUtils.hideKeyboard(mActivity)
                                LoginRegisterAuth().loginAfterRegister(mUser, pass, mActivity)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isValidate(
        email: String?,
        name: String?,
        address: String?,
        phoneNumber: String?,
        pass: String?,
        comfirmPass: String?,
        callback: (Boolean, UserModel, String) -> Unit
    ) {
        val passWordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        val phonePattern = "(84|0[3|5789])+([0-9]{8,9})\\b"

        var title = ""
        var check = true
        if (email.isNullOrEmpty()) {
            title += "\nEmail không được bỏ trống"
            check = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            title += "\n Sai định dạng Email"
            check = false
        } else {
            for (mUser in arrUser) {
                if (mUser.email == email) {
                    title += "\n Email đã tồn tại"
                    check = false
                    break
                }
            }
        }
        if (name.isNullOrEmpty()) {
            title += "\nTên không được bỏ trống"
            check = false
        }

        if (address.isNullOrEmpty()) {
            title += "\nĐịa chỉ không được bỏ trống"
            check = false
        } else if (address.length < 6) {
            title += "\nĐịa chỉ không dưới 6 ký tự"
            check = false
        }

        if (phoneNumber.isNullOrEmpty()) {
            title += "\nSố điện thoại không được bỏ trống"
            check = false

        } else if (!Pattern.compile(phonePattern).matcher(phoneNumber).matches()) {
            title += "\nSai định dạng số điện thoại"
            check = false
        } else {
            for(user in arrUser){
                if (user.sdt == phoneNumber){
                    title += "\nSố điện thoại đã tồn tại"
                    check = false
                    break
                }
            }
        }

        if (pass.isNullOrEmpty()) {
            title += "\nMật khẩu không được bỏ trống"
            check = false
        } else if (!Pattern.compile(passWordPattern).matcher(pass).matches() && pass.length < 6) {
            title += "\nMật khẩu Không chứa ký tự đặc biệt và phải từ 6 ký tự trở lên"
            check = false
        }

        if (comfirmPass.isNullOrEmpty() || comfirmPass != pass) {
            title += "\nXác nhận mật khẩu không trùng khớp"
            check = false
        }

        if (!check) {
            callback(false, UserModel(), pass!!)
            FailDialog(mContext, "Đăng ký thất bại", title).show()
        } else {
            val id = arrUser.last().userId + 1
            val mUser =
                UserModel(id, "", Constant.QUYEN.DOC_GIA, name!!, email!!, phoneNumber!!, address)
            callback(true, mUser, pass!!)
        }
    }

    fun closeView() {
        AlphaAnimation().goneAnimation(this, 300)
        edEmail.setText("")
        edName.setText("")
        edAddress.setText("")
        edPhoneNumber.setText("")
        edPassword.setText("")
        edConfirmPassword.setText("")
    }
}