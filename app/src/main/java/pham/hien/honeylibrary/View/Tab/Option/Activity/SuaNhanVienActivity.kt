package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.util.regex.Pattern

class SuaNhanVienActivity : BaseActivity() {

    private lateinit var imb_backchitiet: ImageView

    private lateinit var rlt_toolbar: RelativeLayout
    private lateinit var layout_luu: RelativeLayout
    private lateinit var imv_avatar: ImageView
    private lateinit var tv_name: TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_id: TextView
    private lateinit var tv_dia_chi: TextView
    private lateinit var tv_sdt: TextView

    private lateinit var radioThuThu: RadioButton
    private lateinit var radioQuanLy: RadioButton

    private lateinit var mUserModel: UserModel
    private lateinit var mListUserModel: ArrayList<UserModel>
    private lateinit var arrUser: List<UserModel>

    override fun getLayout(): Int {
        return R.layout.activity_sua_nhan_vien
    }

    override fun initView() {
        rlt_toolbar = findViewById(R.id.rlt_toolbar)
        imv_avatar = findViewById(R.id.imv_avatar)
        tv_name = findViewById(R.id.tv_name)
        tv_dia_chi = findViewById(R.id.tv_dia_chi)
        tv_sdt = findViewById(R.id.tv_sdt)
        tv_id = findViewById(R.id.tv_id)
        tv_id = findViewById(R.id.tv_id)
        layout_luu = findViewById(R.id.layout_luu)
        radioThuThu = findViewById(R.id.rd_suaThuthu)
        radioQuanLy = findViewById(R.id.rdQuanLy)
    }

    override fun initListener() {
        imb_backchitiet.setOnClickListener(this)
        layout_luu.setOnClickListener(this)
    }

    override fun initViewModel() {

    }

    override fun initObserver() {

    }

    override fun initDataDefault() {
        UserDAO().getListUser {
            arrUser = it
        }
        setData()
    }

    override fun onClick(view: View?) {
        when (view) {
            imb_backchitiet -> finish()
            layout_luu -> finish()
        }
    }

    private fun setData() {
        mUserModel = intent.getSerializableExtra(Constant.USER.USER) as UserModel
        Glide.with(this).load(mUserModel.avatar).placeholder(R.drawable.ic_user_photo_default)
            .into(imv_avatar)
        tv_name.text = mUserModel.name
        tv_sdt.text = mUserModel.sdt
        tv_dia_chi.text = mUserModel.diaChi
        tv_email.text = mUserModel.email
        if (mUserModel.type == Constant.QUYEN.THU_THU) {
            radioThuThu.isChecked
        } else {
            radioQuanLy.isChecked
        }
    }

    private fun suaNhanVien() {
        val thuthu: Int
        if (radioThuThu.isChecked) {
            thuthu = Constant.QUYEN.THU_THU
            Log.e("tuvm", "check$thuthu")
        } else {
            thuthu = Constant.QUYEN.ADMIN
        }
//        checkForm(hoten, mail, diachi, sdt, thuthu) { check, user ->
//            if (check) {
//                UserDAO().updateNhanVien(this, user!!)
//            }
//        }
    }

    private fun checkForm(
        hoten: String,
        email: String,
        diachi: String,
        sdt: String,
        quyen: Int,
        callback: (Boolean, UserModel?) -> Unit,
    ) {
        val phonePattern = "(84|0[3|5|7|8|9])+([0-9]{8,9})\\b"
        var title = ""
        var haveError = false
        if (hoten.isNullOrEmpty()) {
            title += "\nTên không được bỏ trống"
            haveError = true
        } else if (email.isNullOrEmpty()) {
            title += "\nEmail không được trống"
            haveError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            title += "\nEmail sai định dạng"
            haveError = true
        } else if (diachi.isNullOrEmpty()) {
            title += "\nĐịa chỉ không được bỏ trống"
            haveError = true
        } else if (diachi.length!! < 6) {
            title += "\nĐịa chỉ không dưới 6 ký tự"
            haveError = true
        } else if (sdt.isNullOrEmpty()) {
            for (user in arrUser) {
                if (user.sdt == sdt) {
                    title += "\n SDT đã tồn tại"
                    haveError = true
                    break
                }
            }
            title += "\nSố điện thoại không được bỏ trống"
            haveError = true

        } else if (!Pattern.compile(phonePattern).matcher(sdt).matches()) {
            title += "\nSai định dạng số điện thoại"
            haveError = true
        }


        if (haveError) {
            callback(false, null)
            FailDialog(this, "Thêm Thất Bại", title).show()
        } else {
            mUserModel.name = hoten
            mUserModel.sdt = sdt
            mUserModel.email = email
            mUserModel.diaChi = diachi
            mUserModel.type = quyen
            callback(true, mUserModel)
        }
    }
}