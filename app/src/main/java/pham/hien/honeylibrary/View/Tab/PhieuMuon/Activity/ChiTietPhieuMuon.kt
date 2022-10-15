package pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity

import android.util.Log
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Base.BaseActivity

class ChiTietPhieuMuon : BaseActivity() {

    private val TAG = "YingMing"

    private lateinit var mPhieuMuon: PhieuMuon

    override fun getLayout(): Int {
        return R.layout.acitvity_chi_tiet_phieu_muon
    }

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initViewModel() {
    }

    override fun initObserver() {
    }

    override fun initDataDefault() {
        mPhieuMuon = intent.getSerializableExtra(Constant.PHIEUMUON.PHIEUMUON) as PhieuMuon
        Log.d(TAG, "mPhieuMuon: $mPhieuMuon")
    }
}