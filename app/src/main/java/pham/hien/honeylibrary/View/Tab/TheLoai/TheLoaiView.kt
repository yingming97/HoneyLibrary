package pham.hien.honeylibrary.View.Tab.TheLoai

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pham.hien.honeylibrary.FireStoreDataBase.addNewTheLoai
import pham.hien.honeylibrary.FireStoreDataBase.checkTheLoaiTrung
import pham.hien.honeylibrary.FireStoreDataBase.getListTheLoai
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseView


class TheLoaiView : BaseView {

    private lateinit var mContext: Context
    private var checkFirstLaunchView: Boolean = false

    private lateinit var btn_add_the_loai: ImageView
    private lateinit var ed_ma_the_loai: EditText
    private lateinit var ed_ten_the_loai: EditText

    private var mListTheLoai = ArrayList<TheLoai>()
    private val listTheLoaiLiveData = MutableLiveData<ArrayList<TheLoai>>()

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

        btn_add_the_loai = rootView.findViewById(R.id.btn_add_the_loai)
        ed_ma_the_loai = rootView.findViewById(R.id.ed_ma_the_loai)
        ed_ten_the_loai = rootView.findViewById(R.id.ed_ten_the_loai)

        btn_add_the_loai.setOnClickListener(this)
    }

    override fun initViewModel(viewModel: ViewModel?) {

    }

    override fun initObserver(owner: LifecycleOwner?) {
        listTheLoaiLiveData.observe(owner!!) {
            it.let {
                mListTheLoai = it
            }
        }
    }

    override fun initDataDefault(activity: Activity?) {
        super.initDataDefault(activity)
        listTheLoaiLiveData.value = getListTheLoai()
    }

    override fun openForTheFirstTime(activity: Activity?) {
        super.openForTheFirstTime(activity)
        if (!checkFirstLaunchView) {
            checkFirstLaunchView = false
            initDataDefault(activity)
        }
    }

    override fun onClick(view: View) {
        when (view) {
            btn_add_the_loai ->
                if (!checkTheLoaiTrung(ed_ma_the_loai.text.toString(), mListTheLoai)) {
                    addNewTheLoai(
                        TheLoai(
                            ed_ma_the_loai.text.toString(),
                            ed_ten_the_loai.text.toString()
                        )
                    )
                } else {
                    Toast.makeText(mContext, "trùng id", Toast.LENGTH_LONG).show()
                }
        }
    }
}