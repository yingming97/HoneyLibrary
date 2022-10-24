package pham.hien.honeylibrary.View.Main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity
import pham.hien.honeylibrary.ViewModel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var imv_logo: ImageView
    private lateinit var tv_app_name: TextView
    private lateinit var progressBarSplashDefault: ProgressBar
    private lateinit var tvProgressBarSplashDefault: TextView

    private lateinit var splashViewModel: SplashViewModel


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
        splashViewModel.loadProgressBarSplashDefault()
        splashViewModel.loadingSplashDefaultLiveData.observe(this) {
            progressBarSplashDefault.progress = it
            tvProgressBarSplashDefault.text = "Loading ... ${(it * 100 / 200)}%"
            if (it == progressBarSplashDefault.max) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun initDataDefault() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        imv_logo.startAnimation(animation)
    }


    override fun onClick(view: View?) {

    }
}