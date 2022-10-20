package pham.hien.honeylibrary.View.Tab.Option.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Base.BaseActivity
import java.io.Serializable

class MessageActivity : BaseActivity() {

    private lateinit var tv_title: TextView

    override fun getLayout(): Int {
        return R.layout.tro_giup_nhan_vien
    }

    override fun initView() {
        tv_title = findViewById(R.id.tv_title)
        var user: UserModel = intent.extras?.get("user") as UserModel
        tv_title.text = user.name
        Log.d("cccc", "initView: ${user.toString()}")
    }

    override fun initListener() {
    }

    override fun initViewModel() {
    }

    override fun initObserver() {
    }

    override fun initDataDefault() {
    }

}