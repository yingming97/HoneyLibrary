package pham.hien.honeylibrary.View.Tab.TheLoai

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Dialog.ProgressBarLoading
import pham.hien.honeylibrary.View.Tab.TheLoai.Dialog.ThemTheLoaiDialog
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.KeyBoardUtils
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.View.Tab.TheLoai.Adapter.AdapterListTheLoai
import pham.hien.honeylibrary.View.Tab.TheLoai.Dialog.SuaTheLoaiDialog
import pham.hien.honeylibrary.ViewModel.Main.TheLoaiViewModel
import java.lang.Exception


class TheLoaiView : BaseView {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var tv_title: TextView
    private lateinit var tv_no_data: TextView
    private lateinit var imv_add_new_the_loai: ImageView
    private lateinit var imv_empty: ImageView
    private lateinit var imv_search: ImageView
    private lateinit var ed_search_the_loai: EditText
    private lateinit var rcv_list_the_loai: RecyclerView

    private lateinit var mProgressBarLoading: ProgressBarLoading

    private var mMaTheLoai = 0
    private lateinit var mUser: UserModel
    private lateinit var mTheLoaiAdapter: AdapterListTheLoai
    private var mListTheLoai = ArrayList<TheLoai>()

    private lateinit var mListTheLoaiViewModel: TheLoaiViewModel

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
        tv_no_data = rootView.findViewById(R.id.tv_no_data)
        ed_search_the_loai = rootView.findViewById(R.id.ed_search_the_loai)
        imv_empty = rootView.findViewById(R.id.imv_empty)
        imv_search = rootView.findViewById(R.id.imv_search)
        imv_add_new_the_loai = rootView.findViewById(R.id.imv_add_new_the_loai)
        rcv_list_the_loai = rootView.findViewById(R.id.rcv_list_the_loai)

        ScreenUtils().setMarginStatusBar(mContext, tool_bar)

        imv_add_new_the_loai.setOnClickListener(this)
        imv_empty.setOnClickListener(this)

        initRecycleViewTheLoai()
    }

    override fun initViewModel(viewModel: ViewModel?) {
        mListTheLoaiViewModel = viewModel as TheLoaiViewModel
    }

    override fun initObserver(owner: LifecycleOwner?) {
        mListTheLoaiViewModel.mListTheLoaiLiveData.observe(owner!!) {
            mListTheLoai = it
            mMaTheLoai = it.last().maTheLoai + 1
            mTheLoaiAdapter.setList(it)
            mProgressBarLoading.hideLoading()
            rcv_list_the_loai.visibility = View.VISIBLE
        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        mProgressBarLoading = ProgressBarLoading(mContext)
        mProgressBarLoading.showLoading()
        rcv_list_the_loai.visibility = View.GONE
        mListTheLoaiViewModel.getListTheLoai()
        mUser = SharedPrefUtils.getUserData(mContext)!!
        loadView(mUser)
        initSearchListener()
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
                    mListTheLoaiViewModel.getListTheLoai()
                }.show()
            }
            imv_empty -> {
                ed_search_the_loai.setText("")
            }
        }
    }

    private fun initRecycleViewTheLoai() {
        mTheLoaiAdapter = AdapterListTheLoai(mContext, mListTheLoai) {
            SuaTheLoaiDialog(mContext, it, mListTheLoai) {
                mListTheLoaiViewModel.getListTheLoai()
                KeyBoardUtils.hideKeyboard(mContext as Activity)
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
                imv_add_new_the_loai.visibility = View.INVISIBLE
            }
        }
    }

    private fun initSearchListener() {
        ed_search_the_loai.addTextChangedListener(object : TextWatcher {
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
                val maTheLoai = try {
                    Integer.parseInt(str)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val listFilter = ArrayList<TheLoai>()
                for (theLoai in mListTheLoai) {
                    if (theLoai.maTheLoai == maTheLoai || theLoai.tenTheLoai.contains(str, true)) {
                        listFilter.add(theLoai)
                    }
                }
                if (s.isEmpty()) {
                    imv_search.visibility = View.VISIBLE
                    imv_empty.visibility = View.GONE
                    tv_no_data.visibility = View.GONE
                    mTheLoaiAdapter.setList(mListTheLoai)
                } else {
                    imv_empty.visibility = View.VISIBLE
                    imv_search.visibility = View.GONE
                    mTheLoaiAdapter.setList(listFilter)
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