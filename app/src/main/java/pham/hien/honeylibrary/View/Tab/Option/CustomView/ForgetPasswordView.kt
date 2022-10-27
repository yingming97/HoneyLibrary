package pham.hien.honeylibrary.View.Tab.Option.CustomView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Animation.AlphaAnimation
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.yingming.honeylibrary.Dialog.FailDialog

class ForgetPasswordView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false
    private lateinit var btnRegister: Button
    private lateinit var edEmail: EditText
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
        val rootView: View = inflater.inflate(R.layout.view_forget_password, this)
        btnRegister = rootView.findViewById(R.id.btn_register)
        edEmail = rootView.findViewById(R.id.ed_email)

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
        UserDAO().getListUser {
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
                isValidate(edEmail.text.toString()) {
                    if (it) {
                        Firebase.auth.sendPasswordResetEmail(edEmail.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        mContext,
                                        "Mail xác nhận đã được gửi đến email của bạn",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    KeyBoardUtils.hideKeyboard(mActivity)
                                    closeView()
                                }
                            }
                    }
                }
            }
        }
    }

    private fun isValidate(
        email: String?,
        callback: (Boolean) -> Unit
    ) {

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
                    check = true
                    break
                } else {
                    check = false
                }
            }
        }

        if (!check) {
            callback(false)
            title += "\n Email không tồn tại"
            FailDialog(mContext, "Gửi email thất bại", title).show()
        } else {
            callback(true)
        }
    }

    fun closeView() {
        AlphaAnimation().goneAnimation(this, 300)
        edEmail.setText("")
    }


}