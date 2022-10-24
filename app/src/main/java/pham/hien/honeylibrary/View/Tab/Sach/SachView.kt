package pham.hien.honeylibrary.View.Tab.Sach

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import org.w3c.dom.Text
import pham.hien.honeylibrary.Dialog.ProgressBarLoading
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter.AdapterListSach
import pham.hien.honeylibrary.View.Tab.Sach.Activity.AddSachActivity
import pham.hien.honeylibrary.View.Tab.Sach.Activity.ChiTietSachActivity
import pham.hien.honeylibrary.View.Tab.Sach.Adapter.AdapterListSachQuanLy
import pham.hien.honeylibrary.ViewModel.Main.SachViewModel

class SachView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var tv_title: TextView

    private lateinit var imv_add_new_sach: ImageView
    private lateinit var ncv_quan_ly_sach: NestedScrollView
    private lateinit var rcv_list_sach: RecyclerView
    private lateinit var layout_sach_thu_hoi: RelativeLayout
    private lateinit var rcv_list_sach_thu_hoi: RecyclerView
    private lateinit var tv_no_data: TextView

    private lateinit var mSachAdapter: AdapterListSachQuanLy
    private lateinit var mSachThuHoiAdapter: AdapterListSachQuanLy
    private var mListSach = ArrayList<Sach>()
    private var mListSachThuHoi = ArrayList<Sach>()

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
        val rootView = inflater.inflate(R.layout.view_sach, this)

        tool_bar = rootView.findViewById(R.id.tool_bar)
        tv_title = rootView.findViewById(R.id.tv_title)
        imv_add_new_sach = rootView.findViewById(R.id.imv_add_new_sach)
        ncv_quan_ly_sach = rootView.findViewById(R.id.ncv_quan_ly_sach)
        rcv_list_sach = rootView.findViewById(R.id.rcv_list_sach)
        layout_sach_thu_hoi = rootView.findViewById(R.id.layout_sach_thu_hoi)
        rcv_list_sach_thu_hoi = rootView.findViewById(R.id.rcv_list_sach_thu_hoi)
        tv_no_data = rootView.findViewById(R.id.tv_no_data)
        ScreenUtils().setMarginStatusBar(mContext, tool_bar)

        imv_add_new_sach.setOnClickListener(this)
    }

    override fun initViewModel(viewModel: ViewModel?) {
        mSachViewModel = viewModel as SachViewModel
    }

    override fun initObserver(owner: LifecycleOwner?) {
        mSachViewModel.mListSachLiveData.observe(owner!!) {
            if (it.isNotEmpty()) {
                mListSach = it
                tv_no_data.visibility = View.GONE
                mSachAdapter.setListSach(it)
                rcv_list_sach.visibility = View.VISIBLE
                ncv_quan_ly_sach.visibility = View.VISIBLE
            } else {
                tv_no_data.visibility = View.VISIBLE
                ncv_quan_ly_sach.visibility = View.VISIBLE
            }
        }
        mSachViewModel.mListSachThuHoiLiveData.observe(owner) {
            if (it.isEmpty()) {
                layout_sach_thu_hoi.visibility = View.GONE
                ncv_quan_ly_sach.visibility = View.VISIBLE
            } else {
                layout_sach_thu_hoi.visibility = View.VISIBLE
                ncv_quan_ly_sach.visibility = View.VISIBLE
                mListSachThuHoi = it
                mSachThuHoiAdapter.setListSach(it)
            }
        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        val user = SharedPrefUtils.getUserData(mContext)!!
        loadView(user)
        ncv_quan_ly_sach.visibility = View.GONE
        mSachViewModel.getListSach()
        initRecycleViewSach()
        initRecycleViewSachThuHoi()
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
            imv_add_new_sach -> {
                mContext.startActivity(Intent(mContext, AddSachActivity::class.java))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initRecycleViewSach() {
        mSachAdapter = AdapterListSachQuanLy(mContext, mListSach) {
            val intent = Intent(mContext, ChiTietSachActivity::class.java)
            intent.putExtra(Constant.SACH.SACH, it)
            mContext.startActivity(intent)
        }
        rcv_list_sach.layoutManager = LinearLayoutManager(mContext)
        rcv_list_sach.setHasFixedSize(false)
        rcv_list_sach.isNestedScrollingEnabled = false
        rcv_list_sach.adapter = mSachAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun initRecycleViewSachThuHoi() {
        mSachThuHoiAdapter = AdapterListSachQuanLy(mContext, mListSach) {
            val intent = Intent(mContext, ChiTietSachActivity::class.java)
            intent.putExtra(Constant.SACH.SACH, it)
            mContext.startActivity(intent)
        }
        rcv_list_sach_thu_hoi.layoutManager = LinearLayoutManager(mContext)
        rcv_list_sach_thu_hoi.setHasFixedSize(false)
        rcv_list_sach_thu_hoi.isNestedScrollingEnabled = false
        rcv_list_sach_thu_hoi.adapter = mSachThuHoiAdapter
    }

    private fun loadView(user: UserModel) {
        when (user.type) {
            Constant.QUYEN.ADMIN, Constant.QUYEN.THU_THU -> {
                imv_add_new_sach.visibility = View.VISIBLE
                layout_sach_thu_hoi.visibility = View.VISIBLE
            }
            else -> {
                imv_add_new_sach.visibility = View.INVISIBLE
                layout_sach_thu_hoi.visibility = View.GONE
            }
        }
    }
}