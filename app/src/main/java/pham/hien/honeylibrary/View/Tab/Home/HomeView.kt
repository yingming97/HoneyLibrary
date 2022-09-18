package pham.hien.honeylibrary.View.Tab.Home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseView


class HomeView : BaseView {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity
    private var checkFirstLaunchView: Boolean = false

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
        val rootView: View = inflater.inflate(R.layout.view_home, this)

    }


    override fun initViewModel(viewModel: ViewModel?) {
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun initObserver(owner: LifecycleOwner?) {

    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)

    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        mActivity = activity!!
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = true
            initDataDefault(activity)
        }
    }

    override fun onClick(view: View) {
        when (view) {
        }
    }
}