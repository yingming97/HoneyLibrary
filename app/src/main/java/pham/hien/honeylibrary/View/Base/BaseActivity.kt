package pham.hien.honeylibrary.View.Base

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import pham.hien.honeylibrary.R

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {


    var PACKAGE_NAME: String? = null
    private var currentApiVersion = 0
    private var currentLanguage = ""

    protected abstract fun getLayout(): Int

    protected abstract fun initView()

    protected abstract fun initListener()

    protected abstract fun initViewModel()

    protected abstract fun initObserver()

    protected abstract fun initDataDefault()

    open fun initInstanceState(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstanceState(savedInstanceState)

        setContentView(getLayout())
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        currentApiVersion = Build.VERSION.SDK_INT
        PACKAGE_NAME = applicationContext.packageName
        initView()
        initListener()
        initViewModel()
        initObserver()
        initDataDefault()
    }

    open fun overridePendingTransition() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition()
    }


    companion object {

        fun getActivity(context: Context?): Activity? {
            if (context == null) {
                return null
            } else if (context is ContextWrapper) {
                return if (context is Activity) {
                    context as Activity?
                } else {
                    getActivity((context as ContextWrapper).baseContext)
                }
            }
            return null
        }
    }
}


