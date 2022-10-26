package pham.hien.honeylibrary.View.Tab.PhieuMuon.CustomView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Dialog.ProgressBarLoading
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSachThueSua
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSachTraThieu
import pham.hien.honeylibrary.View.Tab.Sach.Activity.AddSachActivity
import pham.hien.honeylibrary.View.Tab.Sach.Activity.ChiTietSachActivity
import pham.hien.honeylibrary.View.Tab.Sach.Adapter.AdapterListSachQuanLy
import pham.hien.honeylibrary.ViewModel.Main.SachViewModel
import java.lang.Exception

class SachTraThieuView : BaseView {

    private val TAG = "YingMing"
    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var tv_save: TextView
    private lateinit var imv_close: ImageView

    private lateinit var rcv_list_sach: RecyclerView

    private lateinit var mSachAdapter: AdapterListSachTraThieu
    private var mListSachThue = ArrayList<SachThue>()
    private var map: MutableMap<Int, Int> = mutableMapOf()
    private var mListSach = ArrayList<Sach>()

    private lateinit var mSachViewModel: SachViewModel

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
        val rootView = inflater.inflate(R.layout.view_sach_tra_thieu, this)

        tool_bar = rootView.findViewById(R.id.tool_bar)
        tv_save = rootView.findViewById(R.id.tv_save)
        imv_close = rootView.findViewById(R.id.imv_close)
        rcv_list_sach = rootView.findViewById(R.id.rcv_list_sach)

        ScreenUtils().setMarginStatusBar(mContext, tool_bar)

        imv_close.setOnClickListener(this)
        tv_save.setOnClickListener(this)

        initRecycleViewSach()

    }

    override fun initViewModel(viewModel: ViewModel?) {
        mSachViewModel = viewModel as SachViewModel
    }

    override fun initObserver(owner: LifecycleOwner?) {
        mSachViewModel.mListSachLiveData.observe(owner!!) {

        }
        mSachViewModel.mListSachThuHoiLiveData.observe(owner) {

        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        mSachViewModel.getListSach()
    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = false
            initDataDefault(activity)
        }
    }


    override fun onClick(view: View) {
        super.onClick(view)
        when (view) {
            tv_save -> {

                mListener.onSave(map)
                Log.d(TAG, "onClick: ")
                closeViewGroup(this, 400)
            }
            imv_close -> {
                closeViewGroup(this, 400)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initRecycleViewSach() {
        mSachAdapter = AdapterListSachTraThieu(mContext, mListSachThue,
            themSoLuong = { maSach, soLuong ->
                map[maSach] = soLuong
            },
            giamSoLuong = { maSach, soLuong ->
                map[maSach] = soLuong
            }
        )
        rcv_list_sach.layoutManager = LinearLayoutManager(mContext)
        rcv_list_sach.setHasFixedSize(false)
        rcv_list_sach.isNestedScrollingEnabled = false
        rcv_list_sach.adapter = mSachAdapter
    }

    fun setListSachThue(lisSachThue: ArrayList<SachThue>) {
        mListSachThue = lisSachThue
        mSachAdapter.setListSachThue(lisSachThue)
    }

    private lateinit var mListener: SaveListSachTraThieu

    fun setListener(save: SaveListSachTraThieu) {
        mListener = save
    }

    interface SaveListSachTraThieu {
        fun onSave(map: MutableMap<Int, Int>)
    }
}