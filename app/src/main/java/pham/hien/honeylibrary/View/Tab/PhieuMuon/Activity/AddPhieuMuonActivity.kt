package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.FireBase.Storage.Images
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.yingming.honeylibrary.Dialog.ThemSachMuonDialog

class AddPhieuMuonActivity : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var tool_bar: RelativeLayout
    private lateinit var ed_sdt_email: EditText

    private lateinit var layout_info_phieu: RelativeLayout
    private lateinit var imv_avatar: ImageView
    private lateinit var tv_name: TextView
    private lateinit var tv_tong_sach: TextView
    private lateinit var tv_thanh_tien: TextView
    private lateinit var tv_ngay_muon: TextView
    private lateinit var tv_han_tra: TextView
    private lateinit var tv_them_phieu: TextView

    private lateinit var list_sach_thue: RecyclerView
    private lateinit var imv_add_sach_thue: ImageView

    private var mListSach = ArrayList<Sach>()
    private var mListUser = ArrayList<UserModel>()
    private var mListSachLiveData = MutableLiveData<ArrayList<Sach>>()
    private var mListUserLiveData = MutableLiveData<ArrayList<UserModel>>()

    override fun getLayout(): Int {
        return R.layout.acitvity_add_phieu_muon
    }

    override fun initView() {
        tool_bar = findViewById(R.id.tool_bar)
        ed_sdt_email = findViewById(R.id.ed_sdt_email)

        layout_info_phieu = findViewById(R.id.layout_info_phieu)
        imv_avatar = findViewById(R.id.imv_avatar)
        tv_name = findViewById(R.id.tv_name)
        tv_tong_sach = findViewById(R.id.tv_tong_sach)
        tv_thanh_tien = findViewById(R.id.tv_thanh_tien)
        tv_ngay_muon = findViewById(R.id.tv_ngay_muon)
        tv_han_tra = findViewById(R.id.tv_han_tra)
        tv_them_phieu = findViewById(R.id.tv_them_phieu)

        list_sach_thue = findViewById(R.id.list_sach_thue)
        imv_add_sach_thue = findViewById(R.id.imv_add_sach_thue)

        ScreenUtils().setMarginStatusBar(this, tool_bar)
    }

    override fun initListener() {
        imv_add_sach_thue.setOnClickListener(this)
        tv_them_phieu.setOnClickListener(this)
    }

    override fun initViewModel() {
    }

    override fun initObserver() {
        mListSachLiveData.observe(this) {
            if (it.isNotEmpty()) {
                mListSach = it
            }
        }
        mListUserLiveData.observe(this) {
            if (it.isNotEmpty()) {
                for (user in it) {
                    if (user.type == 0) {
                        mListUser.add(user)
                    }
                }
            }
        }
    }

    override fun initDataDefault() {
        mListSachLiveData.postValue(SachDAO().getListSach())
        mListUserLiveData.postValue(UserDAO().getListUser())

        Log.d(TAG, "UserModel: ${UserModel(1,"abc",2,"name","email","123455","HD")}")
        Log.d(TAG, "Sach: ${Sach(1,"ténach",1,23,35,56,"giơiThiwk")}")
    }

    override fun onClick(view: View?) {
        when (view) {
            imv_add_sach_thue -> {
                ThemSachMuonDialog(this, mListSach) {}.show()
            }
            tv_them_phieu -> {

            }
        }
    }
}