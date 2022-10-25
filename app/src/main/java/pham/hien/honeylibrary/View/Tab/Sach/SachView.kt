package pham.hien.honeylibrary.View.Tab.Sach

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
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
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.Sach.Activity.AddSachActivity
import pham.hien.honeylibrary.View.Tab.Sach.Activity.ChiTietSachActivity
import pham.hien.honeylibrary.View.Tab.Sach.Adapter.AdapterListSachQuanLy
import pham.hien.honeylibrary.ViewModel.Main.SachViewModel
import java.lang.Exception

class SachView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var tv_title: TextView
    private lateinit var ed_search_sach: EditText
    private lateinit var imv_search: ImageView
    private lateinit var imv_empty: ImageView

    private lateinit var imv_add_new_sach: ImageView
    private lateinit var ncv_quan_ly_sach: NestedScrollView
    private lateinit var rcv_list_sach: RecyclerView
    private lateinit var layout_sach_thu_hoi: RelativeLayout
    private lateinit var rcv_list_sach_thu_hoi: RecyclerView
    private lateinit var tv_no_data: TextView

    private lateinit var mProgressBarLoading: ProgressBarLoading
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
        ed_search_sach = rootView.findViewById(R.id.ed_search_sach)
        imv_search = rootView.findViewById(R.id.imv_search)
        imv_empty = rootView.findViewById(R.id.imv_empty)
        imv_add_new_sach = rootView.findViewById(R.id.imv_add_new_sach)
        ncv_quan_ly_sach = rootView.findViewById(R.id.ncv_quan_ly_sach)
        rcv_list_sach = rootView.findViewById(R.id.rcv_list_sach)
        layout_sach_thu_hoi = rootView.findViewById(R.id.layout_sach_thu_hoi)
        rcv_list_sach_thu_hoi = rootView.findViewById(R.id.rcv_list_sach_thu_hoi)
        tv_no_data = rootView.findViewById(R.id.tv_no_data)

        ScreenUtils().setMarginStatusBar(mContext, tool_bar)

        imv_add_new_sach.setOnClickListener(this)
        imv_empty.setOnClickListener(this)
        initSearchListener()
    }

    override fun initViewModel(viewModel: ViewModel?) {
        mSachViewModel = viewModel as SachViewModel
    }

    override fun initObserver(owner: LifecycleOwner?) {
        mSachViewModel.mListSachLiveData.observe(owner!!) {
            if (it.isNotEmpty()) {
                mListSach = it
                tv_no_data.visibility = View.GONE
                mProgressBarLoading.hideLoading()
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
        mProgressBarLoading = ProgressBarLoading(mContext)
        mProgressBarLoading.showLoading()
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
            imv_empty -> {
                ed_search_sach.setText("")
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
    private fun initSearchListener() {
        ed_search_sach .addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
                val str = s.toString()
                val maSach = try {
                    Integer.parseInt(str)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val listFilter = ArrayList<Sach>()
                for (sach in mListSach) {
                    if (sach.maSach == maSach || sach.tenSach.contains(str, true)) {
                        listFilter.add(sach)
                    }
                }
                if (s.isEmpty()) {
                    imv_search.visibility = View.VISIBLE
                    imv_empty.visibility = View.GONE
                    tv_no_data.visibility = View.GONE
                    mSachAdapter.setListSach(mListSach)
                } else {
                    imv_empty.visibility = View.VISIBLE
                    imv_search.visibility = View.GONE
                    mSachAdapter.setListSach(listFilter)
                    if (listFilter.isEmpty()) {
                        tv_no_data.visibility = View.VISIBLE
                    } else {
                        tv_no_data.visibility = View.GONE
                    }
                }
            }
        })
    }
}