package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.ViewModel.Main.PhieuMuonViewModel
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

    private lateinit var list_sach_thue: RecyclerView
    private lateinit var imv_add_sach_thue: ImageView

    private lateinit var mPhieuMuonViewModel: PhieuMuonViewModel

    private var mListSach = ArrayList<Sach>()

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

        list_sach_thue = findViewById(R.id.list_sach_thue)
        imv_add_sach_thue = findViewById(R.id.imv_add_sach_thue)

        ScreenUtils().setMarginStatusBar(this, tool_bar)
    }

    override fun initListener() {
        imv_add_sach_thue.setOnClickListener(this)
    }

    override fun initViewModel() {
        mPhieuMuonViewModel = ViewModelProvider(this)[PhieuMuonViewModel::class.java]
    }

    override fun initObserver() {
        mPhieuMuonViewModel.mListSachLiveData.observe(this) {
            it?.let {
                mListSach = it
                Log.d(TAG, "initDataDefault: ${it.size}")
            }
        }
    }

    override fun initDataDefault() {
        mPhieuMuonViewModel.getListSach()
    }

    override fun onClick(view: View?) {
        when (view) {
            imv_add_sach_thue -> {
                 ThemSachMuonDialog(this, mListSach) {}.show()
            }
        }
    }
}