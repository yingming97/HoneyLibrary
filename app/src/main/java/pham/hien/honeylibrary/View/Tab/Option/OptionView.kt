package pham.hien.honeylibrary.View.Tab.Option

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Login.LoginActivity
import pham.hien.honeylibrary.View.Tab.Option.Activity.ChangePasswordActivity
import pham.hien.honeylibrary.View.Tab.Option.Activity.DocGiaActivity
import pham.hien.honeylibrary.View.Tab.Option.Activity.NhanVienActivity
import pham.hien.honeylibrary.View.Tab.Option.Activity.TroGiupActivity

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
    private lateinit var imv_avatar: ImageView

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
        imv_avatar = rootView.findViewById(R.id.imv_avatar)



        lnlThongKe.setOnClickListener(this)
        lnlDangXuat.setOnClickListener(this)
        lnlDoiMatKhau.setOnClickListener(this)
        lnlTroGiup.setOnClickListener(this)
        lnlQuanLyDocGia.setOnClickListener(this)
        lnlQuanLyNhanVien.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        imv_avatar.setOnClickListener(this)

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
            checkFirstLaunchView = false
            mActivity = activity!!
            initDataDefault(activity)
        }
    }

    override fun onClick(view: View) {
        if (SharedPrefUtils.getLogin(mContext)) {
//            xu ly vao phan chuc nang o day
            when (view) {
                btnLogin -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }

                lnlDoiMatKhau -> {
                    mContext.startActivity(Intent(mContext, ChangePasswordActivity::class.java))
                }

                lnlQuanLyNhanVien -> {
                    mContext.startActivity(Intent(mContext, NhanVienActivity::class.java))
                }

                lnlQuanLyDocGia -> {
                    mContext.startActivity(Intent(mContext, DocGiaActivity::class.java))
                }

                lnlThongKe -> {
                    Toast.makeText(mContext, "Đang được phát triển.", Toast.LENGTH_LONG).show()
                }

                lnlTroGiup -> {
//                    mContext.startActivity(Intent(mContext, TroGiupActivity::class.java))
                    Toast.makeText(mContext, "Đang được phát triển.", Toast.LENGTH_LONG).show()
                }
                imv_avatar -> {

                }
                lnlDangXuat -> {
                    signOut()
                }

            }
        } else {
            when (view) {
                btnLogin, lnlDoiMatKhau, lnlQuanLyDocGia, lnlThongKe, lnlQuanLyNhanVien, lnlTroGiup, imv_avatar -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }
            }
        }
    }

    fun updateUser(user: UserModel) {
        if (SharedPrefUtils.getLogin(mContext)) {
            btnLogin.visibility = View.GONE
            tvUserName.visibility = View.VISIBLE
            lnlDangXuat.visibility = View.VISIBLE
            checkPermission(user)
            tvUserName.text = user.name
            Glide.with(this).load(user.avatar).placeholder(R.drawable.ic_user_photo_default)
                .into(imv_avatar)
        } else {
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

    private fun signOut() {
        SharedPrefUtils.setUserData(mContext, UserModel())
        SharedPrefUtils.setLogin(mContext, false)
        updateUser(UserModel())
        tvUserName.visibility = View.GONE
        Firebase.auth.signOut()
    }

    private fun checkPermission(user: UserModel?) {
        when (user?.type) {
            Constant.QUYEN.DOC_GIA -> {
                tvUserName.visibility = View.VISIBLE
                lnlTroGiup.visibility = View.VISIBLE
                lnlDoiMatKhau.visibility = View.VISIBLE
                lnlThongKe.visibility = View.GONE
                lnlQuanLyDocGia.visibility = View.GONE
                lnlQuanLyNhanVien.visibility = View.GONE
            }
            Constant.QUYEN.THU_THU -> {
                tvUserName.visibility = View.VISIBLE
                lnlTroGiup.visibility = View.VISIBLE
                lnlDoiMatKhau.visibility = View.VISIBLE
                lnlThongKe.visibility = View.VISIBLE
                lnlQuanLyDocGia.visibility = View.VISIBLE
                lnlQuanLyNhanVien.visibility = View.GONE
            }
            Constant.QUYEN.ADMIN -> {
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