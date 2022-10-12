package pham.hien.honeylibrary.View.Tab.PhieuMuon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Activity.AddPhieuMuonActivity
import pham.yingming.honeylibrary.Dialog.XacNhanDialog

class PhieuMuonView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var imv_add_new_phieu_muon: ImageView
    private lateinit var rcv_list_qua_han: RecyclerView
    private lateinit var rcv_list_dang_muon: RecyclerView
    private lateinit var rcv_list_da_tra: RecyclerView

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

        tool_bar = rootView.findViewById(R.id.tool_bar)
        imv_add_new_phieu_muon = rootView.findViewById(R.id.imv_add_new_phieu_muon)
        rcv_list_qua_han = rootView.findViewById(R.id.rcv_list_qua_han)
        rcv_list_dang_muon = rootView.findViewById(R.id.rcv_list_dang_muon)
        rcv_list_da_tra = rootView.findViewById(R.id.rcv_list_da_tra)

        ScreenUtils().setMarginStatusBar(mContext, tool_bar)

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
//                XacNhanDialog(mContext, "Xán nhận xóa", "Dữ liệu đã xóa không thể khôi phục") {
//                    // Thực thi lệnh khi bấm nút xóa
//                    Toast.makeText(mContext, "Đã xóa", Toast.LENGTH_LONG).show()
//                }.show()
                mContext.startActivity(Intent(mContext, AddPhieuMuonActivity::class.java))
            }
        }
    }
}