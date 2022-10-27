package pham.hien.honeylibrary.View.Support

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.yomaster.yogaforbeginner.View.StatusBar.StatusBarCompat
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.databinding.ActivitySupportBinding

class SupportActivity : AppCompatActivity() {

    lateinit var supportHomeAdapter: SupportHomeAdapter
    lateinit var binding: ActivitySupportBinding
    lateinit var mListUser: List<UserModel>

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
            mListUser = it
            val user = SharedPrefUtils.getUserData(this)
            if(user?.type == Constant.QUYEN.DOC_GIA) {
                it.filter { it.type != Constant.QUYEN.DOC_GIA }
            }
            supportHomeAdapter = SupportHomeAdapter(it,this){
                val intent = Intent(this, ChatScreenActivity::class.java)
                intent.putExtra("chatWith", it)
                startActivity(intent)
            }
            binding.rvUserChat.adapter = supportHomeAdapter
            supportHomeAdapter.notifyDataSetChanged()
        }
        initSearch()
        binding.imvEmpty.setOnClickListener{
            binding.edSearchSupport.text = null
            binding.imvEmpty.visibility = View.GONE
            binding.imvSearch.visibility = View.VISIBLE
        }
        binding.imvBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun initSearch() {
        binding.edSearchSupport.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString()
                val idUser = try {
                    Integer.parseInt(str)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val listNhanVien = ArrayList<UserModel>()
                for (user in mListUser) {
                    if (user.userId == idUser || user.name.contains(
                            str,
                            true
                        ) || user.sdt.contains(str, true) ||
                        user.email.contains(str, true)
                    ) {
                        listNhanVien.add(user)
                    }
                    if (s.isNullOrEmpty()) {
                        binding.imvSearch.visibility = View.VISIBLE
                        binding.imvEmpty.visibility = View.GONE
                        binding.tvNoData.visibility = View.GONE
                        supportHomeAdapter.searchLoad(listNhanVien)
                    } else {
                        binding.imvSearch.visibility = View.GONE
                        binding.imvEmpty.visibility = View.VISIBLE
                        supportHomeAdapter.searchLoad(listNhanVien)
                        if (listNhanVien.isEmpty()) {
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.tvNoData.visibility = View.GONE
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }



}