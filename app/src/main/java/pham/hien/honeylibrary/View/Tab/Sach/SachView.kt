package pham.hien.honeylibrary.View.Tab.Sach

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.android.material.appbar.AppBarLayout
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.ScreenUtils
import pham.hien.honeylibrary.View.Base.BaseView

class SachView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false

    private lateinit var tv_title: TextView

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

        tv_title = rootView.findViewById(R.id.tv_title)

        ScreenUtils().setMarginStatusBar(mContext, tv_title)
    }

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

    override fun initViewModel(viewModel: ViewModel?) {

    }

    override fun initObserver(owner: LifecycleOwner?) {

    }

    override fun onClick(view: View) {
        super.onClick(view)
        when (view) {
        }
    }

}