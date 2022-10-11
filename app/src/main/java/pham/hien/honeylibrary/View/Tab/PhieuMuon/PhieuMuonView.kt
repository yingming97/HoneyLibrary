package pham.hien.honeylibrary.View.Tab.PhieuMuon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.yingming.honeylibrary.Dialog.XacNhanDialog

class PhieuMuonView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tv_title: TextView
    private lateinit var imv_add_new_phieu_muon: ImageView

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
        val rootView: View = inflater.inflate(R.layout.view_phieu_muon, this)

        tv_title = rootView.findViewById(R.id.tv_title)
        imv_add_new_phieu_muon = rootView.findViewById(R.id.imv_add_new_phieu_muon)

        imv_add_new_phieu_muon.setOnClickListener(this)
    }


    override fun initViewModel(viewModel: ViewModel?) {
    }


    @SuppressLint("SetTextI18n")
    override fun initObserver(owner: LifecycleOwner?) {
    }

    @SuppressLint("SetTextI18n")
    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = true
            initDataDefault(activity)
        }
    }

    override fun onClick(view: View) {
        when (view) {
            imv_add_new_phieu_muon -> {
                XacNhanDialog(mContext, "Xán nhận xóa", "Dữ liệu đã xóa không thể khôi phục") {
                    Toast.makeText(mContext, "Đã xóa", Toast.LENGTH_LONG).show()
                }.show()
            }
        }
    }
}