package pham.hien.honeylibrary.View.Tab.TheLoai

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.View.Tab.TheLoai.Dialog.ThemTheLoaiDialog
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.TheLoai.Adapter.AdapterListTheLoai
import pham.hien.honeylibrary.View.Tab.TheLoai.Dialog.SuaTheLoaiDialog
import pham.hien.honeylibrary.ViewModel.Main.TheLoaiViewModel


class TheLoaiView : BaseView {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var tv_title: TextView
    private lateinit var imv_add_new_the_loai: ImageView
    private lateinit var rcv_list_the_loai: RecyclerView
    private lateinit var imv_search_view: android.widget.SearchView

    private lateinit var mListTheLoaiViewModel: TheLoaiViewModel
    private var mListTheLoai = ArrayList<TheLoai>()
    private var mMaTheLoai = 0
    private lateinit var mUser: UserModel
    private var mListTenTheLoai = ArrayList<String>()

    private lateinit var mTheLoaiAdapter: AdapterListTheLoai

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
        val rootView: View = inflater.inflate(R.layout.view_the_loai, this)

        tool_bar = rootView.findViewById(R.id.tool_bar)
        tv_title = rootView.findViewById(R.id.tv_title)
        imv_add_new_the_loai = rootView.findViewById(R.id.imv_add_new_the_loai)
        rcv_list_the_loai = rootView.findViewById(R.id.rcv_list_the_loai)
        imv_search_view = rootView.findViewById(R.id.imv_search)

        ScreenUtils().setMarginStatusBar(mContext, tool_bar)
        imv_add_new_the_loai.setOnClickListener(this)

        initRecycleViewTheLoai()
        initSearchView()
    }

    private fun initSearchView() {
        imv_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mTheLoaiAdapter.filter.filter(query)
                tv_title.visibility = View.INVISIBLE
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mTheLoaiAdapter.filter.filter(newText)
                tv_title.visibility = View.INVISIBLE
                return true
            }

        })


    }

    override fun initViewModel(viewModel: ViewModel?) {
        mListTheLoaiViewModel = viewModel as TheLoaiViewModel
    }

    override fun initObserver(owner: LifecycleOwner?) {
        mListTheLoaiViewModel.mListTheLoaiLiveData.observe(owner!!) {
            if (it.isNotEmpty()) {
                mListTheLoai = it
                mMaTheLoai = it.last().maTheLoai + 1
                Log.d("TAG", "initObserver: " + it.last().maTheLoai)
                mTheLoaiAdapter.setList(it)
                for (theLoai in mListTheLoai) {
                    mListTenTheLoai.add(theLoai.tenTheLoai)
                }
            }
        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        mListTheLoaiViewModel.getListTheLoai()
        mUser = SharedPrefUtils.getUserData(mContext)!!
        loadView(mUser)
    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        mActivity = activity!!
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = false
            initDataDefault(activity)
        }
    }

    override fun onClick(view: View) {
        when (view) {
            imv_add_new_the_loai -> {
                ThemTheLoaiDialog(mContext, mMaTheLoai, mListTheLoai) {
                    Log.d("gggg", "onClick: ${mListTheLoai}}")
                    mListTheLoaiViewModel.getListTheLoai()
                }.show()
            }
        }
    }

    private fun initRecycleViewTheLoai() {
        mTheLoaiAdapter = AdapterListTheLoai(mContext, mListTheLoai) {
            SuaTheLoaiDialog(mContext, it, mListTheLoai) {
                mListTheLoaiViewModel.getListTheLoai()
            }.show()
        }
        rcv_list_the_loai.layoutManager = LinearLayoutManager(mContext)
        rcv_list_the_loai.setHasFixedSize(false)
        rcv_list_the_loai.isNestedScrollingEnabled = false
        rcv_list_the_loai.adapter = mTheLoaiAdapter
    }

    private fun loadView(user: UserModel) {
        when (user.type) {
            Constant.QUYEN.ADMIN, Constant.QUYEN.THU_THU -> {
                imv_add_new_the_loai.visibility = View.VISIBLE
            }
            else -> {
                imv_add_new_the_loai.visibility = View.GONE
            }
        }
    }

}