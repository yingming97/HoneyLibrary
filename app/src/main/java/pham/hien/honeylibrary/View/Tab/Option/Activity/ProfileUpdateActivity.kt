package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Dialog.ProgressBarLoading
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.FireBase.Storage.Images
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ImagesUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.util.regex.Pattern

class ProfileUpdateActivity : BaseActivity() {

    private val TAG = "YingMing"
    private lateinit var imb_backchitiet: ImageView

    private lateinit var rlt_toolbar: RelativeLayout
    private lateinit var layout_luu: LinearLayout
    private lateinit var imv_avatar: ImageView
    private lateinit var imv_change_avatar: ImageView
    private lateinit var ed_name: EditText
    private lateinit var tv_email: TextView
    private lateinit var tv_id: TextView
    private lateinit var ed_dia_chi: EditText
    private lateinit var ed_sdt: EditText

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mProgressBarLoading: ProgressBarLoading
    private lateinit var mUserModel: UserModel

    override fun getLayout(): Int {
        return R.layout.activity_profile_update
    }

    override fun initView() {
        rlt_toolbar = findViewById(R.id.rlt_toolbar)
        imb_backchitiet = findViewById(R.id.imb_backchitiet)
        imv_avatar = findViewById(R.id.imv_avatar)
        imv_change_avatar = findViewById(R.id.imv_change_avatar)
        ed_name = findViewById(R.id.ed_name)
        ed_dia_chi = findViewById(R.id.ed_dia_chi)
        ed_sdt = findViewById(R.id.ed_sdt)
        tv_id = findViewById(R.id.tv_id)
        tv_email = findViewById(R.id.tv_email)
        layout_luu = findViewById(R.id.layout_luu_nhan_Vien)
        imb_backchitiet = findViewById(R.id.imb_backchitiet)
    }

    override fun initListener() {
        imb_backchitiet.setOnClickListener(this)
        layout_luu.setOnClickListener(this)
        imv_avatar.setOnClickListener(this)
        imv_change_avatar.setOnClickListener(this)
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {
        mAuth = Firebase.auth
        mProgressBarLoading = ProgressBarLoading(this)
        mUserModel = SharedPrefUtils.getUserData(this)!!
        setData()
    }

    override fun onClick(view: View?) {
        when (view) {
            imb_backchitiet -> finish()
            layout_luu -> {
                mProgressBarLoading.showLoading()
                checkValidate()
//                finish()
            }
            imv_avatar, imv_change_avatar -> {
                ImagesUtils().checkPermissionChonAnh(this, imv_avatar)
            }
        }
    }

    private fun setData() {
        Glide.with(this).load(mUserModel.avatar).placeholder(R.drawable.ic_user_photo_default)
            .into(imv_avatar)
        tv_id.text = mUserModel.userId.toString()
        ed_name.setText(mUserModel.name)
        ed_sdt.setText(mUserModel.sdt)
        ed_dia_chi.setText(mUserModel.diaChi)
        tv_email.text = mUserModel.email
    }

    private fun checkValidate() {
        var strError = ""
        val phonePattern = "(84|0[3|5|7|8|9])+([0-9]{8,9})\\b"
        if (ed_name.text.toString().isEmpty()) {
            strError += "Tên không được bỏ trống.\n"
        }
        if (ed_sdt.text.toString().isEmpty()) {
            strError += "Số điện thooại không được để trống.\n"
        } else if (!Pattern.compile(phonePattern).matcher(ed_sdt.text.toString()).matches()) {
            strError += "Sai định dạng số điện thoại\n"
        }
        if (ed_dia_chi.text.toString().isEmpty()) {
            strError += "Địa chỉ không được để trống.\n"
        }
        if (strError.isNotEmpty()) {
            mProgressBarLoading.hideLoading()
            FailDialog(this, "Lỗi", strError).show()
        } else {
            Images().uploadImage(imv_avatar, Constant.USER.TB_NAME, mUserModel.userId.toString()) {
                mUserModel.avatar = it
//                mUserModel.email = tv_email.text.toString()
                mUserModel.name = ed_name.text.toString()
                mUserModel.diaChi = ed_dia_chi.text.toString()
                mUserModel.sdt = ed_sdt.text.toString()
                UserDAO().updateNhanVien(this, mUserModel)
                SharedPrefUtils.setUserData(this, mUserModel)
                mProgressBarLoading.hideLoading()
            }
        }
    }
}