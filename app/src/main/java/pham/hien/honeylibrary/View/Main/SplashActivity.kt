package pham.hien.honeylibrary.View.Main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.ViewModel.SplashViewModel
import pham.yingming.honeylibrary.Dialog.FailDangNhapDialog
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.XacNhanDialog

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var imv_logo: ImageView
    private lateinit var tv_app_name: TextView
    private lateinit var progressBarSplashDefault: ProgressBar
    private lateinit var tvProgressBarSplashDefault: TextView

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var mUser: UserModel

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        imv_logo = findViewById(R.id.imv_logo)
        tv_app_name = findViewById(R.id.tv_app_name)
        progressBarSplashDefault = findViewById(R.id.progressBarSplashDefault)
        tvProgressBarSplashDefault = findViewById(R.id.tvProgressBarSplashDefault)

    }

    override fun initListener() {

    }

    override fun initViewModel() {
        this.splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun initObserver() {
        splashViewModel.loadingSplashDefaultLiveData.observe(this) {
            progressBarSplashDefault.progress = it
            tvProgressBarSplashDefault.text = "Loading ... ${(it * 100 / 200)}%"
            if (it == progressBarSplashDefault.max) {
                if (mUser.hoatDong) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    FailDangNhapDialog(this,
                        "Tài khoản của bạn đã bị vô hiệu hóa",
                        "Liên hệ với quản lý để mở tài khoản") {
                        SharedPrefUtils.setLogin(this, false)
                        SharedPrefUtils.setUserData(this, UserModel())
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }.show()
                }
            }
        }
    }

    override fun initDataDefault() {
        mUser = SharedPrefUtils.getUserData(this)!!
        UserDAO().getUser(mUser) {
            mUser = it
            splashViewModel.loadProgressBarSplashDefault()
        }
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        imv_logo.startAnimation(animation)
    }


    override fun onClick(view: View?) {

    }
}