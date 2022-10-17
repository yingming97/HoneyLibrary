package pham.hien.honeylibrary.View.Tab.Sach

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import org.w3c.dom.Text
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseView
import pham.hien.honeylibrary.ViewModel.Main.SachViewModel

class SachView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tool_bar: RelativeLayout
    private lateinit var tv_title: TextView

    private lateinit var imv_add_new_sach: ImageView
    private lateinit var rcv_list_sach: RecyclerView
    private lateinit var tv_no_data: TextView
    private lateinit var pg_load_sach: ProgressBar

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
        val rootView = inflater.inflate(R.layout.view_sach, this)

        tool_bar = rootView.findViewById(R.id.tool_bar)
        tv_title = rootView.findViewById(R.id.tv_title)
        imv_add_new_sach = rootView.findViewById(R.id.imv_add_new_sach)
        rcv_list_sach = rootView.findViewById(R.id.rcv_list_sach)
        tv_no_data = rootView.findViewById(R.id.tv_no_data)
        pg_load_sach = rootView.findViewById(R.id.pg_load_sach)

        ScreenUtils().setMarginStatusBar(mContext, tool_bar)
    }

    override fun initViewModel(viewModel: ViewModel?) {
        mSachViewModel = viewModel as SachViewModel
    }

    override fun initObserver(owner: LifecycleOwner?) {
        mSachViewModel.mListSachLiveData.observe(owner!!) {
            mListSach = it
        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        mSachViewModel.getListSach()
    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = true
            initDataDefault(activity)
        }
    }


    override fun onClick(view: View) {
        super.onClick(view)
        when (view) {
        }
    }

}