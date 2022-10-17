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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.View.Tab.TheLoai.Dialog.ThemTheLoaiDialog
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.ViewModel.Main.TheLoaiViewModel


class TheLoaiView : BaseView {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var tv_title: TextView
    private lateinit var imv_add_new_the_loai: ImageView
    private lateinit var rcv_list_the_loai: RecyclerView

    private lateinit var mListTheLoaiViewModel: TheLoaiViewModel
    private var mListTheLoai = ArrayList<TheLoai>()
    private var mMaTheLoai = 0

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

        ScreenUtils().setMarginStatusBar(mContext, tool_bar)
        imv_add_new_the_loai.setOnClickListener(this)
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
            }
        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        mListTheLoaiViewModel.getListTheLoai()
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
                ThemTheLoaiDialog(mContext, mMaTheLoai){
                   mListTheLoaiViewModel.getListTheLoai()
                } .show()
            }
        }
    }
}