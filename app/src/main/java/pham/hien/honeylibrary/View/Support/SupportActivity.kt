package pham.hien.honeylibrary.View.Support

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.yomaster.yogaforbeginner.View.StatusBar.StatusBarCompat
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.databinding.ActivitySupportBinding

class SupportActivity : AppCompatActivity() {

    lateinit var supportHomeAdapter: SupportHomeAdapter
    lateinit var binding: ActivitySupportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        StatusBarCompat().translucentStatusBar(this, true)
        initData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData(){
        UserDAO().getListUser { it ->
            binding.progressbarLoad.visibility = View.GONE
            val user = SharedPrefUtils.getUserData(this)
            val arrContact = it
            if(user?.type == Constant.QUYEN.DOC_GIA){
                arrContact.filter { it.type != Constant.QUYEN.DOC_GIA}
            }
            supportHomeAdapter = SupportHomeAdapter(it,this){
                val intent = Intent(this, ChatScreenActivity::class.java)
                intent.putExtra("chatWith", it)
                startActivity(intent)
            }
            binding.rvUserChat.adapter = supportHomeAdapter
            supportHomeAdapter.notifyDataSetChanged()
        }
    }
}