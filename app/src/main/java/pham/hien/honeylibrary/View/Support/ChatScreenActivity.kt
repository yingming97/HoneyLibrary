package pham.hien.honeylibrary.View.Support

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.yomaster.yogaforbeginner.View.StatusBar.StatusBarCompat
import pham.hien.honeylibrary.Model.Message
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.databinding.ActivityChatScreenBinding

class ChatScreenActivity : AppCompatActivity() {

    private lateinit var adapter: MessAdapter
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mUser: FirebaseUser
    private lateinit var lsMess: ArrayList<Message>
    private lateinit var userSend: UserModel

    private lateinit var binding: ActivityChatScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()

    }

    private fun initView(){
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        StatusBarCompat().translucentStatusBar(this, true)

        binding.btnSend.setOnClickListener{
            val nd = binding.edMess.text.toString()
            val messenger = Message(nd, mUser.email!!)
            mDatabase
                .child(userSend.firebaseId + mUser.uid)
                .push().setValue(messenger).addOnCompleteListener {
                    mDatabase
                        .child(mUser.uid + userSend.firebaseId)
                        .push().setValue(messenger).addOnCompleteListener {}
                }
            binding.edMess.text = null

        }
    }

    private fun initData(){
        userSend = intent.getSerializableExtra("chatWith") as UserModel
        binding.tvNameContact.text = userSend.name
        mUser = FirebaseAuth.getInstance().currentUser!!
        mDatabase = FirebaseDatabase.getInstance().getReference(Constant.MESSAGE.TB_NAME)
        lsMess = arrayListOf()
        adapter = MessAdapter(lsMess, mUser.email!!)
        binding.rvChatRead.adapter = adapter
        mDatabase.child(userSend.firebaseId + mUser.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lsMess.clear()
                    for (snapshot1 in snapshot.children) {
                        val messages: Message = snapshot1.getValue(Message::class.java)!!
                        lsMess.add(messages)
                    }
                    adapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }
}