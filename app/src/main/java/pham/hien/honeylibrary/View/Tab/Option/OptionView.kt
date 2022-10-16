package pham.hien.honeylibrary.View.Tab.Option

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Login.LoginActivity
import pham.hien.honeylibrary.View.Tab.ChangePassword.ChangePasswordActivity

class OptionView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false
    private lateinit var mActivity: Activity

    private lateinit var btnLogin: Button
    private lateinit var lnlDoiMatKhau: LinearLayout
    private lateinit var lnlQuanLyNhanVien: LinearLayout
    private lateinit var lnlQuanLyDocGia: LinearLayout
    private lateinit var lnlTroGiup: LinearLayout
    private lateinit var lnlThongKe: LinearLayout
    private lateinit var lnlDangXuat: LinearLayout
    private lateinit var tvUserName: TextView

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
        val rootView: View = inflater.inflate(R.layout.view_option, this)

        lnlThongKe = rootView.findViewById(R.id.lnl_thong_ke)
        lnlDangXuat = rootView.findViewById(R.id.lnl_dang_xuat)
        lnlDoiMatKhau = rootView.findViewById(R.id.lnl_doi_mat_khau)
        lnlTroGiup = rootView.findViewById(R.id.lnl_tro_giup)
        lnlQuanLyDocGia = rootView.findViewById(R.id.lnl_quan_ly_doc_gia)
        lnlQuanLyNhanVien = rootView.findViewById(R.id.lnl_quan_ly_nhan_vien)
        btnLogin = rootView.findViewById(R.id.btn_login)
        tvUserName = rootView.findViewById(R.id.tv_user_name)



        lnlThongKe.setOnClickListener(this)
        lnlDangXuat.setOnClickListener(this)
        lnlDoiMatKhau.setOnClickListener(this)
        lnlTroGiup.setOnClickListener(this)
        lnlQuanLyDocGia.setOnClickListener(this)
        lnlQuanLyNhanVien.setOnClickListener(this)
        btnLogin.setOnClickListener(this)

    }


    override fun initViewModel(viewModel: ViewModel?) {
    }

    override fun initObserver(owner: LifecycleOwner?) {

    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        updateUser(SharedPrefUtils.getUserData(mContext)!!)

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
        if(SharedPrefUtils.getLogin(mContext)){
            when (view) {
                btnLogin -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }

                lnlDoiMatKhau -> {
                    mContext.startActivity(Intent(mContext, ChangePasswordActivity::class.java))
                }

                lnlQuanLyNhanVien -> {

                }

                lnlQuanLyDocGia -> {

                }

                lnlThongKe -> {

                }

                lnlTroGiup -> {

                }

                lnlDangXuat -> {
                    signOut()
                }

            }
        }else{
            when (view) {
                btnLogin -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }

                lnlDoiMatKhau -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }

                lnlQuanLyNhanVien -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }

                lnlQuanLyDocGia -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }

                lnlThongKe -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }

                lnlTroGiup -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }
            }
        }
    }

    fun updateUser(user: UserModel) {
        if(SharedPrefUtils.getLogin(mContext)){
            btnLogin.visibility = View.GONE
            tvUserName.text = user.name
            tvUserName.visibility = View.VISIBLE
            lnlDangXuat.visibility = View.VISIBLE
            checkPermission(user)
        }else{
            btnLogin.visibility = View.VISIBLE
            tvUserName.visibility = View.GONE
            lnlDangXuat.visibility = View.GONE

            lnlTroGiup.visibility = View.VISIBLE
            lnlDoiMatKhau.visibility = View.VISIBLE
            lnlThongKe.visibility = View.VISIBLE
            lnlQuanLyDocGia.visibility = View.VISIBLE
            lnlQuanLyNhanVien.visibility = View.VISIBLE
            SharedPrefUtils.setLogin(mContext, false)
        }
    }

    private fun signOut(){
        SharedPrefUtils.setUserData(mContext, UserModel())
        SharedPrefUtils.setLogin(mContext, false)
        updateUser(UserModel())
        tvUserName.visibility = View.GONE
        Firebase.auth.signOut()
    }

    private fun checkPermission(user: UserModel?){
        when (user?.type) {
            Constant.QUYEN.DOC_GIA -> {
                tvUserName.text = user.name
                tvUserName.visibility = View.VISIBLE
                lnlTroGiup.visibility = View.VISIBLE
                lnlDoiMatKhau.visibility = View.VISIBLE
                lnlThongKe.visibility = View.GONE
                lnlQuanLyDocGia.visibility = View.GONE
                lnlQuanLyNhanVien.visibility = View.GONE
            }
            Constant.QUYEN.THU_THU -> {
                tvUserName.text = user.name
                tvUserName.visibility = View.VISIBLE
                lnlTroGiup.visibility = View.VISIBLE
                lnlDoiMatKhau.visibility = View.VISIBLE
                lnlThongKe.visibility = View.VISIBLE
                lnlQuanLyDocGia.visibility = View.VISIBLE
                lnlQuanLyNhanVien.visibility = View.GONE
            }
            Constant.QUYEN.ADMIN -> {
                tvUserName.text = user.name
                tvUserName.visibility = View.VISIBLE
                lnlTroGiup.visibility = View.VISIBLE
                lnlDoiMatKhau.visibility = View.VISIBLE
                lnlThongKe.visibility = View.VISIBLE
                lnlQuanLyDocGia.visibility = View.VISIBLE
                lnlQuanLyNhanVien.visibility = View.VISIBLE
            }
        }
    }
}